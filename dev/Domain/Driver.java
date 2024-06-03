package Domain;

public class Driver {
    private int ID;
    private String name;
    private String licence;
    private String phone_num;

    private boolean availability;

    private int password;
    public Driver(int ID, String name, String licence, String phone_num, int password) {
        this.ID = ID;
        this.name = name;
        this.licence = licence;
        this.phone_num = phone_num;
        this.availability = true;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setpassword(int password) {
        this.password = password;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean getAvailability() {
        return availability;
    }

    public int getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Driver ID: " + ID + "\nName: " + name + "\nLicence: " + licence + "\nPhone Number: " + phone_num;
    }

    public String getLicense() {
        return licence;
    }
}
