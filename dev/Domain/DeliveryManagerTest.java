package Domain;

import com.google.gson.JsonObject;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryManagerTest {

    @Test
    public void testAddDriver() throws SQLException {
        JsonObject driver1 = new JsonObject();
        driver1.addProperty("ID", 99);
        driver1.addProperty("name", "TOM");
        driver1.addProperty("licence", "C1");
        driver1.addProperty("phone_num", "555-1234");
        driver1.addProperty("password", 1234);

        STD_manager.add_driver(driver1);

        Driver driver = STD_manager.get_driver(99);

        assertEquals("TOM", driver.getName());
        assertEquals(99, driver.getID());
        assertEquals("C1", driver.getLicense());
        assertEquals("555-1234", driver.getPhone_num());
        assertEquals(1234, driver.getPassword());
        STD_manager.remove_driver(99);
    }

    @Test
    public void testAddTruck() throws SQLException {
        JsonObject truck1 = new JsonObject();
        truck1.addProperty("ID", 555);
        truck1.addProperty("model", "TEST");
        truck1.addProperty("max_weight", 6000);
        truck1.addProperty("licence", "C1");

        STD_manager.add_truck(truck1);

        Truck truck = STD_manager.get_truck(555);

        assertEquals(555, truck.getID());
        assertEquals("TEST", truck.getModel());
        assertEquals(6000, truck.get_max_Weight());
        assertEquals("C1", truck.getLicense());
        STD_manager.remove_truck(555);
    }

    @Test
    public void testAddSite() throws SQLException {
        JsonObject site1 = new JsonObject();
        site1.addProperty("ID", 200);
        site1.addProperty("type", "loading");
        site1.addProperty("name", "TEST");
        site1.addProperty("address", "testing St");
        site1.addProperty("contacts_name", "John Tester");
        site1.addProperty("phone_num", "555-1111");
        site1.addProperty("area", "north");

        STD_manager.add_site(site1);

        Site site = STD_manager.get_site(200);

        assertEquals(200, site.getSite_ID());
        assertEquals("TEST", site.getSite_name());
        assertEquals("testing St", site.getSite_address());
        assertEquals("John Tester", site.getSite_contact_name());
        assertEquals("555-1111", site.getSite_contact_phone());
        assertEquals("north", site.getArea());
        STD_manager.remove_site(200);

    }
    @Test
    public void testAddItem() throws SQLException {
        JsonObject item1 = new JsonObject();
        item1.addProperty("ID", 300);
        item1.addProperty("name", "TEST");

        Delivery_manager.add_item(item1);

        Item item = Delivery_manager.get_item(300);

        assertEquals(300, item.getItemId());
        assertEquals("TEST", item.getItemName());
        Delivery_manager.remove_item(300);

    }

    @Test
    public void testUpdateSiteArea() throws SQLException{
        JsonObject site1 = new JsonObject();
        site1.addProperty("ID", 400);
        site1.addProperty("type", "loading");
        site1.addProperty("name", "TEST");
        site1.addProperty("address", "testing St");
        site1.addProperty("contacts_name", "John Tester");
        site1.addProperty("phone_num", "555-1111");
        site1.addProperty("area", "north");

        STD_manager.add_site(site1);

        Site site = STD_manager.get_site(400);

        assertEquals("north", site.getArea());
        STD_manager.change_site_area(400, "south");
        assertEquals("south", site.getArea());
        STD_manager.remove_site(400);


    }

    @Test
    public void testAddDelivery() throws SQLException{
        JsonObject delivery1 = new JsonObject();
        delivery1.addProperty("ID", 0);
        delivery1.addProperty("driver_ID", 1);
        delivery1.addProperty("truck_ID", 1);
        delivery1.addProperty("site_ID", 1);
        delivery1.addProperty("date", "2024-07-14");
        delivery1.addProperty("hour", "12:00");

        Delivery_manager.add_delivery(delivery1);

        Delivery delivery = Delivery_manager.get_delivery(0);

        assertEquals(0, delivery.getID());
        assertEquals(1, delivery.getDriverID());
        assertEquals(1, delivery.getTruckID());
        assertEquals(1, delivery.getSiteID());
        assertEquals("2024-07-14", delivery.getDate().toString());

    }

    @Test
    public void testChangeTruck() throws SQLException {
        JsonObject delivery1 = new JsonObject();
        delivery1.addProperty("ID", 1);
        delivery1.addProperty("driver_ID", 1);
        delivery1.addProperty("truck_ID", 1);
        delivery1.addProperty("site_ID", 1);
        delivery1.addProperty("date", "2024-07-14");
        delivery1.addProperty("hour", "12:00");

        Delivery_manager.add_delivery(delivery1);
        assertEquals(1, Delivery_manager.get_delivery(1).getTruckID());

        Delivery_manager.replace_truck(1,3,500);
        assertEquals(3, Delivery_manager.get_delivery(1).getTruckID());
    }

    @Test
    public void testRemoveDriver() throws SQLException {
        JsonObject driver1 = new JsonObject();
        driver1.addProperty("ID", 101);
        driver1.addProperty("name", "TOM");
        driver1.addProperty("licence", "C1");
        driver1.addProperty("phone_num", "555-1234");
        driver1.addProperty("password", 1234);

        STD_manager.add_driver(driver1);
        STD_manager.remove_driver(101);

        assertNull(STD_manager.get_driver(101));
    }

    @Test
    public void testRemoveTruck() throws SQLException {
        JsonObject truck1 = new JsonObject();
        truck1.addProperty("ID", 101);
        truck1.addProperty("model", "TEST");
        truck1.addProperty("max_weight", 6000);
        truck1.addProperty("licence", "C1");

        STD_manager.add_truck(truck1);
        STD_manager.remove_truck(101);

        assertNull(STD_manager.get_truck(101));
    }

    @Test
    public void testRemoveSite() throws SQLException {
        JsonObject site1 = new JsonObject();
        site1.addProperty("ID", 600);
        site1.addProperty("type", "loading");
        site1.addProperty("name", "TEST");
        site1.addProperty("address", "testing St");
        site1.addProperty("contacts_name", "John Tester");
        site1.addProperty("phone_num", "555-1111");
        site1.addProperty("area", "north");

        STD_manager.add_site(site1);
        STD_manager.remove_site(600);

        assertNull(STD_manager.get_site(600));
    }






}
