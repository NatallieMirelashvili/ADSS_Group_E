package Domain;

public class branch_order {
    private int order_id;
    private String branch_name;
    private String shipping_area;
    private int itemId;
    private String itemName;
    private int amount;

    public branch_order(int order_id, String branch_name, String shipping_area, int itemId, String itemName, int amount) {
        this.order_id = order_id;
        this.branch_name = branch_name;
        this.shipping_area = shipping_area;
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount = amount;
    }

    public int getOrder_id() {
        return order_id;
    }
}
