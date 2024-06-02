package DomainLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class AReport {
    //    ***Fields***
    private ArrayList<Item> items;
    int amountDefectives;

    //    ***Constructor***
    public AReport() {
        items = new ArrayList<>();
        amountDefectives = 0;
    }

    public String GenerateReports() {
        String outputForController = "";
        for (int i = 1; i <= amountDefectives; i++) {
            outputForController +=(i + ". CatalogNum:" + items.get(i).getCatalogNum() + "Location:" + items.get(i).getPlace());
        }
        return outputForController;
    }

}
