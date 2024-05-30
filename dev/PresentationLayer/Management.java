package PresentationLayer;
import java.util.Scanner;

public class Management {
//    Notes:

//   *****Menu Functions****
    public static void AddItem(){}
    public static void RemoveItem(){}
    public static void SellItem(){}
    public static void UpdateSales(){}
    public static void UpdateDiscount(){}
    public static void MoveStoreWare(){}
    public static void MoveWareStore(){}
    public static void ReturnToMainMenu(){}


    @Override
    public String toString() {
        return "What would you like to do today?" +
                "1. Add an item to inventory" +
                "2. Remove an item from inventory" +
                "3. Sell an item" +
                "4. Update sales" +
                "5. Update discount from manufacturers" +
                "6. Move an item from store to warehouse" +
                "7. Move an item from warehouse to store" +
                "8. Return to main menu";
    }
    public void runMenu(){
        int choice = 0;
        while (choice!=8){
            choice = GetChoice();
        }
    }
    public int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(this);
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> AddItem();
            case 2 -> RemoveItem();
            case 3 -> SellItem();
            case 4 -> UpdateSales();
            case 5 -> UpdateDiscount();
            case 6 -> MoveStoreWare();
            case 7 -> MoveWareStore();
            case 8 -> ReturnToMainMenu();
            default -> {
                //there is outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-8");
                ;
            }
        }
        return userInput;
    }

}
