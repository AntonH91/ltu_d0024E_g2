package unittest.data.Inventory;

import dbbg2.data.inventory.Author;
import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import org.junit.Assert;
import org.junit.Test;

public class BookTest {

    @Test
    public void authorIsSetCorrectlyByConstructor(){
        final String AUTHOR_NAME = "Astrid Lindgren";
        Book book = new Book("Emil", ItemCategoryType.OTHER_BOOKS, true, "4489456", new Author());
        Assert.assertEquals(AUTHOR_NAME, book.getAuthors().iterator());
    }


}
