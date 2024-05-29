package DomainLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AStock {
    HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock;
    int amountIn;

    public HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> getMyStock() {
        return myStock;
    }

    public void setMyStock(HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock) {
        this.myStock = myStock;
    }

    public void setAmountIn(int amountIn) {
        this.amountIn = amountIn;
    }

    public int getAmountIn() {
        return amountIn;
    }

    public AStock(HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock, int amountIn) {
        this.myStock = myStock;
        this.amountIn = amountIn;
    }


    @Override
    public String toString() {
        return "AStock{" +
                "myStock=" + myStock +
                '}';
    }

}
