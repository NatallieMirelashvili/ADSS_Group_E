package PresentationLayer;
import ServiceLayer.StockController;

import java.util.ArrayList;
import java.util.Scanner;
public class ProduceReports {
    //   *****Menu Functions****
    public static void  InventoryReport(){
        System.out.println("""
                If you want to watch all items for sale please type 'All' and then press ENTER.
                Else, if you want to watch items by categories please type Category and then press ENTER
                """);
        Scanner scan = new Scanner(System.in);
        String which = scan.nextLine();
        if (which.equals("All")) {
            System.out.println(StockController.showAllItemsCtr());
            return;
        }
        ArrayList<String> AskedCategories = Management.showCatalogChoices(scan);
        System.out.println(StockController.showByCatCtr(AskedCategories.get(0), AskedCategories.get(1), AskedCategories.get(2)));

    }
    public static void  ExpiredReport(){
        System.out.println(StockController.showExpReportsCtr());
    }
    public static void  DamagedReport(){
        System.out.println(StockController.showDamageReportsCtr());
    }
    public static void  ThereIsExpItems(){
        if(StockController.isThereExpCtr()){
        System.out.println("The are some expired items, if you want to see which items - please chose option 2\n");
        return;
        }
        System.out.println("There are no expired items in the store - grate selling work!\n");
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
            default -> System.out.println("Please choose valid number between 1-5");

        }
        return userInput;
    }
}