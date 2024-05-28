package DomainLayer;
import java.util.Date;
public class Item {
    int id;
    Date expirationDate;
    Tuple<String, Integer> place;
    int catalogNum;

    public Item(int id, Date expirationDate, Tuple<String, Integer> place, int catalogNum) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.place = place;
        this.catalogNum = catalogNum;
    }

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
}
