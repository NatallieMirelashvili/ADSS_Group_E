import Domain.Driver;
import Presention.*;
//import Presention.Drivers_menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the delivery system");

//        System.out.println("For system initialization");
//        Scanner sc = new Scanner(System.in);
//        int start = sc.nextInt();
//        if (start == 1){
//            System_initialization.start();
//        }

//        boolean validChoice = false;
//        while (!validChoice) {
//            System.out.println("Please select your role");
//            System.out.println("1. Drivers menu");
//            System.out.println("2. Delivery manager menu");
//            Scanner sc = new Scanner(System.in);
//            int choice = sc.nextInt();
//            switch (choice) {
//                case 1:
//                    Drivers_menu.show();
//                    validChoice = true;
//                    break;
//                case 2:
//                    Delivery_manager_menu.show();
//                    validChoice = true;
//                    break;
//                default:
//                    System.out.println("Invalid choice, please enter 1 or 2");
//                    break;
//            }
//        }
    Driver_Addition.add_new_driver();
        Drivers_menu.show();
    }
}