package PresentationLayer;
import java.util.Scanner;
public class ProdReport {
    @Override
    public String toString() {
        return "What would like to report today?" +
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
        switch (userInput){
            case 1:
                ReportDamaged();
                break;
            case 2:
                ReportExpired();
                break;
            case 3:
                ReturnToMainMenu();
                break;
            default:
                //there is a outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-3");;
                break;
        }
        return userInput;
    }

}
