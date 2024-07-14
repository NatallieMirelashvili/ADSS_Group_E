package Domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Delivery {
    private int ID;
    private LocalDate date;
    private LocalTime hour;
    private Truck truck;
    private Driver driver;
    private Site origin;
    private ArrayList<Items_form> item_form = new ArrayList<Items_form>();
    static int counter = 0;

    private HashMap<Integer, Item> loaded_items = new HashMap<Integer, Item>();

    private boolean finished = false;

    public Delivery(LocalDate date, LocalTime hour, Truck truck, Driver driver, Site origin) {
        /**
         * Creates a new delivery.
         * @param date - the date of the delivery
         * @param hour - the hour of the delivery
         * @param truck - the truck of the delivery
         * @param driver - the driver of the delivery
         * @param origin - the origin of the delivery
         */
        this.ID = counter++;
        this.date = date;
        this.hour = hour;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        loaded_items = new HashMap<Integer, Item>();
    }
    public Delivery(int id,LocalDate date, LocalTime hour, Truck truck, Driver driver, Site origin) {
        /**
         * Creates a new delivery.
         * @param date - the date of the delivery
         * @param hour - the hour of the delivery
         * @param truck - the truck of the delivery
         * @param driver - the driver of the delivery
         * @param origin - the origin of the delivery
         */
        this.ID = id;
        this.date = date;
        this.hour = hour;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        loaded_items = new HashMap<Integer, Item>();
        counter++;
    }


    public static int getCounter() {
        /**
         * Returns the counter of the delivery.
         * @return the counter of the delivery
         */
        return (counter);
    }

    public int getID() {
        /**
         * Returns the ID of the delivery.
         * @return the ID of the delivery
         */
        return ID;
    }
    public LocalTime getHour() {
        /**
         * Returns the hour of the delivery.
         * @return the hour of the delivery
         */
        return hour;
    }
    public void setTruck(Truck truck) {
        /**
         * Sets the truck of the delivery.
         * @param truck - the truck of the delivery
         */
        this.truck = truck;
    }

    public void setDriver(Driver driver) {
        /**
         * Sets the driver of the delivery.
         * @param driver - the driver of the delivery
         */
        this.driver = driver;
    }

    public void setOrigin(Site origin) {
        /**
         * Sets the origin of the delivery.
         * @param origin - the origin of the delivery
         */
        this.origin = origin;
    }

    public int getDriverID() {
        /**
         * Returns the ID of the driver of the delivery.
         * @return the ID of the driver of the delivery
         */
        return driver.getID();
    }

    public LocalDate getDate() {
        /**
         * Returns the date of the delivery.
         * @return the date of the delivery
         */
        return this.date;
    }

    public Truck getTruck() {
        /**
         * Returns the truck of the delivery.
         * @return the truck of the delivery
         */
        return this.truck;
    }

    public void removeItems_form(int items_formID) {
        /**
         * Removes an items form.
         * @param items_formID - the ID of the items form
         */
        for (Items_form items_form : item_form) {
            if (items_form.getID() == items_formID) {
                item_form.remove(items_form);
                break;
            }
        }
    }

    public String toString() {
        /**
         * Returns the string representation of the delivery.
         * @return the string representation of the delivery
         */
        StringBuilder sb = new StringBuilder();
        sb.append("Delivery ID: ").append(ID)
                .append("\nDate: ").append(date)
                .append("\nHour: ").append(hour)
                .append("\nTruck ID: ").append(truck.getID())
                .append("\nDriver ID: ").append(driver.getID()).append(" , ").append(driver.getName())
                .append("\nOrigin address: ").append(origin.getSite_address()).append(" contact: ").append(origin.getSite_contact_name()).append(" phone: ").append(origin.getSite_contact_phone()).append("\n");

        for (Items_form items_form : item_form) {
            sb.append(items_form.toString()).append("\n");
        }

        return sb.toString();
    }

    public ArrayList<Items_form> getItems_form() {
        /**
         * Returns the items form of the delivery.
         * @return the items form of the delivery
         */
        return item_form;
    }

    public void add_loaded_item_nquantity(Item item, int quantity) {
        /**
         * Adds a loaded item.
         * @param item - the item to add
         * @param quantity - the quantity of the item
         */
        if (loaded_items.containsKey(item.getItemId())) {
            Item loaded_item = loaded_items.get(item.getItemId());
            loaded_item.setAmount_loaded(loaded_item.getAmount_loaded() + quantity);
            return;
        }
        loaded_items.put(item.getItemId(), new Item(item, quantity));
    }

    public boolean item_loaded(int item_ID) {
        /**
         * Returns whether an item is loaded.
         * @param item_ID - the ID of the item
         * @return whether an item is loaded
         */
        return loaded_items.containsKey(item_ID);
    }

    public int get_item_quantity(int item_ID) {
        /**
         * Returns the quantity of an item in the loaded items.
         * @param item_ID - the ID of the item
         * @return the quantity of an item
         */
        return loaded_items.get(item_ID).getAmount_loaded();
    }

    public Item get_item_loaded(int item_ID) {
        /**
         * Returns the item loaded.
         * @param item_ID - the ID of the item
         * @return the item loaded
         */
        return loaded_items.get(item_ID);
    }

    public void decrease_item_in_loaded_items(int item_id, int quantity) {
        /**
         * Decreases the quantity of an item in the loaded items.
         */
        loaded_items.get(item_id).setAmount_loaded(loaded_items.get(item_id).getAmount_loaded() - quantity);
    }

    public void increase_item_in_loaded_items(int item_id, int quantity) {
        /**
         * Increases the quantity of an item in the loaded items.
         */
        loaded_items.get(item_id).setAmount_loaded(loaded_items.get(item_id).getAmount_loaded() + (quantity-loaded_items.get(item_id).getAmount_loaded()));
    }

    public void remove_item_from_loaded_items(int item_ID) {
        /**
         * Removes an item from the loaded items.
         * @param item_ID - the ID of the item
         */
        loaded_items.remove(item_ID);
    }
   public void update_item_quantity_unloaded_in_delivery(int quantity, int itemId) {
        /**
         * Updates the quantity of an item unloaded in the delivery.
         */
        loaded_items.get(itemId).setAmount_unloaded(quantity + loaded_items.get(itemId).getAmount_unloaded());
    }

    public int get_item_quantity_unloaded_in_delivery(int itemId) {
        /**
         * Returns the quantity of an item unloaded in the delivery.
         */
        return loaded_items.get(itemId).getAmount_unloaded();
    }

    public int calculate_difference_loaded_unloaded(int itemId, int quantity) {
        /**
         * Calculates the difference between the loaded and unloaded items.
         */
        return (loaded_items.get(itemId).getAmount_unloaded() + quantity) - loaded_items.get(itemId).getAmount_loaded();
    }

    public void add_difference_to_loading_site(int itemId, int diff, int itemsFormId) {
        /**
         * Adds the difference to the loading site.
         */
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.getItem(itemId).setAmount_loaded(items_form.getItem(itemId).getAmount_loaded() + diff);
                break;
            }
        }
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getTruckID() {
        return truck.getID();
    }

    public int getSiteID() {
        return origin.getSite_ID();
    }

    public void add_items_form(Items_form itemsForm) {
        item_form.add(itemsForm);
    }

    public Site getOrigin() {
        return origin;
    }

    public Driver getDriver() {
        return driver;
    }

    public void add_loaded_item(Item newItem) {
        if (!loaded_items.containsKey(newItem.getItemId())) {
            loaded_items.put(newItem.getItemId(), newItem);
        }
    }
}