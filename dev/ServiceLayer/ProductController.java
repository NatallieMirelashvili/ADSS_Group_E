package ServiceLayer;
import DomainLayer.Product;
import DomainLayer.Item;
import DomainLayer.Tuple;
import com.google.gson.JsonObject;
import java.time.LocalDate;


public class ProductController {

    /**
     * Name: createPlaceItem - a method which turns a string which presents item's place by the instruction.
     * For example item 1234 is in the warehouse in aile A shelf 6, so the method gets the "A 6" input and returns the
     * proper tuple <A, 6>
     * Args: String placeStr
     * Returns: Tuple<String, Integer>
     * */
    public static Tuple<String, Integer> createPlaceItem(String placeStr){
        String[] passShelf = placeStr.split(" ");
        String pass = passShelf[0];
        Integer shelf = Integer.parseInt(passShelf[1]);
        return new Tuple<>(pass, shelf);
    }

    /**
     * Name: createNewItem - a method which turns a Json object which presents item's characters and adds the new item
     * to the inventory. Using Item's class methods.
     * Args: JsonObject record
     * Returns: None
     * */
    public static void createNewItem(JsonObject record){
        Tuple<String, Integer> place = createPlaceItem(record.get("place").getAsString());
        String expD = record.get("expirationDate").getAsString();
        LocalDate expDate = LocalDate.parse(expD);
        Item newItem = new Item(record.get("id").getAsInt(), expDate, place, record.get("catalogNumItem").getAsInt());
        newItem.addMeToProd();
    }

    /**
     * Name: createNewProd - a method which turns a Json object which presents product's characters and adds the new product
     * to the inventory. Using Product's class constructor and StockController methods.
     * Args: JsonObject record
     * Returns: None
     * */
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

