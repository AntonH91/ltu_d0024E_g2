package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.ArrayList;

@Entity
public class Film extends InventoryItem {

    String director;
    @Basic(optional = false)
    int ageLimit;
    String originCountry;

    private String actors = "";
    //ArrayList<String> actors = new ArrayList<String>();


    public Film() {
        super();
    }

    public Film(String title, ItemCategoryType category, boolean isAvailable, String director, int ageLimit, String originCountry){
        this(title, ItemCategory.getDefaultItemCategory(category), isAvailable, director, ageLimit, originCountry);
    }

    public Film(String title, ItemCategory category, boolean isAvailable, String director, int ageLimit, String originCountry) {
        super (title, category, isAvailable);
        this.director = director;
        this.ageLimit = ageLimit;
        this.originCountry = originCountry;
    }

    public Film(String title, ItemCategory category, boolean isAvailable, String director, int ageLimit, String originCountry, String actors){
        super (title, category, isAvailable);
        this.director = director;
        this.ageLimit = ageLimit;
        this.originCountry = originCountry;
        this.actors = actors;
    }




    public void setDirector(String director){
        this.director = director;
    }

    public void setAgeLimit(int ageLimit){
        this.ageLimit = ageLimit;
    }

    public void setOriginCountry(String originCountry){
        this.originCountry = originCountry;
    }

    public void setActors(String actors) {
        this.actors = actors;
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

    public String getActors() {
        return actors;
    }

}
