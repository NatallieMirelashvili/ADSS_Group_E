package Presention;

import java.util.Scanner;
import Controller.controller;

public class Items_form_addition {
    public static void add_new_items_form(int delivery_ID) {
        boolean add_new_items_form = true;
        int items_form_count = 0;
        while (true) {
            System.out.println("Welcome to create new items form menu");
            System.out.println("Please choose one of the following options:");
            System.out.println("1. create new items form to the delivery");
            System.out.println("2. return to Delivery Manager menu");
            Scanner sc = new Scanner(System.in);
            int choice;
            while (true) {
                try {
                    choice = sc.nextInt();
                    if (choice == 1) {
                        break;
                    }
                    if (choice == 2) {
                        if (items_form_count == 0) {
                            System.out.println("You must add at least one items form to the delivery");
                            continue;
                        }
                        add_new_items_form = false;
                        break;
                    }
                    else  {
                        System.out.println("Invalid input. Please enter 1 or 2");
                    }
                }
                catch (Exception e) {
                    System.out.println("Invalid input. Please enter 1 or 2");
                }
            }
            if (!add_new_items_form) {
                break;
            }

            int site_ID = validateSiteId(sc, delivery_ID, items_form_count);
            if (site_ID == -1) {
                break;
            }

            String site_type = controller.get_site_type(site_ID);
            if (site_type.equals("loading")) {
                System.out.println("this is a loading site, what Items would you like to load at this site?");
            }
            if (site_type.equals("unloading")) {
                System.out.println("this is an unloading site, what Items would you like to unload at this site?");
            }

            controller.add_items_form(delivery_ID, site_ID);
            int items_count = 0;
            while (true) {
                System.out.println("1. Add new item to this destination");
                System.out.println("2. return to create new items form menu");
                int choice_1 = 0;
                while (true) {
                    try {
                        choice_1 = sc.nextInt();
                        if (choice_1 == 1) {
                            break;
                        }
                        if (choice_1 == 2) {
                            if (items_count == 0) {
                                System.out.println("You must add at least one item to the destination");
                                continue;
                            }
                            break;
                        }
                        System.out.println("Invalid input. Please enter 1 or 2");
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter 1 or 2");
                    }
                }
                if (choice_1 == 2) {
                    break;
                }
                System.out.println("Enter item ID");
                int item_ID = validateItemId(sc);

                System.out.println("Enter item quantity");
                int quantity = validateQuantity(sc);
                System.out.println("You have chose to add " + quantity + " of item " + controller.get_item_name(item_ID) + " to the destination " + controller.get_site_name(site_ID) + " in delivery " + delivery_ID);
                items_count++;
                controller.add_item_to_items_form(delivery_ID, site_ID, item_ID, quantity);
            }
            items_form_count++;
        }
    }


    private static int validateSiteId(Scanner sc, int delivery_ID, int count) {
        System.out.println("Please enter destination site ID or press -1 to return to Delivery Manager menu");
        int site_ID = 0;
        boolean validChoice = true;
        while (validChoice) {
            try {
                site_ID = sc.nextInt();
                if (site_ID == -1) {
                    if (count == 0) {
                        System.out.println("You must add at least one destination to the delivery");
                        continue;
                    }
                    return -1;
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
                validChoice = false;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as site ID");
                sc.next(); // discard the invalid input
            }
        }
        return site_ID;
    }

    private static int validateItemId(Scanner sc) {
        int item_ID = 0;
        boolean validChoice = true;
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
                sc.next(); // discard the invalid input
            }
        }
        return item_ID;
    }

    private static int validateQuantity(Scanner sc) {
        int quantity = 0;
        boolean validChoice = true;
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
                sc.next(); // discard the invalid input
            }
        }
        return quantity;
    }
}