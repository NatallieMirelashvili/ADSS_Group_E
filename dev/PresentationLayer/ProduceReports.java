package PresentationLayer;
import DomainLayer.Tuple;
import ServiceLayer.StockController;

import java.util.ArrayList;
import java.util.Scanner;
public class ProduceReports {
    //   *****Menu Functions****
    public static void  InventoryReport(){
        System.out.println("""
                If you want to watch all items for sale please type 'All' and then press ENTER.
                Else, if you want to watch items by categories please type Category and then press ENTER""");
        Scanner scan = new Scanner(System.in);
        String which = scan.nextLine();
        if (which.equals("All")) {
            System.out.println(StockController.showAllItemsCtr());
            return;
        }
        Tuple<ArrayList<String>,Boolean> askCatCorrect = Management.showCatalogChoices(scan);
        System.out.println(StockController.showByCatCtr(askCatCorrect.getVal1().get(0), askCatCorrect.getVal1().get(1), askCatCorrect.getVal1().get(2)));

    }
    public static void  ExpiredReport(){
        System.out.println(StockController.showExpReportsCtr());
    }
    public static void  DamagedReport(){
        System.out.println(StockController.showDamageReportsCtr());
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
            case 1 -> InventoryReport();
            case 2 -> ExpiredReport();
            case 3 -> DamagedReport();
            case 4 -> ReturnToMainMenu();
            default -> System.out.println("Please choose valid number between 1-5");

        }
        return userInput;
    }
}