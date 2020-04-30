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
    ArrayList<String> actors = new ArrayList<String>();


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
