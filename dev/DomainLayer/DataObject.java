package DomainLayer;

import java.util.ArrayList;

public class DataObject {
    ArrayList<Item> itemsObj;
    Inventory invenObj;
    Defective defObj;
    Expired expObj;
    ArrayList<Product> prodObj;

//    Getters

    public ArrayList<Item> getItemsObj() {
        return itemsObj;
    }

    public Inventory getInvenObj() {
        return invenObj;
    }

    public Defective getDefObj() {
        return defObj;
    }

    public Expired getExpObj() {
        return expObj;
    }

    public ArrayList<Product> getProdObj() {
        return prodObj;
    }

//    Setters:

    public void AddItem(Item new_item){
        itemsObj.add(new_item);
//        adding this new item to its product list:
        for(Product prod: prodObj){
            if (new_item.getCatalogNum() == prod.getCatalogNum()){
                prod.addItemToLst(new_item);
                break;
            }
        }
    }
    public void AddProd(Product new_prod){
        prodObj.add(new_prod);
//        adding this new product to the stock:
        invenObj.runProductBySize(new_prod.getCatName(), new_prod.getSubCatName(), new_prod.getSize()).add(new_prod);

    }
    public void setInventory(Inventory inventory){
        invenObj = inventory;
    }
    public void setDefective(Defective defective){
        defObj = defective;
    }
    public void setExpObj(Expired expired){
        expObj = expired;
    }
}
