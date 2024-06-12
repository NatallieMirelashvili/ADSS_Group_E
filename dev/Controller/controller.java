package Controller;
import Domain.*;
import com.google.gson.JsonObject;

public class controller {
    public static void add_driver(JsonObject driver) {
        /**
         * Adds a new driver to the database.
         * @param driver The JSON object containing the driver's details.
         */
        Temp_DB.add_driver(driver);
    }
    public static void add_truck(JsonObject truck) {
        /**
         * Adds a new truck to the database.
         * @param truck The JSON object containing the truck's details.
         */
        Temp_DB.add_truck(truck);
    }
    public static void add_site(JsonObject site) {
        /**
         * Adds a new site to the database.
         * @param site The JSON object containing the site's details.
         */
        Temp_DB.add_site(site);
    }
    public static void add_delivery(JsonObject delivery) {
        /**
         * Adds a new delivery to the database.
         * @param delivery The JSON object containing the delivery's details.
         */
        Temp_DB.add_delivery(delivery);
    }
    public static void add_item(JsonObject item) {
        /**
         * Adds a new item to the database.
         * @param item The JSON object containing the item's details.
         */
        Temp_DB.add_item(item);
    }
    public static String get_item_name(int ID) {
        /**
         * Retrieves the name of a site from the database.
         * @param ID The ID of the site.
         * @return The name of the site.
         */
        return Temp_DB.get_item(ID).getItemName();
    }
    public static String get_site_name(int ID) {
        /**
         * Retrieves the name of a site from the database.
         * @param ID The ID of the site.
         * @return The name of the site.
         */
        return Temp_DB.get_site(ID).getSite_name();
    }
    public static boolean delivery_exists(int ID) {
        /**
         * Checks if a delivery exists in the database.
         * @param ID The ID of the delivery.
         * @return True if the delivery exists, false otherwise.
         */
        return Temp_DB.delivery_exists(ID);
    }
    public static boolean site_exists(int ID) {
        /**
         * Checks if a site exists in the database.
         * @param ID The ID of the site.
         * @return True if the site exists, false otherwise.
         */
        return Temp_DB.site_exists(ID);
    }

    public static boolean driver_exists(int ID) {
        /**
         * Checks if a driver exists in the database.
         * @param ID The ID of the driver.
         * @return True if the driver exists, false otherwise.
         */
        return Temp_DB.driver_exists(ID);
    }

    public static boolean truck_exists(int ID) {
        /**
         * Checks if a truck exists in the database.
         * @param ID The ID of the truck.
         * @return True if the truck exists, false otherwise.
         */
        return Temp_DB.truck_exists(ID);
    }

    public static boolean start_driving(int driverID) {
        /**
         * Starts the driving process for a driver.
         * @param driverID The ID of the driver.
         * @return True if the process started successfully, false otherwise.
         */
        return Temp_DB.start_driving(driverID);
    }

    public static boolean end_driving(int driverID) {
        /**
         * Ends the driving process for a driver.
         * @param driverID The ID of the driver.
         * @return True if the process ended successfully, false otherwise.
         */
        return Temp_DB.end_driving(driverID);
    }

    public static boolean driver_password(int driverID, int password) {
        /**
         * Checks if the password entered by the driver is correct.
         * @param driverID The ID of the driver.
         * @param password The password entered by the driver.
         * @return True if the password is correct, false otherwise.
         */
        return Temp_DB.driver_password(driverID, password);
    }

    public static String print_transport_form(int driverID) {
        /**
         * Prints the transport form for a driver.
         * @param driverID The ID of the driver.
         * @return The transport form.
         */
        return Temp_DB.print_transport_form(driverID);
    }

    public static String print_items_form(int ID, int destinationID) {
        /**
         * Prints the items form for a driver.
         * @param ID The ID of the driver.
         * @param destinationID The ID of the destination.
         * @return The items form.
         */
        return Temp_DB.print_items_form(ID, destinationID);
    }

    public static void change_site_area(int ID, String area) {
        /**
         * Changes the area of a site.
         * @param ID The ID of the site.
         * @param area The new area of the site.
         */
        Temp_DB.change_site_area(ID, area);
    }
    public static boolean truck_available(int ID) {
        /**
         * Checks if a truck is available.
         * @param ID The ID of the truck.
         * @return True if the truck is available, false otherwise.
         */
        return Temp_DB.get_truck(ID).isAvailable();
    }
    public static boolean driver_available(int ID) {
        /**
         * Checks if a driver is available.
         * @param ID The ID of the driver.
         * @return True if the driver is available, false otherwise.
         */
        return Temp_DB.get_driver(ID).getAvailability();
    }

    public static boolean check_license(int truckId, int driverId) {
        /**
         * Checks if the license of a driver matches the license of a truck.
         * @param truckId The ID of the truck.
         * @param driverId The ID of the driver.
         * @return True if the licenses match, false otherwise.
         */
        return Temp_DB.get_truck(truckId).getLicense().equals(Temp_DB.get_driver(driverId).getLicense());
    }
    public static String get_site_type(int site_ID) {
        /**
         * Retrieves the type of a site.
         * @param site_ID The ID of the site.
         * @return The type of the site.
         */
        return Temp_DB.get_site(site_ID).get_type();
    }
    public static int get_delivery_ID() {
        /**
         * Retrieves the ID of the delivery.
         * @return The ID of the delivery.
         */
        return Temp_DB.get_delivery_ID();
    }
    public static boolean destination_exists(int delivery_ID, int site_ID) {
        /**
         * Checks if a destination exists in the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param site_ID The ID of the site.
         * @return True if the destination exists, false otherwise.
         */
        return Temp_DB.destination_exists(delivery_ID, site_ID);
    }
    public static boolean same_area(int delivery_ID, int site_ID) {
        /**
         * Checks if the site that was entered is the same area as the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param site_ID The ID of the site.
         * @return True if the delivery and site are in the same area, false otherwise.
         */
        return Temp_DB.same_area(delivery_ID, site_ID);
    }
    public static boolean item_exists(int item_ID) {
        /**
         * Checks if an item exists in the database.
         * @param item_ID The ID of the item.
         * @return True if the item exists, false otherwise.
         */
        return Temp_DB.item_exists(item_ID);
    }
    public static void add_items_form(int delivery_ID, int site_ID) {
        /**
         * Adds a new items form to the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param site_ID The ID of the site.
         */
        Temp_DB.add_items_form(delivery_ID, site_ID);
    }
    public static void add_item_to_items_form(int delivery_ID, int item_form_ID, int item_ID, int quantity) {
        /**
         * Adds a new item to the items form.
         * @param delivery_ID The ID of the delivery.
         * @param item_form_ID The ID of the items form in the delivery.
         * @param item_ID The ID of the item.
         * @param quantity The quantity of the item.
         */
        Temp_DB.add_item_to_Items_form(delivery_ID, item_form_ID, item_ID, quantity);
    }
    public static boolean delivery_starts_now(int delivery_ID) {
        /**
         * Checks if the delivery starts now.
         * @param delivery_ID The ID of the delivery.
         * @return True if the delivery starts now, false otherwise.
         */
        return Temp_DB.delivery_starts_now(delivery_ID);
    }
    public static double weight_check(int delivery_ID){
        /**
         * Checks the weight of the delivery.
         * @param delivery_ID The ID of the delivery.
         * @return The weight of the delivery.
         */
        return Temp_DB.weight_check(delivery_ID);
    }
    public static void setCurr_weight(int delivery_ID, double weight){
/**
         * Sets the current weight of the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param weight The weight of the delivery.
         */
        Temp_DB.setCurr_weight(delivery_ID, weight);
    }

    public static int get_delivery_destinations_size(int delivery_ID){
        /**
         * Retrieves the number of destinations in the delivery.
         * @param delivery_ID The ID of the delivery.
         * @return The number of destinations in the delivery.
         */
        return Temp_DB.get_delivery(delivery_ID).item_form_size();
    }
    public static boolean get_delivery_destinations_loading(int delivery_ID, int index){
        /**
         * Checks if the destination is loading.
         * @param delivery_ID The ID of the delivery.
         * @param index The index of the destination.
         * @return True if the destination is loading, false otherwise.
         */
        return Temp_DB.get_delivery_destinations_loading(delivery_ID, index);
    }
    public static String get_destinations_name(int delivery_ID, int index){
        /**
         * Retrieves the name of the destination in a delivery.
         * @param delivery_ID The ID of the delivery.
         * @param index The index of the destination.
         * @return The name of the destination.
         */
        return Temp_DB.get_destinations_name(delivery_ID, index);
    }
    public static void add_loaded_item(int delivery_ID,int item_ID,int quantity){
        /**
         * Adds a new loaded item to the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param item_ID The ID of the item.
         * @param quantity The quantity of the item.
         */
        Temp_DB.add_loaded_item(delivery_ID, item_ID, quantity);
    }
    public static boolean item_exists_in_delivery(int delivery_ID, int item_ID){
        /**
         * Checks if an item exists in the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param item_ID The ID of the item.
         * @return True if the item exists in the delivery, false otherwise.
         */
        return Temp_DB.item_exists_in_delivery(delivery_ID, item_ID);
    }
    public static int get_item_quantity_in_delivery(int delivery_ID, int item_ID){
        /**
         * Retrieves the quantity of an item in the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param item_ID The ID of the item.
         * @return The quantity of the item in the delivery.
         */
        return Temp_DB.get_item_quantity_in_delivery(delivery_ID, item_ID);
    }
    public static void decrease_item_in_loaded_items(int delivery_ID,int item_ID,int quantity){
        /**
         * Decreases the quantity of an item in the loaded items.
         * @param delivery_ID The ID of the delivery.
         * @param item_ID The ID of the item.
         * @param quantity The quantity to decrease.
         */
        Temp_DB.decrease_item_in_loaded_items(delivery_ID, item_ID, quantity);
    }
    public static void increase_item_in_loaded_items(int delivery_ID,int item_ID,int quantity)
    /**
         * Increases the quantity of an item in the loaded items.
         * @param delivery_ID The ID of the delivery.
         * @param item_ID The ID of the item.
         * @param quantity The quantity to increase.
         */
    {
        Temp_DB.increase_item_in_loaded_items(delivery_ID, item_ID, quantity);
    }
    public static int get_items_form_ID(){
        /**
         * Retrieves the ID of the items form.
         * @return The ID of the items form.
         */
        return Temp_DB.get_items_form_ID();
    }
    public static boolean items_form_exists(int delivery_ID, int items_form_ID){
        /**
         * Checks if the items form exists in the delivery.
         * @param delivery_ID The ID of the delivery.
         * @param items_form_ID The ID of the items form.
         * @return True if the items form exists in the delivery, false otherwise.
         */
        return Temp_DB.items_form_exists(delivery_ID, items_form_ID);
    }
    public static int get_site_in_items_form(int delivery_ID, int items_form_ID){
        /**
         * Retrieves the site in the items form.
         * @param delivery_ID The ID of the delivery.
         * @param items_form_ID The ID of the items form.
         * @return The site in the items form.
         */
        return Temp_DB.get_site_in_items_form(delivery_ID, items_form_ID);
    }

    public static void remove_item_from_items_form(int deliveryId, int itemsFormId, int itemId) {
        /**
         * Removes an item from an items form in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param itemId The ID of the item.
         */
        Temp_DB.remove_item_from_items_form(deliveryId, itemsFormId, itemId);
    }

    public static void remove_loaded_item(int deliveryId, int itemId) {
        /**
         * Removes a loaded item from a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         */
        Temp_DB.remove_loaded_item(deliveryId, itemId);
    }

    public static int get_item_quantity_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        /**
         * Retrieves the quantity of an item in an items form.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param itemId The ID of the item.
         * @return The quantity of the item in the items form.
         */
        return Temp_DB.get_item_quantity_in_items_form(deliveryId, itemsFormId, itemId);
    }

    public static void set_amount_of_item_in_items_form(int deliveryId, int itemsFormId, int itemId, int quantity) {
        /**
         * Sets the quantity of an item in an items form in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param itemId The ID of the item.
         * @param quantity The quantity of the item.
         */
        Temp_DB.set_amount_of_item_in_items_form(deliveryId, itemsFormId, itemId, quantity);
    }

    public static boolean item_exists_in_items_form(int deliveryId, int itemsFormId, int itemId) {
        /**
         * Checks if an item exists in an items form in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param itemId The ID of the item.
         * @return True if the item exists in the items form, false otherwise.
         */
        return Temp_DB.item_exists_in_items_form(deliveryId, itemsFormId, itemId);
    }
    public static boolean destinations_been_visited(int delivery_ID, int destination_ID, int index){
        /**
         * Checks if the destination has been visited.
         * @param delivery_ID The ID of the delivery.
         * @param destination_ID The ID of the destination.
         * @param index The index of the destination.
         * @return True if the destination has been visited, false otherwise.
         */
        return Temp_DB.destinations_been_visited(delivery_ID, destination_ID, index);
    }

    public static void remove_destination(int deliveryId, int destinationId,int curr_destination_id) {
        /**
         * Removes a destination from a delivery.
         * @param deliveryId The ID of the delivery.
         * @param destinationId The ID of the destination.
         * @param curr_destination_id The current destination ID.
         */
        Temp_DB.remove_destination(deliveryId, destinationId,curr_destination_id);
    }

    public static int get_driver_ID_from_delivery(int deliveryId) {
        /**
         * Retrieves the ID of the driver from a delivery.
         * @param deliveryId The ID of the delivery.
         * @return The ID of the driver.
         */
        return Temp_DB.get_driver_ID_from_delivery(deliveryId);
    }

    public static void replace_truck(int deliveryId, int truckId, int weight) {
        /**
         * Replaces the truck in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param truckId The ID of the truck.
         * @param weight The weight of the truck.
         */
        Temp_DB.replace_truck(deliveryId, truckId, weight);
    }

    public static boolean item_exists_in_destination(int itemId, int destinationId, int deliveryId) {
        /**
         * Checks if an item exists in a destination in a delivery.
         * @param itemId The ID of the item.
         * @param destinationId The ID of the destination.
         * @param deliveryId The ID of the delivery.
         * @return True if the item exists in the destination, false otherwise.
         */
        return Temp_DB.item_exists_in_destination(itemId, destinationId, deliveryId);
    }

    public static void remove_item_from_destination(int deliveryId, int itemId, int destinationId) {
        /**
         * Removes an item from a destination in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         * @param destinationId The ID of the destination.
         */
        Temp_DB.remove_item_from_destination(deliveryId, itemId, destinationId);
    }
    public static void update_item_quantity_unloaded_in_delivery(int quantity,int item_ID,int delivery_ID){
        /**
         * Updates the quantity of an item that needs to be unloaded in a delivery.
         * @param quantity The quantity of the item.
         * @param item_ID The ID of the item.
         * @param delivery_ID The ID of the delivery.
         */
        Temp_DB.update_item_quantity_unloaded_in_delivery(quantity, item_ID, delivery_ID);
    }

    public static int get_item_quantity_unloaded_in_delivery(int deliveryId, int itemId) {
        /**
         * Retrieves the quantity of an item that needs to be unloaded in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         * @return The quantity of the item that needs to be unloaded.
         */
        return Temp_DB.get_item_quantity_unloaded_in_delivery(deliveryId, itemId);
    }

    public static int calculate_difference_loaded_unloaded(int deliveryId, int itemId, int quantity) {
        /**
         * Calculates the difference between the amount of the item that needs to be loaded and unloaded .
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         * @param quantity The quantity of the item.
         * @return The difference between the loaded and unloaded items.
         */
        return Temp_DB.calculate_difference_loaded_unloaded(deliveryId, itemId, quantity);
    }

    public static void add_difference_to_loading_site(int deliveryId, int itemId, int diff, int itemsFormId) {
        /**
         * Adds the difference between the loaded and unloaded item to the loading site at an item form at a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         * @param diff The difference between the loaded and unloaded items.
         * @param itemsFormId The ID of the items form.
         */
        Temp_DB.add_difference_to_loading_site(deliveryId, itemId, diff, itemsFormId);
    }

    public static void update_item_quantity_loaded_in_delivery(int deliveryId, int itemId, int diff) {
        /**
         * Updates the quantity of an item that has been loaded in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         * @param diff The difference between the loaded and unloaded items.
         */
        Temp_DB.update_item_quantity_loaded_in_delivery(deliveryId, itemId, diff);
    }

    public static int get_items_amount_in_destination(int deliveryId, int siteId) {
        /**
         * Retrieves the amount of items in a destination in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param siteId The ID of the site.
         * @return The amount of items in the destination.
         */
        return Temp_DB.get_items_amount_in_destination(deliveryId, siteId);

    }

    public static int get_destination_site_ID(int deliveryId, int index) {
        /**
         * Retrieves the ID of the site in a destination in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param index The index of the destination.
         * @return The ID of the site in the destination.
         */
        return Temp_DB.get_destination_site_ID(deliveryId, index);

    }

    public static int get_item_ID_in_destinations(int deliveryId, int siteId, int item_index) {
        /**
         * Retrieves the ID of an item in a destination in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param siteId The ID of the site.
         * @param item_index The index of the item.
         * @return The ID of the item in the destination.
         */
        return Temp_DB.get_item_ID_in_destinations(deliveryId, siteId, item_index);
    }

    public static int get_item_quantity_in_destinations(int deliveryId, int siteId, int itemId) {
        /**
         * Retrieves the quantity of an item in a destination in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param siteId The ID of the site.
         * @param itemId The ID of the item.
         * @return The quantity of the item in the destination.
         */
        return Temp_DB.get_item_quantity_in_destinations(deliveryId, siteId, itemId);
    }

    public static boolean check_driver_schedule(int driverId, String date) {
        /**
         * Checks the schedule of a driver.
         * @param driverId The ID of the driver.
         * @param date The date of the schedule.
         * @return True if the driver has a delivery in a specific date, false otherwise.
         */
        return Temp_DB.check_driver_schedule(driverId, date);
    }

    public static boolean get_finished_delivery(int deliveryId) {
        /**
         * Checks if a delivery has been finished.
         * @param deliveryId The ID of the delivery.
         * @return True if the delivery has been finished, false otherwise.
         */
        return Temp_DB.get_finished_delivery(deliveryId);
    }

    public static void finished_delivery(int deliveryId) {
        /**
         * Marks a delivery as finished.
         * @param deliveryId The ID of the delivery.
         */
        Temp_DB.finished_delivery(deliveryId);
    }

    public static double truck_max_weight(int truckId) {
        /**
         * Retrieves the maximum weight of a truck.
         * @param truckId The ID of the truck.
         * @return The maximum weight of the truck.
         */
        return Temp_DB.truck_max_weight(truckId);
    }

    public static boolean has_items_form(int delivery_id) {
        /**
         * Checks if a delivery has an items form.
         * @param delivery_id The ID of the delivery.
         * @return True if the delivery has an items form, false otherwise.
         */
        return Temp_DB.has_items_form(delivery_id);
    }

    public static int get_destination_ID(int deliveryId, int i) {
        /**
         * Retrieves the ID of a destination in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param i The index of the destination.
         * @return The ID of the destination.
         */
        return Temp_DB.get_destination_ID(deliveryId, i);
    }

    public static boolean item_exists_in_diff_items_form(int deliveryId, int itemsFormId, int itemId) {
        /**
         * Checks if an item exists in a different items form in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param itemId The ID of the item.
         * @return True if the item exists in the items form, false otherwise.
         */
        return Temp_DB.item_exists_in_diff_items_form(deliveryId, itemsFormId, itemId);
    }

    public static boolean problem_edit_fixed(int deliveryId, int itemsFormId, int itemID, int amount) {
        /**
         * checks if the problem with the item form with a specific item,amount and delivery while editing was fixed.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param itemID The ID of the item.
         * @param amount The amount of the item.
         * @return True if the problem was fixed, false otherwise.
         */
        return Temp_DB.problem_edit_fixed(deliveryId, itemsFormId, itemID, amount);
    }
}
