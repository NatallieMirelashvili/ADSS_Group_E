package PresentationLayer;
import ServiceLayer.StockController;

import java.util.Scanner;
public class MainMenu {
//    Flag
    private static boolean initProgram = false;
//    help functions
    private static boolean isInit(){
        if (!initProgram){
            System.out.println("Please choose option 1 first and initial the products in the market\n");
            return false;
        }
        return true;
    }

// ***Menu Functions***
    public static void initProg(){
        boolean initStatus = isInit();
        if (initStatus){
            System.out.println("System already initialed\n");
            return;
        }
        System.out.println("Welcome to your market system, to start please follow the steps:\n");
        ReadFileAndStock.readProducts();
        System.out.println("Thank you! Products line of your market initialize\n");
        ReadFileAndStock.readItems();
        initProgram = true;
        System.out.println("Thank you! we are ready to start...\n");

    }

    public static void GenerateReports(){
        boolean initStatus = isInit();
        if (!initStatus){return;}
        Report.runMenu();
    }
    public static void inventory(){
        boolean initStatus = isInit();
        if (!initStatus){return;}
        Management.runMenu();
    }
    public static void ThrowItem(){
        boolean initStatus = isInit();
        if (!initStatus){return;}
        ProduceReports.runMenu();
    }
    public static void managerDetails(){
        boolean initStatus = isInit();
        if (!initStatus){return;}
        System.out.println("""
                Warehouse manager: Naveh Gershoni
                email: ng@mailto.yossi
                phone number: 0525381648
                """);
    }
    public static void nextDay(){
        System.out.println("Grate job for today!\n");
        StockController.checkAllItemsExpCtr();
        StockController.checkAllItemsSaleCtr();
    }
    public static void exit(){
        boolean initStatus = isInit();
        if (!initStatus){return;}
        System.out.println("Exiting program...\nGoodBye!");
    }
    public static String printMenu() {
        return """
                What would you like to do today?
                1. fetch program
                2. Generate reports
                3. Decommissioning an items
                4. Preform an inventory actions
                5. Show warehouse manager's contact details
                6. Move to the next day before exit the office
                7. Exit program
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
            case 1 -> initProg();
            case 2 -> GenerateReports();
            case 3 -> ThrowItem();
            case 4 -> inventory();
            case 5 -> managerDetails();
            case 6 -> nextDay();
            case 7 -> exit();
            default -> {
                System.out.println("Please choose valid number between 1-7");

            }
        }
        return userInput;
    }

    public static void main(String[] args){
        runMenu();

    }
}
