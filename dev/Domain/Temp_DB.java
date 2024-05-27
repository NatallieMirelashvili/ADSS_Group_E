package Domain;
import java.util.HashMap;

public class Temp_DB {
    private static HashMap<Integer, Driver> drivers_d = new HashMap<Integer, Driver>();
    private static HashMap<Integer, truck> trucks_d = new HashMap<Integer, truck>();
    private static HashMap<Integer, branch_order> orders_d = new HashMap<Integer, branch_order>();
    private static HashMap<String, site> site_d = new HashMap<String, site>();
    private static HashMap<Integer, Delivery> delivery_forms_d = new HashMap<Integer, Delivery>();

    public static void add_driver(Driver driver) {
        drivers_d.put(driver.getID(), driver);
    }

    public static void add_truck(truck truck) {
        trucks_d.put(truck.getID(), truck);
    }

    public static void add_order(branch_order order) {
        orders_d.put(order.getOrder_id(), order);
    }

    public static void add_site(site site) {
        site_d.put(site.getSite_name(), site);
    }

    public static void add_delivery(Delivery delivery) {
        delivery_forms_d.put(delivery.getID(), delivery);
    }
}
