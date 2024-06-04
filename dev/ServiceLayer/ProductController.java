package ServiceLayer;
import DomainLayer.*;
import com.google.gson.JsonObject;

import java.time.LocalDate;


//    Access to product functions
public class ProductController {

// Create item/ product
    public static void createNewItem(JsonObject record){
        String placeJson = record.get("place").getAsString();
        String[] passShelf = placeJson.split(" ");
        String pass = passShelf[0];
        Integer shelf = Integer.parseInt(passShelf[1]);
        Tuple<String, Integer> place = new Tuple<>(pass, shelf);
        String expD = record.get("expirationDate").getAsString();
        LocalDate expDate = LocalDate.parse(expD);
        Item newItem = new Item(record.get("id").getAsInt(), expDate, place, record.get("catalogNumItem").getAsInt());
        newItem.addMeToProd();
    }

    public static boolean createNewProd(JsonObject record){
        boolean alreadyInStock = StockController.ProdInStockControl(record.get("catalogNumProduct").getAsInt());
        if(!alreadyInStock){
            Product newProd = new Product(record.get("catName").getAsString(), record.get("subCatName").getAsString(),
                    record.get("size").getAsString(),record.get("manuFactor").getAsString(),
                    record.get("catalogNumProduct").getAsInt(),
                    record.get("marketPriceConst").getAsDouble(),record.get("manuPriceConst").getAsDouble(),
                    record.get("discount").getAsDouble(), record.get("minimalAmount").getAsInt());
            newProd.addMeToInvent();
            return true;
        }
        return false;
    }


}

