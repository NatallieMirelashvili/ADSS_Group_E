package Presention;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import Controller.controller;
import com.google.gson.JsonObject;

public class Delivery_form {
    public static void add_new_delivery_form() {
        /**
         * Adds a new delivery form.
         */
        int truck_ID = 0;
        int driver_ID = 0;
        int site_ID = 0;
        String date;
        String time;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter delivery date (format: yyyy-MM-dd) or press -exit- to return to Delivery Manager menu");
        while (true) {
            date = sc.nextLine();
            if (date.equals("exit")) {
                return;
            }
            try {
                dateFormatter.parse(date);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd");
            }
        }
        System.out.println("Please enter delivery time (format: HH:mm) or press -exit- to return to Delivery Manager menu");
        while (true) {
            time = sc.nextLine();
            if (time.equals("exit")) {
                return;
            }
            try {
                timeFormatter.parse(time);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please enter the time in the format HH:mm");
            }
        }

        boolean truckChange = false;
        boolean driverChange = false;
        boolean validChoice = false;
        int choose;
        while (!validChoice) {
            while (!truckChange) {
                truck_ID = getValidTruckID(sc);
                if (truck_ID == -1)
                    return;
                truckChange = true;
            }
            while (!driverChange) {
                driver_ID = getValidDriverID(sc, date);
                if (driver_ID == -1)
                    return;
                driverChange = true;
            }
            if (!checklicense(truck_ID, driver_ID)) {
                System.out.println("Driver and truck do not have the same license type, please choose who to switch");
                System.out.println("1. Truck");
                System.out.println("2. Driver");
                try {
                    choose = sc.nextInt();
                    if (choose == 1) {
                        truckChange = false;
                        continue;
                    }
                    if (choose == 2) {
                        driverChange = false;
                        continue;
                    }
                    System.out.println("Invalid choice, please enter 1 or 2");
                    continue;
                } catch (Exception e) {
                    System.out.println("Invalid choice, please enter 1 or 2");
                    sc.next();
                }
            }
            validChoice = true;
        }
        validChoice = false;
        System.out.println("Please enter delivery origin site ID or press -1 to return to Delivery Manager menu");
        while (!validChoice) {
            try {
                site_ID = sc.nextInt();
                if (site_ID == -1) {
                    return;
                }
                if (site_ID < 0) {
                    System.out.println("Invalid input. Please enter a positive Integer as site ID");
                    continue;
                }
                if (!controller.site_exists(site_ID)) {
                    System.out.println("Site with this ID does not exist please enter a valid site ID");
                    continue;
                }
                validChoice = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as site ID");
                sc.next();
            }
        }
        int delivery_ID = controller.get_delivery_ID();
        System.out.println("Delivery added successfully, delivery ID is:" + delivery_ID);

        JsonObject delivery = new JsonObject();
        delivery.addProperty("date", date);
        delivery.addProperty("hour", time);
        delivery.addProperty("truck_ID", truck_ID);
        delivery.addProperty("driver_ID", driver_ID);
        delivery.addProperty("site_ID", site_ID);
        controller.add_delivery(delivery);

        int choice;
        while (true) {
            System.out.println("Enter 1 to create item form to the delivery or -1 to return to Delivery Manager menu");
            try {
                choice = sc.nextInt();
                if (choice == 1) {
                    Items_form_addition.add_new_items_form(delivery_ID);
                    break;
                }
                if (choice == -1) {
                    return;
                } else {
                    System.out.println("Invalid input. Please enter 1 or -1");

                }
            } catch (Exception e) {
                System.out.println("Error occurred while adding one of the items form, please try again");
                sc.next();
            }
        }
    }


        private static boolean checklicense(int truck_ID, int driver_ID) {
            /**
             * Checks if the driver and truck have the same license type.
             * @param truck_ID The ID of the truck.
             * @param driver_ID The ID of the driver.
             * @return True if the driver and truck have the same license type, false otherwise.
             */
            return controller.check_license(truck_ID, driver_ID);
        }

    static int getValidTruckID(Scanner sc) {
        /**
         * Validates a truck ID.
         * @param sc Scanner object for user input.
         * @return Valid truck ID or -1 if user wants to go back to manager menu.
         */
        int truck_ID;
        System.out.println("Please enter delivery truck ID or press -1 to return to Delivery Manager menu");
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
                }
                else
                return truck_ID;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as truck ID");
                sc.next();
            }
        }
    }

    private static int getValidDriverID(Scanner sc, String date) {
/**
         * Validates a driver ID.
         * @param sc Scanner object for user input.
         * @param date The date of the delivery.
         * @return Valid driver ID or -1 if user wants to go back to manager menu.
         */
        int driver_ID;
        System.out.println("Please enter delivery driver ID or press -1 to return to Delivery Manager menu");
        while (true) {
            try {
                driver_ID = sc.nextInt();
                if (driver_ID == -1) {
                    return -1;
                }
                if (driver_ID < 0) {
                    System.out.println("Invalid input. Please enter a positive Integer as driver ID");
                }
                else if (!controller.driver_exists(driver_ID)) {
                    System.out.println("Driver with this ID does not exist, please enter a valid driver ID");
                } else if (!controller.driver_available(driver_ID)) {
                    System.out.println("Driver with this ID is not available, please enter a valid driver ID");
                }
                else if (!controller.check_driver_schedule(driver_ID, date)) {
                    System.out.println("Driver with this ID has a delivery scheduled for this date, please enter a valid driver ID");
                }
                else
                return driver_ID;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer as driver ID");
                sc.next();
            }
        }
    }


}



