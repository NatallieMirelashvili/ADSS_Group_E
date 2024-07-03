package DomainLayer;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.util.ArrayList;
import static DomainLayer.ItemStatus.*;

public class Facade {

    public ProductController myProductCTR = new ProductController();
    public ItemController myItemCTR = new ItemController();
    public static LocalDate currentDate = LocalDate.now();
    public static LocalDate getCurrentDate(){
        return currentDate;
    }

    /**
     * Name: moveToNextDayService - Use Inventory's method to move to the next day (by date).
     * Args: None
     * Returns: None
     * */
    public static void moveToNextDayService(){
        currentDate = currentDate.plusDays(1);
    }

    /**
     * Name: searchProdByCatNumService - a method which check if the given catalog number is in the inventory
     * Args: int cagNum - The catalog number you want to check.
     * Returns: boolean - Is in the inventory?
     * */
    public boolean searchProdByCatNumService(int cagNum){return myProductCTR.searchProdByCatNumCTR(cagNum);}

    /**
     * Name: searchProdByCategoryService - a method which check if the given categories classified before.
     * Args: String cat - The main category.
     *       String subCat - The sub category.
     *       String size - The category which present the size of the product.
     * Returns: boolean - Is in the inventory?
     * */
    public boolean searchProdByCategoryService(String cat, String subCat, String size){
        return myProductCTR.searchProdByCategoryCTR(cat, subCat, size);
    }

    /**
     * Name: searchItemService - Use Inventory's method to check if item's id is in the inventory.
     * Args: int idToCheck - The id you want to check
     * Returns: boolean - id is in the inventory?
     * */
    public boolean searchItemService(int id){
        return (myItemCTR.searchItemCTR(id)!=null);
    }

    public void addItemService(JsonObject newItem){
        Item itemToReport = myItemCTR.addItemCTR(newItem);
        myProductCTR.reportItemAdd(itemToReport.getCatalogNumItem(),itemToReport.getStoreOrWare());
    }

    public void addProductService(JsonObject newProd){
        myProductCTR.addProdCTR(newProd);}

    /**
     * Name: removeProductService - Use Inventory's method to delete product from inventory.
     * Args: int catalogNum - The catalog number of the product you want to remove.
     * Returns: None
     * */
    public void removeProductService(int catalogNum){
        myProductCTR.removeProdCTR(catalogNum);
    }

    /**
     * Name: removeItemService - Use Inventory's method to sell item in the inventory.
     * Args: int idToRemove - The id number of the item you want to sell.
     * Returns: boolean - item wan in the inventory?
     * */
    public void removeSellItemService(int idToRemove){
        Item itemToReport = myItemCTR.removeItemCTR(idToRemove);
        myProductCTR.reportItemRemove(itemToReport.getCatalogNumItem(),itemToReport.getStoreOrWare());
    }

    /**
     * Name: reportDamageService - Use Inventory's method to report on a damaged item and removes it from inventory.
     * Args: int idToReport - The id number of the item you want to report on.
     * Returns: boolean - item wan in the inventory?
     * */
    public void reportDamageService(int idToReport){
        Item itemToReport = myItemCTR.reportStatusCTR(DAMAGE,idToReport);
        myProductCTR.reportItemRemove(itemToReport.getCatalogNumItem(),itemToReport.getStoreOrWare());
    }

    /**
     * Name: reportExpService - Use Inventory's method to report on an expired item and removes it from inventory.
     * Args: int idToReport - The id number of the item you want to report on.
     * Returns: boolean - item wan in the inventory?
     * */
    public void reportExpService(int idToReport){
        Item itemToReport = myItemCTR.reportStatusCTR(EXPIRED,idToReport);
        myProductCTR.reportItemRemove(itemToReport.getCatalogNumItem(),itemToReport.getStoreOrWare());
    }

    /**
     * Name: reportExpService - Use Inventory's method to report on an expired item and removes it from inventory.
     * Args: int idToReport - The id number of the item you want to report on.
     * Returns: boolean - item wan in the inventory?
     * */
    public void reportForSellService(int idToReport){
        Item itemToReport = myItemCTR.reportStatusCTR(FOR_SALE,idToReport);
        myProductCTR.reportItemAdd(itemToReport.getCatalogNumItem(),itemToReport.getStoreOrWare());
    }

    /**
     * Name: updateSaleService - Use Inventory's method to update sale on product.
     * Args: String cat - The main category.
     *       String subCat - The sub category.
     *       String size - The category which present the size of the product.
     *       LocalDate from - Start the of the sale.
     *       LocalDate to - Due date of the sale.
     *       double ratio - the ratio from the original price of the product.
     * Returns: None
     * */
    public void updateSaleService(String main, String sub, String size, LocalDate from, LocalDate to, int ratio){
        myProductCTR.updateSaleCTR(main, sub, size, from, to, ratio);
    }

    /**
     * Name: updateDiscountService - Use Inventory's method to update manufacturer of product's discount.
     * Args: String cat - The main category.
     *       String subCat - The sub category.
     *       String size - The category which present the size of the product.
     *       LocalDate from - Start the of the sale.
     *       LocalDate to - Due date of the sale.
     *       double ratio - the ratio from the original price of the product.
     *       String manu - The manufacturer you want to update its discount.
     * Returns: boolean - Manufacturer is defined in the system?
     * */
    public boolean updateDiscountService(String main, String sub, String size, int ratio, String manu){
        return myProductCTR.updateDisCTR(main, sub, size, ratio, manu);
    }

    /**
     * Name: moveToWareService - Use Inventory's method to move an item from store to warehouse.
     * Args: int id - id's item to move
     *       Tuple<String, Integer> newPlace - new place of item in the warehouse.
     * Returns: None
     * */
    public void moveToWareService(int id, Tuple<String, Integer> newPlace){
        Item itemToReport = myItemCTR.moveToWhere(id, newPlace,"warehouse");
        myProductCTR.reportItemAdd(itemToReport.getCatalogNumItem(),"warehouse");
        myProductCTR.reportItemRemove(itemToReport.getCatalogNumItem(),"store");
    }

    /**
     * Name: moveToStoreService - Use Inventory's method to move an item from warehouse to store.
     * Args: int id - id's item to move
     *       Tuple<String, Integer> newPlace - new place of item in the store.
     * Returns: None
     * */
    public void moveToStoreService(int id, Tuple<String, Integer> newPlace){
        Item itemToReport = myItemCTR.moveToWhere(id, newPlace,"store");
        myProductCTR.reportItemAdd(itemToReport.getCatalogNumItem(),"store");
        myProductCTR.reportItemRemove(itemToReport.getCatalogNumItem(),"warehouse");
    }

    /**
     * Name: checkMinimalAmountService - Use Inventory's method to check if product got to its minimal amount to alert on.
     * Args: int id - The id number of the item you want to check its product amount.
     * Returns: boolean - item wan in the inventory?
     * */
    public boolean checkMinimalAmountService(int id){
        Item itemToReport = myItemCTR.searchItemCTR(id);
        return myProductCTR.checkMinimalAmountCTR(itemToReport.getCatalogNumItem());
    }

    /**
     * Name: checkAllItemsExpService - Use Inventory's method to check the expiration date of all the inventory's items
     * and removes them if founded.
     * Args: None
     * Returns: None
     * */
    public void checkAllItemsExpService(){
        ArrayList<Item> itemToReport = myItemCTR.checkAllItemsExpCTR();
        for (Item item:itemToReport){
            reportExpService(item.getId());
        }
    }

    /**
     * Name: checkAllProductSaleService - Use Inventory's method to check the expiration date of all the inventory's product's sales
     * if necessary.
     * Args: None
     * Returns: None
     * */
    public void checkAllProductSaleService(){
        myProductCTR.checkAllProductSaleCTR();
    }




    /**
     * Name: inventReportByCatService - Use Inventory's method to return the inventory's report by specific categories.
     * Args: None
     * Returns: StringBuilder - The report (Presentation prints).
     * */
    public StringBuilder inventReportByCatService(String main, String sub, String size) {
        ArrayList<Product> productsToPrint = myProductCTR.inventReportByCatCTR(main, sub, size);
        return myProductCTR.HelperGenerateReportsProduct(productsToPrint);
    }

    /**
     * Name: inventReportAllService - Use Inventory's method to return the inventory's report not by specific categories
     * (All items).
     * Args: None
     * Returns: StringBuilder - The report (Presentation prints).
     * */
    public StringBuilder inventReportAllService(){
        ArrayList<Product> productsToPrint = myProductCTR.inventReportAllService();
        return myProductCTR.HelperGenerateReportsProduct(productsToPrint);
    }

    /**
     * Name: shortageReportService - Use Inventory's method to return the shortage products report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public StringBuilder shortageReportService(){
        ArrayList<Product> productsToPrint = myProductCTR.shortageReportCTR();
        return myProductCTR.HelperGenerateReportsProduct(productsToPrint);
    }

    /**
     * Name: expReportService - Use Inventory's method to return the expired items report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public String expReportService(){
        ArrayList<Item> itemsToPrint = myItemCTR.expReportCTR();
        return myItemCTR.HelperGenerateReportsItem("expired",itemsToPrint);
    }

    /**
     * Name: defectiveReportService - Use Inventory's method to return the damaged items report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public String defectiveReportService(){
        ArrayList<Item> itemsToPrint = myItemCTR.defectiveReportCTR();
        return myItemCTR.HelperGenerateReportsItem("defective",itemsToPrint);
    }












}
