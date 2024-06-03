package Domain;

public class Item {
    // Private attributes for item details
    private int itemId;
    private String itemName;
    private int amount;

    // Constructor to initialize item details
    public Item(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount = 0;
    }

    public Item(Item old_item, int amount){
        this.itemId = old_item.getItemId();
        this.itemName = old_item.getItemName();
        this.amount = amount;
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
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                '\n';
    }

    public Item getItem() {
        return this;
    }
}
