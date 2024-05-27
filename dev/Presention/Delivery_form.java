package Presention;

public class Delivery_form {

    private int ID;
    private String date;
    private String hour;
    private int truck_id;
    private int driver_id;
    private String origin;
    private String[] destination;
    private int[] order_id;

    public Delivery_form(int ID, String date, String hour, int truck_id, int driver_id, String origin, String[] destination, int[] order_id) {
        this.ID = ID;
        this.date = date;
        this.hour = hour;
        this.truck_id = truck_id;
        this.driver_id = driver_id;
        this.origin = origin;
        this.destination = destination;
        this.order_id = order_id;
    }
}
