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
                if (!controller.driver_exists(ID)){
                    System.out.println("Driver with this ID does not exist");
                    System.out.println("Please enter your ID:");
                    continue;
                }
                continue_loop = false;
            }
            catch (Exception e){
                System.out.println("Please enter an Integer as your ID");
            }
        }
        continue_loop = true;
        System.out.println("Please enter your password:");
        while (continue_loop){
            try {
                password = sc.nextInt();
                if (controller.driver_password(ID, password)){
                    continue_loop = false;
                } else {
                    System.out.println("Invalid password, please try again");
                }
            }
            catch (Exception e){
                System.out.println("Please enter an Integer as your password");
                sc.next();
            }
        }
        continue_loop = true;
        while (continue_loop) {
            System.out.println("Welcome to Drivers menu");
            System.out.println("1. Print transport form");
            System.out.println("2. Print items form");
            System.out.println("3. Start of transportation");
            System.out.println("4. End of transportation");
            System.out.println("5. Exit \n");
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
                        if (destinationID == 0) {
                            break;
                        }
                        System.out.println("Site with this ID does not exist");
                        System.out.println("Enter the destination ID:");
                        System.out.println("To return to the main menu enter 0");
                    } else {
                        System.out.println(controller.print_items_form(ID, destinationID));
                        break;
                                }
                }
                break;
            case 3:
                boolean start = controller.start_driving(ID);
                if (!start) {
                    System.out.println("You have no transportation to start \n");
                    break;
                }
                System.out.println("Transportation started successfully \n");
                break;
            case 4:
                boolean end = controller.end_driving(ID);
                if (!end) {
                    System.out.println("You have no transportation to end \n");
                    break;
                }
                controller.end_driving(ID);
                System.out.println("End of transportation \n");
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
