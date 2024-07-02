package DomainLayer;
import java.time.LocalDate;

public class salePrice {

//    ***Fields***
    private static int counter=1;
    private LocalDate startSale;
    private LocalDate endSale;
    private int discountRatio;
    private int id;

//    ***Constructor***
    public salePrice(LocalDate startSale, LocalDate endSale, int discountRatio) {
        this.startSale = startSale;
        this.endSale = endSale;
        this.discountRatio = discountRatio;
        this.id = counter;
        counter++;
    }

//  ***Getters***
    public LocalDate getStartSale() {
        return startSale;
    }
    public LocalDate getEndSale() {
        return endSale;
    }
    public int getDiscountRatio() {
        return discountRatio;
    }

//    ***Setters***
    public void setStartSale(LocalDate startSale) {
        this.startSale = startSale;
    }
    public void setEndSale(LocalDate endSale) {
        this.endSale = endSale;
    }
    public void setDiscountRatio(int discountRatio) {
        this.discountRatio = discountRatio;
    }
}