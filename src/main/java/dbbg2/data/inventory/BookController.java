package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;

public class BookController extends Book {
    private Book changeBook;

    public Book getTheBook(){
        return changeBook;
    }

    public void ammendAuthorInformation(String fName, String lName){

    }

    public void setBook(Book book) {
        changeBook = book;
    }


    public void amendInformationBook(String title, String isbn, String authors, ItemCategory category){
        changeBook.setTitle(title);
        //subjectToChange.setCategory(category);
        changeBook.setIsbn(isbn);
        changeBook.setAuthors(authors);
        changeBook.setCategory(category);

        //saveChangesBook();

    }



    public void saveChangesBook() {
        EntityManager em = JpaPersistence.getEntityManager();

        em.getTransaction().begin();

        this.setBook(em.merge(changeBook));

        em.getTransaction().commit();

    }


}
