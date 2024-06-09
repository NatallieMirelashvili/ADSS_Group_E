package Domain;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Temp_DB {
    private static HashMap<Integer, Driver> drivers_d = new HashMap<Integer, Driver>();
    private static HashMap<Integer, Truck> trucks_d = new HashMap<Integer, Truck>();
    private static HashMap<Integer, Site> site_d = new HashMap<Integer, Site>();
    private static HashMap<Integer, Delivery> delivery_forms_d = new HashMap<Integer, Delivery>();
    private static HashMap<Integer, Item> items_d = new HashMap<Integer, Item>();

    public static void add_driver(JsonObject driver) {
        String ID = driver.get("ID").getAsString();
        String name = driver.get("name").getAsString();
        String licence = driver.get("licence").getAsString();
        String phone_num = driver.get("phone_num").getAsString();
        String password = driver.get("password").getAsString();
        Driver new_driver = new Driver(Integer.parseInt(ID), name, licence, phone_num, Integer.parseInt(password));
        drivers_d.put(new_driver.getID(), new_driver);
    }

    public static void add_truck(JsonObject truck) {
        String ID = truck.get("ID").getAsString();
        String model = truck.get("model").getAsString();
        String max_weight = truck.get("max_weight").getAsString();
        String licence = truck.get("licence").getAsString();
        Truck new_truck = new Truck(Integer.parseInt(ID), model, Double.parseDouble(max_weight), licence);
        trucks_d.put(new_truck.getID(), new_truck);
    }

    public static void add_site(JsonObject site) {
        int ID = site.get("ID").getAsInt();
        String type = site.get("type").getAsString();
        String name = site.get("name").getAsString();
        String address = site.get("address").getAsString();
        String contacts_name = site.get("contacts_name").getAsString();
        String phone_num = site.get("phone_num").getAsString();
        String area = site.get("area").getAsString();
        Site new_site = new Site(ID, type, name, address, contacts_name, phone_num, area);
        site_d.put(new_site.getSite_ID(), new_site);
    }

    public static void add_delivery(JsonObject delivery) {
        String date = delivery.get("date").getAsString();
        String hour = delivery.get("hour").getAsString();
        int driverID = delivery.get("driver_ID").getAsInt();
        int truckID = delivery.get("truck_ID").getAsInt();
        int siteID = delivery.get("site_ID").getAsInt();
        Driver driver = get_driver(driverID);
        Truck truck = get_truck(truckID);
        Site site = get_site(siteID);
        Delivery new_delivery = new Delivery(LocalDate.parse(date), LocalTime.parse(hour), truck, driver, site);
        delivery_forms_d.put(new_delivery.getID(), new_delivery);
    }

    public static void add_item(JsonObject item) {
        String ID = item.get("ID").getAsString();
        String name = item.get("name").getAsString();
        Item new_item = new Item(Integer.parseInt(ID), name);
        items_d.put(new_item.getItemId(), new_item);
    }

    public static Item get_item(int itemID) {
        return items_d.get(itemID);
    }

    public static Driver get_driver(int driverID) {
        return drivers_d.get(driverID);
    }

    public static Truck get_truck(int truckID) {
        return trucks_d.get(truckID);
    }

    public static Site get_site(int site_ID) {
        return site_d.get(site_ID);
    }

    public static Delivery get_delivery(int deliveryID) {
        return delivery_forms_d.get(deliveryID);
    }

    public static void remove_driver(int driverID) {
        drivers_d.remove(driverID);
    }

    public static void remove_truck(int truckID) {
        trucks_d.remove(truckID);
    }

    public static void remove_site(int site_id) {
        site_d.remove(site_id);
    }

    public static void remove_delivery(int deliveryID) {
        delivery_forms_d.remove(deliveryID);
    }

    public static void remove_item(int itemID) {
        items_d.remove(itemID);
    }

    public static boolean site_exists(int ID) {
        return site_d.containsKey(ID);
    }

    public static boolean driver_exists(int ID) {
        return drivers_d.containsKey(ID);
    }

    public static boolean truck_exists(int ID) {
        return trucks_d.containsKey(ID);
    }

    public static boolean delivery_exists(int ID) {
        return delivery_forms_d.containsKey(ID);
    }
    public static boolean start_driving(int driverID) {
        get_driver(driverID).setAvailability(false);
        int deliveryID = get_delivery_id(driverID);
        if (deliveryID == -1) {
            return false;
        }
        Delivery del = get_delivery(deliveryID);
        get_truck(del.getTruck_of_delivery().getID()).setAvailability(false);
        return true;
    }

    public static boolean end_driving(int driverID) {
        get_driver(driverID).setAvailability(true);
        int deliveryID = get_delivery_id(driverID);
        if (deliveryID == -1 || get_driver(driverID).getAvailability()) {
            return false;
        }
        Delivery del = get_delivery(deliveryID);
        get_truck(del.getTruck_of_delivery().getID()).setAvailability(true);
        return true;
    }

    public static boolean driver_password(int driverID, int password) {
        return get_driver(driverID).getPassword() == password;
    }

    public static int get_delivery_id(int driverID) {
        for (int deliveryID : delivery_forms_d.keySet()) {
            if (delivery_forms_d.get(deliveryID).getDriverID() == driverID && delivery_forms_d.get(deliveryID).getDate().equals(LocalDate.now())) {
                return deliveryID;
            }
        }
        return -1;
    }

    public static String print_transport_form(int driverID) {
        for (int deliveryID : delivery_forms_d.keySet()) {
            if (delivery_forms_d.get(deliveryID).getDriverID() == driverID && delivery_forms_d.get(deliveryID).getDate().equals(LocalDate.now())) {
                return delivery_forms_d.get(deliveryID).toString();
            }
        }
        return "No delivery form for today";
    }

    public static String print_items_form(int ID, int destinationID) {
        for (int deliveryID : delivery_forms_d.keySet()) {
            if (delivery_forms_d.get(deliveryID).getDriverID() == ID && delivery_forms_d.get(deliveryID).getDate().equals(LocalDate.now())) {
                for (Items_form items_form : delivery_forms_d.get(deliveryID).getItems_form()) {
                    if (items_form.getDestination().getSite_ID() == destinationID) {
                        return items_form.toString();
                    }
                }
            }
        }
        return "No items form for today";
    }

    public static void change_site_area(int ID, String area) {
        site_d.get(ID).setArea(area);
    }

    public static int get_delivery_ID() {
        return Delivery.getCounter();
    }

    public static boolean destination_exists(int delivery_ID, int site_ID) {
        ArrayList<Items_form> item_form_arr = get_delivery(delivery_ID).getItems_form();
        for (Items_form items_form : item_form_arr) {
            if (items_form.getDestination().getSite_ID() == site_ID) {
                return true;
            }
        }
        return false;
    }

    public static boolean same_area(int delivery_ID, int site_ID) {
        ArrayList<Items_form> item_form_arr = get_delivery(delivery_ID).getItems_form();
        if (item_form_arr.size() == 0) {
            return true;
        }
        Site destination = get_site(site_ID);
        Site last_destination = item_form_arr.get(item_form_arr.size() - 1).getDestination();
        return destination.getArea().equals(last_destination.getArea());
    }
    public static boolean item_exists(int item_ID) {
        return items_d.containsKey(item_ID);
    }
    public static void add_items_form(int delivery_ID, int site_ID){
        Delivery delivery = get_delivery(delivery_ID);
        Site destination = get_site(site_ID);
        delivery.createItems_form(destination);
    }
    public static void add_item_to_Items_form(int delivery_ID, int item_form_ID, int item_ID, int quantity){
        Delivery delivery = get_delivery(delivery_ID);
        Item item = get_item(item_ID);
        delivery.add_item_to_Items_form(item_form_ID, item, quantity);
    }

    public static boolean delivery_starts_now(int delivery_ID){
        Delivery this_delivery = get_delivery(delivery_ID);
        if (this_delivery.getDate().equals(LocalDate.now())){
            return true;
        }
        return false;
    }

    public static double weight_check(int delivery_ID){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.getTruck_of_delivery().get_max_Weight();
    }
    public static void setCurr_weight(int delivery_ID, double weight){
        Delivery this_delivery = get_delivery(delivery_ID);
        this_delivery.getTruck_of_delivery().setCurr_weight(weight);
    }
    public static boolean get_delivery_destinations_loading(int delivery_ID, int index){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.getItems_form().get(index).getDestination().get_type().equals("loading");
    }
    public static String get_destinations_name(int delivery_ID, int index){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.getItems_form().get(index).getDestination().getSite_name();
    }
    public static void add_loaded_item(int delivery_ID,int item_ID, int quantity){
        Delivery this_delivery = get_delivery(delivery_ID);
        Item this_item = get_item(item_ID);
        this_delivery.add_loaded_item(this_item, quantity);
    }
    public static boolean item_exists_in_delivery(int delivery_ID, int item_ID){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.item_loaded(item_ID);
    }
    public static int get_item_quantity_in_delivery(int delivery_ID, int item_ID){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.get_item_quantity(item_ID);
    }
    public static void decrease_item_in_loaded_items(int delivery_ID,int item_ID,int quantity){
        Delivery this_delivery = get_delivery(delivery_ID);
        if (this_delivery.get_item_loaded(item_ID).getAmount_loaded() == quantity){
            this_delivery.remove_item_from_loaded_items(item_ID);
            return;
        }
        this_delivery.decrease_item_in_loaded_items(item_ID, quantity);
    }
    public static void increase_item_in_loaded_items(int delivery_ID,int item_ID,int quantity){
        Delivery this_delivery = get_delivery(delivery_ID);
        this_delivery.increase_item_in_loaded_items(item_ID, quantity);
    }
    public static int get_items_form_ID() {
        return Items_form.getCounter();
    }
    public static boolean items_form_exists(int delivery_ID, int items_form_ID){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.items_form_exists(items_form_ID);
    }
    public static int get_site_in_items_form(int delivery_ID, int items_form_ID){
        Delivery this_delivery = get_delivery(delivery_ID);
        return this_delivery.getItems_form().get(items_form_ID).getDestination().getSite_ID();
    }

    public static void remove_item_from_items_form(int deliveryId, int itemsFormId, int itemId) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.remove_item_from_items_form(itemsFormId, itemId);
    }

    public static void remove_loaded_item(int deliveryId, int itemId) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.remove_item_from_loaded_items(itemId);
    }

    public static int get_item_quantity_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.get_item_quantity_in_items_form(itemsFormId, itemId);

    }

    public static void set_amount_of_item_in_items_form(int deliveryId, int itemsFormId, int itemId, int quantity) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.set_amount_of_item_in_items_form(itemsFormId, itemId, quantity);
    }

    public static boolean item_exists_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.item_exists_in_items_form(itemsFormId, itemId);
    }


    public static boolean destinations_been_visited(int deliveryId, int destinationId, int index) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.destinations_been_visited(destinationId, index);
    }

    public static void remove_destination(int deliveryId, int destinationId, int curr_destination_id) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.remove_destination(destinationId,curr_destination_id);

    }

    public static int get_driver_ID_from_delivery(int deliveryId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.getDriverID();
    }

    public static void replace_truck(int deliveryId, int truckId, int weight) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.replace_truck(truckId, weight);
    }

    public static boolean item_exists_in_destination(int itemId, int destinationId, int deliveryId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.item_exists_in_destination(itemId, destinationId);
    }

    public static void remove_item_from_destination(int deliveryId, int itemId, int destinationId) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.remove_item_from_destination(itemId, destinationId);
    }

    public static void update_item_quantity_unloaded_in_delivery(int quantity, int itemId, int deliveryId) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.update_item_quantity_unloaded_in_delivery(quantity, itemId);
    }

    public static int get_item_quantity_unloaded_in_delivery(int deliveryId, int itemId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.get_item_quantity_unloaded_in_delivery(itemId);
    }

    public static int calculate_difference_loaded_unloaded(int deliveryId, int itemId, int quantity) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.calculate_difference_loaded_unloaded(itemId, quantity);
    }

    public static void add_difference_to_loading_site(int deliveryId, int itemId, int diff, int itemsFormId) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.add_difference_to_loading_site(itemId, diff, itemsFormId);
    }

    public static void update_item_quantity_loaded_in_delivery(int deliveryId, int itemId, int diff) {
        Delivery this_delivery = get_delivery(deliveryId);
        this_delivery.increase_item_in_loaded_items(itemId, diff);
    }

    public static int get_destination_site_ID(int deliveryId, int index) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.get_destination_site_ID(index);
    }

    public static int get_items_amount_in_destination(int deliveryId, int siteId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.get_items_amount_in_destination(siteId);
    }

    public static int get_item_ID_in_destinations(int deliveryId, int siteId, int itemIndex) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.get_item_ID_in_destinations(siteId, itemIndex);
    }

    public static int get_item_quantity_in_destinations(int deliveryId, int siteId, int itemId) {
        Delivery this_delivery = get_delivery(deliveryId);
        return this_delivery.get_item_quantity_in_destinations(siteId, itemId);
    }

    public static boolean check_driver_schedule(int driverId, String date) {
        for (Delivery delivery : delivery_forms_d.values()) {
            if (delivery.getDriverID() == driverId && delivery.getDate().toString().equals(date)) {
                return false;
            }
        }
        return true;
    }

}
