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
    public static void add_item(JsonObject item) {
        Temp_DB.add_item(item);
    }
    public static String get_item_name(int ID) {
        return Temp_DB.get_item(ID).getItemName();
    }
    public static String get_site_name(int ID) {
        return Temp_DB.get_site(ID).getSite_name();
    }
    public static boolean delivery_exists(int ID) {
        return Temp_DB.delivery_exists(ID);
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
    public static void add_item_to_items_form(int delivery_ID, int item_form_ID, int item_ID, int quantity) {
        Temp_DB.add_item_to_Items_form(delivery_ID, item_form_ID, item_ID, quantity);
    }
    public static boolean delivery_starts_now(int delivery_ID) {
        return Temp_DB.delivery_starts_now(delivery_ID);
    }
    public static double weight_check(int delivery_ID){
        return Temp_DB.weight_check(delivery_ID);
    }
    public static void setCurr_weight(int delivery_ID, double weight){
        Temp_DB.setCurr_weight(delivery_ID, weight);
    }

    public static int get_delivery_destinations_size(int delivery_ID){
        return Temp_DB.get_delivery(delivery_ID).item_form_size();
    }
    public static boolean get_delivery_destinations_loading(int delivery_ID, int index){
        return Temp_DB.get_delivery_destinations_loading(delivery_ID, index);
    }
    public static String get_destinations_name(int delivery_ID, int index){
        return Temp_DB.get_destinations_name(delivery_ID, index);
    }
    public static void add_loaded_item(int delivery_ID,int item_ID,int quantity){
        Temp_DB.add_loaded_item(delivery_ID, item_ID, quantity);
    }
    public static boolean item_exists_in_delivery(int delivery_ID, int item_ID){
        return Temp_DB.item_exists_in_delivery(delivery_ID, item_ID);
    }
    public static int get_item_quantity_in_delivery(int delivery_ID, int item_ID){
        return Temp_DB.get_item_quantity_in_delivery(delivery_ID, item_ID);
    }
    public static void decrease_item_in_loaded_items(int delivery_ID,int item_ID,int quantity){
        Temp_DB.decrease_item_in_loaded_items(delivery_ID, item_ID, quantity);
    }
    public static void increase_item_in_loaded_items(int delivery_ID,int item_ID,int quantity)
    {
        Temp_DB.increase_item_in_loaded_items(delivery_ID, item_ID, quantity);
    }
    public static int get_items_form_ID(){
        return Temp_DB.get_items_form_ID();
    }
    public static boolean items_form_exists(int delivery_ID, int items_form_ID){
        return Temp_DB.items_form_exists(delivery_ID, items_form_ID);
    }
    public static int get_site_in_items_form(int delivery_ID, int items_form_ID){
        return Temp_DB.get_site_in_items_form(delivery_ID, items_form_ID);
    }

    public static void remove_item_from_items_form(int deliveryId, int itemsFormId, int itemId) {
        Temp_DB.remove_item_from_items_form(deliveryId, itemsFormId, itemId);
    }

    public static void remove_loaded_item(int deliveryId, int itemId) {
        Temp_DB.remove_loaded_item(deliveryId, itemId);
    }

    public static int get_item_quantity_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        return Temp_DB.get_item_quantity_in_items_form(deliveryId, itemsFormId, itemId);
    }

    public static void set_amount_of_item_in_items_form(int deliveryId, int itemsFormId, int itemId, int quantity) {
        Temp_DB.set_amount_of_item_in_items_form(deliveryId, itemsFormId, itemId, quantity);
    }

    public static boolean item_exists_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        return Temp_DB.item_exists_in_items_form(deliveryId, itemsFormId, itemId);
    }
    public static boolean destinations_been_visited(int delivery_ID, int destination_ID, int index){
        return Temp_DB.destinations_been_visited(delivery_ID, destination_ID, index);
    }

    public static void remove_destination(int deliveryId, int destinationId,int curr_destination_id) {
        Temp_DB.remove_destination(deliveryId, destinationId,curr_destination_id);
    }

    public static int get_driver_ID_from_delivery(int deliveryId) {
        return Temp_DB.get_driver_ID_from_delivery(deliveryId);
    }

    public static void replace_truck(int deliveryId, int truckId, int weight) {
        Temp_DB.replace_truck(deliveryId, truckId, weight);
    }

    public static boolean item_exists_in_destination(int itemId, int destinationId, int deliveryId) {
        return Temp_DB.item_exists_in_destination(itemId, destinationId, deliveryId);
    }

    public static void remove_item_from_destination(int deliveryId, int itemId, int destinationId) {
        Temp_DB.remove_item_from_destination(deliveryId, itemId, destinationId);
    }
    public static void update_item_quantity_unloaded_in_delivery(int quantity,int item_ID,int delivery_ID){
        Temp_DB.update_item_quantity_unloaded_in_delivery(quantity, item_ID, delivery_ID);
    }

    public static int get_item_quantity_unloaded_in_delivery(int deliveryId, int itemId) {
        return Temp_DB.get_item_quantity_unloaded_in_delivery(deliveryId, itemId);
    }

    public static int calculate_difference_loaded_unloaded(int deliveryId, int itemId, int quantity) {
        return Temp_DB.calculate_difference_loaded_unloaded(deliveryId, itemId, quantity);
    }

    public static void add_difference_to_loading_site(int deliveryId, int itemId, int diff, int itemsFormId) {
        Temp_DB.add_difference_to_loading_site(deliveryId, itemId, diff, itemsFormId);
    }

    public static void update_item_quantity_loaded_in_delivery(int deliveryId, int itemId, int diff) {
        Temp_DB.update_item_quantity_loaded_in_delivery(deliveryId, itemId, diff);
    }

    public static int get_items_amount_in_destination(int deliveryId, int siteId) {
        return Temp_DB.get_items_amount_in_destination(deliveryId, siteId);

    }

    public static int get_destination_site_ID(int deliveryId, int index) {
        return Temp_DB.get_destination_site_ID(deliveryId, index);

    }

    public static int get_item_ID_in_destinations(int deliveryId, int siteId, int item_index) {
        return Temp_DB.get_item_ID_in_destinations(deliveryId, siteId, item_index);
    }

    public static int get_item_quantity_in_destinations(int deliveryId, int siteId, int itemId) {
        return Temp_DB.get_item_quantity_in_destinations(deliveryId, siteId, itemId);
    }

    public static boolean check_driver_schedule(int driverId, String date) {
        return Temp_DB.check_driver_schedule(driverId, date);
    }

    public static boolean get_finished_delivery(int deliveryId) {
        return Temp_DB.get_finished_delivery(deliveryId);
    }

    public static void finished_delivery(int deliveryId) {
        Temp_DB.finished_delivery(deliveryId);
    }

    public static double truck_max_weight(int truckId) {
        return Temp_DB.truck_max_weight(truckId);
    }

    public static boolean has_items_form(int delivery_id) {
        return Temp_DB.has_items_form(delivery_id);
    }

    public static int get_destination_ID(int deliveryId, int i) {
        return Temp_DB.get_destination_ID(deliveryId, i);
    }
}
