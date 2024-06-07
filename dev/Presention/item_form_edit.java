package Presention;
import java.util.Scanner;
import Controller.controller;

public class item_form_edit {
public static void edit_item_form(int delivery_ID,Scanner sc) {
        System.out.println("Please enter the ID of the items form you would like to edit");
        int items_form_ID = 0;
        while (true){
        try {
            items_form_ID = sc.nextInt();
            if (!controller.items_form_exists(delivery_ID, items_form_ID)) {
                System.out.println("items form does not exist, please enter a valid items form ID");
                continue;
            }
            break;
        }
        catch (Exception e) {
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
                        break;
                    }
                    if (choice == 2) {
                        break;
                    }
                    if (choice == 3) {
                        break;
                    }
                    if (choice == 4) {
                        return;
                    }
                    else {
                        System.out.println("Invalid input. Please enter 1, 2, 3 or 4");
                    }
                }
                catch (Exception e) {
                    System.out.println("Invalid input. Please enter 1, 2, 3 or 4");
                    sc.next();
                }
            }
            if (choice == 1) {
                add_item_to_items_form(delivery_ID, items_form_ID,sc);
            }
            if (choice == 2) {
                remove_item_from_items_form(delivery_ID, items_form_ID,sc);
            }
            if (choice == 3) {
                set_amount_of_item_in_items_form(delivery_ID, items_form_ID,sc);
            }
        }
    }

    private static void set_amount_of_item_in_items_form(int deliveryId, int itemsFormId,Scanner sc) {
        System.out.println("Please enter the ID of the item you would like to set the amount of");
        int item_ID = 0;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (!controller.item_exists_in_items_form(deliveryId, itemsFormId, item_ID)){
                    System.out.println("Item does not exist, please enter a valid item ID");
                    continue;
                }
                break;
            }
            catch (Exception e) {
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
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        controller.set_amount_of_item_in_items_form(deliveryId, itemsFormId, item_ID, quantity);
        controller.increase_item_in_loaded_items(deliveryId, item_ID, quantity);
    }

    private static void add_item_to_items_form(int delivery_ID, int items_form_ID,Scanner sc) {
        System.out.println("Please enter the ID of the item you would like to add");
        int item_ID = 0;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (!controller.item_exists(item_ID)) {
                    System.out.println("Item does not exist, please enter a valid item ID");
                    continue;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
        }}
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
            }
            catch (Exception e) {
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
    }

    private static void remove_item_from_items_form(int delivery_ID, int items_form_ID,Scanner sc){
        System.out.println("Please enter the ID of the item you would like to remove");
        int item_ID = 0;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (!controller.item_exists_in_items_form(delivery_ID, items_form_ID, item_ID)) {
                    System.out.println("Item does not exist, please enter a valid item ID");
                    continue;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        controller.remove_item_from_items_form(delivery_ID, items_form_ID, item_ID);
        controller.remove_loaded_item(delivery_ID, item_ID);

    }
}
