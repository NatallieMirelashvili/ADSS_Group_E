package Domain;

public class truck {
    private int id;
    private String model;
    private double curr_weight;
    private double max_weight;
    private String [] licence;

    public truck(int id, String model, double curr_weight, double max_weight, String[] licence) {
        this.id = id;
        this.model = model;
        this.curr_weight = curr_weight;
        this.max_weight = max_weight;
        this.licence = licence;
    }

    public int getID() {
        return id;
    }
}

