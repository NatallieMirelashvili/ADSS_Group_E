package Domain;

import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Delivery_manager {
    public static DeliveryRepo delivers = new DeliveryRepo();
    public static ItemRepositoryDelivery items = new ItemRepositoryDelivery();


    public static void add_delivery(JsonObject delivery) {
        // CRITICAL ASSUMPTION: The creation of a delivery is entered to HashMap and to DB,
        // it receives updates in the DB after changes are made in the HashMap
        String date = delivery.get("date").getAsString();
        String hour = delivery.get("hour").getAsString();
        int driverID = delivery.get("driver_ID").getAsInt();
        int truckID = delivery.get("truck_ID").getAsInt();
        int siteID = delivery.get("site_ID").getAsInt();
        Driver driver = STD_manager.get_driver(driverID);
        Truck truck = STD_manager.get_truck(truckID);
        Site site = STD_manager.get_site(siteID);
        Delivery new_delivery = new Delivery(LocalDate.parse(date), LocalTime.parse(hour), truck, driver, site);
        delivers.add(new_delivery,driverID,truckID,siteID);
    }

    public static void add_item(JsonObject item) {
        items.add(item);
    }


    public static void add_items_form(int delivery_ID, int site_ID){
        Site destination = STD_manager.get_site(site_ID);
        Items_form items_form = createItems_form(destination, delivery_ID);
        delivers.get(delivery_ID).add_items_form(items_form);
        update_delivery(delivers.get(delivery_ID));
    }

    public static Items_form createItems_form(Site destination, int delivery_ID){
        Items_form items_form = new Items_form(destination);
        delivers.addNewItemForm(items_form,delivery_ID);
        return items_form;
    }
    private static void update_delivery(Delivery delivery) {
        delivers.update(delivery);
    }
    public static Delivery get_delivery(int id) {
        return delivers.get(id);
    }

    public static Product_to_Delivery get_item(int id) {
        return items.get(id);
    }

    public static boolean delivery_exists(int id) {
        return delivers.exists(id);
    }

    public static int get_delivery_ID() {
        return Delivery.getCounter();
    }

    public static boolean destination_exists(int deliveryId, int siteId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == siteId) {
                return true;
            }
        }
        return false;
    }

    public static boolean same_area(int deliveryId, int siteId) {
        return delivers.get(deliveryId).getOrigin().getArea().equals(STD_manager.get_site(siteId).getArea());
    }

    public static boolean item_exists(int itemId) {
        return items.exists(itemId);
    }

    public static void add_item_to_Items_form(int deliveryId, int itemFormId, int itemId, int quantity) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemFormId) {
                form.addItem(new Product_to_Delivery(items.get(itemId), quantity));
            }
        }
        update_delivery(delivers.get(deliveryId));
        delivers.addItemToItemsForm(itemFormId, itemId, quantity);
    }

    public static boolean delivery_starts_now(int deliveryId) {
        return delivers.get(deliveryId).getDate().equals(LocalDate.now());
    }

    public static double weight_check(int deliveryId) {
        return delivers.get(deliveryId).getTruck().get_max_Weight();
    }

    public static void setCurr_weight(int deliveryId, double weight) {
        delivers.get(deliveryId).getTruck().setCurr_weight(weight);
        STD_manager.update_truck_weight(delivers.get(deliveryId).getTruck().getID(), weight);

    }

    public static int get_delivery_destinations_size(int deliveryId) {
        return delivers.get(deliveryId).getItems_form().size();
    }

    public static boolean get_delivery_destinations_loading(int deliveryId, int index) {
        return delivers.get(deliveryId).getItems_form().get(index).getDestination().get_type().equals("loading");
    }

    public static String get_destinations_name(int deliveryId, int index) {
        return delivers.get(deliveryId).getItems_form().get(index).getDestination().getSite_name();
    }

    public static int get_items_form_ID() {
        return Items_form.getCounter();
    }

    public static boolean items_form_exists(int deliveryId, int itemsFormId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                return true;
            }
        }
        return false;
    }

    public static int get_site_in_items_form(int deliveryId, int itemsFormId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                return form.getDestination().getSite_ID();
            }
        }
        return -1;
    }

    public static void remove_item_from_items_form(int deliveryId, int itemsFormId, int itemId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                form.removeItem(itemId);
            }
        }
        update_delivery(delivers.get(deliveryId));
        delivers.removeItemFromItemsForm(itemsFormId, itemId);
    }

    public static int get_item_quantity_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                return form.getItem(itemId).getAmount_loaded();
            }
        }
        return -1;
    }

    public static void set_amount_of_item_in_items_form(int deliveryId, int itemsFormId, int itemId, int quantity) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                form.getItem(itemId).setAmount_loaded(quantity);
            }
        }
        update_delivery(delivers.get(deliveryId));
        delivers.updateItemAmountLoaded(itemsFormId, itemId, quantity);
    }

    public static boolean item_exists_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                return form.item_exists(itemId);
            }
        }
        return false;
    }

    public static boolean destinations_been_visited(int deliveryId, int destinationId, int index) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (int i = 0; i < index; i++) {
            if (items_form.get(i).getDestination().getSite_ID() == destinationId) {
                return true;
            }
        }
        return false;
    }

    public static int get_driver_ID_from_delivery(int deliveryId) {
        return delivers.get(deliveryId).getDriver().getID();
    }

    public static void replace_truck(int deliveryId, int truckId, int weight) {
        delivers.get(deliveryId).getTruck().setCurr_weight(0);
        delivers.get(deliveryId).getTruck().setAvailability(true);
        delivers.get(deliveryId).setTruck(STD_manager.get_truck(truckId));
        delivers.get(deliveryId).getTruck().setCurr_weight(weight);
        delivers.get(deliveryId).getTruck().setAvailability(false);
        STD_manager.update_truck_weight(truckId, weight);
        update_delivery(delivers.get(deliveryId));
    }

    public static boolean item_exists_in_destination(int itemId, int destinationId, int deliveryId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == destinationId) {
                return form.item_exists(itemId);
            }
        }
        return false;
    }

    public static void remove_item_from_destination(int deliveryId, int itemId, int destinationId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == destinationId) {
                form.removeItem(itemId);
            }
        }
        update_delivery(delivers.get(deliveryId));
        delivers.removeItemFromItemsForm(deliveryId, itemId);
    }

    public static int get_items_amount_in_destination(int deliveryId, int siteId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == siteId) {
                return form.getItems().size();
            }
        }
        return 0;
    }

    public static int get_destination_site_ID(int deliveryId, int index) {
        return delivers.get(deliveryId).getItems_form().get(index).getDestination().getSite_ID();
    }

    public static int get_item_ID_in_destinations(int deliveryId, int siteId, int itemIndex) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == siteId) {
                return form.getItems().get(itemIndex).getItem().getItemId();
            }
        }
        return 0;
    }

    public static int get_item_quantity_in_destinations(int deliveryId, int siteId, int itemId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == siteId) {
                return form.getItem(itemId).getAmount_loaded();
            }
        }
        return 0;
    }

    public static boolean check_driver_schedule(int driverId, String date) {
        return delivers.check_driver_schedule(driverId, date);
    }

    public static boolean get_finished_delivery(int deliveryId) {
        //TODO: maybe add this to deliverys table
        return delivers.get(deliveryId).isFinished();
    }

    public static void finished_delivery(int deliveryId) {
        delivers.get(deliveryId).setFinished(true);
        update_delivery(delivers.get(deliveryId));
    }

    public static boolean has_items_form(int deliveryId) {
        return !delivers.get(deliveryId).getItems_form().isEmpty();
    }

    public static int get_destination_ID(int deliveryId, int i) {
    return delivers.get(deliveryId).getItems_form().get(i).getDestination().getSite_ID();

    }

    public static boolean item_exists_in_diff_items_form(int deliveryId, int itemsFormId, int itemId) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() != itemsFormId) {
                if (form.item_exists(itemId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean problem_edit_fixed(int deliveryId, int itemsFormId, int itemID, int amount) {
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getID() == itemsFormId) {
                if (form.getItem(itemID).getAmount_loaded() >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int findDeliveryByDriverIDAndDate(int driverID) {
        for (int deliveryID : delivers.getKeys()) {
            if (delivers.get(deliveryID).getDriverID() == driverID && delivers.get(deliveryID).getDate().equals(LocalDate.now())) {
                return deliveryID;
            }
        }
        return -1;
    }

    public static void add_loaded_item(int deliveryId, int itemId, int quantity) {
        delivers.get(deliveryId).add_loaded_item_nquantity(items.get(itemId), quantity);
        delivers.add_loaded_item(deliveryId,itemId, quantity);
        update_delivery(delivers.get(deliveryId));

    }

    public static boolean item_exists_in_delivery(int deliveryId, int itemId) {
        return delivers.get(deliveryId).item_loaded(itemId);
    }

    public static int get_item_quantity_in_delivery(int deliveryId, int itemId) {
        return delivers.get(deliveryId).get_item_quantity(itemId);
    }

    public static void decrease_item_in_loaded_items(int deliveryId, int itemId, int quantity) {
        if (delivers.get(deliveryId).get_item_loaded(itemId).getAmount_loaded() == quantity){
            delivers.get(deliveryId).remove_item_from_loaded_items(itemId);
            delivers.remove_item_from_loaded_items(deliveryId,itemId);
            return;
        }
        delivers.get(deliveryId).decrease_item_in_loaded_items(itemId,quantity);
        delivers.decrease_item_in_loaded_items(deliveryId,itemId, quantity);
    }

    public static void increase_item_in_loaded_items(int deliveryId, int itemId, int quantity) {
        delivers.get(deliveryId).increase_item_in_loaded_items(itemId,quantity);
        delivers.increase_item_in_loaded_items(deliveryId,itemId, quantity);
    }

    public static void remove_loaded_item(int deliveryId, int itemId) {
        delivers.remove_item_from_loaded_items(deliveryId,itemId);
    }

    public static void update_item_quantity_unloaded_in_delivery(int quantity, int itemId, int deliveryId) {
        delivers.get(deliveryId).update_item_quantity_unloaded_in_delivery(quantity,itemId);
        delivers.update_Item_Amount_Unloaded(deliveryId,itemId,quantity);
    }

    public static int get_item_quantity_unloaded_in_delivery(int deliveryId, int itemId) {
        return delivers.get(deliveryId).get_item_quantity_unloaded_in_delivery(itemId);
    }

    public static int calculate_difference_loaded_unloaded(int deliveryId, int itemId, int quantity) {
        return delivers.get(deliveryId).calculate_difference_loaded_unloaded(itemId, quantity);
    }

    public static void add_difference_to_loading_site(int deliveryId, int itemId, int diff, int itemsFormId) {
        delivers.get(deliveryId).add_difference_to_loading_site(itemId,diff,itemsFormId);
        delivers.add_difference_to_loading_site(itemId,diff,itemsFormId);
    }

    public static void remove_destination(int deliveryId, int destinationId, int currDestinationId) {
        Delivery d = delivers.get(deliveryId);
        Items_form load_form = null;
        Items_form unload_form = null;
        ArrayList<Items_form> items_form = delivers.get(deliveryId).getItems_form();
        for (Items_form form : items_form) {
            if (form.getDestination().getSite_ID() == destinationId)
                    unload_form = form;
            else if (form.getDestination().getSite_ID() == currDestinationId){
                    unload_form = form;
                }
            }
        for (Product_to_Delivery producttoDelivery : unload_form.getItems()) {
            // If the item exists in the loading form, update its loaded amount
            if (load_form.item_exists(producttoDelivery.getItemId())) {
                // Decrease the loaded amount of the item in the loading form by the loaded amount of the item in the unloading form
                load_form.getItem(producttoDelivery.getItemId()).setAmount_loaded(load_form.getItem(producttoDelivery.getItemId()).getAmount_loaded() - producttoDelivery.getAmount_loaded());
                delivers.updateItemAmountLoaded(load_form.getID(), producttoDelivery.getItemId(), load_form.getItem(producttoDelivery.getItemId()).getAmount_loaded()- producttoDelivery.getAmount_loaded());
                // Decrease the loaded amount of the item in the loaded items by the loaded amount of the item in the unloading form
                d.decrease_item_in_loaded_items(producttoDelivery.getItemId(), producttoDelivery.getAmount_loaded());
                decrease_item_in_loaded_items(deliveryId, producttoDelivery.getItemId(), producttoDelivery.getAmount_loaded());
                // If the loaded amount of the item in the loading form is zero or less, remove the item from the loading form and the loaded items
                if (load_form.getItem(producttoDelivery.getItemId()).getAmount_loaded() <= 0) {
                    load_form.removeItem(producttoDelivery.getItemId());
                    delivers.removeItemFromItemsForm(load_form.getID(), producttoDelivery.getItemId());
                }
            }
        }
        // Remove the unloading form from the items forms of the delivery
        items_form.remove(unload_form);
        delivers.removeItemForm(unload_form.getID());
    }


    public static String[] print_all_deliveries() {
        ArrayList<String> deliveries = new ArrayList<>();
        for (Delivery delivery : delivers.getAll()) {
            deliveries.add(delivery.toString());
        }
        return deliveries.toArray(new String[0]);
    }

    public static void addAllItems(ArrayList<JsonObject> items) {
        for (JsonObject item : items) {
            add_item(item);
        }
    }

    public static void remove_item(int id) {
        items.remove(id);
    }

    public static void remove_delivery(int id) {
        delivers.remove(id);
    }
    public static ArrayList<String> get_pending_orders() {
        return delivers.get_pending_orders();
    }
}






