package DomainLayer;
import java.util.ArrayList;
import java.time.LocalDate;

public class Item {
//    ***Fields***
    private int id;
    private LocalDate expirationDate;
    private Tuple<String, Integer> place;
    private String StoreOrWare;
    private int catalogNumItem;
    private Product myProduct;


//    ***Constructor***

    public Item(int id, LocalDate expirationDate, Tuple<String, Integer> place, int catalogNumItem) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.place = place;
        this.catalogNumItem = catalogNumItem;
        if (place.getVal1().length()==1) {
            this.StoreOrWare = "Warehouse";
        }
        else {
            this.StoreOrWare = "Store";
        }
        this.myProduct=null;
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
    public String getStoreOrWare() { return StoreOrWare;
    }
    public int getCatalogNumItem() {
        return catalogNumItem;
    }
    public Product getMyProduct() { return myProduct; }

//    ***Setters***
    public void setPlace(Tuple<String, Integer> place) {
        this.place = place;
    }
    public void setStoreOrWare(String storeOrWare) { this.StoreOrWare = storeOrWare;
    }


    //add item to list of product appropriate
    //*this function assume that product appropriate is exist because it checked in presentation layer
    public void addMeToProd() {
        Product myProduct = Inventory.FindProduct(this.catalogNumItem);
        myProduct.addItemToLst(this);
        this.myProduct=myProduct;
    }
}
