package Presention;

import com.google.gson.JsonObject;
import Controller.controller;
import java.util.Scanner;

public class Site_addition {
    public static void add_new_site(String[] args) {
        boolean validChoice = false;
        int ID = 0;
        String type = "";
        System.out.println("Enter site ID");
        Scanner sc = new Scanner(System.in);
        while (!validChoice) {
            try {
                ID = sc.nextInt();
                if (controller.site_exists(ID)) {
                    System.out.println("Site with this ID already exists");
                    System.out.println("Enter site ID");
                    continue;
                }
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        sc.nextLine();
        validChoice = false;
        System.out.println("Enter site type - loading or unloading");
        while (!validChoice) {
            type = sc.nextLine();
            if (type.equals("loading") || type.equals("unloading")) {
                validChoice = true;
            } else {
                System.out.println("Invalid input. Please enter loading or unloading");
            }
        }
        System.out.println("Enter site name");
        String name = sc.nextLine();
        System.out.println("Enter site address");
        String address = sc.nextLine();
        System.out.println("Enter site contact name");
        String contact_name = sc.nextLine();
        System.out.println("Enter site contact phone number");
        String contact_phone = sc.nextLine();
        System.out.println("Enter site area");
        String area = sc.nextLine();
        System.out.println("Site added successfully");

        JsonObject site = new JsonObject();
        site.addProperty("ID", ID);
        site.addProperty("type", type);
        site.addProperty("name", name);
        site.addProperty("address", address);
        site.addProperty("contacts_name", contact_name);
        site.addProperty("phone_num", contact_phone);
        site.addProperty("area", area);

        controller.add_site(site);
    }
}