package Domain;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Temp_DB {
    private static HashMap<Integer, Driver> drivers_d = new HashMap<Integer, Driver>();
    private static HashMap<Integer, truck> trucks_d = new HashMap<Integer, truck>();
    private static HashMap<Integer, site> site_d = new HashMap<Integer, site>();
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
        String curr_weight = truck.get("curr_weight").getAsString();
        String max_weight = truck.get("max_weight").getAsString();
        String licence = truck.get("licence").getAsString();
        truck new_truck = new truck(Integer.parseInt(ID), model, Double.parseDouble(curr_weight), Double.parseDouble(max_weight), licence);
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
        site new_site = new site(ID, type, name, address, contacts_name, phone_num, area);
        site_d.put(new_site.getSite_ID(), new_site);
    }

    public static void add_delivery(JsonObject delivery) {
        String date = delivery.get("date").getAsString();
        String hour = delivery.get("hour").getAsString();
        int driverID = delivery.get("driver_ID").getAsInt();
        int truckID = delivery.get("truck_ID").getAsInt();
        int siteID = delivery.get("site_ID").getAsInt();
        Driver driver = get_driver(driverID);
        truck truck = get_truck(truckID);
        site site = get_site(siteID);
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

    public static truck get_truck(int truckID) {
        return trucks_d.get(truckID);
    }

    public static site get_site(int site_ID) {
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

    public static void remove_site(String site_name) {
        site_d.remove(site_name);
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
                for (items_form items_form : delivery_forms_d.get(deliveryID).getItems_form()) {
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
}
