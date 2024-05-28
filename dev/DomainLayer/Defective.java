package DomainLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Defective extends AStock{
    public Defective(HashMap<String, HashMap<String, HashMap<Double, ArrayList<Product>>>> myStock, int amountIn) {
        super(myStock, amountIn);
    }
}
