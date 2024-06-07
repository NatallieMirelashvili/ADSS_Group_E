package Presention;
import java.util.Scanner;
import Controller.controller;

public class Delivery_duration {
        public static void start_delivery_duration() {
            System.out.println("Enter delivery ID to start delivery or press -1 to go back to manager menu");
            Scanner sc = new Scanner(System.in);
            int ID = valid_delivery_ID(sc);
            if (ID == -1) {
                return;
            }
            for (int i = 0; i < controller.get_delivery_destinations_size(ID); i++) {
                if (controller.get_delivery_destinations_loading(ID, i)) {
                    String destination_name = controller.get_destinations_name(ID, i);
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
                    if (weight > controller.weight_check(ID)){
                        System.out.println("The weight exceeds from the maximum truck weight.");
                        System.out.println("How would you like to handle an exception?");
                        System.out.println("1. How would you like to handle an exception?");
                        System.out.println("2. Changing the transport destination and its items");
                        System.out.println("3. replace the truck");
                        System.out.println("4. Removal of some items");
                    }
                    else {
                        controller.setCurr_weight(ID, weight);
                        System.out.println("The weight has been updated successfully, you can continue to the next destination");
            }}}
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
