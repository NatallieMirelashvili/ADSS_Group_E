package Domain;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Temp_DB {
    private static HashMap<Integer, Driver> drivers_d = new HashMap<Integer, Driver>();
    private static HashMap<Integer, truck> trucks_d = new HashMap<Integer, truck>();
    private static HashMap<String, site> site_d = new HashMap<String, site>();
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
        String type = site.get("type").getAsString();
        String name = site.get("name").getAsString();
        String address = site.get("address").getAsString();
        String contacts_name = site.get("contacts_name").getAsString();
        String phone_num = site.get("phone_num").getAsString();
        String area = site.get("area").getAsString();
        site new_site = new site(type, name, address,contacts_name, phone_num, area);
        site_d.put(new_site.getSite_name(), new_site);
    }

    public static void add_delivery(JsonObject delivery) {
        String date = delivery.get("date").getAsString();
        String hour = delivery.get("hour").getAsString();
        Delivery new_delivery = new Delivery(LocalDate.parse(date), LocalTime.parse(hour));
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

    public static site get_site(String site_name) {
        return site_d.get(site_name);
    }

    public static Delivery get_delivery(int deliveryID) {
        return delivery_forms_d.get(deliveryID);
    }
}
