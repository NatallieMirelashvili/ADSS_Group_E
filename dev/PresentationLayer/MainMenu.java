package PresentationLayer;
import ServiceLayer.StockController;

import java.util.Scanner;
public class MainMenu {
//    Flag
    private static boolean initProgram = false;
//    help functions
    private static boolean isInit(){
        return initProgram;
    }

// ***Menu Functions***
    public static void initProg(){
        boolean initStatus = isInit();
        if (initStatus){
            System.out.println("System already initialed\n");
            return;
        }
        System.out.println("Welcome to your market system\n");
        boolean bool = ReadFile.readProducts();
        if (!bool){
            return;
        }
        System.out.println("Thank you! Products line of your market initialize\n");
        bool = ReadFile.readItems();
        if (!bool){
            return;
        }
        initProgram = true;
        System.out.println("Thank you! we are ready to start...\n");
    }

    public static void GenerateReports(){
//        boolean initStatus = isInit();
//        if (!initStatus){return;}
        ProduceReports.runMenu();

    }
    public static void inventory(){
//        boolean initStatus = isInit();
//        if (!initStatus){return;}
        Management.runMenu();
    }
    public static void ThrowItem(){
//        boolean initStatus = isInit();
//        if (!initStatus){return;}
        Report.runMenu();
    }
    public static void managerDetails(){
//        boolean initStatus = isInit();
//        if (!initStatus){return;}
        System.out.println("""
                Warehouse manager: Naveh Gershoni
                email: ng@mailto.yossi
                phone number: 0525381648
                """);
    }
    public static void nextDay(){
        System.out.println("Grate job for today!\n");
        StockController.updateDateToNextCtr();
        StockController.checkAllItemsExpCtr();
        StockController.checkAllItemsSaleCtr();
    }
    public static void exit(){
//        boolean initStatus = isInit();
//        if (!initStatus){return;}
        System.out.println("Exiting program...\nGoodBye!");
    }
    public static String printMenu() {
        return """
                What would you like to do today?
                1. fetch program
                2. sell/report on a damage/expired item
                3. Generate reports
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
            case 2 -> ThrowItem();
            case 3 -> GenerateReports();
            case 4 -> inventory();
            case 5 -> managerDetails();
            case 6 -> nextDay();
            case 7 -> exit();
            default -> System.out.println("Please choose valid number between 1-7");

        }
        return userInput;
    }

    public static void main(String[] args){
        runMenu();

    }
}
