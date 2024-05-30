package DomainLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Defective extends AStock{

    //    ***Constructor***
    public Defective(HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock, int amountIn) {
        super(myStock, amountIn);
    }
}
