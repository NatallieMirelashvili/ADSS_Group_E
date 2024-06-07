package Domain;
import java.util.ArrayList;

public class items_form {
    int id;
    site destination;
    private ArrayList<Item> items;

    static int counter = 0;
    public items_form(site destination, ArrayList<Item> items) {
    this.id = counter++;
    this.destination = destination;
    this.items = items;
}

        public int getID() {
            return id;
        }
        public site getDestination() {
            return destination;
        }

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setDestination(site destination) {
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
                    item.setAmount(amount);
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

        // לבדוק אם לעשות get לאיטם ואז הסרה, מסיר גם מהשדה עצמו או רק מהקופי שלו

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("items_form{")
                .append("Destination: ").append(destination.getSite_address()).append(" contact: ").append(destination.getSite_contact_name()).append(" phone: ").append(destination.getSite_contact_phone())
                .append(", items=");

        for (Item item : items) {
            sb.append("\n").append(item.toString());
        }

        sb.append('\n');
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
