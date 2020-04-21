package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.OtherBooks;

import javax.print.attribute.standard.MediaSize;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryManager {

    public static ArrayList<InventoryItem> getInventoryItems() {
        ArrayList<InventoryItem> output = new ArrayList<>();

        output.add(new Book("The Hobbit",new OtherBooks(), true,"123", "JRR Tolkien"));
        output.add(new Book("Harry Potter",new OtherBooks(), true,"123", "JK Rowling"));
        output.add(new Book("Emil",new OtherBooks(), true,"123", "Astrid Lindgren"));
        output.add(new Book("Dexter",new OtherBooks(), true,"123", "Jeff Lindsay"));

        return output;
    }

}
