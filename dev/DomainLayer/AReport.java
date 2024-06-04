package DomainLayer;

import java.util.ArrayList;

abstract public class AReport {

//   ***Fields***
    protected ArrayList<Tuple<String,Item>> items;
    protected String name;
    protected int amount;

    //   ***Getters***

    public ArrayList<Tuple<String, Item>> getItems() {
        return items;
    }
    public int getAmount() {
        return amount;
    }
    public String getName() {
        return name;
    }

    //   ***Methood***

    //print a report by format->
    //Report (by name) items:
    //1.
    //Category:
    //Sub Category:
    //Size:
    //Manufacturer:
    //Location:
    //Catalog Number:
    //ID:
    //....
    //Total (by name) items:
    //After printing all items are removed
    public String GenerateReports() {
        StringBuilder outputForController = new StringBuilder();
        String title = "Report" + name + "items:\n";
        outputForController.append(title);
        //all information
        for (int i=1; i<= items.size(); i++) {
            String Serial = i+".\n";
            String location = "Location:" + items.get(i).getVal2().getPlace()+"\n";
            String catalogNum = "Catalog Number:" + items.get(i).getVal2().getCatalogNumItem()+"\n";
            String id = "ID:" + items.get(i).getVal2().getId()+"\n";
            outputForController.append(Serial).append(items.get(i).getVal1()).append(location).append(catalogNum).append(id);
        }
        String summery = "Total" + name + "items:" + items.size() +"\n";
        outputForController.append(summery);
        items.clear();
        return outputForController.toString();
    }

    //add item to report with important details
    public void AddItemToMe(Tuple<String,Item> toAdd){
        items.add(toAdd);
    }

}
