package DomainLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


import static DomainLayer.DataObject.getDefObj;
import static DomainLayer.DataObject.getExpObj;

public class Inventory{

    //    ***Fields***
    static private HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock=new HashMap<>();
    static private int amountProducts=0;
    static private LocalDate currentDate = LocalDate.now();

    //    ***Getters***
    static public int getAmountItems() {
        int allItems=0;
        for (int i=0; i<amountProducts; i++) {
            allItems += runAllProducts().get(i).getTotalAmount();
        }
        return allItems;
    }
    static public int getAmountProducts() {
        return amountProducts;
    }
    static public HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> getMyStock() {
        return myStock;
    }

    //     ***Setters***

    static public void setAmountProducts(int amountProducts) {
        Inventory.amountProducts = amountProducts;
    }

    //    ***Methood***

    //update sale price about products by category, sub category and size that given (all or some)
    static public void setSalePrice(String cat, String subCat , String size, LocalDate from, LocalDate to, double ratioSale) {
        salePrice newSale = new salePrice(from, to, ratioSale);
        if (!subCat.equals("0") && !(size.equals("0"))) { //have cat,subCat,size
            setSaleOnProducts(runProductBySize(cat, subCat, size), newSale);
        } else if (size.equals("0")) { //have cat, subCat
            setSaleOnProducts(runProductBySubCat(cat, subCat), newSale);
        } else { //have cat
            setSaleOnProducts(runProductByCat(cat), newSale);
        }
    }

    //help function-update sale price about list of products
   static public void setSaleOnProducts (ArrayList<Product> products, salePrice newSale){
        for (Product product : products)
            product.setMySalePrice(newSale);
    }

    //update discount about products by category, sub category and size that given (all or some)
    static public void setDiscount(String cat, String subCat , String size, double myDiscount, String myManufacturer) {
        if (!subCat.equals("0") && !(size.equals("0"))) { //have cat,subCat,size
            discountOnProducts(runProductBySize(cat, subCat, size), myDiscount,myManufacturer);
        } else if (size.equals("0")) { //have cat, subCat
            discountOnProducts(runProductBySubCat(cat, subCat), myDiscount,myManufacturer);
        } else { //have cat
            discountOnProducts(runProductByCat(cat), myDiscount,myManufacturer);
        }
    }

    //return an array list of all products by category, sub category and size
    static public ArrayList<Product> runProductBySize (String cat, String subCat, String size) {
        return myStock.get(cat).get(subCat).get(size);
    }

    //return an array list of all products by category and sub category
    static public ArrayList<Product> runProductBySubCat (String cat, String subCat) {
        ArrayList<Product> productsRes=new ArrayList<>();
        HashMap<String, ArrayList<Product>> products1 = myStock.get(cat).get(subCat);
        for (String mySize : products1.keySet()) {
            productsRes.addAll(runProductBySize (cat,subCat,mySize));
        }
        return productsRes;
    }

    //return an array list of all products by category
    static public ArrayList<Product> runProductByCat (String cat) {
        ArrayList<Product> productsRes=new ArrayList<>();
        HashMap<String, HashMap<String, ArrayList<Product>>> subCatToChange = myStock.get(cat);
            for (String mySubCat : subCatToChange.keySet()) {
                productsRes.addAll(runProductBySubCat (cat, mySubCat));
            }
            return productsRes;
    }

    //return an array list of all products by category
    static public ArrayList<Product> runAllProducts() {
        ArrayList<Product> productsRes=new ArrayList<>();
        for (String Category : myStock.keySet()) {
            productsRes.addAll(runProductByCat (Category));
        }
        return productsRes;
    }

    //help function-update discount about list of products
    static public void discountOnProducts (ArrayList<Product> products, double discount, String manufacturer) {
        for (Product product : products) {
            if (product.getManuFactor().equals(manufacturer))
                product.setDiscount(discount);
        }
    }

    //find if product exist in inventory
    static public boolean ProductExist(int catNumber) {
        if (FindProduct(catNumber)!=null)
            return true;
        return false;
    }

    //return product in inventory if he exists; otherwise return null.
    static public Product FindProduct(int catNumber) {
        Product productRes = null;
        ArrayList<Product> AllProducts = runAllProducts();
        for (Product product : AllProducts){
            if (product.getCatalogNumProduct()==catNumber){
                productRes=product;
                break;
            }
        }
        return productRes;
    }

    //add a new product to inventory that didn't exist
    static public void addProductToInventory(Product newP) {
        ArrayList<Product> proToAdd = new ArrayList<>();
        proToAdd.add(newP);
        if (!myStock.containsKey(newP.getCatName()))  {
            myStock.putIfAbsent(newP.getCatName(), new HashMap<>());
            myStock.get(newP.getCatName()).putIfAbsent(newP.getSubCatName(), new HashMap<>());
            myStock.get(newP.getCatName()).get(newP.getSubCatName()).putIfAbsent(newP.getSize(), new ArrayList<>());
            myStock.get(newP.getCatName()).get(newP.getSubCatName()).get(newP.getSize()).addAll(proToAdd);
        } else if (!myStock.get(newP.getCatName()).containsKey(newP.getSubCatName())) {
            myStock.get(newP.getCatName()).putIfAbsent(newP.getSubCatName(), new HashMap<>());
            myStock.get(newP.getCatName()).get(newP.getSubCatName()).putIfAbsent(newP.getSize(), new ArrayList<>());
            myStock.get(newP.getCatName()).get(newP.getSubCatName()).get(newP.getSize()).addAll(proToAdd);
        } else if (!myStock.get(newP.getCatName()).get(newP.getSubCatName()).containsKey(newP.getSize())) {
            myStock.get(newP.getCatName()).get(newP.getSubCatName()).putIfAbsent(newP.getSize(), new ArrayList<>());
            myStock.get(newP.getCatName()).get(newP.getSubCatName()).get(newP.getSize()).addAll(proToAdd);
        } else {
            runProductBySize(newP.getCatName(), newP.getSubCatName(), newP.getSize()).add(newP);
        }
        amountProducts++;
        }

    //find if item exist in inventory
    static public boolean ItemExist(int IDItem) {
        if (FindItemInInvent(IDItem)!=null)
            return true;
        return false;
    }

    //return item in inventory if he exists; otherwise return null.
    static public Item FindItemInInvent(int IDItem) {
        for (int i = 0; i < amountProducts; i++) {
            Item item = runAllProducts().get(i).FindItemInPro(IDItem);
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    //if item exists return Tuple<String,Item> such the string represent category, sub category and size
    //of his product; otherwise return null.
    static public Tuple<String,Item> removeItem(int IDItem) {
        Tuple<String,Item> itemToRemove= new Tuple<>();
        Item item = FindItemInInvent(IDItem);
            if(item!=null){
                Product product = item.getMyProduct();
                String detailsItem = product.printProduct();
                itemToRemove.setVal1(detailsItem);
                itemToRemove.setVal2(item);
                product.removeItemToLst(item);
            }
        return itemToRemove;
    }

    //remove product from inventory and all his items
    static public void removeProd(int catalogNum) {
        Product myProd = FindProduct(catalogNum);
        if (myProd!=null){
            runProductBySize(myProd.getCatName(),myProd.getSubCatName(),myProd.getSize()).remove(myProd);
            amountProducts--;
    }
}
    //remove item from inventory when he is sale
    static public void ItemSells(int IDItem){
        Item item = FindItemInInvent(IDItem);
        if (item != null) {
            Product product = item.getMyProduct();
            product.removeItemToLst(item);
        }
    }

    //remove item from inventory when he is defective and move to list of defectives with details important
    static public void ItemDefective(int IDItem){
        Tuple<String,Item> itemDefective = removeItem(IDItem);
        getDefObj().getItems().add(itemDefective);
    }

    //remove item from inventory when he is expired and move to list of expired with details important
    static public void ItemExpired(int IDItem){
        Tuple<String,Item> itemExpired = removeItem(IDItem);
        getExpObj().getItems().add(itemExpired);
    }

    //Generate a reports
    static public String generateReportExpired(){
        return getExpObj().GenerateReports();
    }
    static public String generateReportDamage(){
        return getDefObj().GenerateReports();
    }
    static public int getAmountExp(){
        return getExpObj().getAmount();
    }
    static public int getAmountDef(){
        return getDefObj().getAmount();
    }

    //return true if the product of item is minimal amount
    static public boolean checkMinimal(int itemID) {
        Item item = FindItemInInvent(itemID);
        if (item != null) {
            return item.getMyProduct().isMinimal();
        }
        return false;
    }

    //move item from store to warehouse
    static public void FromStoreToWare(int idToMove, Tuple<String, Integer> placeNew){
        Item itemMove = FindItemInInvent(idToMove);
        if (itemMove != null) {
            itemMove.getMyProduct().ProFromStoreToWare(idToMove, placeNew);
        }
    }

    //move item from warehouse to store
    static public void FromWareToStore(int idToMove, Tuple<String, Integer> placeNew) {
        Item itemMove = FindItemInInvent(idToMove);
        if (itemMove != null) {
            itemMove.getMyProduct().ProFromWareToStore(idToMove, placeNew);
        }
    }

    //remove all items that expired to list of items that expired according to date
    static public void checkAllItemsExp() {
        ArrayList<Product> products = runAllProducts();
        for (Product product : products) {
            for (Item item : product.getItems()) {
                if (item.getExpirationDate().isBefore(currentDate)) {
                    ItemExpired(item.getId());
                }
            }
        }
    }

    //update sale price according to date
    static public void checkAllProdSale() {
        ArrayList<Product> products = runAllProducts();
        for (Product product : products) {
            if (product.getMySalePrice().getEndSale().isBefore(currentDate)) {
                product.setMarketPriceCurr(product.getMarketPriceConst());
            }
        }
    }

    //Generate a report of inventory:
    //helper function
    static public StringBuilder HelperGenerateReportsStock(ArrayList<Product> toPrint) {
        int i = 1;
        StringBuilder output = new StringBuilder();
        String title = "Inventory report:\n\n";
        for (Product product : toPrint) {
            output.append(i).append(".");
            output.append(product.printProduct());
            String catalogNum = "Catalog Number:" + product.getCatalogNumProduct() + "\n";
            String total = "Total amount:" + product.getTotalAmount() + "\n";
            output.append(catalogNum).append(total).append("\n\n");
            i++;
        }
        return output;
    }
    //Generate a report of all inventory
    static public StringBuilder GenerateReportsStock() {
        StringBuilder outputForController = new StringBuilder();
        outputForController.append(HelperGenerateReportsStock(runAllProducts());
        return outputForController;
    }
    //Generate a report of inventory according to category
    static public StringBuilder GenerateReportsStockByCat(String cat, String subCat, String size) {
        StringBuilder outputForController = new StringBuilder();
        if (!subCat.equals("0") && !(size.equals("0"))) { //have cat,subCat,size
            outputForController.append(HelperGenerateReportsStock(runProductBySize(cat, subCat, size)));
        } else if (size.equals("0")) { //have cat, subCat
            outputForController.append(HelperGenerateReportsStock(runProductBySubCat(cat, subCat)));
        } else { //have cat
            outputForController.append(HelperGenerateReportsStock(runProductByCat(cat)));
        }
        return outputForController;
    }
}

