package Domain;

public class Driver {
    private int ID;
    private String name;
    private String licence;
    private String phone_num;
    private boolean assignment;
    public Driver(int ID, String name, String licence, String phone_num, boolean assignment) {
        this.ID = ID;
        this.name = name;
        this.licence = licence;
        this.phone_num = phone_num;
        this.assignment = assignment;
    }

    public int getID() {
        return ID;
    }


}
