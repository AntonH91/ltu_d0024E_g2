package dbbg2.data.inventory;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "InventoryCopy")
public class InventoryCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cid;

    @Basic(optional = false)
    //@Index(unique = true)
    private String barcode = "";
    private String location = "";
    private boolean onLoan = false;
    private boolean lendable = true;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "invId")
    private InventoryItem item;


    public InventoryCopy(){

    }

    public InventoryCopy(String barcode, String location, boolean lendable, InventoryItem item) {

        this.barcode = barcode;
        this.location = location;
        this.lendable = lendable;
        this.item = item;
    }
// Setters

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public void setLendable(boolean lendable) {
        this.lendable = lendable;
    }

// Getters

    public String getBarcode() {
        return barcode;
    }

    public String getLocation() {
        return location;
    }

    public boolean getOnLoan() {
        return onLoan;
    }

    public boolean getLendable() {
        return lendable;
    }

    public InventoryItem getItem() {
        return item;
    }

    public long getCid() {
        return cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryCopy that = (InventoryCopy) o;
        return cid == that.cid &&
                onLoan == that.onLoan &&
                lendable == that.lendable &&
                barcode.equals(that.barcode) &&
                Objects.equals(location, that.location) &&
                item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, barcode, location, onLoan, lendable, item);
    }
}
