package Domain;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class STD_manager {
    public static DriverRepo drivers = new DriverRepo();
    public static TruckRepo trucks = new TruckRepo();
    public static SiteRepo sites = new SiteRepo();


    public static void add_driver(JsonObject driver) {
        drivers.add(driver);
    }

    public static void add_truck(JsonObject truck) {
        trucks.add(truck);
    }

    public static void add_site(JsonObject site) {
        sites.add(site);
    }

    public static Driver get_driver(int driverID) {
        return drivers.get(driverID);
    }

    public static Truck get_truck(int truckID) {
        return trucks.get(truckID);
    }

    public static Site get_site(int site_ID) {
        return sites.get(site_ID);
    }



    public static void remove_driver(int driverID) {
        drivers.remove(driverID);
    }

    public static void remove_truck(int truckID) {
        trucks.remove(truckID);
    }

    public static void remove_site(int site_id) {
        sites.remove(site_id);
    }

    public static boolean site_exists(int ID) {
        return sites.exists(ID);
    }

    public static boolean driver_exists(int ID) {
        return drivers.exists(ID);
    }

    public static boolean truck_exists(int ID) {
        return trucks.exists(ID);
    }

    public static boolean driver_password(int driverID, int password) {
        return get_driver(driverID).getPassword() == password;
    }

    public static void change_site_area(int ID, String area) {
        sites.setArea(area, ID);
    }

    public static double truck_max_weight(int truckId) {
        return get_truck(truckId).get_max_Weight();
    }


    public static void update_truck_weight(int id, double weight) {
        trucks.update_weight(id, weight);
    }

    public static boolean start_driving(int driverID) {
        int delivery_id = Delivery_manager.findDeliveryByDriverIDAndDate(driverID);
        if (delivery_id != -1) {
            get_driver(driverID).setAvailability(false);
            Delivery_manager.get_delivery(delivery_id).getTruck().setAvailability(false);
            return true;
        }
        return false;
    }

    public static boolean end_driving(int driverID) {
        int delivery_id = Delivery_manager.findDeliveryByDriverIDAndDate(driverID);
        if (delivery_id != -1) {
            get_driver(driverID).setAvailability(true);
            Delivery_manager.get_delivery(delivery_id).getTruck().setAvailability(true);
            return true;
        }
        return false;
    }


    public static String print_transport_form(int driverID) {
        int delivery_id = Delivery_manager.findDeliveryByDriverIDAndDate(driverID);
        if (delivery_id != -1) {
            return Delivery_manager.get_delivery(delivery_id).toString();
        }
        return "No delivery for this driver today";
    }

    public static String print_items_form(int driver_id, int destinationID) {
        int delivery_id = Delivery_manager.findDeliveryByDriverIDAndDate(driver_id);
        if (delivery_id != -1) {
            for (Items_form form: Delivery_manager.get_delivery(delivery_id).getItems_form()){
                if (form.getDestination().getSite_ID() == destinationID){
                    return form.toString();
                }
            }
        }
        return "No delivery for this driver today";

    }

    public static String[] print_all_drivers() {
        return drivers.getAll().stream().map(Driver::toString).toArray(String[]::new);
    }

    public static String[] print_all_trucks() {
        return trucks.getAll().stream().map(Truck::toString).toArray(String[]::new);
    }

    public static String[] print_all_sites() {
        return sites.getAll().stream().map(Site::toString).toArray(String[]::new);
    }

    public static void addAllTrucks(ArrayList<JsonObject> trucks) {
        for (JsonObject truck : trucks) {
            add_truck(truck);
        }
    }

    public static void addAllDrivers(ArrayList<JsonObject> drivers) {
        for (JsonObject driver : drivers) {
            add_driver(driver);
        }
    }

    public static void addAllSites(ArrayList<JsonObject> sites) {
        for (JsonObject site : sites) {
            add_site(site);
        }
    }


}
