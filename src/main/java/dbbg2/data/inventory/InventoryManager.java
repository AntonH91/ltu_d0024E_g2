package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.users.User;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
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

}