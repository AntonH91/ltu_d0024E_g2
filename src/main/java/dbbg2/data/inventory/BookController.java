package dbbg2.data.inventory;

import dbbg2.controllers.InventoryController;
import dbbg2.data.inventory.itemCategory.ItemCategory;

import java.util.ArrayList;

public class BookController extends InventoryController {
    private Book subjectToChange;

    public void ammendInformationBook(String title, ItemCategory category, boolean isAvailable, String isbn, ArrayList<String> author){
        subjectToChange.setTitle(title);
        subjectToChange.setCategory(category);
        subjectToChange.setAvailable(isAvailable);
        subjectToChange.setIsbn(isbn);
        subjectToChange.setAuthor(author);
    }


}
