package Domain;

public class Item {
    // Private attributes for item details
    private int itemId;
    private String itemName;
    private int amount_loaded;

    private int amount_unloaded;


    // Constructor to initialize item details
    public Item(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount_loaded = 0;
        this.amount_unloaded = 0;
    }

    public Item(Item old_item, int amount){
        this.itemId = old_item.getItemId();
        this.itemName = old_item.getItemName();
        this.amount_loaded = amount;
    }

    // Getter and setter methods for itemId
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    // Getter and setter methods for itemName
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Getter and setter methods for amount
    public int getAmount_loaded() {
        return amount_loaded;
    }

    public void setAmount_loaded(int amount) {
        this.amount_loaded = amount;
    }
    public int getAmount_unloaded() {
        return amount_unloaded;
    }

    public void setAmount_unloaded(int amount_unloaded) {
        this.amount_unloaded = amount_unloaded;
    }

    @Override
    public String toString() {
        return  "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount_loaded +
                '\n';
    }

    public Item getItem() {
        return this;
    }
}
