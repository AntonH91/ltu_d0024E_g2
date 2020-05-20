package dbbg2.data.inventory;

import dbbg2.controllers.InventoryController;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.users.User;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BookController extends InventoryController {
    private Book subjectToChange;

    public Book getTheBook(){
        return subjectToChange;
    }

    public void setBook(Book book) {
        subjectToChange = book;
    }


    public void ammendInformationBook(String title, ItemCategory category, String isbn){
        subjectToChange.setTitle(title);
        subjectToChange.setCategory(category);
        subjectToChange.setIsbn(isbn);
    }


    public void saveChanges() {
        EntityManager em = JpaPersistence.getEntityManager();

        em.getTransaction().begin();

        this.setBook(em.merge(subjectToChange));

        em.getTransaction().commit();

    }


}
