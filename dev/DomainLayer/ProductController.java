package DomainLayer;

import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductController {

    public static ProductRepo myProductRepo = new ProductRepo();
    public static productToDeliveryRepo myProductToDeliveryRepo = new productToDeliveryRepo();
    private int amountProducts = 0;

    public int getAmountProducts() {
        return amountProducts;
    }

    public static Product searchProdByCatNumCTR(int catNumber) {
        return myProductRepo.find(catNumber);
    }

    //find if product exist in inventory when given category
    public boolean searchProdByCategoryCTR(String cat, String subCat, String size) {
        return myProductRepo.findIfExistWithData(cat, subCat, size);
    }

    public void addProdCTR(JsonObject newProdRec) {
        myProductRepo.add(newProdRec);
        amountProducts++;
    }

    //remove product from inventory and all his items
    public void removeProdCTR(int catalogNum) {
        myProductRepo.remove(catalogNum);
        amountProducts--;
    }

    public void reportItemRemove(int catNum, String place) {
        myProductRepo.updateAmountItem("REMOVE", catNum, place);
    }

    public void reportItemAdd(int catNum, String place) {
        myProductRepo.updateAmountItem("ADD", catNum, place);
    }

    //update sale price about products by category, sub category and size that given (all or some)
    public void updateSaleCTR(String cat, String subCat, String size, LocalDate from, LocalDate to, int ratioSale) {
        JsonObject js = new JsonObject();
        js.addProperty("startSale", from.toString());
        js.addProperty("endSale",to.toString());
        js.addProperty("discountRatio",ratioSale);
        if (subCat.equals("0")) {
            myProductRepo.updateSalePriceRepo(myProductRepo.findAllProductsByMainCat(cat),js);
            return;
        }
        if (size.equals("0")) {
            myProductRepo.updateSalePriceRepo(myProductRepo.findAllProductsBySubCat(cat, subCat), js);
            return;
        }
        myProductRepo.updateSalePriceRepo(myProductRepo.findAllProductsBySize(cat, subCat, size), js);
    }

    //update discount about products by category, sub category and size that given (all or some)
    public boolean updateDisCTR(String cat, String subCat, String size, int myDiscount, String myManufacturer) {
        if (subCat.equals("0")) {
            return myProductRepo.updateDisRepo(myProductRepo.findAllProductsByMainCat(cat), myDiscount, myManufacturer);
        }
        if (size.equals("0")) {
            return myProductRepo.updateDisRepo(myProductRepo.findAllProductsBySubCat(cat, subCat), myDiscount, myManufacturer);
        }
        return myProductRepo.updateDisRepo(myProductRepo.findAllProductsBySize(cat, subCat, size), myDiscount, myManufacturer);
    }

    public void RequestDeliveryCTR(String cat, String subCat, String size, String myManufacturer) {
        ArrayList<Product> products = myProductRepo.findAllProductsBySize(cat, subCat, size);
        JsonObject productManu = new JsonObject();
        for (Product product:products){
            if (product.getManufacture().equals(myManufacturer)) {
                productManu.addProperty("idStore", 4);
                productManu.addProperty("manufacturer", product.getManufacture());
                productManu.addProperty("catalogNumber", product.getCatalogNumProduct());
                productManu.addProperty("orderAmount", product.getOrderAmount());
                myProductToDeliveryRepo.add(productManu);
            }
        }
    }

    //return true if the product of item is minimal amount
    public boolean checkMinimalAmountCTR(int catNum) {
        Product productToUpdate = myProductRepo.find(catNum);
        return productToUpdate.checkMinimalProd();
    }

    public void checkAllProductSaleCTR() {
        ArrayList<Product> allProduct = myProductRepo.findAll();
        for (Product product : allProduct) {
            if (product.getMySalePrice() != null) {
                if (product.getMySalePrice().getEndSale().isBefore(Facade.getCurrentDate())) {
                    myProductRepo.updatePrice(product, product.getMarketPriceConst());
                }
                if (product.getMySalePrice().getStartSale().isEqual(Facade.getCurrentDate())) {
                    double price = product.getMarketPriceConst() - (product.getMarketPriceConst() * product.getMySalePrice().getDiscountRatio() / 100);
                    myProductRepo.updatePrice(product, price);
                }
            }
        }
    }

    public ArrayList<Product> inventReportByCatCTR(String mainCat, String subCat, String size) {
        ArrayList<Product> productsToPrint = new ArrayList<>();
        if (subCat.equals("0")) {
            productsToPrint = myProductRepo.findAllProductsByMainCat(mainCat);
        }
        else if (size.equals("0")) {
            productsToPrint = myProductRepo.findAllProductsBySubCat(mainCat, subCat);
        }
        else {
            productsToPrint = myProductRepo.findAllProductsBySize(mainCat, subCat, size);
        }
        return productsToPrint;
    }

    public ArrayList<Product> inventReportAllService(){
        return myProductRepo.findAll();
    }

    public ArrayList<Product> shortageReportCTR() {
        ArrayList<Product> productsToCheck = myProductRepo.findAll();
        ArrayList<Product> productsToPrint = new ArrayList<>();
        for (Product product : productsToCheck) {
            if (product.checkMinimalProd()){
                productsToPrint.add(product);
                myProductRepo.setIsMinimal(product, true);
            }
        }
        return productsToPrint;
    }

    public StringBuilder HelperGenerateReportsProduct(ArrayList<Product> toPrint) {
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

}
