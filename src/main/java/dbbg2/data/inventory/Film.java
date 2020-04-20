package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;

import java.util.ArrayList;

public class Film extends InventoryItem {

    String director;
    int ageLimit;
    String originCountry;
    ArrayList<String> actors = new ArrayList<String>();


    public Film(String title, ItemCategory category, boolean isAvailable, String director, int ageLimit, String originCountry) {
        super (title, category, isAvailable);
        this.director = director;
        this.ageLimit = ageLimit;
        this.originCountry = originCountry;
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

    public ArrayList<String> getActors() {
        return actors;
    }

}
