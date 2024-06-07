package ServiceLayer;
import DomainLayer.Inventory;
import DomainLayer.Tuple;

import java.time.LocalDate;

public class StockController {

    /**
     * Name: ProdInStockControl - a method which check if the given catalog number is in the inventory
     * Args: int cagNum - The catalog number you want to check.
     * Returns: boolean - Is in the inventory?
     * */
    public static boolean ProdInStockControl(int cagNum){return Inventory.ProductExist(cagNum);}

    /**
     * Name: ProdInStockByCatCTR - a method which check if the given categories classified before.
     * Args: String cat - The main category.
     *       String subCat - The sub category.
     *       String size - The category which present the size of the product.
     * Returns: boolean - Is defined?
     * */
    public static boolean ProdInStockByCatCTR(String cat, String subCat, String size){
        return Inventory.ProductExistByCat(cat, subCat, size);

    }

    /**
     * Name: removeProdControl - Use Inventory's method to delete product from inventory.
     * Args: int CatNumToRemove - The catalog number of the product you want to remove.
     * Returns: None
     * */
    public static void removeProdControl(int CatNumToRemove){
        Inventory.removeProd(CatNumToRemove);
    }

    /**
     * Name: sellItemControl - Use Inventory's method to sell item in the inventory.
     * Args: int idSell - The id number of the item you want to sell.
     * Returns: boolean - item wan in the inventory?
     * */
    public static boolean sellItemControl(int idSell){
        return Inventory.ItemSells(idSell);
    }

    /**
     * Name: reportDamageControl - Use Inventory's method to report on a damaged item and removes it from inventory.
     * Args: int idDamage - The id number of the item you want to report on.
     * Returns: boolean - item wan in the inventory?
     * */
    public static boolean reportDamageControl(int idDamage){
        return Inventory.ItemDefective(idDamage);
    }

    /**
     * Name: reportExpiredControl - Use Inventory's method to report on an expired item and removes it from inventory.
     * Args: int idExp - The id number of the item you want to report on.
     * Returns: boolean - item wan in the inventory?
     * */
    public static boolean reportExpiredControl(int idExp){
        return Inventory.ItemExpired(idExp);
    }

    /**
     * Name: checkMinimalControl - Use Inventory's method to check if product got to its minimal amount to alert on.
     * Args: int idOfItem - The id number of the item you want to check its product amount.
     * Returns: boolean - item wan in the inventory?
     * */
    public static boolean checkMinimalControl(int idOfItem){return Inventory.checkMinimal(idOfItem);}


    /**
     * Name: updateSaleControl - Use Inventory's method to update sale on product.
     * Args: String cat - The main category.
     *       String subCat - The sub category.
     *       String size - The category which present the size of the product.
     *       LocalDate from - Start the of the sale.
     *       LocalDate to - Due date of the sale.
     *       double ratio - the ratio from the original price of the product.
     * Returns: None
     * */
    public static void updateSaleControl(String main, String sub, String size, LocalDate from, LocalDate to, double ratio){
        Inventory.setSalePrice(main, sub, size, from, to, ratio);
    }

    /**
     * Name: updateSaleControl - Use Inventory's method to update manufacturer of product's discount.
     * Args: String cat - The main category.
     *       String subCat - The sub category.
     *       String size - The category which present the size of the product.
     *       LocalDate from - Start the of the sale.
     *       LocalDate to - Due date of the sale.
     *       double ratio - the ratio from the original price of the product.
     *       String manu - The manufacturer you want to update its discount.
     * Returns: boolean - Manufacturer is defined in the system?
     * */
    public static boolean updateDisControl(String main, String sub, String size,double ratio, String manu){
        return Inventory.setDiscount(main, sub, size, ratio, manu);
    }

    /**
     * Name: moveItemFromSControl - Use Inventory's method to move an item from store to warehouse.
     * Args: int idToMove - id's item to move
     *       Tuple<String, Integer> newPlace - new place of item in the warehouse.
     * Returns: None
     * */
    public static void moveItemFromSControl(int idToMove,Tuple<String, Integer> newPlace){
        Inventory.FromStoreToWare(idToMove, newPlace);
    }

    /**
     * Name: moveItemFromWControl - Use Inventory's method to move an item from warehouse to store.
     * Args: int idToMove - id's item to move
     *       Tuple<String, Integer> newPlace - new place of item in the store.
     * Returns: None
     * */
    public static void moveItemFromWControl(int idToMove, Tuple<String, Integer> newPlace){
        Inventory.FromWareToStore(idToMove, newPlace);
    }

    /**
     * Name: checkAllItemsExpCtr - Use Inventory's method to check the expiration date of all the inventory's items
     * and removes them if founded.
     * Args: None
     * Returns: None
     * */
    public static void checkAllItemsExpCtr(){
        Inventory.checkAllItemsExp();
    }

    /**
     * Name: checkAllItemsSaleCtr - Use Inventory's method to check the expiration date of all the inventory's product's sales
     * if necessary.
     * Args: None
     * Returns: None
     * */
    public static void checkAllItemsSaleCtr(){
        Inventory.checkAllProdSale();
    }

    /**
     * Name: checkAllItemsSaleCtr - Use Inventory's method to move to the next day (by date).
     * Args: None
     * Returns: None
     * */
    public static void updateDateToNextCtr(){
        Inventory.moveToNextDay();
    }

    /**
     * Name: showAllItemsCtr - Use Inventory's method to return the inventory's report not by specific categories
     * (All items).
     * Args: None
     * Returns: StringBuilder - The report (Presentation prints).
     * */
    public static StringBuilder showAllItemsCtr(){
        return Inventory.GenerateReportsStock();
    }

    /**
     * Name: showByCatCtr - Use Inventory's method to return the inventory's report by specific categories.
     * Args: None
     * Returns: StringBuilder - The report (Presentation prints).
     * */
    public static StringBuilder showByCatCtr(String main, String sub, String size){
        return Inventory.GenerateReportsStockByCat(main, sub, size);
    }

    /**
     * Name: showExpReportsCtr - Use Inventory's method to return the expired items report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public static String showExpReportsCtr(){
        return Inventory.generateReportExpired();
    }

    /**
     * Name: showDamageReportsCtr - Use Inventory's method to return the damaged items report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public static String showDamageReportsCtr(){
        return Inventory.generateReportDamage();
    }


    /**
     * Name: idInStockCTR - Use Inventory's method to check if item's id is in the inventory.
     * Args: int idToCheck - The id you want to check
     * Returns: boolean - id is in the inventory?
     * */
    public static boolean idInStockCTR(int idToCheck){
        return Inventory.ItemExist(idToCheck);
    }


}

