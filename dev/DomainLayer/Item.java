package DomainLayer;
import java.time.LocalDate;

import static DomainLayer.ItemStatus.*;


public class Item {
//    ***Fields***
    private int id;
    private LocalDate expirationDate;
    private Tuple<String, Integer> place;
    private String StoreOrWare;
    private int catalogNumItem;
    private Product myProduct;
    private ItemStatus status;


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
        this.status=FOR_SALE;
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

    public ItemStatus getStatus() {
        return status;
    }

    //    ***Setters***
    public void setPlace(Tuple<String, Integer> place) {
        this.place = place;
    }
    public void setStoreOrWare(String storeOrWare) { this.StoreOrWare = storeOrWare;
    }

    public void setMyProduct(Product myProduct) {
        this.myProduct = myProduct;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
    public String printItem() {
        String category = "Category:" + this.getMyProduct().getCatName() + "\n";
        String subCategory = "Sub Category:" + this.getMyProduct().getSubCatName() + "\n";
        String size = "Size:" + this.getMyProduct().getProdSize() + "\n";
        String manufacturer = "Manufacturer:" + this.getMyProduct().getManufacture() + "\n";
        String location = "Location:" + this.getPlace() + "\n";
        String catalogNum = "Catalog Number:" + this.getCatalogNumItem() + "\n";
        String id = "ID:" + this.getId() + "\n";
        return category + subCategory + size + manufacturer + location + catalogNum + id;
    }
}
