package Presention;
import Domain.Facade;
import java.util.Scanner;

import static Presention.Management.readStrFromUsr;


/**
 * MainMenu - a class which its purpose to run the main menu for thr user. The main menu functions contains passes to
 * sub menus by subjects which presented to user.
 * */
public class InventoryMenu {

    public static Facade myFacade = new Facade();


// ***Menu Functions***

    /***
     *Name:initProg - A method which manage the initial of the system if it is necessary.
     * Args:None
     * Returns:None
     */


    public static void GenerateReports(){
        ProduceReports.runMenu();

    }
    public static void inventory(){
        Management.runMenu();
    }
    public static void ThrowItem(){
        Report.runMenu();
    }

    /***
     *Name:managerDetails - A method which prints the warehouse's manager's context details.
     * Args:None
     * Returns:None
     */
    public static void managerDetails(){
        System.out.println("""
                Warehouse manager: Naveh Gershoni
                email: ng@mailto.yossi
                phone number: 0525381648
                """);
    }

    /***
     *Name:nextDay - A method which moves the current day - in the system's world! The method updates product's price by
     * the end date of sales or starts sale if its start day of sale. Additionally, its removes expired items from the inventory.
     * You might want to use this option when its end of the work day in the market.
     * Args:None
     * Returns:None
     */
    public static void nextDay(){
        System.out.println("Grate job for today!\n");
        Facade.moveToNextDayService();
        myFacade.checkAllItemsExpService();
        myFacade.checkAllProductSaleService();
    }

    public static void RequestDelivery(){
        Scanner scan = new Scanner(System.in);
        String manuMSG = "Please enter the name of the manufacturer you want to make a delivery: ";
        String manu = readStrFromUsr(manuMSG, scan);
        if (myFacade.RequestDelivery(manu)){
            System.out.println("The delivery was made successfully!\n");
            return;
        }
        System.out.println("There are not products in shortage from this manufacturer.\n");
    }
    public static void exit(){
        System.out.println("Exiting Inventory Menu");
    }
    public static String printMenu() {
        return """
                What would you like to do today?
                1. sell/report on a damage/expired item/retrieve item
                2. Generate reports
                3. Preform an inventory actions
                4. Show warehouse manager's contact details
                5. End of shift report
                6. Request to delivery
                7. Exit Inventory Menu
                """;
    }

    public static void runMenu(){
        int choice = 0;
        while (choice!=7){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(printMenu());
        int userInput = scanner.nextInt();
        switch (userInput) {
//
            case 1 -> ThrowItem();
            case 2 -> GenerateReports();
            case 3 -> inventory();
            case 4 -> managerDetails();
            case 5 -> nextDay();
            case 6 -> RequestDelivery();
            case 7 -> exit();
            default -> System.out.println("Please choose valid number between 1-7");

        }
        return userInput;
    }

    public static void main(String[] args){
        runMenu();

    }
}
