package DomainLayer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AStock {
//    ***Fields***
    protected HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock;
    protected int amountIn;


//    ***Constructor***
            public AStock(HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock, int amountIn) {
                this.myStock = myStock;
                this.amountIn = amountIn;
            }



//    ***Setters***
    public void setMyStock(HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock) {
        this.myStock = myStock;
    }

    public void setAmountIn(int amountIn) {
        this.amountIn = amountIn;
    }



//    ***Getters***
    public int getAmountIn() {
        return amountIn;
    }
    public HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> getMyStock() {
        return myStock;
    }

    @Override
    public String toString() {
        return "AStock{" +
                "myStock=" + myStock +
                '}';
    }

}
