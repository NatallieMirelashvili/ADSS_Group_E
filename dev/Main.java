import Domain.Driver;
import Presention.*;
//import Presention.Drivers_menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static boolean sysinit = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to the delivery system");
            System.out.println("Please choose one of the following options:");
            System.out.println("1. System initialization");
            System.out.println("2. Drivers menu");
            System.out.println("3. Delivery manager menu");
            System.out.println("4. Exit");
            try {
                choice = sc.nextInt();
                if (choice == 1 && sysinit) {
                    System.out.println("System is already initialized");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println();
                        System_initialization.start();
                        sysinit = true;
                    }
                    case 2 -> {
                        System.out.println();
                        Drivers_menu.show();
                    }
                    case 3 -> {
                        System.out.println();
                        Delivery_manager_menu.first_entrance();
                    }
                    case 4 -> {
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice, please enter 1, 2, 3 or 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // discard the invalid input
            }
        } while (true); // loop indefinitely until user chooses to exit
    }
}