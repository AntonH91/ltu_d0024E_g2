package dbbg2.data.inventory;

import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {


    public static ArrayList<InventoryCopy> getInventoryCopy() {
        ArrayList<InventoryCopy> invCopy = new ArrayList<>();
        return invCopy;
    }

    /**
     * Fetches an inventorycopy with the given barcode.
     *
     * @param barCode The barcode to search for
     * @return The found inventorycopy
     * @throws LibraryEntityNotFoundException Thrown if there is no matching InventoryCopy
     */
    public static InventoryCopy getInventoryCopy(String barCode) throws LibraryEntityNotFoundException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryCopy> q = em.createQuery("SELECT ic FROM InventoryCopy ic WHERE ic.barcode =:barcode", InventoryCopy.class);

        try {
            return q.setParameter("barcode", barCode).getSingleResult();
        } catch (NoResultException e) {
            throw new LibraryEntityNotFoundException(String.format("Could not find Inventory Copy with barcode: %s", barCode));
        }


    }

    public static List<InventoryItem> getInventoryItems(String title) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryItem> q = em.createQuery("select i from Inventory i " +
                "WHERE (i.title = :title or :title = '') "
                , InventoryItem.class);

        q.setParameter("title", title);
        return q.getResultList();
    }


    public static List<Book> getBooks(String title, String authors) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Book> q = em.createQuery("select b from Book b " +
                        "WHERE (b.title = :title or :title = '') " +
                        "AND (b.authors LIKE CONCAT ('%', :authors, '%') or :authors = '') "
                //"AND (b.category = :category or :category = '')"
                , Book.class);

        //c.place like %:place%")

        q.setParameter("title", title);
        q.setParameter("authors", authors);
        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getResultList();
    }

    public static List<Film> getFilms(String title) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Film> q = em.createQuery("select f from Film f " +
                        "WHERE (f.title = :title or :title = '') "
                //"AND (b.category = :category or :category = '')"
                , Film.class);

        q.setParameter("title", title);
        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getResultList();
    }

    public static List<Film> getFilms(String title, String originCountry, String director) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Film> q = em.createQuery("select f from Film f " +
                        "WHERE (f.title = :title or :title = '') " +
                        "AND (f.originCountry = :originCountry or :originCountry = '') " +
                        "AND (f.director = :director or :director = '') "


                //"AND (b.category = :category or :category = '')"
                , Film.class);

        q.setParameter("title", title);
        q.setParameter("originCountry", originCountry);
        q.setParameter("director", director);

        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getResultList();
    }


    public static InventoryItem getItemCopy(int invId) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryItem> q = em.createQuery("select ii from Inventory ii " +
                        "WHERE (ii.invId = :invId) "
                        //"AND (ii.invId = :invId) "
                //"AND (b.category = :category or :category = '')"
                , InventoryItem.class);

        //q.setParameter("title", title);
        q.setParameter("invId", invId);
        //q.setParameter("inventoryId", inventoryId);
        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getSingleResult();
    }


    public static List<InventoryItem> searchBooks(String title, String inventoryId) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryItem> q = em.createQuery("SELECT i FROM Inventory i LEFT JOIN i.keywords ik JOIN ik.items iki " +
                        "WHERE (i.title = :title or :title = '') " +
                        "AND (i.inventoryId = :inventoryId or :inventoryId = '') " +
                        "AND (i.author = author: or author = ''"
                //"AND (i.keywords = :keywords or :keywords = '') "
                //"AND (b.category = :category or :category = '')"
                , InventoryItem.class);

        q.setParameter("title", title);
        q.setParameter("inventoryId", inventoryId);
        //q.setParameter("keywords", keywords);
        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getResultList();
    }


    public static List<InventoryCopy> test2GetItemTitleCopies(String title) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<InventoryCopy> q = em.createQuery("SELECT ic FROM InventoryCopy ic LEFT JOIN ic.item ii " +
                        "WHERE (ii.title = :title or :title = '') "


                //" WHERE  ic.Inventory_ID = :invId"
                //"AND (ii.title = :title or :title = '') "
                //"AND (ii.invId = :invId) "
                //"AND (b.category = :category or :category = '')"
                , InventoryCopy.class);

        //q.setParameter("barcode", barcode);
        q.setParameter("title", title);
        //q.setParameter("Inventory_ID", invId);
        //q.setParameter("inventoryId", inventoryId);
        //q.setParameter("category", ItemCategory.getDefaultItemCategory(category).getItemCategoryTitle());

        return q.getResultList();
    }


    public void deleteBook(String title, int invId) {
        EntityManager em = JpaPersistence.getEntityManager();
        try {
            em.getTransaction().begin();
            InventoryItem remItem = null;
            try {
                remItem = em.getReference(InventoryItem.class, invId);
                remItem.getInvId();
            } catch (EntityNotFoundException enfe) {

            }
            em.remove(remItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
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