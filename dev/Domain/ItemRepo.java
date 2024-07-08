package Domain;

import DataAccessLayer.ItemDAO;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepo implements IRepository<Item>{
    private static HashMap<Integer, Item> items_d = new HashMap<Integer, Item>();
    private static ItemDAO itemDAO = new ItemDAO();

    @Override
    public void add(JsonObject obj) {
        String ID = obj.get("ID").getAsString();
        String name = obj.get("name").getAsString();
        itemDAO.add(obj);
    }

    @Override
    public void remove(int id) {
        items_d.remove(id);
        itemDAO.remove(id);
    }

    @Override
    public void update(Item obj) {
        items_d.put(obj.getItemId(), obj);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", obj.getItemId());
        jsonObject.addProperty("name", obj.getItemName());
        itemDAO.update(jsonObject);
    }

    @Override
    public List<Item> getAll() {
        List<JsonObject> jsonItems = itemDAO.getAll();
        List<Item> items = new ArrayList<>();
        for (JsonObject jsonItem : jsonItems) {
            int id = jsonItem.get("id").getAsInt();
            String name = jsonItem.get("name").getAsString();
            Item item = new Item(id, name);
            items.add(item);
            items_d.put(id, item);
        }
        return items;
    }

    @Override
    public Item get(int id) {
        if (items_d.containsKey(id)) {
            return items_d.get(id);
        }
        JsonObject jsonItem = itemDAO.get(id);
        if (jsonItem != null) {
            int itemId = jsonItem.get("id").getAsInt();
            String name = jsonItem.get("name").getAsString();
            Item item = new Item(itemId, name);
            items_d.put(itemId, item);
            return item;
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
