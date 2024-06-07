package DomainLayer;

import java.util.ArrayList;

import static DomainLayer.DataObject.*;

public class ReportGeneration {
    //Generate a reports of expired and defective
    static public String generateReportExpired(){ return getExpObj().GenerateReports(); }
    static public String generateReportDamage(){
        return getDefObj().GenerateReports();
    }
    static public int getAmountExp(){
        return getExpObj().getAmount();
    }
    static public int getAmountDef(){
        return getDefObj().getAmount();
    }

    //Generate a report of inventory:
    //helper function
    static public StringBuilder HelperGenerateReportsStock(ArrayList<Product> toPrint) {
        StringBuilder output = new StringBuilder();
        if (toPrint.isEmpty()){
            output.append("There is no existing items");
            return output;
        }
        int i = 1;
        String title = "Inventory report:\n\n";
        output.append(title);
        for (Product product : toPrint) {
            output.append(i).append(".\n");
            output.append(product.printProduct());
            String catalogNum = "Catalog Number:" + product.getCatalogNumProduct() + "\n";
            String marketPrice = "Marketing price:" + product.getMarketPriceCurr() + "\n";
            String manuPrice = "Manufacturer price:" + product.getManuPriceCurr() + "\n";
            String totalStore = "Total amount of items in store:" + product.getStoreAmount() + "\n";
            String totalWare = "Total amount of items in warehouse:" + product.getWarehouseAmount() + "\n\n";
            output.append(catalogNum).append(marketPrice).append(manuPrice).append(totalStore).append(totalWare);
            i++;
        }
        return output;
    }
    //Generate a report of all inventory
    static public StringBuilder GenerateReportsStock() {
        StringBuilder outputForController = new StringBuilder();
        outputForController.append(HelperGenerateReportsStock(getInventObj().runAllProducts()));
        return outputForController;
    }
    //Generate a report of inventory according to category
    static public StringBuilder GenerateReportsStockByCat(String cat, String subCat, String size) {
        StringBuilder outputForController = new StringBuilder();
        if(subCat.equals("0")){
            outputForController.append(HelperGenerateReportsStock(getInventObj().runProductByCat(cat)));
            return outputForController;
        }
        if(size.equals("0")){
            outputForController.append(HelperGenerateReportsStock(getInventObj().runProductBySubCat(cat, subCat)));
            return outputForController;
        }
        outputForController.append(HelperGenerateReportsStock(getInventObj().runProductBySize(cat, subCat, size)));
        return outputForController;

    }

}
