package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.users.User;
import dbbg2.utils.persistence.JpaPersistence;
import jdk.jfr.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public static ArrayList<InventoryItem> getInventoryItem() {
        ArrayList<InventoryItem> output = new ArrayList<>();

        output.add(new Book("The Hobbit", ItemCategoryType.OTHER_BOOKS, true,"123", "JRR Tolkien"));
        output.add(new Book("Harry Potter",ItemCategoryType.OTHER_BOOKS, true,"123", "JK Rowling"));
        output.add(new Book("Emil",ItemCategoryType.OTHER_BOOKS, true,"123", "Astrid Lindgren"));
        output.add(new Book("Dexter",ItemCategoryType.OTHER_BOOKS, true,"123", "Jeff Lindsay"));
        output.add(new Film("Avatar", ItemCategoryType.FILM, true, "nobody", 18, "US"));
//String title, ItemCategoryType category, boolean isAvailable, String director, int ageLimit, String originCountry

        return output;
    }

    public static ArrayList<InventoryCopy> getInventoryCopy() {
        ArrayList<InventoryCopy> invCopy = new ArrayList<>();
        return invCopy;
    }
    public static InventoryCopy getInventoryCopy(String barCode) throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryCopy> q = em.createQuery("SELECT ic FROM InventoryCopy ic WHERE ic.barcode =:barcode", InventoryCopy.class);

        return q.setParameter("barcode", barCode).getSingleResult();


    }

    public static List<InventoryItem> getInventoryItems(String title, String inventoryId){
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryItem> q = em.createQuery("select i from Inventory i where (i.title = :title or :title = '') " +
                "and (i.inventoryId = :inventoryId or :inventoryId = '') ", InventoryItem.class);

        q.setParameter("title", title);
        q.setParameter("inventoryId", inventoryId);
        return q.getResultList();
    }

    public static List<InventoryItem> getInventoryItems(String inventoryId, String title, String keyword, String category){
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryItem> q = em.createQuery("SELECT i FROM Inventory i " +
                "WHERE (i.inventoryId = :inventoryId or :inventoryId = '') " +
                        "AND (i.title = :title or :title = '') " +
                        "AND (i.keyword = :keyword or :keyword = '') " +
                        "AND (i.category = :category or :category = '') ", InventoryItem.class);



        q.setParameter("inventoryId", inventoryId);
        q.setParameter("title", title);
        q.setParameter("keyword", keyword);
        q.setParameter("category", category);

        return q.getResultList();
    }

    public static List<Book> getBooks(String title, String inventoryId){
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Book> q = em.createQuery("select b from Book b " +
                        "WHERE (b.title = :title or :title = '') " +
                        "AND (b.inventoryId = :inventoryId or :inventoryId = '') "
                        //"AND (b.category = :category or :category = '')"
                , Book.class);

        q.setParameter("title", title);
        q.setParameter("inventoryId", inventoryId);
        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getResultList();
    }

    public void deleteBook(String title, int invId){
        EntityManager em = JpaPersistence.getEntityManager();
        try {
            em.getTransaction().begin();
            InventoryItem remItem = null;
            try {
                remItem = em.getReference(InventoryItem.class, invId);
                remItem.getInventoryId();
            } catch (EntityNotFoundException enfe) {

            }
            em.remove(remItem);
            em.getTransaction().commit();
        } finally {
            if(em != null) {
                em.close();
            }
        }

    }

   /* public void deleteBook(String title, String inventoryId){
        EntityManager em = JpaPersistence.getEntityManager();

        em.getTransaction().begin();

        TypedQuery<InventoryManager> q = em.createQuery("DELETE FROM Inventory i WHERE i.inventoryId = :inventoryId and i.title = :title", InventoryManager.class);

        q.setParameter("title", title);
        q.setParameter("inventoryId", inventoryId);

        q.executeUpdate();

    }*/


}