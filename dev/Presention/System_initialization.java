package Presention;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Paths;

import Controller.controller;

public class System_initialization {
    public static void start() {
        System.out.println("System initialization started");
        System.out.println("Reading Trucks...");
        if (!readTrucks("trucks1.csv")) {
            System.out.println("Error reading trucks");
        }
        System.out.println("Trucks read successfully");
        System.out.println("Reading Drivers...");
        if (!readDrivers("drivers.csv")) {
            System.out.println("Error reading drivers");
        }
        System.out.println("Drivers read successfully");
        System.out.println("Reading Sites...");
        if (!readSites("sites.csv")) {
            System.out.println("Error reading sites");
        }
        System.out.println("Sites read successfully");
        System.out.println("Reading Items...");
        if (!readItems("items.csv")) {
            System.out.println("Error reading items");
        }
        System.out.println("Items read successfully");
        System.out.println("System initialization finished\n");
    }
    private static boolean readTrucks(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(getPathToFiles(csvFile)));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                JsonObject truck = new JsonObject();
                truck.addProperty("ID", Integer.parseInt(data[0]));
                truck.addProperty("model", data[1]);
                truck.addProperty("max_weight", Double.parseDouble(data[2]));
                truck.addProperty("licence", data[3]);
                controller.add_truck(truck);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (Exception e) {
            System.out.println("Error reading file");
            return false;
        }
    }

    private static boolean readDrivers(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(getPathToFiles(csvFile)));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                JsonObject driver = new JsonObject();
                driver.addProperty("ID", Integer.parseInt(data[0]));
                driver.addProperty("name", data[1]);
                driver.addProperty("licence", data[2]);
                driver.addProperty("phone_num", data[3]);
                driver.addProperty("password", Integer.parseInt(data[4]));
                controller.add_driver(driver);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (Exception e) {
            System.out.println("Error reading file");
            return false;
        }
    }

    private static boolean readSites(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(getPathToFiles(csvFile)));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                JsonObject site = new JsonObject();
                site.addProperty("ID", Integer.parseInt(data[0]));
                site.addProperty("type", data[1]);
                site.addProperty("name", data[2]);
                site.addProperty("address", data[3]);
                site.addProperty("contacts_name", data[4]);
                site.addProperty("phone_num", data[5]);
                site.addProperty("area", data[6]);
                controller.add_site(site);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (Exception e) {
            System.out.println("Error reading file");
            return false;
        }
    }
    private static boolean readItems(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(getPathToFiles(csvFile)));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                JsonObject item = new JsonObject();
                item.addProperty("ID", Integer.parseInt(data[0]));
                item.addProperty("name", data[1]);
                controller.add_item(item);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (Exception e) {
            System.out.println("Error reading file");
            return false;
        }
    }

    private static String getPathToFiles(String relevanceFile){
        String currDir = System.getProperty("user.dir");
        File currDirF = new File(currDir);
        return Paths.get(currDirF.getParent(), "dev","DataFiles",relevanceFile).toString();
    }
}
