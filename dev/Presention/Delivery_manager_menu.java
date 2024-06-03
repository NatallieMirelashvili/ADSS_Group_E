package Presention;
import java.util.*;
import Controller.controller;

public class Delivery_manager_menu {
    public static void first_entrance(){
        final int manager_password = 102030;
        boolean continue_loop = true;
        int password = 0;
        // int counter = 0; TODO: IF MISSED THE PASSWORD 3 TIMES IT WILL EXIT
        Scanner psd = new Scanner(System.in);
        System.out.println("Please enter your password:");
        while (continue_loop){
            try {
                password = psd.nextInt();
                if (password != manager_password){
                    System.out.println("Invalid password, please try again");
                    continue;
                }
                continue_loop = false;
            }
            catch (Exception e){
                System.out.println("Please enter an Integer");
                psd.next();
            }
        }
        show();
    }
    public static void show() {
        Scanner sc = new Scanner(System.in);
        boolean continue_loop = true;
        System.out.println("Welcome to Delivery Manager menu");
        while (continue_loop) {
            System.out.println("1. Add new delivery");
            System.out.println("2. Chance site's area");
            System.out.println("3. Add new driver");
            System.out.println("4. Add new truck");
            System.out.println("5. Add new site");
            System.out.println("6. Start delivery");
            System.out.println("7. Exit \n");
            System.out.println("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter an Integer");
                continue;
            }

            switch (choice){
                case 1:
                    Delivery_form.add_new_delivery_form();
                    break;
                case 2:
                    System.out.println("Enter site ID");
                    String area;
                    while(true) {
                        try {
                            int ID = sc.nextInt();
                            if (ID < 0) {
                                System.out.println("Invalid input. Please enter a positive Integer as site ID");
                                continue;
                            }
                            if (!controller.site_exists(ID)) {
                                System.out.println("Site with this ID does not exist please enter a valid site ID");
                                continue;
                            }
                            sc.nextLine();
                            System.out.println("Enter new area - north, center or south");
                            while (true) {
                                area = sc.nextLine();
                                if (!area.equals("north") && !area.equals("center") && !area.equals("south")) {
                                    System.out.println("Invalid area, please enter north, center or south");
                            }
                                else break;
                            }
                            controller.change_site_area(ID, area);
                            System.out.println("Area changed successfully");
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter an Integer");
                            sc.next();
                        }
                    }
                    break;
                case 3:
                    Driver_Addition.add_new_driver();
                    break;
                case 4:
                    Truck_addition.add_new_truck();
                    break;
                case 5:
                    Site_addition.add_new_site();
                    break;
                case 6: // TODO: THIS FUNCTION IS NOT IMPLEMENTED YET
                    //Delivery_duration.start_delivery();
                    break;
                case 7:
                    continue_loop = false;
                    break;
                default:
                    System.out.println("Invalid choice, please enter a number between 1 and 7");
            }
        }

    }


}
