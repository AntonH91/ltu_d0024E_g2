package launchers;

import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategoryType;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Program entrypoint
 */
public class LibraryDbb {
    public static void main(String[] args) {

        testUsers();
        testInventory();
        testUserRetrieval();
        createBooks();

        JpaPersistence.disconnect();
    }

    private static void createBooks() {
        List<Book> books = new ArrayList<>();


        books.add(new Book("The Hobbit", ItemCategoryType.OTHER_BOOKS, true, "123", "JRR Tolkien"));
        books.add(new Book("Harry Potter", ItemCategoryType.OTHER_BOOKS, true, "123", "JK Rowling"));
        books.add(new Book("Emil", ItemCategoryType.OTHER_BOOKS, true, "123", "Astrid Lindgren"));
        books.add(new Book("Dexter", ItemCategoryType.OTHER_BOOKS, true, "123", "Jeff Lindsay"));

        int index = 0;
        EntityManager em = JpaPersistence.getEntityManager();

        em.getTransaction().begin();
        for (Book b : books) {
            b.addCopy(String.valueOf(index), "A Shelf");
            index++;
            em.merge(b);
        }

        em.getTransaction().commit();

    }


    private static void testUserRetrieval() {

        try {
            User u = UserManager.getAuthenticatedUser("bspr9817", "password4");
            System.out.println(u);
        } catch (LibraryEntityNotFoundException e) {
            System.out.println("Could not find user!");
        }

    }


    private static void testInventory() {
        EntityManager em = JpaPersistence.getEntityManager();


        TypedQuery<InventoryItem> q = em.createQuery("SELECT invItem FROM Inventory invItem", InventoryItem.class);
        List<InventoryItem> inventoryItemList = q.getResultList();

        for (Object o : inventoryItemList) {
            System.out.println(o);
        }
        System.out.println("Size: " + inventoryItemList.size());

        // New InventoryItem

        em.getTransaction().begin();
        Book b = new Book("Harry potter", ItemCategoryType.OTHER_BOOKS, true, "15682", "JK Rowling");
        b.getCopies().add(new InventoryCopy("474", "shelf", true, b));

        em.merge(b);
        em.getTransaction().commit();

    }


    private static void testUsers() {
        EntityManager em = JpaPersistence.getEntityManager();

        // Read existing
        TypedQuery<User> q = em.createQuery("SELECT u FROM Users u", User.class);
        List<User> userList = q.getResultList();

        for (User u : userList) {
            System.out.println(u.toString());
        }
        System.out.println("Size: " + userList.size());

        // New user

        em.getTransaction().begin();
        /*User u = new User();

        u.setFirstName("Anton");
        u.setLastName("Högelin");
        //u.setPersonNr("123");
        u.setEmail("b@c.d");
        em.merge(u);*/

        Employee e = new Employee();
        e.setFirstName("Jeff");
        e.setLastName("Geofferson");
        e.setPersonNr("123");
        e.setEmail("a@b.c");
        e.setPassword("password1");
        em.merge(e);

        Visitor v = new Visitor(VisitorCategoryType.GENERAL_PUBLIC);
        v.setFirstName("Abe");
        v.setLastName("Lincoln");
        v.setPersonNr("321");
        v.setEmail("abe@example.com");
        v.setPassword("password2");
        em.merge(v);

        v = new Visitor(VisitorCategoryType.RESEARCHER);
        v.setFirstName("Albert");
        v.setLastName("Einstein");
        v.setPersonNr("321");
        v.setEmail("aeinstein@example.com");
        v.setPassword("password3");
        em.merge(v);


        v = new Visitor(VisitorCategoryType.UNIVERSITY_STAFF);
        v.setFirstName("Bruce");
        v.setLastName("Springsteen");
        v.setPersonNr("321");
        v.setEmail("ssteen@example.com");
        v.setPassword("password4");
        em.merge(v);

        em.getTransaction().commit();

        em.close();
    }
}
