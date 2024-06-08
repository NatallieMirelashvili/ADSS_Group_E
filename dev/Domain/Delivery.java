package Domain;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Delivery {
    private int ID;
    private LocalDate date;
    private LocalTime hour;
    private truck truck;
    private Driver driver;
    private site origin;
    private ArrayList<items_form> item_form = new ArrayList<items_form>();
    static int counter = 0;

    private HashMap<Integer, Item> loaded_items = new HashMap<Integer, Item>();

    public Delivery(LocalDate date, LocalTime hour, truck truck, Driver driver, site origin) {
        this.ID = counter++;
        this.date = date;
        this.hour = hour;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        loaded_items = new HashMap<Integer, Item>();
    }

    public static int getCounter() {
        return (counter);
    }

    public int getID() {
        return ID;
    }

    public void setTruck(truck truck) {
        this.truck = truck;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setOrigin(site origin) {
        this.origin = origin;
    }

    public int getDriverID() {
        return driver.getID();
    }

    public LocalDate getDate() {
        return this.date;
    }

    public truck getTruck_of_delivery() {
        return this.truck;
    }

    public void createItems_form(site destination) {
        ArrayList<Item> items = new ArrayList<Item>();
        items_form new_items_form = new items_form(destination, items);
        item_form.add(new_items_form);
    }

    public void add_item_to_Items_form(int items_form_ID, Item item, int amount) {
        for (items_form items_form_s : item_form) {
            if (items_form_s.getID() == items_form_ID) {
                items_form_s.addItem(new Item(item, amount));
                break;
            }
        }
    }

    public void removeItems_form(int items_formID) {
        for (items_form items_form : item_form) {
            if (items_form.getID() == items_formID) {
                item_form.remove(items_form);
                break;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Delivery ID: ").append(ID)
                .append("\nDate: ").append(date)
                .append("\nHour: ").append(hour)
                .append("\nTruck: ").append(truck.getID())
                .append("\nDriver: ").append(driver.getID()).append(",").append(driver.getName())
                .append("\nOrigin address: ").append(origin.getSite_address()).append(" contact: ").append(origin.getSite_contact_name()).append(" phone: ").append(origin.getSite_contact_phone());

        for (items_form items_form : item_form) {
            sb.append("\n").append(items_form.toString());
        }

        return sb.toString();
    }

    public ArrayList<items_form> getItems_form() {
        return item_form;
    }

    public int item_form_size() {
        return item_form.size();
    }

    public void add_loaded_item(Item item, int quantity) {
        if (loaded_items.containsKey(item.getItemId())) {
            Item loaded_item = loaded_items.get(item.getItemId());
            loaded_item.setAmount(loaded_item.getAmount() + quantity);
            return;
        }
        loaded_items.put(item.getItemId(), new Item(item, quantity));
    }

    public boolean item_loaded(int item_ID) {
        return loaded_items.containsKey(item_ID);
    }

    public int get_item_quantity(int item_ID) {
        return loaded_items.get(item_ID).getAmount();
    }

    public Item get_item_loaded(int item_ID) {
        return loaded_items.get(item_ID);
    }

    public void decrease_item_in_loaded_items(int item_id, int quantity) {
        loaded_items.get(item_id).setAmount(loaded_items.get(item_id).getAmount() - quantity);
    }
    public void increase_item_in_loaded_items(int item_id, int quantity) {
        loaded_items.get(item_id).setAmount(quantity);
    }

    public void remove_item_from_loaded_items(int item_ID) {
        loaded_items.remove(item_ID);
    }

    public boolean items_form_exists(int items_form_ID) {
        for (items_form items_form : item_form) {
            if (items_form.getID() == items_form_ID) {
                return true;
            }
        }
        return false;
    }

    public void remove_item_from_items_form(int itemsFormId, int itemId) {
        for (items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.removeItem(itemId);
                break;
            }
        }
    }

    public int get_item_quantity_in_items_form(int itemsFormId, int itemId) {
        for (items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                return items_form.getItem(itemId).getAmount();
            }
        }
        return 0;
    }

    public void set_amount_of_item_in_items_form(int itemsFormId, int itemId, int quantity) {
        for (items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.setAmount(itemId, quantity);
                break;
            }
        }
    }

    public boolean item_exists_in_items_form(int itemsFormId, int itemId) {
        for (items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                return items_form.item_exists(itemId);
            }
        }
        return false;
    }

    public boolean destinations_been_visited(int destinationId, int index) {
        for (int i = 0; i <= index; i++){
            if (item_form.get(i).getDestination().getSite_ID() == destinationId){
                return true;
            }
        }
        return false;
    }

    public void replace_truck(int truckId, int weight) {
        this.truck.setAvailability(true);
        this.truck = Temp_DB.get_truck(truckId);
        this.truck.setAvailability(false);
        this.truck.setCurr_weight(weight);
    }
}