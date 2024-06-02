package DomainLayer;
import java.util.ArrayList;
import java.util.Date;
public class Item {
//    ***Fields***
    private int id;
    private Date expirationDate;
    private Tuple<String, Integer> place;
    private int catalogNum;
    private Product myProduct;


//    ***Constructor***

    public Item(int id, Date expirationDate, Tuple<String, Integer> place, int catalogNum) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.place = place;
        this.catalogNum = catalogNum;
        for (Product product: DataObject.getProdObj()){
            if (product.getCatalogNum()==(catalogNum)) {
                this.myProduct = product;
                break;
            }
        }
    }
//    ***Getters***

    public int getId() {
        return id;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Tuple<String, Integer> getPlace() {
        return place;
    }

    public int getCatalogNum() {
        return catalogNum;
    }

//    ***Setters***

    public void setId(int id) {
        this.id = id;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPlace(Tuple<String, Integer> place) {
        this.place = place;
    }

    public void setCatalogNum(int catalogNum) {
        this.catalogNum = catalogNum;
    }
    public void addMeToProd(){
//        In the next work get this from data layer
        ArrayList<Product> products = DataObject.getInventObj().getProdByCatalog();
        for(Product prod: products){
            if (this.getCatalogNum() == prod.getCatalogNum()){
                prod.addItemToLst(this);
                break;
            }
        }
    }

}
