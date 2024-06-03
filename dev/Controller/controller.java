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
}
