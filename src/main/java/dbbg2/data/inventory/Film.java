package dbbg2.data.inventory;

import java.util.ArrayList;

public class Film extends InventoryItem {

    String director;
    int ageLimit;
    String originCountry;
    ArrayList<String> actors = new ArrayList<String>();

    private Film(int inventoryId, String title, ArrayList<String> filmKeyword, ItemCategory category, String isbn,  ArrayList<String> filmActors, String director, int ageLimit, String originCountry) {

        this.category = category;
        this.director = director;
        this.ageLimit = ageLimit;
        this.originCountry = originCountry;
        this.inventoryId = inventoryId;
        this.title = title;
        actors = filmActors;
        keyword = filmKeyword;
    }

    public void setDirector(){
        this.director = director;
    }

    public void setAgeLimit(){
        this.ageLimit = ageLimit;
    }

    public void setOriginCountry(){
        this.originCountry = originCountry;
    }

    public void setTitle(){
        this.title = title;
    }



// Getters


    public String getDirector() {
        return director;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    @Override
    public int getInventoryId() {
        return inventoryId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    @Override
    public ArrayList<String> getKeyword() {
        return keyword;
    }

    @Override
    public ItemCategory getCategory() {
        return category;
    }
}
