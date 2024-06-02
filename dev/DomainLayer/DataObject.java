package DomainLayer;

import java.util.ArrayList;

public class DataObject {
    private static ArrayList<Item> itemsObj;
    private static Inventory invenObj;
    private static Defective defObj;
    private static Expired expObj;
    private static ArrayList<Product> prodObj;

//    Getters

    public static ArrayList<Item> getItemsObj() {
        return itemsObj;
    }

    public static Inventory getInvenObj() {
        return invenObj;
    }

    public static Defective getDefObj() {
        return defObj;
    }

    public static Expired getExpObj() {
        return expObj;
    }

    public static ArrayList<Product> getProdObj() {
        return prodObj;
    }

//    Setters:

    public static void AddItem(Item new_item){
        itemsObj.add(new_item);
        //adding this new item to its product list:
        new_item.addMeToProd();

    }
    public static void AddProd(Product new_prod){
        prodObj.add(new_prod);

//        adding this new product to the stock:


    }
    public static void setInventory(Inventory inventory){
        invenObj = inventory;
    }
    public static void setDefective(Defective defective){
        defObj = defective;
    }
    public static void setExpObj(Expired expired){
        expObj = expired;
    }
}
