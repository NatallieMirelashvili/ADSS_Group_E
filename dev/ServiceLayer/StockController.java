package ServiceLayer;
import DomainLayer.Inventory;
import DomainLayer.Tuple;

import java.time.LocalDate;

public class StockController {
    public static boolean ProdInStockControl(int cagNum){return Inventory.ProductExist(cagNum);}
    public static boolean ProdInStockByCatCTR(String cat, String subCat, String size){
        return Inventory.ProductExistByCat(cat, subCat, size);

    }

    //    delete from inventory:
    public static void removeProdControl(int CatNumToRemove){
        Inventory.removeProd(CatNumToRemove);
    }

//    you can only sell or report item if its exist

    public static boolean sellItemControl(int idSell){
        return Inventory.ItemSells(idSell);
    }
    public static boolean reportDamageControl(int idDamage){
        return Inventory.ItemDefective(idDamage);
    }
    public static boolean reportExpiredControl(int idExp){
        return Inventory.ItemExpired(idExp);
    }

    public static boolean checkMinimalControl(int idOfItem){return Inventory.checkMinimal(idOfItem);}

    public static void updateSaleControl(String main, String sub, String size, LocalDate from, LocalDate to, double ratio){
        Inventory.setSalePrice(main, sub, size, from, to, ratio);
    }

    public static boolean updateDisControl(String main, String sub, String size,double ratio, String manu){
        return Inventory.setDiscount(main, sub, size, ratio, manu);
    }

    public static void moveItemFromSControl(int idToMove,Tuple<String, Integer> newPlace){
        Inventory.FromStoreToWare(idToMove, newPlace);
    }

    public static void moveItemFromWControl(int idToMove, Tuple<String, Integer> newPlace){
        Inventory.FromWareToStore(idToMove, newPlace);
    }

    public static void checkAllItemsExpCtr(){
        Inventory.checkAllItemsExp();
    }

    public static void checkAllItemsSaleCtr(){
        Inventory.checkAllProdSale();
    }
    public static void updateDateToNextCtr(){
        Inventory.moveToNextDay();
    }
    public static StringBuilder showAllItemsCtr(){
        return Inventory.GenerateReportsStock();
    }
    public static StringBuilder showByCatCtr(String main, String sub, String size){
        return Inventory.GenerateReportsStockByCat(main, sub, size);
    }
    public static String showExpReportsCtr(){
        return Inventory.generateReportExpired();
    }

    public static String showDamageReportsCtr(){
        return Inventory.generateReportDamage();
    }



    public static boolean idInStockCTR(int isToCheck){
        return Inventory.ItemExist(isToCheck);
    }


}

