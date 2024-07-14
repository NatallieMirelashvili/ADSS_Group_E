package PresentationLayer;
import DomainLayer.Tuple;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import static PresentationLayer.MainMenu.myFacade;

public class Management {
//    help function:
    /***
     *Name:showCatalogChoices - A sub function which present the categories you can classify your products when its necessary.
     * Args: Scanner scan - so the function will be able to scan your answers.
     * Returns: ArrayList<String> - of the asked categories.
     */
    public static ArrayList<String> showCatalogChoices(Scanner scan){
        ArrayList<String> categoriesForSales = new ArrayList<>(3);
        String mainCat = readStrFromUsr("Please enter the main category", scan);
        String sub = readStrFromUsr("If you want to add a specific sub category, enter its name or press 0 if dont", scan);
        String size = readStrFromUsr("If you want to add a specific size of product, for liquid add the " +
                "word 'litter' else add the word 'gram' after the size number or press 0 if you dont", scan);
        categoriesForSales.add(mainCat);
        categoriesForSales.add(sub);
        categoriesForSales.add(size);
        boolean bool = myFacade.searchProdByCategoryService(categoriesForSales.get(0),categoriesForSales.get(1),categoriesForSales.get(2));
        if(!bool){
            System.out.println("There is no such products with the categories you supplied!\nIf you want to add any - please choose option 1\n");
            return null;
        }
        return categoriesForSales;
    }
    static String readStrFromUsr(String msg, Scanner scan){
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
        for (int i = 0; i < iteration; i++){
            String member = memberLst.get(i);
            String value = readStrFromUsr(msgLst.get(i), scanner);
            myJson.addProperty(member, value);
        }
        return myJson;
    }

    /***
     *Name:AddProd - A Method who adds new product to the inventory by the asked characters.
     * Args: None
     * Returns: None
     */
//   *****Menu Functions****
    public static void AddProd(){
        ArrayList<String> msgLst = new ArrayList<>(10);
        msgLst.add("Enter main category: ");
        msgLst.add("Enter sub category: ");
        msgLst.add("Enter size, for liquid add the word 'litter' else add the word 'gram' after the size number: ");
        msgLst.add("Enter product's manufacturer: ");
        msgLst.add("Enter catalog number: ");
        msgLst.add("Enter initial market price: ");
        msgLst.add("Enter initial manufacturer price of product:");
        msgLst.add("Enter minimal amount in which to inform when its product about to run out: ");
        msgLst.add("Enter a discount from manufacturer in ratio (if you dont want to add please type '0'): ");
        msgLst.add("Enter a the amount of items you want to order from this product when it ran out: ");
        ArrayList<String> memberLst = new ArrayList<>(10);
        memberLst.add("catName");
        memberLst.add("subCatName");
        memberLst.add("ProdSize");
        memberLst.add("manuFactor");
        memberLst.add("catalogNumProduct");
        memberLst.add("marketPriceConst");
        memberLst.add("manuPriceConst");
        memberLst.add("minimalAmount");
        memberLst.add("discount");
        memberLst.add("orderAmount");
        JsonObject JsonObjProd = CreateJason(10, msgLst, memberLst);
        boolean bool = myFacade.searchProdByCatNumService(JsonObjProd.get("catalogNumProduct").getAsInt());
        if (!bool){
            myFacade.addProductService(JsonObjProd);
            System.out.println("The product added successfully");
            return;
        }
        System.out.println("Added failed - the product already in stock.\n");

    }

    /***
     *Name:AddItem - A Method who adds new item to the inventory by the asked characters.
     * Please notice that the catalog number of the new item fix to the proper product and the proper product already
     * added to the inventory. For example if you want to add new milk with 2 litter with id of 1234 please unsure that
     * the product Dairy Milk 0.5 litter with catalog number of 1515 added to the inventory before and the catalog
     * number of item 1234 you input is: 1515.
     * Args: None
     * Returns: None
     */
    public static void AddItem(){
        ArrayList<String> msgLst = new ArrayList<>(4);
        msgLst.add("Enter item id: ");
        msgLst.add("Enter expiration date YYYY-MM-DD: ");
        msgLst.add("If you want to add this item to the warehouse Enter the aile (a letter from A-Z) and then PRESS coma and enter the shelf number.\n" +
                "else enter item's main category and then PRESS coma then" +
                " enter the shelf number:");
        msgLst.add("Enter catalog number from the Catalog Number Table you received with the program instructions: ");
        ArrayList<String> memberLst = new ArrayList<>(4);
        memberLst.add("id");
        memberLst.add("expirationDate");
        memberLst.add("place");
        memberLst.add("catalogNumItem");
        JsonObject JsonObjItem = CreateJason(4, msgLst, memberLst);
        boolean bool = myFacade.searchItemService(JsonObjItem.get("id").getAsInt());
        if(bool){
            System.out.println("Added field - item already in stock!");
            return;
        }
        boolean prodInStock = myFacade.searchProdByCatNumService(JsonObjItem.get("catalogNumItem").getAsInt());
        if(prodInStock){
            myFacade.addItemService(JsonObjItem);
        System.out.println("Item added successfully\n");
        return;
        }
        System.out.println("There is no such products in the market," +
                " if you want to add a proper product please type 'YES' and then press ENTER. " +
                "Else type 'NO' and then press ENTER." +
                "");
        Scanner scan = new Scanner(System.in);
        if(scan.nextLine().equals("YES"))
            {AddProd();
                myFacade.addItemService(JsonObjItem);
            System.out.println("Thank you, the item and new product added successfully\n");
            return;
            }
        System.out.println("OK, item will not add\n");
    }

    /***
     *Name:RemoveProd - A method which removes a product from the inventory. Please use it when you want to stop sell
     * items of this product in your store.
     * Args: None
     * Returns: None
     */

    public static void RemoveProd(){
        Scanner scan = new Scanner(System.in);
        String idMsg = "Please enter the catalog number of the product you want to cancel from selling";
        int input = readIntFromUsr(idMsg, scan);
        if(!myFacade.searchProdByCatNumService(input)){
            System.out.println("Sorry, you can't remove unexcited product\n");
            return;
        }
        myFacade.removeProductService(input);
        System.out.println("Product removed successfully!\n");
    }

    /***
     *Name:UpdateSales - A method which update a sale on products in the inventory.
     * Args: None
     * Returns: None
     */
    public static void UpdateSales(){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> askCatCorrect = showCatalogChoices(scan);
       if(askCatCorrect == null)
           return;
        String getStartMSG = "Please enter the start date of sale by the format: YYYY-MM-DD";
        String fromSTR = readStrFromUsr(getStartMSG, scan);
        LocalDate fromDate = LocalDate.parse(fromSTR);
        String getToMSG ="Please enter the due date of sale by the format: YYYY-MM-DD";
        String toSTR = readStrFromUsr(getToMSG, scan);
        LocalDate dueDate = LocalDate.parse(toSTR);
        System.out.println("Please enter the sale percentage in integer (10, 20, and so...): ");
        int percentage = scan.nextInt();
        myFacade.updateSaleService(askCatCorrect.get(0), askCatCorrect.get(1), askCatCorrect.get(2), fromDate, dueDate, percentage);
        System.out.println("Sale updated successfully\n");
    }
    /***
     *Name:UpdateDiscount - A method which update a discount ratios on products from their manufacturer who provide them.
     * Args: None
     * Returns: None
     */
    public static void UpdateDiscount(){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> askCatCorrect = showCatalogChoices(scan);
        if(askCatCorrect == null)
            return;
        String manuMSG = "Please enter the name of the manufacturer you want to update its percentage: ";
        String manu = readStrFromUsr(manuMSG, scan);
        System.out.println("Please enter the discount percentage (10, 20, and so...): ");
        int ratio = scan.nextInt();
        if(myFacade.updateDiscountService(askCatCorrect.get(0), askCatCorrect.get(1), askCatCorrect.get(2), ratio, manu)){
            System.out.println("Discount from manufacturer updated successfully\n");
            return;
        }
        System.out.println("There is no such manufacturer " + manu + "!\n");
    }

    /***
     *Name:MoveStoreWare - A method which move an item from store to the warehouse.
     * Args: None
     * Returns: None
     */
    public static void MoveStoreWare(){
        Scanner scan1 = new Scanner(System.in);
        String idMsg = "Please enter the id number of the item you want to move from store to warehouse";
        int id = readIntFromUsr(idMsg, scan1);
        if(myFacade.searchItemService(id)){
            Scanner scan2 = new Scanner(System.in);
            String placeMSG = "Enter the aile (a letter from A-Z) and then PRESS coma and enter the shelf number.";
            String placeInLine = readStrFromUsr(placeMSG, scan2);
            Tuple<String, Integer> place = Tuple.createPlaceItem(placeInLine);
            myFacade.moveToWareService(id,place);
            System.out.println("Item moved to warehouse successfully!\n");
            return;
        }
        System.out.println("This item not in stock!\n");
    }
    /***
     *Name:MoveWareStore - A method which move an item from warehouse to the store.
     * Args: None
     * Returns: None
     */
    public static void MoveWareStore(){
        Scanner scan1 = new Scanner(System.in);
        String idMsg = "Please enter the id number of the item you want to move from store to warehouse";
        int id = readIntFromUsr(idMsg, scan1);
        if(myFacade.searchItemService(id)){
            System.out.println("Please type the pass in the store (its the item main category) where you want to put the item " +
                    "and then PRESS coma and enter the shelf number.");
            Scanner scan2 = new Scanner(System.in);
            String placeInLine = scan2.nextLine();
            Tuple<String, Integer> place = Tuple.createPlaceItem(placeInLine);
            myFacade.moveToStoreService(id,place);
            System.out.println("Item moved to store successfully!\n");
            return;
        }
        System.out.println("This item not in stock!\n");
    }
    public static void ReturnToMainMenu(){
        System.out.println("Returning to main menu...");
    }


    public static String PrintMenu() {
        return """
                What an action on your inventory would you like to do?
                1. Add a product to inventory
                2. Add an item to inventory
                3. Remove a product from inventory
                4. Update sales
                5. Update discount from manufacturers
                6. Move an item from store to warehouse
                7. Move an item from warehouse to store
                8. Return to main menu
                """;
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
        int userInput = scanner.nextInt();
        switch (userInput) {
            case 1 -> AddProd();
            case 2 -> AddItem();
            case 3 -> RemoveProd();
            case 4 -> UpdateSales();
            case 5 -> UpdateDiscount();
            case 6 -> MoveStoreWare();
            case 7 -> MoveWareStore();
            case 8 -> ReturnToMainMenu();
            default -> System.out.println("Please choose valid number between 1-8");

        }
        return userInput;
    }

}