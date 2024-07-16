package Domain;
import java.util.ArrayList;

public class Items_form {
    int id;
    Site destination;
    private ArrayList<Product_to_Delivery> producttoDeliveries;

    static int counter = 0;
    public Items_form(Site destination, ArrayList<Product_to_Delivery> producttoDeliveries) {
    this.id = counter++;
    this.destination = destination;
    this.producttoDeliveries = producttoDeliveries;
}
    public Items_form(Site destination) {
        this.id = counter++;
        this.destination = destination;
        this.producttoDeliveries = new ArrayList<Product_to_Delivery>();
    }
    public Items_form(int id, Site destination) {
        this.id = id;
        this.destination = destination;
        this.producttoDeliveries = new ArrayList<Product_to_Delivery>();
        counter++;
    }

        public int getID() {
            return id;
        }
        public Site getDestination() {
            return destination;
        }

        public ArrayList<Product_to_Delivery> getItems() {
            return producttoDeliveries;
        }

        public void setDestination(Site destination) {
            this.destination = destination;
        }

        public void setItems(ArrayList<Product_to_Delivery> producttoDeliveries) {
            this.producttoDeliveries = producttoDeliveries;
        }

        public void addItem(Product_to_Delivery producttoDelivery) {
            producttoDeliveries.add(producttoDelivery);
        }

        public void removeItem(int itemID) {
            for (Product_to_Delivery producttoDelivery : producttoDeliveries) {
                if (producttoDelivery.getItemId() == itemID) {
                    producttoDeliveries.remove(producttoDelivery);
                    break;
                }
            }
        }

        public void setAmount(int itemID, int amount) {
            for (Product_to_Delivery producttoDelivery : producttoDeliveries) {
                if (producttoDelivery.getItemId() == itemID) {
                    producttoDelivery.setAmount_loaded(amount);
                    break;
                }
            }
        }

        public Product_to_Delivery getItem(int itemID) {
            for (Product_to_Delivery producttoDelivery : producttoDeliveries) {
                if (producttoDelivery.getItemId() == itemID) {
                    return producttoDelivery;
                }
            }
            return null;
        }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("items_form ID: ").append(id).append("\n{\n")
                .append("Destination: ").append(destination.getSite_address()).append(" Type: ").append(destination.get_type()).append(" contact: ").append(destination.getSite_contact_name()).append(" phone: ").append(destination.getSite_contact_phone())
                .append(",\nitems:\n");

        for (Product_to_Delivery producttoDelivery : producttoDeliveries) {
            sb.append(producttoDelivery.toString());
        }
        sb.append("}");
        return sb.toString();
    }
    public static int getCounter() {
        return counter;
    }

    public boolean item_exists(int itemId) {
        for (Product_to_Delivery producttoDelivery : producttoDeliveries) {
            if (producttoDelivery.getItemId() == itemId) {
                return true;
            }
        }
        return false;
    }
}
