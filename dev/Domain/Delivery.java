package Domain;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Delivery {
    private int ID;
    private LocalDate date;
    private LocalTime hour;
    private truck truck;
    private Driver driver;
    private site origin;
    private ArrayList<items_form> item_form = new ArrayList<items_form>();

    static int counter = 0;
public Delivery(LocalDate date, LocalTime hour) {
        this.ID = counter++;
        this.date = date;
        this.hour = hour;
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

    public LocalDate getDate(){
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
    public void add_item_to_Items_form(String destination_name, int itemID, int amount) {
        for (items_form items_form : item_form) {
            if (items_form.getDestination().getSite_name().equals(destination_name)) {
                items_form.addItem(new Item(Temp_DB.get_item(itemID), amount));
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
}
