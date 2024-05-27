package Domain;

public class Delivery {
    private int ID;
    private String date;
    private String hour;
    private truck truck;
    private Driver driver;
    private site origin;
    private site[] destination;
    private items_form items_form;

public Delivery(int ID, String date, String hour, truck truck, Driver driver, site origin, site[] destination, items_form items_form) {
        this.ID = ID;
        this.date = date;
        this.hour = hour;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        this.destination = destination;
        this.items_form = items_form;

    }

    public int getID() {
        return ID;
    }
}
