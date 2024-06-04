package DomainLayer;
import java.util.ArrayList;
import java.time.LocalDate;
public class Item {
//    ***Fields***
    private int id;
    private LocalDate expirationDate;
    private Tuple<String, Integer> place;
    private int catalogNumItem;
    private Product myProduct;


//    ***Constructor***

    public Item(int id, LocalDate expirationDate, Tuple<String, Integer> place, int catalogNumItem) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.place = place;
        this.catalogNumItem = catalogNumItem;

    }
//    ***Getters***

    public int getId() {
        return id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Tuple<String, Integer> getPlace() {
        return place;
    }

    public int getCatalogNumItem() {
        return catalogNumItem;
    }

//    ***Setters***

    public void setId(int id) {
        this.id = id;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPlace(Tuple<String, Integer> place) {
        this.place = place;
    }

    public void setCatalogNumItem(int catalogNumItem) {
        this.catalogNumItem = catalogNumItem;
    }

    public void addMeToProd() {
        Product myProduct = Inventory.FindProduct(this.catalogNumItem);
        myProduct.addItemToLst(this);
    }
}
