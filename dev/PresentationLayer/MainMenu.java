package PresentationLayer;
import DomainLayer.Facade;
import java.util.Scanner;


/**
 * MainMenu - a class which its purpose to run the main menu for thr user. The main menu functions contains passes to
 * sub menus by subjects which presented to user.
 * */
public class MainMenu {

    public static Facade myFacade = new Facade();
//    Flag
    private static boolean initProgram = false;
//    help functions
    private static boolean isInit(){
        return initProgram;
    }

// ***Menu Functions***

    /***
     *Name:initProg - A method which manage the initial of the system if it is necessary.
     * Args:None
     * Returns:None
     */
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
        myFacade.moveToNextDayService();
        myFacade.checkAllItemsExpService();
        myFacade.checkAllProductSaleService();
    }
    public static void exit(){
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
                6. End of shift report
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
