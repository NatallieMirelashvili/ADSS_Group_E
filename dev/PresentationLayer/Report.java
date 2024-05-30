package PresentationLayer;
import java.util.Scanner;
public class Report {
    //   *****Menu Functions****
    public static void  InventoryReport(){}
    public static void  ExpiredReport(){}
    public static void  DamagedReport(){}
    public static void  ThereIsExpItems(){}
    public static void  ReturnToMainMenu(){}
    @Override
    public String toString() {
        return "What report would you like to produce today?" +
                "1. Produce an inventory report" +
                "2. Produce an expired report" +
                "3. Produce a damaged report" +
                "4. Check if there are an expired items in store" +
                "5. Return to main menu";
    }
    public void runMenu(){
        int choice = 0;
        while (choice!=5){
            choice = GetChoice();
        }
    }
    public int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(this);
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> InventoryReport();
            case 2 -> ExpiredReport();
            case 3 -> DamagedReport();
            case 4 -> ThereIsExpItems();
            case 5 -> ReturnToMainMenu();
            default -> {
                //there is a outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-5");
                ;
            }
        }
        return userInput;
    }
}
