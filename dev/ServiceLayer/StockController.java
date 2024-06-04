package ServiceLayer;
import DomainLayer.Defective;
import DomainLayer.Expired;
import DomainLayer.Inventory;

public class StockController {
    public static boolean ProdInStockControl(int cagNum){return Inventory.ProductExist(cagNum);}



    //    delete from inventory:
    public static void removeProdControl(int idToRemove){
        if(!ProdInStockControl(idToRemove)){
            System.out.println("Sorry, you can't remove unexcited product\n");
            return;
        }
        Inventory.removeProd(idToRemove);
    }

//    you can only sell or report item if its exist

    public static void sellItemControl(int idSell){
        Inventory.ItemSells(idSell);
    }
    public static void reportDamageControl(int idDamage){
        Inventory.ItemDefective(idDamage);
    }
    public static void reportExpiredControl(int idExp){
        Inventory.ItemExpired(idExp);
    }

    public static boolean checkMinimalControl(int idOfItem){return Inventory.checkMinimal(idOfItem);}

    //need also filed (LocalDate from, LocalDate to, double ratioSale)
    public static void updateSaleControl(String main, String sub, String size){
        Inventory.setSalePrice(main, sub, size);
    }

    //need also filed (double myDiscount, String myManufacturer)
    public static void updateDisControl(String main, String sub, String size){
        Inventory.setDiscount(main, sub, size);
    }

    //need also filed (Tuple<String, Integer> placeNew)
    public static void moveItemFromSControl(int idToMove){
        Inventory.FromStoreToWare(idToMove);
    }

    //need also filed (Tuple<String, Integer> placeNew)

    public static void moveItemFromWControl(int idToMove){
        Inventory.FromWareToStore(idToMove);
    }

    public static void checkAllItemsExpCtr(){
        Inventory.checkAllItemsExp();
    }

    public static void checkAllItemsSaleCtr(){
        Inventory.checkAllItemsSale();
    }
    public static String showAllItemsCtr(){
        return Inventory.showAllItems();
    }
    public static String showByCatCtr(String main, String sub, String size){
        return Inventory.showItemsByCat(main, sub, size);
    }
    public static String showExpReportsCtr(){
        return Inventory.generateReportExpired();
    }

    public static String showDamageReportsCtr(){
        return Inventory.generateReportDamage();
    }

//    if number of expired items is not 0 - there are some expired items, so we returns true
    public static boolean isThereExpCtr(){
            return Inventory.getAmountExp() != 0;
    }





}
//LocalDate instead of Date

