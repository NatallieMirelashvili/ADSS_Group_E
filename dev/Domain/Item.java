package Domain;

public class Item {
    // Private attributes for item details
    private String itemId;
    private String itemName;
    private int amount;

    // Constructor to initialize item details
    public Item(String itemId, String itemName, int amount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount = amount;
    }

    // Getter and setter methods for itemId
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
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
        return "Domain.Item{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                '}';
    }

}
