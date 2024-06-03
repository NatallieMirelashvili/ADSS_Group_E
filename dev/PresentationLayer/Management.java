package PresentationLayer;
import ServiceLayer.ProductController;
import ServiceLayer.StockController;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Scanner;


public class Management {

//    help function:
    private static ArrayList<String> showCatalogChoices(){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> categoriesForSales = new ArrayList<>(3);
        String mainCat = readStrFromUsr("Please enter the main category you would like to update its sale", scan);
        String sub = readStrFromUsr("If you want to update sale on a specific sub category, enter its name or press 0 if dont", scan);
        String size = readStrFromUsr("If you want to update sale on a specific size of product, for liquid add the " +
                "word 'litters' else add the word 'grams' after the size number or press 0 if you dont", scan);
        categoriesForSales.add(mainCat);
        categoriesForSales.add(sub);
        categoriesForSales.add(size);
        return categoriesForSales;
    }
    private static String readStrFromUsr(String msg, Scanner scan){
        System.out.println(msg);
        return scan.nextLine();
    }
    private static int readIntFromUsr(String msg, Scanner scan){
        System.out.println(msg);
        return scan.nextInt();
    }

    private static JsonObject CreateJason(int iteration, ArrayList<String> msgLst, ArrayList<String> memberLst){
        JsonObject myJson = new JsonObject();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i <= iteration; i++){
            String member = memberLst.get(i);
            String value = readStrFromUsr(msgLst.get(i), scanner);
            myJson.addProperty(member, value);
        }
        return myJson;
    }

    private static void CheckGetToMinimal(int catalogNum){
        boolean bool = StockController.checkMinimal(catalogNum);
        if (bool){
            System.out.println("The product with the catalog number: " + catalogNum + "is about to run out!");
        }
    }

//   *****Menu Functions****
    public static void AddProd(){
        ArrayList<String> msgLst = new ArrayList<>(8);
        msgLst.add("Enter main category: ");
        msgLst.add("Enter sub category: ");
        msgLst.add("Enter size, for liquid add the word 'litters' else add the word 'grams' after the size number: ");
        msgLst.add("Enter product's manufacturer: ");
        msgLst.add("Enter catalog number: ");
        msgLst.add("Enter initial market price: ");
        msgLst.add("Enter initial manufacturer price of product:");
        msgLst.add("Enter minimal amount in which to inform when its product about to run out: ");
        ArrayList<String> memberLst = new ArrayList<>(8);
        memberLst.add("catName");
        memberLst.add("subCatName");
        memberLst.add("size");
        memberLst.add("manuFactor");
        memberLst.add("catalogNum");
        memberLst.add("marketPriceConst");
        memberLst.add("manuPriceConst");
        memberLst.add("minimalAmount");
        JsonObject JsonObj = CreateJason(8, msgLst, memberLst);
//       need to send the json to proper controller


    }
    public static void AddItem(){
        ArrayList<String> msgLst = new ArrayList<>(4);
        msgLst.add("Enter item id: ");
        msgLst.add("Enter expiration date DD-MM-YYYY: ");
        msgLst.add("Enter the aile - if you are in warehouse enter a letter from A-Z. else enter item's main category" +
                "Please press ',' tab and then enter the shelf number: ");
        msgLst.add("Enter catalog number from the Catalog Number Table you received with the program instructions: ");
        ArrayList<String> memberLst = new ArrayList<>(4);
        memberLst.add("id");
        memberLst.add("expirationDate");
        memberLst.add("place");
        memberLst.add("catalogNum");
        JsonObject JsonObj = CreateJason(4, msgLst, memberLst);

    }
    public static void RemoveProd(){
        Scanner scan = new Scanner(System.in);
        int input = readIntFromUsr("Please enter the catalog number of the product you want cancel from selling", scan);
        boolean status = StockController.removeItem(input);
        CheckGetToMinimal(input);
    }
    public static void SellItem() {
        int input = readIntFromUsr("Please enter the id number of the item you want to sell", scan);
        boolean status = StockController.sellItem(input);
        CheckGetToMinimal(input);
    }
    public static void UpdateSales(){
        ArrayList<String> askCat = showCatalogChoices();
        StockController.updateSale(askCat.get(0), askCat.get(1), askCat.get(2));
    }
    public static void UpdateDiscount(){
        ArrayList<String> askCat = showCatalogChoices();
        StockController.updateDis(askCat.get(0), askCat.get(1), askCat.get(2));
    }
    public static void MoveStoreWare(){
        Scanner scan = new Scanner(System.in);
        int id = readIntFromUsr("Please enter the id number of the item you want to move from store to warehouse", scan);
        ProductController.moveItemFromS(id);
    }
    public static void MoveWareStore(){
        Scanner scan = new Scanner(System.in);
        int id = readIntFromUsr("Please enter the id number of the item you want to move from store to warehouse", scan);
        ProductController.moveItemFromW(id);
    }
    public static void ReturnToMainMenu(){
        System.out.println("Returning to main menu...");
    }


    public static String PrintMenu() {
        return """
                What would you like to do today?
                1. Add a product to inventory
                2. Add an item to inventory
                3. Remove a product from inventory
                4. Sell an item
                5. Update sales
                6. Update discount from manufacturers
                7. Move an item from store to warehouse
                8. Move an item from warehouse to store
                9. Return to main menu""";
    }
    public static void runMenu(){
        int choice = 0;
        while (choice!=9){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(PrintMenu());
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> AddProd();
            case 2 -> AddItem();
            case 3 -> RemoveProd();
            case 4 -> SellItem();
            case 5 -> UpdateSales();
            case 6 -> UpdateDiscount();
            case 7 -> MoveStoreWare();
            case 8 -> MoveWareStore();
            case 9 -> ReturnToMainMenu();
            default -> {
                System.out.println("Please choose valid number between 1-9");
                ;
            }
        }
        return userInput;
    }

}
