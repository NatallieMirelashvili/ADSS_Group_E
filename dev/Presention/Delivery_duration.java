package Presention;
import java.util.Scanner;
import Controller.controller;

public class Delivery_duration {
        public static void start_delivery_duration() {
            System.out.println("Enter delivery ID to start delivery or press -1 to go back to manager menu");
            Scanner sc = new Scanner(System.in);
            int delivery_ID = valid_delivery_ID(sc);
            if (delivery_ID == -1) {
                return;
            }
            for (int i = 0; i < controller.get_delivery_destinations_size(delivery_ID); i++) {
                if (controller.get_delivery_destinations_loading(delivery_ID, i)) { // if loading destination
                    String destination_name = controller.get_destinations_name(delivery_ID, i);
                    System.out.println("You arrived at " + destination_name + " destination");
                    System.out.println("Please enter truck current weight");
                    int weight = 0;
                    boolean weight_valid = false;
                    while (!weight_valid){
                        try {
                            weight = sc.nextInt();
                            weight_valid = true;
                        }catch (Exception e){
                            System.out.println("Invalid input. Please enter an Integer");
                        }
                    }
                    if (weight > controller.weight_check(delivery_ID)) {
                        System.out.println("The weight exceeds from the maximum truck weight.");
                        System.out.println("How would you like to handle an exception?");
                        System.out.println("1. Remove a destination site and its items");
                        System.out.println("2. Changing the destination site and its items");
                        System.out.println("3. replace the truck");
                        System.out.println("4. Remove some items");
                        int choice = 0;
                        while (true) {
                            try {
                                choice = sc.nextInt();
                                if (choice == 1) {
                                    Delivery_errors.remove_destination(delivery_ID, i);
                                    break;
                                }
                                if (choice == 2) {
                                    Delivery_errors.change_destination(delivery_ID,i);
                                    break;
                                }
                                if (choice == 3) {
                                    Delivery_errors.replace_truck(delivery_ID, weight);
                                    break;
                                }
                                if (choice == 4) {
                                    Delivery_errors.remove_items(delivery_ID,i);
                                    break;
                                } else {
                                    System.out.println("Invalid choice, please enter 1, 2, 3 or 4");
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid input. Please enter 1, 2, 3 or 4");
                            }
                        }
                    }
                    else {
                        controller.setCurr_weight(delivery_ID, weight);
                        System.out.println("The weight has been updated successfully, you can continue to the next destination");
                        }
                }
                else {
                    System.out.println("You arrived at " + controller.get_destinations_name(delivery_ID, i) + " destination");
                    // TODO: implement unloading destination, remove loaded items in delivery
                }
            }
            System.out.println("The delivery has been completed successfully");
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
                    if (controller.delivery_exists(ID) && controller.delivery_starts_now(ID)) {
                        System.out.println("Delivery number " + ID + " has started");
                        validChoice = true;
                    } else {
                        System.out.println("Delivery with this ID does not exist");
                        System.out.println("Enter delivery ID or press -1 to go back to delivery manager menu");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter an Integer");
                }
            }
            return ID;
        }
}
