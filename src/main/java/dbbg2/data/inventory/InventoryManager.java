package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategoryType;

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

}