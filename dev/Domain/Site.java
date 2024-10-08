package Domain;

public class Site {
    private int site_id;
    private String type;
    private String name;
    private String address;
    private String contacts_name;
    private String phone;
    private String area;

    public Site(int site_id, String type, String name, String address, String contacts_name, String phone, String area) {
        this.site_id = site_id;
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

    public String getSite_address() {
        return address;
    }

    public String getSite_contact_name() {
        return contacts_name;
    }

    public String getSite_contact_phone() {
        return phone;
    }
    public String ToString() {
        return "Site Name: " + name + "\nType: " + type + "\nAddress: " + address + "\nContact Name: " + contacts_name + "\nPhone Number: " + phone + "\nArea: " + area;
    }

    public int getSite_ID() {
        return site_id;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String get_type() {
        return type;
    }
    public String getArea() {
        return area;
    }
    public String toString() {
        return "Site ID: " + site_id + "\nSite Name: " + name + "\nType: " + type + "\nAddress: " + address + "\nContact Name: " + contacts_name + "\nPhone Number: " + phone + "\nArea: " + area;
    }
}
