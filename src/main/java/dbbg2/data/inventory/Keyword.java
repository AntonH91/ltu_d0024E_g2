package dbbg2.data.inventory;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Keyword {
    @Id
    private String keyword;
    //@ManyToMany (mappedBy = "keyword")
    //private Set<InventoryItem> items = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "Inventory_keyword",
            joinColumns = {@JoinColumn(name = "Keyword_name")},
            inverseJoinColumns = {@JoinColumn(name = "Inventory_Id")})
    private final Set<InventoryItem> items = new HashSet<>();

    public Set<InventoryItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public void removeItem(InventoryItem item) {
        items.remove(item);
    }
}


