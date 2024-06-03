package Presention;

import Controller.controller;
import com.google.gson.JsonObject;

import java.util.Scanner;

public class Truck_addition {
    public static void add_new_truck() {
        int ID = 0;
        double curr_weight = 0;
        double max_weight = 0;
        String licence = "";
        boolean validChoice = false;
        System.out.println("Enter truck ID");
        Scanner sc = new Scanner(System.in);
        while (!validChoice) {
            try {
                ID = sc.nextInt();
                if (controller.truck_exists(ID)) {
                    System.out.println("Truck with this ID already exists");
                    System.out.println("Enter truck ID");
                    continue;
                }
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        validChoice = false;
        sc.nextLine();
        System.out.println("Enter truck model");
        String model = sc.nextLine();
        System.out.println("Enter truck max weight");
        while (!validChoice) {
            try {
                max_weight = sc.nextDouble();
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number");
                sc.next();
            }
        }
        sc.nextLine();
        validChoice = false;
        System.out.println("Enter truck licence - C or C1");
        while (!validChoice) {
            licence = sc.nextLine();
            if (licence.equals("C") || licence.equals("C1")) {
                validChoice = true;
            } else {
                System.out.println("Invalid input. Please enter C or C1");
            }
        }

        System.out.println("Truck added successfully");


        JsonObject new_truck = new JsonObject();
        new_truck.addProperty("ID", ID);
        new_truck.addProperty("model", model);
        new_truck.addProperty("curr_weight", curr_weight);
        new_truck.addProperty("max_weight", max_weight);
        new_truck.addProperty("licence", licence);
        controller.add_truck(new_truck);
    }
}

