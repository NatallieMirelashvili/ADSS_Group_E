package DomainLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;


import static DomainLayer.DataObject.getDefObj;
import static DomainLayer.DataObject.getExpObj;

public class Inventory{

    //    ***Fields***
    static private HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock=new HashMap<>();
    static private int amountItems=0;
    static private int amountProducts=0;
    private static LocalDate currentDate = LocalDate.now();



    //    ***Getters***
    public int getAmountItems() {
        return amountItems;
    }

    public int getAmountProducts() {
        return amountProducts;
    }
    public HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> getMyStock() {
        return myStock;
    }


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


//    ***Running by category***


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

//    ***Help Functions***

    //help function-update discount about list of products
    static public void discountOnProducts (ArrayList<Product> products, double discount, String manufacturer) {
        for (Product product : products) {
            if (product.getManuFactor().equals(manufacturer))
                product.setDiscount(discount);
        }
    }

    //find if product exist in inventory
    static public boolean ProductExist(int catNumber) {
        ArrayList<Product> AllProducts = runAllProducts();
        for (Product product : AllProducts){
            if (product.getCatalogNumProduct()==catNumber){
                return true;
            }
        }
        return false;
    }

    //return product in inventory only if he exists
    static public Product FindProduct(int catNumber) {
        Product productRes = null;
        ArrayList<Product> AllProducts = runAllProducts();
        for (Product product : AllProducts){
            if (product.getCatalogNumProduct()==catNumber){
                productRes=product;
            }
        }
        return productRes;
    }

        //add product if he didn't exist
    public void addProductToInventory(Product newP) {
        ArrayList<Product> proToAdd = new ArrayList<>();
        proToAdd.add(newP);
        if (!myStock.containsKey(newP.getCatName())) {
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
        Tuple<String,Item> itemToRemove = null;
        Item item = FindItemInInvent(IDItem);
            if(item!=null){
                Product product = FindProduct(item.getCatalogNumItem());
                String category = "Category:" + product.getCatName()+"\n";
                String subCategory = "Sub Category:" + product.getSubCatName()+"\n";
                String size = "Size:" + product.getSize()+"\n";
                itemToRemove.setVal1(category + subCategory + size);
                itemToRemove.setVal2(item);
                amountItems--;
            }
        return itemToRemove;
    }

    static public void removeProd(int catalogNum) {
        if (ProductExist(catalogNum)) {
            Product myProd = FindProduct(catalogNum);
            myStock.get(myProd.getCatName()).get(myProd.getSubCatName()).get(myProd.getSize()).remove(myProd);
            amountProducts--;
        }
    }

    static public void ItemSells(int IDItem){
        removeItem(IDItem);
    }
    static public void ItemDefective(int IDItem){
        Tuple<String,Item> itemDefective = removeItem(IDItem);
        getDefObj().getItems().add(itemDefective);
    }

    static public void ItemExpired(int IDItem){
        Tuple<String,Item> itemExpired = removeItem(IDItem);
        getExpObj().getItems().add(itemExpired);
    }

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


        //check Minimal
    static public boolean checkMinimal(int itemID) {
        Item item = FindItemInInvent(itemID);
        if (item != null) {
            Product product = FindProduct(item.getCatalogNumItem());
            if (product.isMinimal()) {
                return true;
            }
        }
        return false;
    }

    static public Product MoveItem(int idToMove, Tuple<String, Integer> placeNew) {
        Item item = FindItemInInvent(idToMove);
        item.setPlace(placeNew);
        Product product = FindProduct(item.getCatalogNumItem());
        return product;
    }
        static public void FromStoreToWare(int idToMove, Tuple<String, Integer> placeNew){
        Product product=MoveItem(idToMove,placeNew);
        product.setStoreAmount(product.getStoreAmount()-1);
        product.setWarehouseAmount(product.getWarehouseAmount()+1);
    }

    static public void FromWareToStore(int idToMove, Tuple<String, Integer> placeNew){
        Product product=MoveItem(idToMove,placeNew);
        product.setStoreAmount(product.getStoreAmount()+1);
        product.setWarehouseAmount(product.getWarehouseAmount()-1);
    }

    static public void checkAllItemsExp(){
        ArrayList<Product> products = runAllProducts();
        for (Product product:products){
            for (Item item: product.getItems()){
                if (item.getExpirationDate().isBefore(currentDate)){
                    ItemExpired(item.getId());
                }
            }
        }
    }

    static public void checkAllItemsSale() {
        ArrayList<Product> products = runAllProducts();
        for (Product product : products) {
            if (product.getMySalePrice().getEndSale().isBefore(currentDate)) {
                product.setMarketPriceCurr(product.getMarketPriceConst());
            }
        }
    }

    static public String GenerateReportsStock() {
        StringBuilder outputForController = new StringBuilder();
        String title = "Inventory report:\n";


    }

    }

