package Presention;

import java.util.*;
import Controller.controller;

public class Drivers_menu {

    public static void show() {
        int ID = 0;
        int password = 1234;
        boolean continue_loop = true;
        System.out.println("Please enter your ID:");
        Scanner sc = new Scanner(System.in);
        while (continue_loop){
            try {
                ID = sc.nextInt();
                continue_loop = false;
            }
            catch (Exception e){
                System.out.println("Please enter an Integer");
                sc.next();
            }
        }
        continue_loop = true;
        System.out.println("Please enter your password:");
        while (continue_loop){
            try {
                password = sc.nextInt();
                continue_loop = false;
            }
            catch (Exception e){
                System.out.println("Please enter an Integer");
                sc.next();
            }
        }
        if (controller.driver_exists(ID) && controller.driver_password(ID, password)){
            System.out.println("Welcome driver");
        } else {
            System.out.println("Invalid ID or password");
            return;
        }
        while (continue_loop) {
            System.out.println("Welcome to Drivers menu");
            System.out.println("1. Print transport form");
            System.out.println("2. Print items form");
            System.out.println("3. Start of transportation");
            System.out.println("4. End of transportation");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");

        int choice;
        try {
            choice = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter an Integer");
            continue;
        }

        switch (choice) {
            case 1:
                System.out.println(controller.print_transport_form(ID));
                break;
            case 2:
                int destinationID = 0;
                System.out.println("Enter the destination ID:");
                while (continue_loop){
                    while (!sc.hasNextInt()) {
                        System.out.println("Please enter an Integer");
                        sc.next();
                    }
                    destinationID = sc.nextInt();
                    if (!controller.site_exists(destinationID)) {
                        System.out.println("Site with this ID does not exist");
                        System.out.println("Enter the destination ID:");
                    } else {
                        continue_loop = false;
                    }
                }

                System.out.println(controller.print_items_form(ID, destinationID));
                break;
            case 3:
                boolean start = controller.start_driving(ID);
                if (!start) {
                    System.out.println("You have no transportation to start");
                    break;
                }
                System.out.println("Transportation started successfully");
                break;
            case 4:
                boolean end = controller.end_driving(ID);
                if (!end) {
                    System.out.println("You have no transportation to end");
                    break;
                }
                controller.end_driving(ID);
                System.out.println("End of transportation");
                break;
            case 5:
                System.out.println("Exit");
                continue_loop = false;
                break;
            default:
                System.out.println("Invalid choice, please enter 1, 2, 3, 4 or 5");
                break;
        }
    }
}}
