package DomainLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Expired extends AStock{
    public Expired(HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock, int amountIn) {
        super(myStock, amountIn);
    }
}
