package Presention;
import java.util.Scanner;
import Controller.controller;

public class Items_form_edit {
    private static boolean made_changes = false;
    public static void edit_item_form(int delivery_ID, Scanner sc,int ItemID, int amount) {
        /**
         * Edits an item form.
         */
        System.out.println("Please enter the ID of the items form you would like to edit");
        int items_form_ID = 0;
        while (true) {
            try {
                items_form_ID = sc.nextInt();
                if (!controller.items_form_exists(delivery_ID, items_form_ID)) {
                    System.out.println("items form does not exist, please enter a valid items form ID");
                    continue;
                }
                if (!controller.get_site_type(controller.get_site_in_items_form(delivery_ID, items_form_ID)).equals("loading")) {
                    System.out.println("You can only edit items form in loading sites");
                    System.out.println("Please enter a valid items form ID");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        while (true) {
            System.out.println("Please choose one of the following options:");
            System.out.println("1. Add item to the items form");
            System.out.println("2. Remove item from the items form");
            System.out.println("3. Set amount of item in the items form");
            System.out.println("4. Finish editing the items form");
            int choice;
            while (true) {
                try {
                    choice = sc.nextInt();
                    if (choice == 1) {
                        made_changes = true;
                        break;
                    }
                    if (choice == 2) {
                        made_changes = true;
                        break;
                    }
                    if (choice == 3) {
                        made_changes = true;
                        break;
                    }
                    if (choice == 4) {
                        if (!made_changes){
                            System.out.println("You must make at least one change to the items form");
                            System.out.println("Please choose the number of on of the options above");
                            continue;
                        }
                        if (!controller.problem_edit_fixed(delivery_ID, items_form_ID, ItemID, amount)){
                            System.out.println("You must fix the problem before finishing the editing");
                            System.out.println("Please choose the number of one of the options above");
                            continue;
                        }
                        made_changes = false;
                        return;
                    } else {
                        System.out.println("Invalid input. Please enter 1, 2, 3 or 4");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter 1, 2, 3 or 4");
                    sc.next();
                }
            }
            if (choice == 1) {
                int m = add_item_to_items_form(delivery_ID, items_form_ID, sc);
                if (m == -1) {
                    made_changes = false;
                    continue;
                }
            }
            if (choice == 2) {
                int t = remove_item_from_items_form(delivery_ID, items_form_ID, sc);
                if (t == -1) {
                    made_changes = false;
                    continue;
                }
            }
            if (choice == 3) {
                int n =set_amount_of_item_in_items_form(delivery_ID, items_form_ID, sc);
                if (n == -1) {
                    made_changes = false;
                    continue;
                }
            }
        }
    }

    private static int set_amount_of_item_in_items_form(int deliveryId, int itemsFormId, Scanner sc) {
        /**
         * Sets the amount of an item in an items form.
         * @param deliveryId The ID of the delivery.
         * @param itemsFormId The ID of the items form.
         * @param sc Scanner object for user input.
         * @return -1 if cancelled, 0 otherwise.
         */
        System.out.println("Please enter the ID of the item you would like to set the amount of or press -1 to cancel");
        int item_ID = 0;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (item_ID == -1) {
                    return -1;
                }
                if (!controller.item_exists_in_items_form(deliveryId, itemsFormId, item_ID)) {
                    System.out.println("Item does not exist, please enter a valid item ID");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        int quantity = 0;
        System.out.println("Please enter the new quantity of the item");
        while (true) {
            try {
                quantity = sc.nextInt();
                if (quantity <= 0) {
                    System.out.println("Invalid input. Please enter a positive Integer");
                    continue;
                }
                if (controller.get_item_quantity_in_items_form(deliveryId, itemsFormId, item_ID) > quantity) {
                    System.out.println("The quantity is lower then the quantity of this item in the items form, please enter a valid quantity");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        controller.set_amount_of_item_in_items_form(deliveryId, itemsFormId, item_ID, quantity);
        controller.increase_item_in_loaded_items(deliveryId, item_ID, quantity);
        return 0;
    }

    private static int add_item_to_items_form(int delivery_ID, int items_form_ID, Scanner sc) {
        /**
         * Adds an item to an items form.
         * @param delivery_ID The ID of the delivery.
         * @param items_form_ID The ID of the items form.
         * @param sc Scanner object for user input.
         * @return -1 if cancelled, 0 otherwise.
         */
        System.out.println("Please enter the ID of the item you would like to add or press -1 to cancel");
        int item_ID = 0;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (item_ID == -1) {
                    return -1;
                }
                if (!controller.item_exists(item_ID)) {
                    System.out.println("Item does not exist, please enter a valid item ID");
                    continue;
                }
                if (controller.item_exists_in_delivery(delivery_ID, item_ID)) {
                    System.out.println("This item is already in the delivery");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        int quantity = 0;
        System.out.println("Please enter the quantity of the item you would like to add");
        while (true) {
            try {
                quantity = sc.nextInt();
                if (quantity <= 0) {
                    System.out.println("Invalid input. Please enter a positive Integer");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        controller.add_item_to_items_form(delivery_ID, items_form_ID, item_ID, quantity);
        controller.add_loaded_item(delivery_ID, item_ID, quantity);

//        String site_type = controller.get_site_type(controller.get_site_in_items_form(delivery_ID, items_form_ID));
//        if (site_type.equals("loading")) {
//            controller.add_loaded_item(delivery_ID, item_ID, quantity);
//        }
    return 0;
    }

    private static int remove_item_from_items_form(int delivery_ID, int items_form_ID, Scanner sc) {
        /**
         * Removes an item from an items form.
         * @param delivery_ID The ID of the delivery.
         * @param items_form_ID The ID of the items form.
         * @param sc Scanner object for user input.
         * @return -1 if cancelled, 0 otherwise.
         */
        System.out.println("Please enter the ID of the item you would like to remove or press -1 to cancel");
        int item_ID = 0;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (item_ID == -1) {
                    return -1;
                }
                if (!controller.item_exists_in_items_form(delivery_ID, items_form_ID, item_ID)) {
                    System.out.println("Item does not exist, please enter a valid item ID");
                    continue;
                }
                if (controller.item_exists_in_diff_items_form(delivery_ID, items_form_ID, item_ID)) {
                    System.out.println("This item is in the diff items form, you can't remove it from the items form");
                    System.out.println("Please enter a valid item ID, or press -1 to cancel");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }

        controller.remove_item_from_items_form(delivery_ID, items_form_ID, item_ID);
        controller.remove_loaded_item(delivery_ID, item_ID);
        return 0;
    }

    public static void add_difference_to_loading_site(int deliveryId, int itemId, int diff, Scanner sc) {
        /**
         * Adds the difference to a loading site.
         * @param deliveryId The ID of the delivery.
         * @param itemId The ID of the item.
         * @param diff The difference to add.
         * @param sc Scanner object for user input.
         */
        System.out.println("Please enter the ID of the item form you would like to add the difference to");
        int items_form_ID = 0;
        while (true) {
            try {
                items_form_ID = sc.nextInt();
                if (!controller.items_form_exists(deliveryId, items_form_ID)) {
                    System.out.println("items form does not exist, please enter a valid items form ID");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        controller.add_difference_to_loading_site(deliveryId, itemId, diff, items_form_ID);
        controller.update_item_quantity_loaded_in_delivery(deliveryId, itemId, diff);

    }
}
