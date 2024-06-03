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
        StringBuilder outputForController = new StringBuilder();
        for (int i = 1; i <= amountDefectives; i++) {
            outputForController.append(i).append(". CatalogNum:").append(items.get(i).getCatalogNum()).append("Location:").append(items.get(i).getPlace());
        }
        return outputForController.toString();
    }

}
