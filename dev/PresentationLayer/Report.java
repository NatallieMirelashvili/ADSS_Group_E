package PresentationLayer;
import ServiceLayer.ProductController;
import ServiceLayer.StockController;

import java.util.Scanner;
public class Report {

//    ***Help Functions***
    private static int GetIdFromUSR(String msg){
        Scanner scanner = new Scanner(System.in);
        System.out.print(msg);
        return scanner.nextInt();
    }

    private static void CheckGetToMinimal(int idItem) {
        boolean bool = StockController.checkMinimalControl(idItem);
        if (bool) {
            System.out.println("Those items are about to run out!\n");
        }
    }


    //   *****Menu Functions****


    public static void  ReportDamaged(){
        String msg = "Please enter the id number of the item you want to report its damage:\n";
        int id_damage = GetIdFromUSR(msg);
        CheckGetToMinimal(id_damage);
        boolean bool = StockController.reportDamageControl(id_damage);
        if(!bool){
            System.out.println("This item with the id: " + id_damage + " is not in the inventory\n");
            return;}
        System.out.println("The item reported successfully!\n");
    }
    public static void ReportExpired(){
        String msg = "Please enter the id number of the item you want to report on its expiration:\n";
        int id_expire = GetIdFromUSR(msg);
        CheckGetToMinimal(id_expire);
        boolean bool = StockController.reportExpiredControl(id_expire);
        if(!bool){
            System.out.println("This item with the id: " + id_expire + " is not in the inventory\n");
            return;
        }
        System.out.println("The item reported successfully!\n");
    }
    public static void SellItem() {
        String msg = "Please enter the id number of the item you want to sell\n";
        int input = GetIdFromUSR(msg);
        CheckGetToMinimal(input);
        boolean bool = StockController.sellItemControl(input);
        if(!bool){
            System.out.println("This item with the id: " + input + " is not in the inventory\n");
            return;
        }
        System.out.println("The item sold successfully!\n");
    }
    public static void  ReturnToMainMenu(){
        System.out.println("Returning to main menu...\n");
}
    public static String PrintMenu() {
        return """
                What would you like to report on?
                1. Report on a damaged item
                2. Report on an expired item.
                3. Sell an item
                4. Return to main menu
                """;
    }
    public static void runMenu(){
        int choice = 0;
        while (choice!=4){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(PrintMenu());
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> ReportDamaged();
            case 2 -> ReportExpired();
            case 3 -> SellItem();
            case 4 -> ReturnToMainMenu();
            default -> {
                System.out.println("Please choose valid number between 1-4");

            }
        }
        return userInput;
    }
}