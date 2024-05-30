package PresentationLayer;
import java.util.Scanner;
public class ProdReport {
//    Note: expired item or defective report contains a: catalog number, place in store, all categories, item id
//    inventory report by category add user input for these option and support all options (mini switch case) - contains
//    current amount of any product in those categories, places in store, id, catalog number.
//   *****Menu Functions****
public static void  ReportDamaged(){}
public static void  ReportExpired(){}
public static void  ReturnToMainMenu(){}
    @Override
    public String toString() {
        return "What would you like to report today?" +
                "1. Report on a damaged item" +
                "2. Report on an expired item" +
                "3. Return to main menu";
    }
    public void runMenu(){
        int choice = 0;
        while (choice!=3){
            choice = GetChoice();
        }
    }
    public int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(this);
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> ReportDamaged();
            case 2 -> ReportExpired();
            case 3 -> ReturnToMainMenu();
            default -> {
                //there is outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-3");
                ;
            }
        }
        return userInput;
    }

}
