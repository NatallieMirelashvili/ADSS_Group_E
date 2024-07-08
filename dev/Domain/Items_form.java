package Domain;
import java.util.ArrayList;

public class Items_form {
    int id;
    Site destination;
    private ArrayList<Item> items;

    static int counter = 0;
    public Items_form(Site destination, ArrayList<Item> items) {
    this.id = counter++;
    this.destination = destination;
    this.items = items;
}
    public Items_form(Site destination) {
        this.id = counter++;
        this.destination = destination;
        this.items = new ArrayList<Item>();
    }

        public int getID() {
            return id;
        }
        public Site getDestination() {
            return destination;
        }

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setDestination(Site destination) {
            this.destination = destination;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }

        public void addItem(Item item) {
            items.add(item);
        }

        public void removeItem(int itemID) {
            for (Item item : items) {
                if (item.getItemId() == itemID) {
                    items.remove(item);
                    break;
                }
            }
        }

        public void setAmount(int itemID, int amount) {
            for (Item item : items) {
                if (item.getItemId() == itemID) {
                    item.setAmount_loaded(amount);
                    break;
                }
            }
        }

        public Item getItem(int itemID) {
            for (Item item : items) {
                if (item.getItemId() == itemID) {
                    return item;
                }
            }
            return null;
        }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("items_form ID: ").append(id).append("\n{\n")
                .append("Destination: ").append(destination.getSite_address()).append(" contact: ").append(destination.getSite_contact_name()).append(" phone: ").append(destination.getSite_contact_phone())
                .append(",\nitems:\n");

        for (Item item : items) {
            sb.append(item.toString());
        }
        sb.append("}");
        return sb.toString();
    }
    public static int getCounter() {
        return counter;
    }

    public boolean item_exists(int itemId) {
        for (Item item : items) {
            if (item.getItemId() == itemId) {
                return true;
            }
        }
        return false;
    }
}
