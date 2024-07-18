package Presention;

import Controller.controller;
import com.google.gson.JsonObject;

import java.util.Scanner;

public class Driver_Addition {
    public static void add_new_driver() {
        /**
         * Adds a new driver.
         */
        int ID = 0;
        String licence = "";
        int password = 1234;
        boolean validChoice = false;
        System.out.println("Enter driver ID");
        Scanner sc = new Scanner(System.in);
        while (!validChoice) {
            try {
                ID = sc.nextInt();
                sc.nextLine();
                if (ID < 0) {
                    System.out.println("Invalid input. Please enter a positive Integer");
                    continue;
                }
                if (controller.driver_exists(ID)) {
                    System.out.println("Driver with this ID already exists");
                    System.out.println("Enter driver ID");
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
        System.out.println("Enter driver name");
        String name = sc.nextLine();
        System.out.println("Enter driver licence - C or C1");
        while (!validChoice) {
            licence = sc.nextLine();
            if (licence.equals("C") || licence.equals("C1")) {
                validChoice = true;
            } else {
                System.out.println("Invalid input. Please enter C or C1");
            }
        }
        validChoice = false;
        System.out.println("Enter driver phone number");
        String phone_num = sc.nextLine();
        System.out.println("Enter driver password");
        while (!validChoice) {
            try {
                password = sc.nextInt();
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }

            System.out.println("Driver added successfully \n");

            JsonObject driver = new JsonObject();
            driver.addProperty("ID", ID);
            driver.addProperty("name", name);
            driver.addProperty("licence", licence);
            driver.addProperty("phone_num", phone_num);
            driver.addProperty("password", password);

            controller.add_driver(driver);
        }
    }
