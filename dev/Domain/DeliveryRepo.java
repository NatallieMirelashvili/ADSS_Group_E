package Domain;

import DataAccessLayer.DeliveryDAO;
import DataAccessLayer.ItemDetailsDAO;
import DataAccessLayer.Items_formDAO;
import DataAccessLayer.ItemsLoadedDAO;
import com.google.gson.JsonObject;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliveryRepo implements IRepository<Delivery> {
    private static HashMap<Integer, Delivery> delivery_forms_d = new HashMap<Integer, Delivery>();
    // all delivery's in the DB
    private static DeliveryDAO deliveryDAO = new DeliveryDAO();
    // all items form in the DB
    private static Items_formDAO items_formDAO = new Items_formDAO();
    // all items in the items form in the DB
    private static ItemDetailsDAO itemDetailsDAO = new ItemDetailsDAO();
    // all items in delivery form in the DB
    private static ItemsLoadedDAO itemsLoadedDAO = new ItemsLoadedDAO();
    //TODO: maybe add a ItemDAO object here


    @Override
    public void add(JsonObject delivery) {

    }

    public void add(Delivery delivery, int driverID, int truckID, int siteID) {
        delivery_forms_d.put(delivery.getID(), delivery);
        JsonObject deliveryjo = new JsonObject();
        deliveryjo.addProperty("ID", delivery.getID());
        deliveryjo.addProperty("date", delivery.getDate().toString());
        deliveryjo.addProperty("hour", delivery.getHour().toString());
        deliveryjo.addProperty("truck_ID", truckID);
        deliveryjo.addProperty("driver_ID", driverID);
        deliveryjo.addProperty("site_ID", siteID);
        deliveryDAO.add(deliveryjo);
    }


    @Override
    public void remove(int id) {
        delivery_forms_d.remove(id);
        deliveryDAO.remove(id);
    }

    @Override
    public void update(Delivery obj) {
        if (delivery_forms_d.containsKey(obj.getID())) {
            delivery_forms_d.put(obj.getID(), obj);
            JsonObject deliveryjo = new JsonObject();
            deliveryjo.addProperty("ID", obj.getID());
            deliveryjo.addProperty("date", obj.getDate().toString());
            deliveryjo.addProperty("hour", obj.getHour().toString());
            deliveryjo.addProperty("truck_ID", obj.getTruckID());
            deliveryjo.addProperty("driver_ID", obj.getDriverID());
            deliveryjo.addProperty("site_ID", obj.getSiteID());
            deliveryDAO.update(deliveryjo);
        }
    }

    @Override
    public List<Delivery> getAll() {
        List<Delivery> deliveries = new ArrayList<>(delivery_forms_d.values());
        List<JsonObject> jsonDeliveries = deliveryDAO.getAll();
        for (JsonObject delivery : jsonDeliveries) {
            int id = delivery.get("ID").getAsInt();
            if (!delivery_forms_d.containsKey(id)) {
                String date = delivery.get("date").getAsString();
                String hour = delivery.get("hour").getAsString();
                int driverID = delivery.get("driver_id").getAsInt();
                int truckID = delivery.get("truck_id").getAsInt();
                int siteID = delivery.get("site_id").getAsInt();

                Delivery newDelivery = new Delivery(id, LocalDate.parse(date), LocalTime.parse(hour), STD_manager.get_truck(truckID), STD_manager.get_driver(driverID), STD_manager.get_site(siteID));
                // TODO: needs more work, adding items form and his items
                deliveries.add(newDelivery);
                delivery_forms_d.put(id, newDelivery);
            }
        }
        return deliveries;
    }

    @Override
    public Delivery get(int id) {
        // TODO: needs more work, adding items form and his items
        if (delivery_forms_d.get(id) != null) {
            return delivery_forms_d.get(id);
        } else {
            JsonObject delivery = deliveryDAO.get(id);
            if (delivery == null) {
                return null;
            }
            int ID = delivery.get("ID").getAsInt();
            String date = delivery.get("date").getAsString();
            String hour = delivery.get("hour").getAsString();
            int driverID = delivery.get("driver_id").getAsInt();
            int truckID = delivery.get("truck_id").getAsInt();
            int siteID = delivery.get("site_id").getAsInt();

            Delivery newDelivery = new Delivery(id, LocalDate.parse(date), LocalTime.parse(hour), STD_manager.get_truck(truckID), STD_manager.get_driver(driverID), STD_manager.get_site(siteID));

            delivery_forms_d.put(id, newDelivery);
            return newDelivery;
        }
    }


    @Override
    public boolean exists(int id) {
        return delivery_forms_d.containsKey(id) || deliveryDAO.get(id) != null;
    }

    public void addNewItemForm(Items_form itemsForm, int deliveryID) {
        JsonObject itemsFormJO = new JsonObject();
        itemsFormJO.addProperty("ID", itemsForm.getID());
        itemsFormJO.addProperty("delivery_id", deliveryID);
        itemsFormJO.addProperty("destination_id", itemsForm.getDestination().getSite_ID());
        items_formDAO.add(itemsFormJO);

    }

    public void addItemToItemsForm(int itemFormId, int itemId, int quantity) {
        JsonObject itemDetailsJO = new JsonObject();
        itemDetailsJO.addProperty("item_id", itemId);
        itemDetailsJO.addProperty("items_form_id", itemFormId);
        itemDetailsJO.addProperty("amount_loaded", quantity);
        itemDetailsJO.addProperty("amount_unloaded", 0);
        itemDetailsDAO.add(itemDetailsJO);
    }

    public void removeItemFromItemsForm(int itemsFormId, int itemId) {
        itemDetailsDAO.remove(itemId, itemsFormId);
    }

    public void updateItemAmountLoaded(int itemsFormId, int itemId, int quantity) {
        itemDetailsDAO.setItemAmountLoaded(itemId, itemsFormId, quantity);

    }

    public boolean check_driver_schedule(int driverId, String date) {
        for (Delivery delivery : delivery_forms_d.values()) {
            if (delivery.getDriverID() == driverId && delivery.getDate().toString().equals(date)) {
                return false;
            }
        }
        return true;
    }

    public int[] getKeys() {
        int[] keys = new int[delivery_forms_d.size()];
        int i = 0;
        for (int key : delivery_forms_d.keySet()) {
            keys[i] = key;
            i++;
        }
        return keys;
    }

    public void add_loaded_item(int deliveryId, int itemId, int quantity) {
        JsonObject itemLoadedJO = new JsonObject();
        itemLoadedJO.addProperty("delivery_id", deliveryId);
        itemLoadedJO.addProperty("item_id", itemId);
        itemLoadedJO.addProperty("amount_loaded", quantity);
        itemLoadedJO.addProperty("amount_unloaded", 0);
        itemsLoadedDAO.add(itemLoadedJO);
    }


    public void remove_item_from_loaded_items(int deliveryId, int itemId) {
        itemsLoadedDAO.remove(deliveryId,itemId);
    }

    public void decrease_item_in_loaded_items(int deliveryId, int itemId, int quantity) {
        itemsLoadedDAO.setAmountLoaded(deliveryId,itemId,itemsLoadedDAO.getAmountLoaded(deliveryId,itemId)-quantity);
    }

    public void increase_item_in_loaded_items(int deliveryId, int itemId, int quantity) {
        itemsLoadedDAO.setAmountLoaded(deliveryId,itemId,itemsLoadedDAO.getAmountLoaded(deliveryId,itemId)+(quantity-itemsLoadedDAO.getAmountLoaded(deliveryId,itemId)));
    }

    public void update_Item_Amount_Unloaded(int deliveryId, int itemId, int quantity) {
        itemsLoadedDAO.setAmountUnLoaded(deliveryId,itemId,itemsLoadedDAO.getAmountUnLoaded(deliveryId,itemId)+quantity);
    }

    public void add_difference_to_loading_site(int itemId, int diff, int itemsFormId) {
        itemDetailsDAO.increaseItemAmountLoaded(itemsFormId,itemId,diff);
    }
}