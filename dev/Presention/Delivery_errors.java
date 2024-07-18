package Presention;
import Controller.controller;
import java.util.Scanner;


public class Delivery_errors {
    public static int new_destination_ID=-1;
    public static void remove_destination(int delivery_ID, int index, int curr_destination_ID) {
        /**
         * Removes a destination from a delivery.
         * @param delivery_ID The ID of the delivery.
         * @param index The index of the destination.
         * @param curr_destination_ID The current destination ID.
         */
        System.out.println("Please enter the ID of an unloading destination you would like to remove");
        Scanner sc = new Scanner(System.in);
        int destination_ID = valid_destination_ID(sc, delivery_ID, index);
        controller.remove_destination(delivery_ID, destination_ID, curr_destination_ID);
    }

    public static void replace_truck(int deliveryId, int weight) {

        /**
         * Replaces a truck in a delivery.
         * @param deliveryId The ID of the delivery.
         * @param weight The weight of the delivery.
         */
        System.out.println("Please enter the ID of the truck you would like to replace the current truck with");
        Scanner sc = new Scanner(System.in);
        int truck_ID = getValidTruckID(sc,weight,deliveryId);
        controller.replace_truck(deliveryId, truck_ID, weight);
    }


    public static int valid_destination_ID(Scanner sc, int delivery_ID, int index) {
        /**
         * Validates a destination ID.
         * @param sc Scanner object for user input.
         * @param delivery_ID The ID of the delivery.
         * @param index The index of the destination.
         * @return Valid destination ID.
         */
        int destination_ID;
        while (true) {
            try {
                destination_ID = sc.nextInt();
                if (destination_ID < 0) {
                    System.out.println("Invalid destination ID. Please enter a valid destination ID");
                    continue;
                }
                if (!controller.destination_exists(delivery_ID,destination_ID)) {
                    System.out.println("Destination with this ID does not exist in this delivery, please enter a valid destination ID");
                    continue;
                }
                if (controller.get_site_type(destination_ID).equals("loading")) {
                    System.out.println("This destination is an loading destination, please enter a unloading destination ID");
                    continue;
                }
                if (controller.destinations_been_visited(delivery_ID, destination_ID, index)) {
                    System.out.println("This destination is already visited, please enter an unloading destination ID that has not been visited yet");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        return destination_ID;
    }


    public static void remove_items(int deliveryId, int index) {
/**
         * Removes items from a destination.
         * @param deliveryId The ID of the delivery.
         * @param index The index of the destination.
         */

        System.out.println("Please enter the ID of the unloading destination you would like to remove items from");
        Scanner sc = new Scanner(System.in);
        int destination_ID = valid_destination_ID(sc, deliveryId, index);
        System.out.println("Please enter the ID of the item you would like to remove");
        int item_ID = valid_item_ID(sc, deliveryId, destination_ID);
        controller.remove_item_from_destination(deliveryId, item_ID, destination_ID);
        controller.remove_loaded_item(deliveryId,item_ID);

    }

    private static int valid_item_ID(Scanner sc, int deliveryId, int destinationId) {
        /**
         * Validates an item ID.
         * @param sc Scanner object for user input.
         * @param deliveryId The ID of the delivery.
         * @param destinationId The ID of the destination.
         * @return Valid item ID.
         */
        int item_ID;
        while (true) {
            try {
                item_ID = sc.nextInt();
                if (item_ID < 0) {
                    System.out.println("Invalid item ID. Please enter a valid item ID");
                    continue;
                }
                if (!controller.item_exists_in_delivery(deliveryId, item_ID)) {
                    System.out.println("Item with this ID does not exist in this delivery, please enter a valid item ID");
                    continue;
                }
                if (!controller.item_exists_in_destination(item_ID, destinationId, deliveryId)) {
                    System.out.println("Item with this ID does not exist in this destination, please enter a valid item ID");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        return item_ID;
    }

    public static void change_destination(int deliveryId, int index, int curr_destination_ID) {
        /**
         * Changes the destination of a delivery.
         * @param deliveryId The ID of the delivery.
         * @param index The index of the destination.
         * @param curr_destination_ID The current destination ID.
         */
        System.out.println("Please enter the ID of the unloading destination you would like to change");
        Scanner sc = new Scanner(System.in);
        int destination_ID = valid_destination_ID(sc, deliveryId, index);
        System.out.println("Please enter the ID of the new destination site");
        new_destination_ID = valid_change_destination_ID(sc, deliveryId);
        controller.remove_destination(deliveryId, destination_ID, curr_destination_ID);
        Items_form_addition.add_new_items_form(deliveryId);
        new_destination_ID=-1;


    }

    public static int valid_change_destination_ID(Scanner sc, int delivery_ID) {
        /**
         * Validates a destination ID for changing a destination.
         * @param sc Scanner object for user input.
         * @param delivery_ID The ID of the delivery.
         * @return Valid destination ID.
         */
        int destination_ID;
        while (true) {
            try {
                destination_ID = sc.nextInt();
                if (destination_ID < 0) {
                    System.out.println("Invalid destination ID. Please enter a valid destination ID");
                    continue;
                }
                if (controller.destination_exists(delivery_ID,destination_ID)) {
                    System.out.println("Destination with this ID already exist in this delivery, please enter a valid destination ID");
                    continue;
                }
                if (controller.get_site_type(destination_ID).equals("loading")) {
                    System.out.println("This destination is an loading destination, please enter an unloading destination ID");
                    continue;
                }
                if (!controller.same_area(delivery_ID,destination_ID)){
                    System.out.println("The destination is not in the same area as the previous destination, please enter a destination in the same area");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer");
                sc.next();
            }
        }
        return destination_ID;
    }

    static int getValidTruckID(Scanner sc,int weight,int deliveryId) {
        /**
         * Validates a truck ID.
         * @param sc Scanner object for user input.
         * @param weight The weight of the delivery.
         * @param deliveryId The ID of the delivery.
         * @return Valid truck ID.
         */
        int truck_ID;
        int driver_ID = controller.get_driver_ID_from_delivery(deliveryId);
        while (true) {
            try {
                truck_ID = sc.nextInt();
                if (truck_ID == -1) {
                    return -1;
                }
                if (truck_ID < 0) {
                    System.out.println("Invalid input. Please enter a positive Integer as truck ID");
                }
                else if (!controller.truck_exists(truck_ID)) {
                    System.out.println("Truck with this ID does not exist, please enter a valid truck ID");
                } else if (!controller.truck_available(truck_ID)) {
                    System.out.println("Truck with this ID is not available, please enter a valid truck ID");
                } else if (controller.truck_max_weight(truck_ID) < weight) {
                    System.out.println("The truck is not suitable for the weight, please enter a valid truck ID");
                }
                else if (!controller.check_license(truck_ID, driver_ID))
                {
                    System.out.println("The truck and driver do not have the same license type, please enter a valid truck ID");
                }
                else
                    return truck_ID;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as truck ID");
                sc.next();
            }
        }
    }

    }

