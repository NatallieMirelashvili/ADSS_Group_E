package PresentationLayer;
import java.util.Scanner;

public class Management {
    @Override
    public String toString() {
        return "What would like to do today?" +
                "1. Add an item to inventory" +
                "2. Remove an item from inventory" +
                "3. Sell an item" +
                "4. Update sales" +
                "5. Update discount from manufacturers" +
                "6. Return to main menu";
    }
    public void runMenu(){
        int choice = 0;
        while (choice!=6){
            choice = GetChoice();
        }
    }
    public int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(this);
        int userInput = scanner.nextInt();;
        switch (userInput){
            case 1:
                AddItem();
                break;
            case 2:
                RemoveItem();
                break;
            case 3:
                SellItem();
                break;
            case 4:
                UpdateSales();
                break;
            case 5:
                Updatediscount();
                break;
            case 6:
                ReturnToMainMenu();
                break;
            default:
                //there is a outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-6");;
                break;
        }
        return userInput;
    }
}
