package dbbg2.controllers;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategoryType;
import dbbg2.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Program entrypoint
 */
public class LibraryDbb {
    public static void main(String[] args) {

        testUsers();
        testInventory();

    }

    private static void testInventory(){
        EntityManager em = JpaPersistence.getEntityManager();


        Query q = em.createQuery("SELECT invItem FROM Inventory invItem");
        List inventoryItemList = q.getResultList();

        for (Object o: inventoryItemList) {
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
        Query q = em.createQuery("SELECT u FROM Users u");
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
        em.merge(e);

        Visitor v = new Visitor(VisitorCategoryType.GENERAL_PUBLIC);
        v.setFirstName("Abe");
        v.setLastName("Lincoln");
        v.setPersonNr("321");
        v.setEmail("abe@example.com");
        em.merge(v);

        v = new Visitor(VisitorCategoryType.RESEARCHER);
        v.setFirstName("Albert");
        v.setLastName("Einstein");
        v.setPersonNr("321");
        v.setEmail("aeinstein@example.com");
        em.merge(v);


        v = new Visitor(VisitorCategoryType.UNIVERSITY_STAFF);
        v.setFirstName("Bruce");
        v.setLastName("Springsteen");
        v.setPersonNr("321");
        v.setEmail("ssteen@example.com");
        em.merge(v);

        em.getTransaction().commit();

        em.close();
    }
}
