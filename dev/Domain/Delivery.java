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

//    public int createItems_form(Site destination) {
//        /**
//         * Creates a new items form.
//         * @param destination - the destination of the items form
//         */
//        ArrayList<Item> items = new ArrayList<Item>();
//        Items_form new_items_form = new Items_form(destination, items);
//        item_form.add(new_items_form);
//        return new_items_form.getID();
//    }

//    public void add_item_to_Items_form(int items_form_ID, Item item, int amount) {
//        /**
//         * Adds an item to an items form.
//         * @param items_form_ID - the ID of the items form
//         * @param item - the item to add
//         * @param amount - the amount of the item
//         */
//        for (Items_form items_form_s : item_form) {
//            if (items_form_s.getID() == items_form_ID) {
//                items_form_s.addItem(new Item(item, amount));
//                break;
//            }
//        }
//    }

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

//    public int item_form_size() {
//        /**
//         * Returns the size of the items form.
//         * @return the size of the items form
//         */
//        return item_form.size();
//    }

    public void add_loaded_item(Item item, int quantity) {
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

//    public boolean items_form_exists(int items_form_ID) {
//        /**
//         * Returns whether an items form exists.
//         * @param items_form_ID - the ID of the items form
//         * @return whether an items form exists
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getID() == items_form_ID) {
//                return true;
//            }
//        }
//        return false;
//    }

//    public void remove_item_from_items_form(int itemsFormId, int itemId) {
//        /**
//         * Removes an item from an items form.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getID() == itemsFormId) {
//                items_form.removeItem(itemId);
//                break;
//            }
//        }
//    }

//    public int get_item_quantity_in_items_form(int itemsFormId, int itemId) {
//        /**
//         * Returns the quantity of an item in an items form.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getID() == itemsFormId) {
//                return items_form.getItem(itemId).getAmount_loaded();
//            }
//        }
//        return 0;
//    }

    public void set_amount_of_item_in_items_form(int itemsFormId, int itemId, int quantity) {
        /**
         * Sets the amount of an item in an items form.
         */
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.setAmount(itemId, quantity);
                break;
            }
        }
    }

    public boolean item_exists_in_items_form(int itemsFormId, int itemId) {
        /**
         * Returns whether an item exists in an items form.
         */
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                return items_form.item_exists(itemId);
            }
        }
        return false;
    }

//    public boolean destinations_been_visited(int destinationId, int index) {
//        /**
//         * Returns whether the destinations have been visited.
//         */
//        for (int i = 0; i <= index; i++) {
//            if (item_form.get(i).getDestination().getSite_ID() == destinationId) {
//                return true;
//            }
//        }
//        return false;
//    }

//    public void replace_truck(int truckId, int weight) {
//        /**
//         * Replaces the truck and change its availability accordingly.
//         */
//        this.truck.setAvailability(true);
//        this.truck = Temp_DB.get_truck(truckId);
//        this.truck.setAvailability(false);
//        this.truck.setCurr_weight(weight);
//    }

//    public boolean item_exists_in_destination(int itemId, int destinationId) {
//        /**
//         * Returns whether an item exists in a destination.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getDestination().getSite_ID() == destinationId) {
//                return items_form.item_exists(itemId);
//            }
//        }
//        return false;
//    }

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

//    public void remove_item_from_destination(int itemId, int destinationId) {
//        /**
//         * Removes an item from a destination.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getDestination().getSite_ID() == destinationId) {
//                items_form.removeItem(itemId);
//                break;
//            }
//        }
//    }

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

    public void remove_destination(int destinationId, int curr_destination_id) {
        /**
         * Removes a destination from the delivery.
         * @param destinationId The ID of the destination to be removed.
         * @param curr_destination_id The ID of the current destination.
         */
        // Initialize the loading and unloading forms
        Items_form load_form = null;
        Items_form unload_form = null;

        // Iterate over all items forms in the delivery
        for (Items_form items_form : item_form) {
            // If the destination of the items form matches the destination to be removed, set it as the unloading form
            if (items_form.getDestination().getSite_ID() == destinationId) {
                unload_form = items_form;
                // If the destination of the items form matches the current destination, set it as the loading form
            } else if (items_form.getDestination().getSite_ID() == curr_destination_id) {
                load_form = items_form;
            }
        }
        // Iterate over all items in the unloading form
        for (Item item : unload_form.getItems()) {
            // If the item exists in the loading form, update its loaded amount
            if (load_form.item_exists(item.getItemId())) {
                // Decrease the loaded amount of the item in the loading form by the loaded amount of the item in the unloading form
                load_form.getItem(item.getItemId()).setAmount_loaded(load_form.getItem(item.getItemId()).getAmount_loaded() - item.getAmount_loaded());
                // Decrease the loaded amount of the item in the loaded items by the loaded amount of the item in the unloading form
                decrease_item_in_loaded_items(item.getItemId(), item.getAmount_loaded());
                // If the loaded amount of the item in the loading form is zero or less, remove the item from the loading form and the loaded items
                if (load_form.getItem(item.getItemId()).getAmount_loaded() <= 0) {
                    load_form.removeItem(item.getItemId());
                    remove_item_from_loaded_items(item.getItemId());
                }
            }
        }
        // Remove the unloading form from the items forms of the delivery
        item_form.remove(unload_form);
    }

//    public int get_destination_site_ID(int index) {
//        /**
//         * Returns the site ID of the destination at the given index.
//         */
//        return item_form.get(index).getDestination().getSite_ID();
//    }

//    public int get_items_amount_in_destination(int siteId) {
//        /**
//         * Returns the amount of items in the destination.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getDestination().getSite_ID() == siteId) {
//                return items_form.getItems().size();
//            }
//        }
//        return 0;
//    }

//    public int get_item_ID_in_destinations(int siteId, int itemIndex) {
//        /**
//         * Returns the ID of the item in the destination.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getDestination().getSite_ID() == siteId) {
//                return items_form.getItems().get(itemIndex).getItemId();
//            }
//        }
//        return 0;
//    }

//    public int get_item_quantity_in_destinations(int siteId, int itemId) {
//        /**
//         * Returns the quantity of the item in the destination.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getDestination().getSite_ID() == siteId) {
//                return items_form.getItem(itemId).getAmount_loaded();
//            }
//        }
//        return 0;
//    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

//    public int get_destination_ID(int i) {
//        /**
//         * Returns the ID of the destination at the given index.
//         */
//        return item_form.get(i).getDestination().getSite_ID();
//    }

//    public boolean item_exists_in_diff_items_form(int itemsFormId, int itemId) {
//        /**
//         * Returns whether an item exists in a different items form.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getID() != itemsFormId) {
//                if (items_form.item_exists(itemId)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    public boolean problem_edit_fixed(int itemsFormId, int itemID, int amount) {
//        /**
//         * Returns whether the problem of editing the item is fixed.
//         */
//        for (Items_form items_form : item_form) {
//            if (items_form.getID() == itemsFormId) {
//                if (items_form.item_exists(itemID)) {
//                    if (items_form.getItem(itemID).getAmount_loaded() >= amount) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

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
}