package ServiceLayer;
import DomainLayer.Inventory;
import DomainLayer.Item;
import DomainLayer.Product;
import DomainLayer.Tuple;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


//    Access to product functions
public class ProductController {

// Create item/ product
    public static void createNewItem(JsonObject record){
        Tuple<String, Integer> place =
        Item newItem = gs.fromJson(record, Item.class);
        newItem.addMeToProd();
    }

    public static void createNewProd(JsonObject record){
        boolean alreadyInStock = StockController.ProdInStockControl(record.get("catalogNum").getAsInt());
        if(!alreadyInStock){
        Gson gs = new Gson();
        Product newProd = gs.fromJson(record, Product.class);
        newProd.addMeToInven();}
        System.out.println("Added failed - the product already in stock.\n");
    }


}

