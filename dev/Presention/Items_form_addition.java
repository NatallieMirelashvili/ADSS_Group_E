package Presention;

import java.util.Scanner;
import Controller.controller;

public class Items_form_addition {
    public static void add_new_items_form(int delivery_ID) {
        boolean validChoice = false;
        int count = 0;
        while (true) {
            System.out.println("1. Add new destination to delivery");
            System.out.println("2. return to Delivery Manager menu");
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            while (true) {
                try {
                    choice = sc.nextInt();
                    if (choice == 1) {
                        validChoice = true;
                        break;
                    }
                    if (choice == 2) {
                        if (count == 0) {
                            System.out.println("You must add at least one destination to the delivery");
                            continue;
                        }
                        Delivery_manager_menu.show();
                    }
                    else  {
                        System.out.println("Invalid input. Please enter 1 or 2");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter 1 or 2");
                }
            }

            int site_ID = 0;
            System.out.println("Enter destination site ID or press -1 to return to Delivery Manager menu");
            while (validChoice) {
                try {
                    site_ID = sc.nextInt();
                    if (site_ID == -1) {
                        if (count == 0) {
                            System.out.println("You must add at least one destination to the delivery");
                            continue;
                        }
                        Delivery_manager_menu.show();
                    }
                    if (!controller.site_exists(site_ID)) {
                        System.out.println("Site with this ID does not exist, please enter a valid site ID");
                        continue;
                    }
                    if (controller.destination_exists(delivery_ID, site_ID)) {
                        System.out.println("This site is already in the delivery, please enter a different site ID");
                        continue;
                    }
                    if (!controller.same_area(delivery_ID, site_ID)) {
                        System.out.println("This site is not at the same area as this delivery, please enter a different site ID");
                        continue;
                    }
                    count++;
                    validChoice = false;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter an Integer as site ID");
                }
            }
            String site_type = controller.get_site_type(site_ID);
            if (site_type.equals("loading")) {
                System.out.println("this is a loading site, what Items would you like to load at this site?");
            }
            if (site_type.equals("unloading")) {
                System.out.println("this is an unloading site, what Items would you like to unload at this site?");
            }

            controller.add_items_form(delivery_ID, site_ID);
            int count_1 = 0;
            while (true) {
                System.out.println("1. Add new item to this destination");
                System.out.println("2. return to Delivery Manager menu");
                int choice_1 = 0;
                boolean validChoice_1 = true;
                while (validChoice_1) {
                    try {
                        choice_1 = sc.nextInt();
                        if (choice_1 == 1) {
                            break;
                        }
                        if (choice_1 == 2) {
                            if (count_1 == 0) {
                                System.out.println("You must add at least one item to the destination");
                                continue;
                            }
                            Delivery_manager_menu.show();
                        }
                        if (choice_1 != 2) {
                            System.out.println("Invalid input. Please enter 1 or 2");
                            continue;
                        }
                        validChoice_1 = false;
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter 1 or 2");
                    }
                }
                System.out.println("Enter item ID");
                int item_ID = 0;
                validChoice = true;
                while (validChoice) {
                    try {
                        item_ID = sc.nextInt();
                        if (!controller.item_exists(item_ID)) {
                            System.out.println("Item with this ID does not exist, please enter a valid item ID");
                            continue;
                        }
                        validChoice = false;
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter an Integer as item ID");
                    }
                }
                System.out.println("Enter item quantity");
                int quantity = 0;
                validChoice = true;
                while (validChoice) {
                    try {
                        quantity = sc.nextInt();
                        if (quantity <= 0) {
                            System.out.println("Invalid input. Please enter a positive Integer as quantity");
                            continue;
                        }
                        validChoice = false;
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter an Integer as quantity");
                    }
                }
                count_1++;
                controller.add_item_to_items_form(delivery_ID, site_ID, item_ID, quantity);
            }
        }
    }
}