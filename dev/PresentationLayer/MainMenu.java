package PresentationLayer;
import java.util.Scanner;
public class MainMenu {
//    Flag
    private static int initProgram = 0;
//    help functions
    private static boolean isInit(){
        if (initProgram == 0){
            System.out.println("Please choose option 1 first");
            return false;
        }
        return true;
    }

// ***Menu Functions***
    public static void initProg(){
        boolean bool = isInit();
        if (bool){
            System.out.println("System initialed");
        }
        ReadFileAndStock.readFileByType();
        ReadFileAndStock.createStock();
        initProgram = 1;
    }
//    move all read file to another class and add reaction of stock

    public static void GenerateReports(){
        boolean bool = isInit();
        if (!bool){return;}
        ProdReport.runMenu();
    }
    public static void inventory(){
        boolean bool = isInit();
        if (!bool){return;}
        Management.runMenu();
    }
    public static void ThrowItem(){
        boolean bool = isInit();
        if (!bool){return;}
        Report.runMenu();
    }
    public static void managerDetails(){
        boolean bool = isInit();
        if (!bool){return;}
        System.out.println("Warehouse manager: Naveh Gershoni" +
                "email: ng@mailto.yossi" +
                "phone number: 0525381648");
    }
    public static void exit(){
        boolean bool = isInit();
        if (!bool){return;}
        System.out.println("GoodBye...");
    }
    public static String printMenu() {
        return "What would you like to do today?" +
                "1. fetch program" +
                "2. Generate reports" +
                "3. Decommissioning an items" +
                "4. Preform an inventory actions" +
                "5. Show warehouse manager's contact details" +
                "6. Exit program";
    }

    public static void runMenu(){
        int choice = 0;
        while (choice!=5){
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
            case 6 -> exit();
            default -> {
                //there is outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-3");

            }
        }
        return userInput;
    }

    public static void main(String[] args){
        runMenu();

    }
}
