package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public class InventoryManager {

    public static ArrayList<InventoryItem> getInventoryItems() {
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
}