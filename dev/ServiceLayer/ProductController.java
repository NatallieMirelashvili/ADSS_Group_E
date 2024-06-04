package ServiceLayer;
import DomainLayer.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;


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
        Item newItem = new Item(record.get("id").getAsInt(), expDate, place, record.get("catalogNum").getAsInt());
        newItem.addMeToProd();
    }

    public static void createNewProd(JsonObject record){
        boolean alreadyInStock = StockController.ProdInStockControl(record.get("catalogNum").getAsInt());
        if(!alreadyInStock){
            String totJson = record.get("total").getAsString();
            String[] StoreWare = totJson.split(" ");
            Integer store = Integer.parseInt(StoreWare[0]);
            Integer ware = Integer.parseInt(StoreWare[1]);
            Tuple<Integer, Integer> totalTup = new Tuple<>(store, ware);
            ArrayList<Item> items = new ArrayList<>(0);
            salePrice sale = null;
            Product newProd = new Product(record.get("catName").getAsString(), record.get("subCatName").getAsString(),
                    record.get("size").getAsString(),totalTup,record.get("manuFactor").getAsString(), record.get("catalogNum").getAsInt(),
                    record.get("marketPriceConst").getAsInt(),record.get("manuPriceConst").getAsInt(),record.get("marketPriceCurr").getAsInt(),
                    record.get("manuPriceCurr").getAsInt(), sale,
                    record.get("discount").getAsInt(),items, record.get("minimalAmount").getAsInt());
            newProd.addMeToInven();
        }

        System.out.println("Added failed - the product already in stock.\n");
    }


}

