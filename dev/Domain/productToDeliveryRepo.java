package Domain;

import DataAccessLayer.ProductToDelAccessObj;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class productToDeliveryRepo implements IRepository<JsonObject>{
    private ArrayList<JsonObject> myJasonToDelivery = new ArrayList<>();
    private final ProductToDelAccessObj myDAOJasonDelivery = new ProductToDelAccessObj();
    public ArrayList<JsonObject> getMyJasonToDelivery() {
        return myJasonToDelivery;
    }

    @Override
    public JsonObject find(int unique) {
        return null;
    }

    @Override
    public JsonObject add(JsonObject newRec) {
        myDAOJasonDelivery.insert(newRec);
        myJasonToDelivery.add(newRec);
        return newRec;
    }

    @Override
    public JsonObject remove(int unique) {
        return null;
    }
}
