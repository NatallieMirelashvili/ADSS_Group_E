package DomainLayer;

import java.util.ArrayList;

public class DataObject {
    ArrayList<Item> itemsObj;
    Inventory invenObj;
    Defective defObj;
    Expired expObj;
    ArrayList<Product> prodObj;

    public void AddItem(Item new_item){
        itemsObj.add(new_item);
    }
}
