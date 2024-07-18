import java.util.Scanner;
import Presention.InventoryMenu;

public class MainMenu {
    public static String[] args1 = new String[0];
    public static void exit(){
        System.out.println("Exiting program...\nGoodBye!");
    }
    public static String printMenu() {
        return """
                What would you like to do today?
                1. Inventory Actions
                2. Delivery Actions
                3. Exit program
                """;
    }

    public static void runMenu(){
        int choice = 0;
        while (choice!=3){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(printMenu());
        int userInput = scanner.nextInt();
        switch (userInput) {
//
            case 1 -> InventoryMenu.main(args1);
            case 2 -> DeliveryMenu.main(args1);
            case 3 -> exit();
            default -> System.out.println("Please choose valid number between 1-3");

        }
        return userInput;
    }

    public static void main(String[] args){
        runMenu();

    }
}
