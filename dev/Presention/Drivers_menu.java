package Presention;

import java.util.*;

public class Drivers_menu {

    public static void show() {
        boolean continue_loop = true;
        while (continue_loop) {
            System.out.println("Welcome to Drivers menu");
            System.out.println("1. Print transport form");
            System.out.println("2. Print items form");
            System.out.println("3. Start of transportation");
            System.out.println("4. End of transportation");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");

        int choice;
        Scanner sc = new Scanner(System.in);
        try {
            choice = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter an Integer");
            continue;
        }

        switch (choice) {
            case 1:
                Delivery_form.print();
                break;
            case 2:
                Items_form.print();
                break;
            case 3:
                System.out.println("Start of transportation");
                break;
            case 4:
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
