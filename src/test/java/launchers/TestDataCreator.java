package launchers;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.users.Employee;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class TestDataCreator {

    public static void main(String[] args) {
        createAllTestData();
    }

    public static void createAllTestData() {
        EntityManager em = JpaPersistence.getEntityManager();


        em.getTransaction().begin();

        try {
            clearDatabase(em);
        } catch (Exception e) {
            System.out.println("Failed to clear database, with error: ");
            e.printStackTrace();
        }


        createVisitorCategories(em);
        createItemCategories(em);
        createUsers(em);
        createInventory(em);

        em.getTransaction().commit();
        JpaPersistence.disconnect();
    }

    private static void createVisitorCategories(EntityManager em) {

        em.persist(new VisitorCategory("General Public", 3));
        em.persist(new VisitorCategory("Student", 5));
        em.persist(new VisitorCategory("University Staff", 20));
        em.persist(new VisitorCategory("Researcher", 10));

    }

    private static void createItemCategories(EntityManager em) {
        em.persist(new ItemCategory("Film", 7, true));
        em.persist(new ItemCategory("Journal", 0, false));
        em.persist(new ItemCategory("Other Books", 30, true));
        em.persist(new ItemCategory("Reference Literature", 0, false));
        em.persist(new ItemCategory("Student literature", 14, true));
    }

    private static void createUsers(EntityManager em) {

        List<VisitorCategory> categories = em.createQuery("SELECT vc FROM VisitorCategory vc", VisitorCategory.class).getResultList();

        Employee e = new Employee();
        e.setFirstName("Jeff");
        e.setLastName("Geofferson");
        e.setPersonNr("123");
        e.setEmail("a@b.c");
        e.setPassword("password1");
        em.persist(e);

        Visitor v = new Visitor(categories.get(0));
        v.setFirstName("Abe");
        v.setLastName("Lincoln");
        v.setPersonNr("321");
        v.setEmail("abe@example.com");
        v.setPassword("password2");
        em.persist(v);

        v = new Visitor(categories.get(1));
        v.setFirstName("Albert");
        v.setLastName("Einstein");
        v.setPersonNr("321");
        v.setEmail("aeinstein@example.com");
        v.setPassword("password3");
        em.persist(v);


        v = new Visitor(categories.get(3));
        v.setFirstName("Bruce");
        v.setLastName("Springsteen");
        v.setPersonNr("321");
        v.setEmail("ssteen@example.com");
        v.setPassword("password4");
        em.persist(v);
    }


    private static void createInventory(EntityManager em) {
        List<Book> books = new ArrayList<>();
        List<ItemCategory> categories = em.createQuery("SELECT ic FROM ItemCategory ic", ItemCategory.class).getResultList();

        String[] keywords = {"Action", "Adventure", "Studying", "Science", "Weird", "Story", "Fantasy", "Egg", "Thriller"};

        books.add(new Book("The Hobbit", categories.get(1), true, "123", "JRR Tolkien"));
        books.add(new Book("Harry Potter", categories.get(1), true, "123", "JK Rowling"));
        books.add(new Book("Emil", categories.get(1), true, "123", "Astrid Lindgren"));
        books.add(new Book("Dexter", categories.get(3), true, "123", "Jeff Lindsay"));

        int index = 0;

        for (Book b : books) {
            b.addCopy(String.valueOf(index), "A Shelf");
            index++;
            em.merge(b);
        }

    }

    private static void addRandomKeywords(InventoryItem i, String[] keywords) {

        /*
        int length = keywords.length;

        Random rng = new Random();

        int keywordAmount = rng.nextInt(length/2) + 1;
        keywordAmount = Math.min(keywordAmount, length);



        while(keywordAmount-- > 0) {
            Keyword kw = new Keyword();
            kw.setKeyword(keywords[rng.nextInt(length)]);
            i.getKeyword().add(kw);
        }
        */


    }


    private static void clearDatabase(EntityManager em) {

        String[] queries = {
                "DELETE FROM loancopies;",
                "DELETE FROM Loan;",
                "DELETE FROM Inventory_keyword;",
                "DELETE FROM InventoryCopy;",
                "DELETE FROM book;",
                "DELETE FROM film;",
                "DELETE FROM inventory;",
                "DELETE FROM itemcategory;",
                "DELETE FROM keyword;",
                "DELETE FROM visitor;",
                "DELETE FROM employees;",
                "DELETE FROM users;",
                "DELETE FROM itemcategory;",
                "DELETE FROM visitorcategory"
        };

        runNativeQueries(em, queries);

    }

    private static void dropAndRemakeDatabase(EntityManager em) {
        runNativeQueries(em, "DROP DATABASE library_dbb_jpa;", "CREATE DATABASE library_dbb_jpa;");
    }


    private static void runNativeQueries(EntityManager em, String... queries) {
        for (String query : queries) {
            Query q = em.createNativeQuery(query);
            q.executeUpdate();
        }
    }


}
