package DomainLayer;
import java.util.Date;

public class salePrice {
    Date startSale;
    Date endSale;
    double discountRatio;

    public salePrice(Date startSale, Date endSale, double discountRatio) {
        this.startSale = startSale;
        this.endSale = endSale;
        this.discountRatio = discountRatio;
    }

}