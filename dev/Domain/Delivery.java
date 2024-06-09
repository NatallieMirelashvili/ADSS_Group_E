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

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setOrigin(Site origin) {
        this.origin = origin;
    }

    public int getDriverID() {
        return driver.getID();
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Truck getTruck_of_delivery() {
        return this.truck;
    }

    public void createItems_form(Site destination) {
        ArrayList<Item> items = new ArrayList<Item>();
        Items_form new_items_form = new Items_form(destination, items);
        item_form.add(new_items_form);
    }

    public void add_item_to_Items_form(int items_form_ID, Item item, int amount) {
        for (Items_form items_form_s : item_form) {
            if (items_form_s.getID() == items_form_ID) {
                items_form_s.addItem(new Item(item, amount));
                break;
            }
        }
    }

    public void removeItems_form(int items_formID) {
        for (Items_form items_form : item_form) {
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
                .append("\nTruck ID: ").append(truck.getID())
                .append("\nDriver ID: ").append(driver.getID()).append(" , ").append(driver.getName())
                .append("\nOrigin address: ").append(origin.getSite_address()).append(" contact: ").append(origin.getSite_contact_name()).append(" phone: ").append(origin.getSite_contact_phone()).append("\n");

        for (Items_form items_form : item_form) {
            sb.append(items_form.toString()).append("\n");
        }

        return sb.toString();
    }

    public ArrayList<Items_form> getItems_form() {
        return item_form;
    }

    public int item_form_size() {
        return item_form.size();
    }

    public void add_loaded_item(Item item, int quantity) {
        if (loaded_items.containsKey(item.getItemId())) {
            Item loaded_item = loaded_items.get(item.getItemId());
            loaded_item.setAmount_loaded(loaded_item.getAmount_loaded() + quantity);
            return;
        }
        loaded_items.put(item.getItemId(), new Item(item, quantity));
    }

    public boolean item_loaded(int item_ID) {
        return loaded_items.containsKey(item_ID);
    }

    public int get_item_quantity(int item_ID) {
        return loaded_items.get(item_ID).getAmount_loaded();
    }

    public Item get_item_loaded(int item_ID) {
        return loaded_items.get(item_ID);
    }

    public void decrease_item_in_loaded_items(int item_id, int quantity) {
        loaded_items.get(item_id).setAmount_loaded(loaded_items.get(item_id).getAmount_loaded() - quantity);
    }
    public void increase_item_in_loaded_items(int item_id, int quantity) {
        loaded_items.get(item_id).setAmount_loaded(loaded_items.get(item_id).getAmount_loaded() + quantity);
    }

    public void remove_item_from_loaded_items(int item_ID) {
        loaded_items.remove(item_ID);
    }

    public boolean items_form_exists(int items_form_ID) {
        for (Items_form items_form : item_form) {
            if (items_form.getID() == items_form_ID) {
                return true;
            }
        }
        return false;
    }

    public void remove_item_from_items_form(int itemsFormId, int itemId) {
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.removeItem(itemId);
                break;
            }
        }
    }

    public int get_item_quantity_in_items_form(int itemsFormId, int itemId) {
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                return items_form.getItem(itemId).getAmount_loaded();
            }
        }
        return 0;
    }

    public void set_amount_of_item_in_items_form(int itemsFormId, int itemId, int quantity) {
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.setAmount(itemId, quantity);
                break;
            }
        }
    }

    public boolean item_exists_in_items_form(int itemsFormId, int itemId) {
        for (Items_form items_form : item_form) {
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

    public boolean item_exists_in_destination(int itemId, int destinationId) {
        for (Items_form items_form : item_form) {
            if (items_form.getDestination().getSite_ID() == destinationId) {
                return items_form.item_exists(itemId);
            }
        }
        return false;
    }

    public void update_item_quantity_unloaded_in_delivery(int quantity, int itemId) {
        loaded_items.get(itemId).setAmount_unloaded(quantity+loaded_items.get(itemId).getAmount_unloaded());
    }

    public int get_item_quantity_unloaded_in_delivery(int itemId) {
        return loaded_items.get(itemId).getAmount_unloaded();
    }

    public void remove_item_from_destination(int itemId, int destinationId) {
        for (Items_form items_form : item_form) {
            if (items_form.getDestination().getSite_ID() == destinationId) {
                items_form.removeItem(itemId);
                break;
            }
        }
    }

    public int calculate_difference_loaded_unloaded(int itemId, int quantity) {
        return (loaded_items.get(itemId).getAmount_unloaded() + quantity) - loaded_items.get(itemId).getAmount_loaded();
    }

    public void add_difference_to_loading_site(int itemId, int diff, int itemsFormId) {
        for (Items_form items_form : item_form) {
            if (items_form.getID() == itemsFormId) {
                items_form.getItem(itemId).setAmount_loaded(items_form.getItem(itemId).getAmount_loaded() + diff);
                break;
            }
        }
    }

    public void remove_destination(int destinationId, int curr_destination_id) {
        Items_form load_form = null;
        Items_form unload_form = null;
        for (Items_form items_form : item_form) {
            if (items_form.getDestination().getSite_ID() == destinationId) {
                unload_form = items_form;
            } else if(items_form.getDestination().getSite_ID() == curr_destination_id) {
                load_form = items_form;
            }
        }
        for (Item item : unload_form.getItems()) {
            if (load_form.item_exists(item.getItemId())) {
                load_form.getItem(item.getItemId()).setAmount_loaded(load_form.getItem(item.getItemId()).getAmount_loaded() - item.getAmount_loaded());
                decrease_item_in_loaded_items(item.getItemId(), item.getAmount_loaded());
                if (load_form.getItem(item.getItemId()).getAmount_loaded() <=0) {
                    load_form.removeItem(item.getItemId());
                    remove_item_from_loaded_items(item.getItemId());
                }
            }
        }
        item_form.remove(unload_form);
    }

    public int get_destination_site_ID(int index) {
        return item_form.get(index).getDestination().getSite_ID();
    }

    public int get_items_amount_in_destination(int siteId) {
        for (Items_form items_form : item_form) {
            if (items_form.getDestination().getSite_ID() == siteId) {
                return items_form.getItems().size();
            }
        }
        return 0;
    }

    public int get_item_ID_in_destinations(int siteId, int itemIndex) {
        for (Items_form items_form : item_form) {
            if (items_form.getDestination().getSite_ID() == siteId) {
                return items_form.getItems().get(itemIndex).getItemId();
            }
        }
        return 0;
    }

    public int get_item_quantity_in_destinations(int siteId, int itemId) {
        for (Items_form items_form : item_form) {
            if (items_form.getDestination().getSite_ID() == siteId) {
                return items_form.getItem(itemId).getAmount_loaded();
            }
        }
        return 0;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public int get_destination_ID(int i) {
        return item_form.get(i).getDestination().getSite_ID();
    }
}