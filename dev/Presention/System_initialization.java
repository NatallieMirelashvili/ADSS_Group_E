package Presention;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import Controller.controller;

public class System_initialization {
    public static void start() {
        System.out.println("System initialization started");
        System.out.println("loading Trucks...");
        if (!loadTrucks()) {
            System.out.println("Error loading trucks");
        }
        System.out.println("Trucks loaded successfully");
        System.out.println("loading Drivers...");
        if (!loadDrivers()) {
            System.out.println("Error loading drivers");
        }
        System.out.println("Drivers loaded successfully");
        System.out.println("loading Sites...");
        if (!loadSites()) {
            System.out.println("Error loading sites");
        }
        System.out.println("Sites loaded successfully");
        System.out.println("loading Items...");
        if (!loadItems()) {
            System.out.println("Error loading items");
        }
        System.out.println("Items loaded successfully");
        System.out.println("System initialization finished\n");
    }

    private static boolean loadItems() {
        ArrayList<JsonObject> items = new ArrayList<>();

        JsonObject item1 = new JsonObject();
        item1.addProperty("ID", 1);
        item1.addProperty("name", "Apples");
        items.add(item1);

        JsonObject item2 = new JsonObject();
        item2.addProperty("ID", 2);
        item2.addProperty("name", "Bananas");
        items.add(item2);

        JsonObject item3 = new JsonObject();
        item3.addProperty("ID", 3);
        item3.addProperty("name", "Bread");
        items.add(item3);

        JsonObject item4 = new JsonObject();
        item4.addProperty("ID", 4);
        item4.addProperty("name", "Milk");
        items.add(item4);

        JsonObject item5 = new JsonObject();
        item5.addProperty("ID", 5);
        item5.addProperty("name", "Cheese");
        items.add(item5);

        JsonObject item6 = new JsonObject();
        item6.addProperty("ID", 6);
        item6.addProperty("name", "Chicken Breast");
        items.add(item6);

        JsonObject item7 = new JsonObject();
        item7.addProperty("ID", 7);
        item7.addProperty("name", "Rice");
        items.add(item7);

        JsonObject item8 = new JsonObject();
        item8.addProperty("ID", 8);
        item8.addProperty("name", "Pasta");
        items.add(item8);

        JsonObject item9 = new JsonObject();
        item9.addProperty("ID", 9);
        item9.addProperty("name", "Tomatoes");
        items.add(item9);

        JsonObject item10 = new JsonObject();
        item10.addProperty("ID", 10);
        item10.addProperty("name", "Lettuce");
        items.add(item10);

        JsonObject item11 = new JsonObject();
        item11.addProperty("ID", 11);
        item11.addProperty("name", "Orange Juice");
        items.add(item11);

        JsonObject item12 = new JsonObject();
        item12.addProperty("ID", 12);
        item12.addProperty("name", "Yogurt");
        items.add(item12);

        JsonObject item13 = new JsonObject();
        item13.addProperty("ID", 13);
        item13.addProperty("name", "Butter");
        items.add(item13);

        JsonObject item14 = new JsonObject();
        item14.addProperty("ID", 14);
        item14.addProperty("name", "Eggs");
        items.add(item14);

        JsonObject item15 = new JsonObject();
        item15.addProperty("ID", 15);
        item15.addProperty("name", "Cereal");
        items.add(item15);

        JsonObject item16 = new JsonObject();
        item16.addProperty("ID", 16);
        item16.addProperty("name", "Potatoes");
        items.add(item16);

        JsonObject item17 = new JsonObject();
        item17.addProperty("ID", 17);
        item17.addProperty("name", "Carrots");
        items.add(item17);

        JsonObject item18 = new JsonObject();
        item18.addProperty("ID", 18);
        item18.addProperty("name", "Onions");
        items.add(item18);

        JsonObject item19 = new JsonObject();
        item19.addProperty("ID", 19);
        item19.addProperty("name", "Toilet Paper");
        items.add(item19);

        JsonObject item20 = new JsonObject();
        item20.addProperty("ID", 20);
        item20.addProperty("name", "Shampoo");
        items.add(item20);
        try{
        controller.addAllItems(items);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

    private static boolean loadSites() {
        ArrayList<JsonObject> sites = new ArrayList<>();

        JsonObject site1 = new JsonObject();
        site1.addProperty("ID", 1);
        site1.addProperty("type", "loading");
        site1.addProperty("name", "Warehouse A");
        site1.addProperty("address", "1234 Elm St");
        site1.addProperty("contacts_name", "John Manager");
        site1.addProperty("phone_num", "555-1111");
        site1.addProperty("area", "north");
        sites.add(site1);

        JsonObject site2 = new JsonObject();
        site2.addProperty("ID", 2);
        site2.addProperty("type", "unloading");
        site2.addProperty("name", "Distribution Center B");
        site2.addProperty("address", "5678 Oak St");
        site2.addProperty("contacts_name", "Jane Supervisor");
        site2.addProperty("phone_num", "555-2222");
        site2.addProperty("area", "center");
        sites.add(site2);

        JsonObject site3 = new JsonObject();
        site3.addProperty("ID", 3);
        site3.addProperty("type", "loading");
        site3.addProperty("name", "Warehouse C");
        site3.addProperty("address", "9101 Pine St");
        site3.addProperty("contacts_name", "Bob Coordinator");
        site3.addProperty("phone_num", "555-3333");
        site3.addProperty("area", "south");
        sites.add(site3);

        JsonObject site4 = new JsonObject();
        site4.addProperty("ID", 4);
        site4.addProperty("type", "unloading");
        site4.addProperty("name", "Distribution Center D");
        site4.addProperty("address", "1122 Maple St");
        site4.addProperty("contacts_name", "Alice Foreman");
        site4.addProperty("phone_num", "555-4444");
        site4.addProperty("area", "north");
        sites.add(site4);

        JsonObject site5 = new JsonObject();
        site5.addProperty("ID", 5);
        site5.addProperty("type", "loading");
        site5.addProperty("name", "Warehouse E");
        site5.addProperty("address", "3344 Birch St");
        site5.addProperty("contacts_name", "Charlie Operator");
        site5.addProperty("phone_num", "555-5555");
        site5.addProperty("area", "center");
        sites.add(site5);

        JsonObject site6 = new JsonObject();
        site6.addProperty("ID", 6);
        site6.addProperty("type", "loading");
        site6.addProperty("name", "Warehouse F");
        site6.addProperty("address", "5566 Cedar St");
        site6.addProperty("contacts_name", "Eve Director");
        site6.addProperty("phone_num", "555-6666");
        site6.addProperty("area", "south");
        sites.add(site6);

        JsonObject site7 = new JsonObject();
        site7.addProperty("ID", 7);
        site7.addProperty("type", "unloading");
        site7.addProperty("name", "Distribution Center G");
        site7.addProperty("address", "7788 Spruce St");
        site7.addProperty("contacts_name", "Frank Manager");
        site7.addProperty("phone_num", "555-7777");
        site7.addProperty("area", "north");
        sites.add(site7);

        JsonObject site8 = new JsonObject();
        site8.addProperty("ID", 8);
        site8.addProperty("type", "loading");
        site8.addProperty("name", "Warehouse H");
        site8.addProperty("address", "9900 Redwood St");
        site8.addProperty("contacts_name", "Grace Supervisor");
        site8.addProperty("phone_num", "555-8888");
        site8.addProperty("area", "center");
        sites.add(site8);

        JsonObject site9 = new JsonObject();
        site9.addProperty("ID", 9);
        site9.addProperty("type", "unloading");
        site9.addProperty("name", "Distribution Center I");
        site9.addProperty("address", "2233 Walnut St");
        site9.addProperty("contacts_name", "Hank Coordinator");
        site9.addProperty("phone_num", "555-9999");
        site9.addProperty("area", "south");
        sites.add(site9);

        JsonObject site10 = new JsonObject();
        site10.addProperty("ID", 10);
        site10.addProperty("type", "loading");
        site10.addProperty("name", "Warehouse J");
        site10.addProperty("address", "4455 Ash St");
        site10.addProperty("contacts_name", "Ivy Foreman");
        site10.addProperty("phone_num", "555-0000");
        site10.addProperty("area", "north");
        sites.add(site10);
        try{
        controller.addAllSites(sites);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

    private static boolean loadDrivers() {
        ArrayList<JsonObject> drivers = new ArrayList<>();

        JsonObject driver1 = new JsonObject();
        driver1.addProperty("ID", 1);
        driver1.addProperty("name", "John Doe");
        driver1.addProperty("licence", "C");
        driver1.addProperty("phone_num", "555-1234");
        driver1.addProperty("password", 1234);
        drivers.add(driver1);

        JsonObject driver2 = new JsonObject();
        driver2.addProperty("ID", 2);
        driver2.addProperty("name", "Jane Smith");
        driver2.addProperty("licence", "C1");
        driver2.addProperty("phone_num", "555-5678");
        driver2.addProperty("password", 1234);
        drivers.add(driver2);

        JsonObject driver3 = new JsonObject();
        driver3.addProperty("ID", 3);
        driver3.addProperty("name", "Bob Johnson");
        driver3.addProperty("licence", "C");
        driver3.addProperty("phone_num", "555-9101");
        driver3.addProperty("password", 1234);
        drivers.add(driver3);

        JsonObject driver4 = new JsonObject();
        driver4.addProperty("ID", 4);
        driver4.addProperty("name", "Alice Davis");
        driver4.addProperty("licence", "C1");
        driver4.addProperty("phone_num", "555-1122");
        driver4.addProperty("password", 1234);
        drivers.add(driver4);

        JsonObject driver5 = new JsonObject();
        driver5.addProperty("ID", 5);
        driver5.addProperty("name", "Charlie Brown");
        driver5.addProperty("licence", "C");
        driver5.addProperty("phone_num", "555-3344");
        driver5.addProperty("password", 1234);
        drivers.add(driver5);

        JsonObject driver6 = new JsonObject();
        driver6.addProperty("ID", 6);
        driver6.addProperty("name", "Eve Miller");
        driver6.addProperty("licence", "C1");
        driver6.addProperty("phone_num", "555-5566");
        driver6.addProperty("password", 1234);
        drivers.add(driver6);

        JsonObject driver7 = new JsonObject();
        driver7.addProperty("ID", 7);
        driver7.addProperty("name", "Frank Wilson");
        driver7.addProperty("licence", "C");
        driver7.addProperty("phone_num", "555-7788");
        driver7.addProperty("password", 1234);
        drivers.add(driver7);

        JsonObject driver8 = new JsonObject();
        driver8.addProperty("ID", 8);
        driver8.addProperty("name", "Grace Lee");
        driver8.addProperty("licence", "C1");
        driver8.addProperty("phone_num", "555-9900");
        driver8.addProperty("password", 1234);
        drivers.add(driver8);

        JsonObject driver9 = new JsonObject();
        driver9.addProperty("ID", 9);
        driver9.addProperty("name", "Hank Taylor");
        driver9.addProperty("licence", "C");
        driver9.addProperty("phone_num", "555-2233");
        driver9.addProperty("password", 1234);
        drivers.add(driver9);

        JsonObject driver10 = new JsonObject();
        driver10.addProperty("ID", 10);
        driver10.addProperty("name", "Ivy White");
        driver10.addProperty("licence", "C1");
        driver10.addProperty("phone_num", "555-4455");
        driver10.addProperty("password", 1234);
        drivers.add(driver10);
        try{
        controller.addAllDrivers(drivers);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

    private static boolean loadTrucks() {
        ArrayList<JsonObject> trucks = new ArrayList<>();

        JsonObject truck1 = new JsonObject();
        truck1.addProperty("ID", 1);
        truck1.addProperty("model", "Ford F-150");
        truck1.addProperty("max_weight", 3000);
        truck1.addProperty("licence", "C");
        trucks.add(truck1);

        JsonObject truck2 = new JsonObject();
        truck2.addProperty("ID", 2);
        truck2.addProperty("model", "Chevrolet Silverado");
        truck2.addProperty("max_weight", 3500);
        truck2.addProperty("licence", "C1");
        trucks.add(truck2);

        JsonObject truck3 = new JsonObject();
        truck3.addProperty("ID", 3);
        truck3.addProperty("model", "Ram 1500");
        truck3.addProperty("max_weight", 3200);
        truck3.addProperty("licence", "C");
        trucks.add(truck3);

        JsonObject truck4 = new JsonObject();
        truck4.addProperty("ID", 4);
        truck4.addProperty("model", "Toyota Tundra");
        truck4.addProperty("max_weight", 3300);
        truck4.addProperty("licence", "C1");
        trucks.add(truck4);

        JsonObject truck5 = new JsonObject();
        truck5.addProperty("ID", 5);
        truck5.addProperty("model", "Nissan Titan");
        truck5.addProperty("max_weight", 3100);
        truck5.addProperty("licence", "C");
        trucks.add(truck5);

        JsonObject truck6 = new JsonObject();
        truck6.addProperty("ID", 6);
        truck6.addProperty("model", "GMC Sierra");
        truck6.addProperty("max_weight", 3400);
        truck6.addProperty("licence", "C");
        trucks.add(truck6);

        JsonObject truck7 = new JsonObject();
        truck7.addProperty("ID", 7);
        truck7.addProperty("model", "Honda Ridgeline");
        truck7.addProperty("max_weight", 3150);
        truck7.addProperty("licence", "C1");
        trucks.add(truck7);

        JsonObject truck8 = new JsonObject();
        truck8.addProperty("ID", 8);
        truck8.addProperty("model", "Jeep Gladiator");
        truck8.addProperty("max_weight", 3250);
        truck8.addProperty("licence", "C1");
        trucks.add(truck8);

        JsonObject truck9 = new JsonObject();
        truck9.addProperty("ID", 9);
        truck9.addProperty("model", "Cadillac Escalade EXT");
        truck9.addProperty("max_weight", 3600);
        truck9.addProperty("licence", "C");
        trucks.add(truck9);

        JsonObject truck10 = new JsonObject();
        truck10.addProperty("ID", 10);
        truck10.addProperty("model", "Lincoln Mark LT");
        truck10.addProperty("max_weight", 3700);
        truck10.addProperty("licence", "C1");
        trucks.add(truck10);
        try{
        controller.addAllTrucks(trucks);
        return true;
        }catch (Exception e){
            return false;
        }
    }

}
