package ServiceLayer;
import DomainLayer.Item;
import DomainLayer.Product;
import DomainLayer.DataObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import DomainLayer.Product;

import java.util.Date;

//    Access to product functions
public class ProductController {
    private static DataObject forCreatedStuff = new DataObject();

// Create item/ product
    public static void createNewItem(JsonObject record){
        Gson gs = new Gson();
        Item newItem = gs.fromJson(record, Item.class);
        forCreatedStuff.AddItem(newItem);
    }

    public static void createNewProd(JsonObject record){
        Gson gs = new Gson();
        Product newProd = gs.fromJson(record, Product.class);
        forCreatedStuff.AddProd(newProd);
    }
}
