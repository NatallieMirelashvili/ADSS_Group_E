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
    static public LocalDate currentDate = LocalDate.now();

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
        if (subCat.equals("0")) {
            setSaleOnProducts(runProductByCat(cat), newSale);
            return;
        }
        if(size.equals("0")){
            setSaleOnProducts(runProductBySubCat(cat, subCat), newSale);
            return;
        }
        setSaleOnProducts(runProductBySize(cat, subCat, size), newSale);
    }

    //help function-update sale price about list of products
   static public void setSaleOnProducts (ArrayList<Product> products, salePrice newSale){
        for (Product product : products)
            product.setMySalePrice(newSale);
    }

    //update discount about products by category, sub category and size that given (all or some)
    static public boolean setDiscount(String cat, String subCat , String size, double myDiscount, String myManufacturer) {
        if (subCat.equals("0")) {
            return discountOnProducts(runProductByCat(cat), myDiscount,myManufacturer);
        }
        if(size.equals("0")){
            return discountOnProducts(runProductBySubCat(cat, subCat), myDiscount,myManufacturer);
        }
        return discountOnProducts(runProductBySize(cat, subCat, size), myDiscount,myManufacturer);
    }

    //return an array list of all products by category, sub category and size
    static public ArrayList<Product> runProductBySize (String cat, String subCat, String size) {
        if (ProductExistByCat(cat, subCat,size)) {
            return myStock.get(cat).get(subCat).get(size);
        }
        return new ArrayList<Product>();
    }

    //return an array list of all products by category and sub category
    static public ArrayList<Product> runProductBySubCat (String cat, String subCat) {
        if (ProductExistByCat(cat, subCat,"0")) {
            ArrayList<Product> productsRes = new ArrayList<>();
            HashMap<String, ArrayList<Product>> products1 = myStock.get(cat).get(subCat);
            for (String mySize : products1.keySet()) {
                productsRes.addAll(runProductBySize(cat, subCat, mySize));
            }
            return productsRes;
        }
        return new ArrayList<Product>();
    }

    //return an array list of all products by category
    static public ArrayList<Product> runProductByCat (String cat) {
        if (ProductExistByCat(cat, "0","0")) {
            ArrayList<Product> productsRes = new ArrayList<>();
            HashMap<String, HashMap<String, ArrayList<Product>>> subCatToChange = myStock.get(cat);
            for (String mySubCat : subCatToChange.keySet()) {
                productsRes.addAll(runProductBySubCat(cat, mySubCat));
            }
            return productsRes;
        }
        return new ArrayList<Product>();
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
    static public boolean discountOnProducts (ArrayList<Product> products, double discount, String manufacturer) {
        for (Product product : products) {
            if (product.getManuFactor().equals(manufacturer)) {
                product.setDiscount(discount);
                return true;
            }
        }
        return false;
    }

    //find if product exist in inventory when given category
    static public boolean ProductExistByCat(String cat, String subCat, String size) {
        if (myStock.get(cat) != null) {
            if (myStock.get(cat).get(subCat) != null) {
                if (myStock.get(cat).get(subCat).get(size) != null) {
                    return true;
                } else {  // case where size does not exist
                    return size.equals("0");
                }
            } else {   // case where subCat does not exist
                return (size.equals("0") && subCat.equals("0"));
            }
        } else { // case where cat does not exist
            return false;
        }
    }

    //find if product exist in inventory when given catalog number
    static public boolean ProductExist(int catNumber) {
        return FindProduct(catNumber) != null;
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
    static public boolean ItemSells(int IDItem){
        Item item = FindItemInInvent(IDItem);
        if (item != null) {
            Product product = item.getMyProduct();
            product.removeItemToLst(item);
            return true;
        }
        return false;
    }

    //remove item from inventory when he is defective and move to list of defectives with details important
    static public boolean ItemDefective(int IDItem){
        Tuple<String,Item> itemDefective = removeItem(IDItem);
        if(itemDefective.getVal2() == null)
            return false;
        getDefObj().getItems().add(itemDefective);
        return true;
    }

    //remove item from inventory when he is expired and move to list of expired with details important
    static public boolean ItemExpired(int IDItem){
        Tuple<String,Item> itemExpired = removeItem(IDItem);
        if(itemExpired.getVal2() == null)
            return false;
        getExpObj().getItems().add(itemExpired);
        return true;
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
        int loc=0;
        ArrayList<Product> products = runAllProducts();
        for (Product product : products) {
            int j=product.getItems().size();
            for (int i=0; i<j; i++) {
                if (product.getItems().get(loc).getExpirationDate().isBefore(currentDate)) {
                    ItemExpired(product.getItems().get(loc).getId());
                }
                else {
                    loc++;
                }
            }
            loc=0;
        }

    }

    //update sale price according to date
    static public void checkAllProdSale() {
        ArrayList<Product> products = runAllProducts();
        for (Product product : products) {
            if(product.getMySalePrice()!=null){
            if (product.getMySalePrice().getEndSale().isBefore(currentDate)) {
                product.setMarketPriceCurr(product.getMarketPriceConst());
                }
            if (product.getMySalePrice().getStartSale().isEqual(currentDate)) {
                product.setMarketPriceCurr(product.getMarketPriceConst()-
                        (product.getMarketPriceConst()* product.getMySalePrice().getDiscountRatio()/100));
            }
            }
        }
    }
    public static void moveToNextDay(){
        currentDate = currentDate.plusDays(1);
    }


}

