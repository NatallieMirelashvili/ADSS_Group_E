package Controller;
import Domain.*;
import com.google.gson.JsonObject;

public class controller {
    public static void add_driver(JsonObject driver) {
        Temp_DB.add_driver(driver);
    }
    public static void add_truck(JsonObject truck) {
        Temp_DB.add_truck(truck);
    }
    public static void add_site(JsonObject site) {
        Temp_DB.add_site(site);
    }
    public static void add_delivery(JsonObject delivery) {
        Temp_DB.add_delivery(delivery);
    }
    public static boolean site_exists(int ID) {
        return Temp_DB.site_exists(ID);
    }

    public static boolean driver_exists(int ID) {
        return Temp_DB.driver_exists(ID);
    }

    public static boolean truck_exists(int ID) {
        return Temp_DB.truck_exists(ID);
    }

    public static boolean start_driving(int driverID) {
        return Temp_DB.start_driving(driverID);
    }

    public static boolean end_driving(int driverID) {
        return Temp_DB.end_driving(driverID);
    }

    public static boolean driver_password(int driverID, int password) {
        return Temp_DB.driver_password(driverID, password);
    }

    public static String print_transport_form(int driverID) {
        return Temp_DB.print_transport_form(driverID);
    }

    public static String print_items_form(int ID, int destinationID) {
        return Temp_DB.print_items_form(ID, destinationID);
    }

    public static void change_site_area(int ID, String area) {
        Temp_DB.change_site_area(ID, area);
    }
    public static boolean truck_available(int ID) {
        return Temp_DB.get_truck(ID).isAvailable();
    }
    public static boolean driver_available(int ID) {
        return Temp_DB.get_driver(ID).getAvailability();
    }

    public static boolean check_license(int truckId, int driverId) {
        return Temp_DB.get_truck(truckId).getLicense().equals(Temp_DB.get_driver(driverId).getLicense());
    }
    public static String get_site_type(int site_ID) {
        return Temp_DB.get_site(site_ID).get_type();
    }
    public static int get_delivery_ID() {
        return Temp_DB.get_delivery_ID();
    }
    public static boolean destination_exists(int delivery_ID, int site_ID) {
        return Temp_DB.destination_exists(delivery_ID, site_ID);
    }
    public static boolean same_area(int delivery_ID, int site_ID) {
        return Temp_DB.same_area(delivery_ID, site_ID);
    }
    public static boolean item_exists(int item_ID) {
        return Temp_DB.item_exists(item_ID);
    }
    public static void add_items_form(int delivery_ID, int site_ID) {
        Temp_DB.add_items_form(delivery_ID, site_ID);
    }
    public static void add_item_to_items_form(int delivery_ID, int site_ID, int item_ID, int quantity) {
        Temp_DB.add_item_to_Items_form(delivery_ID, site_ID, item_ID, quantity);
    }


}
