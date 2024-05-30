package DomainLayer;
import java.util.Date;

public class salePrice {

//    ***Fields***
    private Date startSale;
    private Date endSale;
    private double discountRatio;

//    ***Constructor***
    public salePrice(Date startSale, Date endSale, double discountRatio) {
        this.startSale = startSale;
        this.endSale = endSale;
        this.discountRatio = discountRatio;
    }

//  ***Getters***

    public Date getStartSale() {
        return startSale;
    }

    public Date getEndSale() {
        return endSale;
    }

    public double getDiscountRatio() {
        return discountRatio;
    }

//    ***Setters***


    public void setStartSale(Date startSale) {
        this.startSale = startSale;
    }

    public void setEndSale(Date endSale) {
        this.endSale = endSale;
    }

    public void setDiscountRatio(double discountRatio) {
        this.discountRatio = discountRatio;
    }
}