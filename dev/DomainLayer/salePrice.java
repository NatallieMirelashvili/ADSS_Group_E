package DomainLayer;
import java.time.LocalDate;

public class salePrice {

//    ***Fields***
    private LocalDate startSale;
    private LocalDate endSale;
    private double discountRatio;

//    ***Constructor***
    public salePrice(LocalDate startSale, LocalDate endSale, double discountRatio) {
        this.startSale = startSale;
        this.endSale = endSale;
        this.discountRatio = discountRatio;
    }

//  ***Getters***
    public LocalDate getStartSale() {
        return startSale;
    }
    public LocalDate getEndSale() {
        return endSale;
    }
    public double getDiscountRatio() {
        return discountRatio;
    }

//    ***Setters***
    public void setStartSale(LocalDate startSale) {
        this.startSale = startSale;
    }
    public void setEndSale(LocalDate endSale) {
        this.endSale = endSale;
    }
    public void setDiscountRatio(double discountRatio) {
        this.discountRatio = discountRatio;
    }
}