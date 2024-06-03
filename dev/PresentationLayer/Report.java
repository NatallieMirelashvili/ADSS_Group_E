package PresentationLayer;
import java.util.Scanner;
public class Report {
    //   *****Menu Functions****
    public static void  InventoryReport(){
        //from controller
    }
    public static void  ExpiredReport(){
        //from controller
    }
    public static void  DamagedReport(){
        //from controller
    }
    public static void  ThereIsExpItems(){
        //from controller
    }
    public static void  ReturnToMainMenu(){
        System.out.println("Returning to main menu...\n");

    }
    public static String PrintMenu() {
        return """
                What report would you like to produce today?
                1. Produce an inventory report
                2. Produce an expired report
                3. Produce a damaged report
                4. Check if there are an expired items in store
                5. Return to main menu""";
    }
    public static void runMenu(){
        int choice = 0;
        while (choice!=5){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(PrintMenu());
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> InventoryReport();
            case 2 -> ExpiredReport();
            case 3 -> DamagedReport();
            case 4 -> ThereIsExpItems();
            case 5 -> ReturnToMainMenu();
            default -> {
                System.out.println("Please choose valid number between 1-5");

            }
        }
        return userInput;
    }
}