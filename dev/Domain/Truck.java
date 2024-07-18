package Domain;

public class Truck {
    private int id;
    private String model;
    private double curr_weight;
    private double max_weight;
    private String licence;
    private boolean availability;


    public Truck(int id, String model, double max_weight, String licence) {
        this.id = id;
        this.model = model;
        this.curr_weight = 0;
        this.max_weight = max_weight;
        this.licence = licence;
        this.availability = true;
    }

    public int getID() {
        return id;
    }
    public double get_max_Weight(){
        return this.max_weight;
    }

    public void setCurr_weight(double curr_weight) {
        this.curr_weight = curr_weight;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String ToString() {
        return "Truck ID: " + id + "\nModel: " + model + "\nCurrent Weight: " + curr_weight + "\nMax Weight: " + max_weight + "\nLicence: " + licence;
    }
    public boolean isAvailable() {
        return availability;
    }

    public String getLicense() {
        return licence;
    }
    public String toString() {
        return "Truck ID: " + id + "\nModel: " + model + "\nCurrent Weight: " + curr_weight + "\nMax Weight: " + max_weight + "\nLicence: " + licence;
    }

    public String getModel() {
        return model;
    }
}

