package data.Inventory;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import org.junit.Assert;
import org.junit.Test;

public class BookTest {

    @Test
    public void authorIsSetCorrectlyByConstructor(){
        final String AUTHOR_NAME = "Astrid Lindgren";
        Book book = new Book("Emil", ItemCategoryType.OTHER_BOOKS, true, "4489456", AUTHOR_NAME);
        Assert.assertEquals(AUTHOR_NAME, book.getAuthor().get(0));
    }


}
