package Presention;
import java.util.Scanner;
import Controller.controller;

public class Delivery_duration {
        public static void start_delivery_duration() {
            System.out.println("Enter delivery ID to start delivery or press -1 to go back to manager menu");
            Scanner sc = new Scanner(System.in);
            int delivery_ID;
            while(true) {
                try {
                    delivery_ID = valid_delivery_ID(sc);
                    if (delivery_ID == -1) {
                        return;
                    }
                    else{
                        System.out.println("Delivery number " + delivery_ID + " has started !");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter an Integer");
                    sc.next();
                }
            }
            for (int i = 0; i < controller.get_delivery_destinations_size(delivery_ID); i++) {
                int curr_destination_ID = controller.get_destination_ID(delivery_ID, i);
                if (controller.get_delivery_destinations_loading(delivery_ID, i)) { // if loading destination
                    String destination_name = controller.get_destinations_name(delivery_ID, i);
                    System.out.println("You arrived at " + destination_name + " destination");

                    int weight = 0;
                    boolean weight_valid = false;
                    while (!weight_valid) {
                        try {
                            System.out.println("Please enter truck current weight");
                            weight = sc.nextInt();
                            weight_valid = true;
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter an Integer");
                            sc.next();
                        }

                        if (weight > controller.weight_check(delivery_ID)) {
                            System.out.println("The weight exceeds from the maximum truck weight.");
                            System.out.println("How would you like to handle an exception?");
                            System.out.println("1. Remove a destination site and its items");
                            System.out.println("2. Changing the destination site and its items");
                            System.out.println("3. replace the truck");
                            System.out.println("4. Remove some items");
                            int choice;
                            while (true) {
                                try {
                                    choice = sc.nextInt();
                                    if (choice == 1) {
                                        Delivery_errors.remove_destination(delivery_ID, i,curr_destination_ID);
                                        weight_valid = false;
                                        break;
                                    }
                                    if (choice == 2) {
                                        Delivery_errors.change_destination(delivery_ID, i);
                                        weight_valid = false;
                                        break;
                                    }
                                    if (choice == 3) {
                                        Delivery_errors.replace_truck(delivery_ID, weight);
                                        controller.setCurr_weight(delivery_ID, weight);
                                        System.out.println("The weight has been updated successfully, you can continue to the next destination");
                                        weight_valid=true;
                                        break;
                                    }
                                    if (choice == 4) {
                                        Delivery_errors.remove_items(delivery_ID, i);
                                        weight_valid=false;
                                        break;
                                    } else {
                                        System.out.println("Invalid choice, please enter 1, 2, 3 or 4");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input. Please enter 1, 2, 3 or 4");
                                    sc.next();
                                }
                            }
                        } else {
                            controller.setCurr_weight(delivery_ID, weight);
                            System.out.println("The weight has been updated successfully, you can continue to the next destination");
                        }
                    }
                }
                else {
                    System.out.println("You arrived at " + controller.get_destinations_name(delivery_ID, i) + " destination");
                    int site_id = controller.get_destination_site_ID(delivery_ID,i);
                    for (int j=0;j<controller.get_items_amount_in_destination(delivery_ID,site_id);j++){
                        int item_ID = controller.get_item_ID_in_destinations(delivery_ID,site_id,j);
                        String item_name = controller.get_item_name(item_ID);
                        int quantity = controller.get_item_quantity_in_destinations(delivery_ID,site_id,item_ID);
                        System.out.println("You have unloaded " + quantity + " " + item_name + " from the truck");
                        controller.decrease_item_in_loaded_items(delivery_ID,item_ID,quantity);
                    }
                }
            }
            System.out.println("The delivery has been completed successfully");
            controller.finished_delivery(delivery_ID);
        }

        public static int valid_delivery_ID(Scanner sc) {
            int ID = 0;
            boolean validChoice = false;
            while (!validChoice) {
                try {
                    ID = sc.nextInt();
                    if (ID == -1) {
                        return -1;
                    }
                    if (controller.delivery_exists(ID)){
                        if (controller.delivery_starts_now(ID)){
                            validChoice = true;
                            if (controller.get_finished_delivery(ID)) {
                                System.out.println("Delivery number " + ID + " has already been completed");
                                System.out.println("Enter delivery ID or press -1 to go back to delivery manager menu");
                                validChoice = false;
                            } else if (!controller.has_items_form(ID)){
                                System.out.println("Delivery number " + ID + " does not have an items form");
                                System.out.println("Enter delivery ID or press -1 to go back to delivery manager menu");
                                validChoice = false;
                            }
                        }
                    }
                    else {
                        System.out.println("Delivery with this ID does not exist");
                        System.out.println("Enter delivery ID or press -1 to go back to delivery manager menu");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter an Integer");
                    sc.next();
                }
            }
            return ID;
        }
}
