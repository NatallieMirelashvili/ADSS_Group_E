package Presention;

import Domain.Item;

public class Delivery_order {
    private String order_id;
    private String shipping_area;
    private int itemId;
    private String itemName;
    private int amount;

    public Delivery_order(String order_id, String shipping_area, int itemId, String itemName, int amount) {
        this.order_id = order_id;
        this.shipping_area = shipping_area;
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount = amount;
    }
}
