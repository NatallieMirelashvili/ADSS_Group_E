package Domain;

public class site {
    private String type;

    private String name;
    private String address;
    private String contacts_name;
    private String phone;
    private String area;

    public site(String type, String name, String address, String contacts_name, String phone, String area) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.contacts_name = contacts_name;
        this.phone = phone;
        this.area = area;
    }

    public String getSite_name() {
        return name;
    }
}
