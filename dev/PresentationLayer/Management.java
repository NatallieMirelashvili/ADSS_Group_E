package PresentationLayer;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Scanner;


public class Management {

//    help function:
    private static int showCatalogChoices(){
        Scanner scan = new Scanner(System.in);
        String mainCat = readFromUsr("Please enter the main category you would like to update its sale", scan);
        String sub = readFromUsr("If you want to update sale on a specific sub category, enter its name or press 0 if dont", scan);
        String size = readFromUsr("If you want to update sale on a specific size of product, for liquid add the " +
                "word 'litters' else add the word 'grams' after the size number or press 0 if you dont", scan);
        return 0;
    }
    private static String readFromUsr(String msg, Scanner scan){
        System.out.println(msg);
        return scan.nextLine();
    }

    private static JsonObject CreateJason(int iteration, ArrayList<String> msgLst, ArrayList<String> memberLst){
        JsonObject myJson = new JsonObject();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i <= iteration; i++){
            String member = memberLst.get(i);
            String value = readFromUsr(msgLst.get(i), scanner);
            myJson.addProperty(member, value);
        }
        return myJson;
    }

    private static void alertOnMinAmount(int makat){
        System.out.println("The product with the catalog number: " + makat + "is about to run out!");
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
//        JsonObj.addProperty("total", "0, 0");
//        JsonObj.addProperty("manuPriceCurr", JsonObj.get("manuPriceConst").getAsString());
//        JsonObj.addProperty("marketPriceCurr", JsonObj.get("marketPriceConst").getAsString());
//        JsonObj.addProperty("mySalePrice", "null");
//        JsonObj.addProperty("discount", "0");
//        JsonObj.addProperty("items", "null");
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
        String input = readFromUsr("Please enter the catalog number of the product you want cancel from selling", scan);
//        ask from product controller
    }
    public static void SellItem() {
        Scanner scan = new Scanner(System.in);
        String input = readFromUsr("Please enter the id number of the item you want to sell", scan);
//        ask from Stock controller - see if you get to the minimal amount for report a lak.
    }
    public static void UpdateSales(){
        int stam = showCatalogChoices();
//        ask from stock controller

    }
    public static void UpdateDiscount(){
        int stam = showCatalogChoices();
        // ask from stock controller
    }
    public static void MoveStoreWare(){
        Scanner scan = new Scanner(System.in);
        String mainCat = readFromUsr("Please enter the id number of the item you want to move from store to warehouse", scan);
//        ask from product controller
    }
    public static void MoveWareStore(){
        Scanner scan = new Scanner(System.in);
        String mainCat = readFromUsr("Please enter the id number of the item you want to move from warehouse to store", scan);
//        ask from product controller
    }
    public static void ReturnToMainMenu(){
        System.out.println("Returning to main menu");
    }


    public static String PrintMenu() {
        return "What would you like to do today?" +
                "1. Add an item to inventory" +
                "2. Add a product to inventory" +
                "3. Remove a product from inventory" +
                "3. Sell an item" +
                "4. Update sales" +
                "5. Update discount from manufacturers" +
                "6. Move an item from store to warehouse" +
                "7. Move an item from warehouse to store" +
                "8. Return to main menu";
    }
    public static void runMenu(){
        int choice = 0;
        while (choice!=8){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(PrintMenu());
        int userInput = scanner.nextInt();;
        switch (userInput) {
            case 1 -> AddItem();
            case 2 -> RemoveProd();
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
