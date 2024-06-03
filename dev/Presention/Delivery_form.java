package Presention;
import java.util.*;
import Controller.controller;
import com.google.gson.JsonObject;

public class Delivery_form {
public static void add_new_delivery_form() {
        int truck_ID = 0;
        int driver_ID = 0;
        int site_ID = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter delivery date");
        String date = sc.nextLine();
        System.out.println("Please enter delivery time");
        String time = sc.nextLine();
        System.out.println("Please enter delivery truck ID");
        boolean validChoice = false;
        while (!validChoice) {
            try {
                truck_ID = sc.nextInt();
                if (!controller.truck_exists(truck_ID)) {
                    System.out.println("Truck with this ID does not exist");
                    continue;
                }
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as truck ID");
                sc.next();
            }
        }
        validChoice = false;
        System.out.println("Please enter delivery driver ID");
        while (!validChoice) {
            try {
                driver_ID = sc.nextInt();
                if (!controller.driver_exists(driver_ID)) {
                    System.out.println("Driver with this ID does not exist");
                    continue;
                }
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as driver ID");
                sc.next();
            }
        }
        validChoice = false;
        System.out.println("Please enter delivery origin site ID");
        while (!validChoice) {
            try {
                site_ID = sc.nextInt();
                if (!controller.site_exists(site_ID)) {
                    System.out.println("Site with this ID does not exist");
                    continue;
                }
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as site ID");
            }
        }

    JsonObject delivery = new JsonObject();
    delivery.addProperty("date", date);
    delivery.addProperty("time", time);
    delivery.addProperty("truck_ID", truck_ID);
    delivery.addProperty("driver_ID", driver_ID);
    delivery.addProperty("site_ID", site_ID);
    controller.add_delivery(delivery);
    }

    static public void return_to_manager_menu(Scanner sc) {
        System.out.println("Press -exit- to return to Delivery Manager menu");
        if (sc.nextLine().equals("exit")) {
            Delivery_manager_menu.show();
        }
    }


}
