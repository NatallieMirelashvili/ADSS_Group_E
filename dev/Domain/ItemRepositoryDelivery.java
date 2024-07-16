package Domain;

import DataAccessLayer.ItemDAO;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepositoryDelivery implements IRepositoryDelivery<Product_to_Delivery> {
    private static HashMap<Integer, Product_to_Delivery> items_d = new HashMap<Integer, Product_to_Delivery>();
    private static ItemDAO itemDAO = new ItemDAO();

    @Override
    public void add(JsonObject obj) {
        itemDAO.add(obj);
    }

    @Override
    public void remove(int id) {
        items_d.remove(id);
        itemDAO.remove(id);
    }

    @Override
    public void update(Product_to_Delivery obj) {
        items_d.put(obj.getItemId(), obj);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", obj.getItemId());
        jsonObject.addProperty("name", obj.getItemName());
        itemDAO.update(jsonObject);
    }

    @Override
    public List<Product_to_Delivery> getAll() {
        List<JsonObject> jsonItems = itemDAO.getAll();
        List<Product_to_Delivery> producttoDeliveries = new ArrayList<>();
        for (JsonObject jsonItem : jsonItems) {
            int id = jsonItem.get("id").getAsInt();
            String name = jsonItem.get("name").getAsString();
            Product_to_Delivery producttoDelivery = new Product_to_Delivery(id, name);
            producttoDeliveries.add(producttoDelivery);
            items_d.put(id, producttoDelivery);
        }
        return producttoDeliveries;
    }

    @Override
    public Product_to_Delivery get(int id) {
        if (items_d.containsKey(id)) {
            return items_d.get(id);
        }
        JsonObject jsonItem = itemDAO.get(id);
        if (jsonItem != null) {
            int itemId = jsonItem.get("ID").getAsInt();
            String name = jsonItem.get("name").getAsString();
            Product_to_Delivery producttoDelivery = new Product_to_Delivery(itemId, name);
            items_d.put(itemId, producttoDelivery);
            return producttoDelivery;
        }
        return null;
    }

    @Override
    public boolean exists(int id) {
        if (itemDAO.get(id) != null) {
            return true;
        }
        return false;
    }
}
