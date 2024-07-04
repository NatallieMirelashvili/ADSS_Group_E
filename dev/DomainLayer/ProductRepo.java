package DomainLayer;
import DataAccessLayer.ProductAccessObj;
import DataAccessLayer.SaleItemAccessObj;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductRepo implements IRepository<Product>{
    private HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myProducts = new HashMap<>();
    private final ProductAccessObj myDAOProduct = new ProductAccessObj();
    private final SaleItemAccessObj myDAOSaleItem = new SaleItemAccessObj();
    public HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> getMyProducts() {
        return myProducts;
    }

    //      find if product exist in inventory when given catalog number
//      static public boolean ProductExist(int catNumber) {
//      return searchProdByCatNumCTR(catNumber) != null;
    public boolean findIfExist(String cat, String subCat, String size) {
        if (myProducts.get(cat) != null) {
            if (myProducts.get(cat).get(subCat) != null) {
                if (myProducts.get(cat).get(subCat).get(size) != null) {
                    return true;
                } else {  // case where size does not exist
                    return size.equals("0");
                }
            } else {   // case where subCat does not exist
                return (size.equals("0") && subCat.equals("0"));
            }
        } else { // case where cat does not exist in repo

            return false;
        }
    }

    //return product in inventory if he exists; otherwise return null.
    public Product find(int catNumber) {
//        All products NOT include from DB
        ArrayList<Product> AllProducts = findAll();
        for (Product product : AllProducts) {
            if (product.getCatalogNumProduct() == catNumber) {
                return product;
            }
        }
//        try in DB:
        JsonObject founded = myDAOProduct.search(catNumber);
        if(founded!=null)
        {
            return addToRepoOnly(founded, true);
        }
        return null;
    }

    //return an array list of all products by category
    public ArrayList<Product> findAllProductsByMainCat(String cat) {
        ArrayList<Product> productsInCatch = new ArrayList<>();
        if (findIfExist(cat, "0", "0")) {
            HashMap<String, HashMap<String, ArrayList<Product>>> subCatToChange = myProducts.get(cat);
            for (String mySubCat : subCatToChange.keySet()) {
                productsInCatch.addAll(findAllProductsBySubCat(cat, mySubCat));
            }
        }
            ArrayList<Integer> alreadyHave = new ArrayList<>();
            for (Product prod: productsInCatch){
                alreadyHave.add(prod.getCatalogNumProduct());
            }
            ArrayList<JsonObject> theRestFromDB = myDAOProduct.findAllProductsByMainCatDB(cat, alreadyHave);
//            FETCH TO CATCH
            return FetchAndCombine(theRestFromDB, productsInCatch);

    }

    //return an array list of all products by category and sub category
    public ArrayList<Product> findAllProductsBySubCat(String cat, String subCat) {
        ArrayList<Product> productsInCatch = new ArrayList<>();
        if (findIfExist(cat, subCat, "0")) {
            HashMap<String, ArrayList<Product>> products1 = myProducts.get(cat).get(subCat);
            for (String mySize : products1.keySet()) {
                productsInCatch.addAll(findAllProductsBySize(cat, subCat, mySize));
            }
        }
            ArrayList<Integer> alreadyHave = new ArrayList<>();
            for (Product prod: productsInCatch){
                alreadyHave.add(prod.getCatalogNumProduct());
            }
            ArrayList<JsonObject> theRestFromDB = myDAOProduct.findAllProductsBySubCatDB(cat, subCat, alreadyHave);
//            FETCH TO CATCH
            return FetchAndCombine(theRestFromDB, productsInCatch);

    }

    //return an array list of all products by category, sub category and size
    public ArrayList<Product> findAllProductsBySize(String cat, String subCat, String size) {
        ArrayList<Product> inCatch = new ArrayList<>();
        if (findIfExist(cat, subCat, size)) {
           inCatch = myProducts.get(cat).get(subCat).get(size);
        }
            ArrayList<Integer> alreadyHave = new ArrayList<>();
            for (Product prod: inCatch){
                alreadyHave.add(prod.getCatalogNumProduct());
            }
            ArrayList<JsonObject> theRestFromDB = myDAOProduct.findAllProductsBySizeDB(cat, subCat, size, alreadyHave);
//            FETCH TO CATCH
            return FetchAndCombine(theRestFromDB, inCatch);
    }

    //return an array list of all products by category only in catch memo
    public ArrayList<Product> findAll() {
        ArrayList<Product> productsRes = new ArrayList<>();
        for (String Category : myProducts.keySet()) {
            productsRes.addAll(findAllProductsByMainCat(Category));
        }
        return productsRes;
    }
    // adding to inventory + DB
    public Product add(JsonObject newRec) {
//        Bran new Prod
        myDAOProduct.insert(newRec);
        return addToRepoOnly(newRec, false);

    }
    //add a new product to inventory (NOT TO DB) that didn't exist

    private Product addToRepoOnly(JsonObject newRec, boolean fromDB){
        Product newProd = parseToProd(newRec, fromDB);
        ArrayList<Product> proToAdd = new ArrayList<>();
        proToAdd.add(newProd);
        if (!myProducts.containsKey(newProd.getCatName())) {
            myProducts.putIfAbsent(newProd.getCatName(), new HashMap<>());
            myProducts.get(newProd.getCatName()).putIfAbsent(newProd.getSubCatName(), new HashMap<>());
            myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).putIfAbsent(newProd.getProdSize(), new ArrayList<>());
            myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).get(newProd.getProdSize()).addAll(proToAdd);
        } else if (!myProducts.get(newProd.getCatName()).containsKey(newProd.getSubCatName())) {
            myProducts.get(newProd.getCatName()).putIfAbsent(newProd.getSubCatName(), new HashMap<>());
            myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).putIfAbsent(newProd.getProdSize(), new ArrayList<>());
            myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).get(newProd.getProdSize()).addAll(proToAdd);
        } else if (!myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).containsKey(newProd.getProdSize())) {
            myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).putIfAbsent(newProd.getProdSize(), new ArrayList<>());
            myProducts.get(newProd.getCatName()).get(newProd.getSubCatName()).get(newProd.getProdSize()).addAll(proToAdd);
        } else {
            findAllProductsBySize(newProd.getCatName(), newProd.getSubCatName(), newProd.getProdSize()).add(newProd);
        }
        return newProd;
    }

    public Product remove(int unique) {
        Product myProd = find(unique);
        findAllProductsBySize(myProd.getCatName(), myProd.getSubCatName(), myProd.getProdSize()).remove(myProd);
        myDAOProduct.remove(unique);
        return myProd;
    }

    //update function:
    public void updateSalePriceRepo(ArrayList<Product> products, salePrice newSale) {
        ArrayList<Integer> forDB = new ArrayList<>();
        for (Product product : products)
        {
            product.setMySalePrice(newSale);
            forDB.add(product.getCatalogNumProduct());
        }
//        DB stuff:
        JsonObject saleItemRec = new JsonObject();
        saleItemRec.addProperty("idSale", newSale.getId());
        saleItemRec.addProperty("startSale", newSale.getStartSale().toString());
        saleItemRec.addProperty("endSale", newSale.getEndSale().toString());
        saleItemRec.addProperty("discountRatio", newSale.getDiscountRatio());
        myDAOSaleItem.insert(saleItemRec);
        myDAOProduct.updateSaleDB(forDB, newSale.getId(), newSale.getDiscountRatio());
    }

    public boolean updateDisRepo(ArrayList<Product> products, int ratio, String manufacturer) {
        ArrayList<Integer> forDB = new ArrayList<>();
        int success = 0;
        for (Product product : products) {
            if (product.getManufacture().equals(manufacturer)) {
                product.setDiscount(ratio);
                forDB.add(product.getCatalogNumProduct());
                success++;
            }
        }
        if(success == 0)
            return false;
        myDAOProduct.updateDisDB(forDB, ratio);
        return true;
    }

    public void updateAmountItem(String addOrRemove, int catNum, String place) {
        Product prodToUpdate = find(catNum);
        if (addOrRemove.equals("ADD")) {
            if (place.equals("Store")) {
                prodToUpdate.setStoreAmount(prodToUpdate.getStoreAmount() + 1);
            } else //warehouse
                prodToUpdate.setWarehouseAmount(prodToUpdate.getWarehouseAmount() + 1);
        } else { //REMOVE
            if (place.equals("Store")) {
                prodToUpdate.setStoreAmount(prodToUpdate.getStoreAmount() - 1);
            } else //warehouse
                prodToUpdate.setWarehouseAmount(prodToUpdate.getWarehouseAmount() - 1);
        }
    }

    public void updatePrice(Product product, double newPrice){
        product.setMarketPriceCurr(newPrice);
    }
    private Product parseToProd(JsonObject record, boolean fromDB){
        Product newProd = new Product(record.get("catName").getAsString(), record.get("subCatName").getAsString(),
                record.get("ProdSize").getAsString(), record.get("manuFactor").getAsString(),
                record.get("catalogNumProduct").getAsInt(),
                record.get("marketPriceConst").getAsDouble(), record.get("manuPriceConst").getAsDouble(),
                record.get("discount").getAsInt(), record.get("minimalAmount").getAsInt(),record.get("orderAmount").getAsInt() );
        if(!fromDB)
        {
            return newProd;
        }
//        CHECK THIS -> SALE ITEM STILL DONT ADDED TO TABLE
        if(!record.get("mySalePrice").isJsonNull()){
//            Setting sale price if there is one
            JsonObject saleRec = myDAOSaleItem.search(record.get("mySalePrice").getAsInt());
            String start = saleRec.get("startSale").getAsString();
            LocalDate startDate = LocalDate.parse(start);
            String end = saleRec.get("endSale").getAsString();
            LocalDate endDate = LocalDate.parse(end);
            salePrice sale = new salePrice(startDate, endDate,saleRec.get("discountRatio").getAsInt(), saleRec.get("idSale").getAsInt());
            newProd.setMySalePrice(sale);
        }
//            set is minimal
            newProd.setMinimal(record.get("isMinimal").getAsBoolean());
//            set total -> "Amount in store,amount in warehouse"
            String[] totalSTR = record.get("total").getAsString().split(",");
            Tuple<Integer, Integer> total = new Tuple<>(Integer.parseInt(totalSTR[0]), Integer.parseInt(totalSTR[1]));
            newProd.setTotal(total);
//            set curr prices:
            newProd.setManuPriceCurr(record.get("manuPriceCurr").getAsDouble());
            newProd.setMarketPriceCurr(record.get("marketPriceCurr").getAsDouble());
            return newProd;
    }

//    parsing the given array list of Json founded from DB to Products and combine the given array list of products into one
    private ArrayList<Product> FetchAndCombine(ArrayList<JsonObject> records, ArrayList<Product> alreadyIn){
        ArrayList<Product> combined = new ArrayList<>(alreadyIn);
//        Parse to products:
        for(JsonObject rec: records){
            Product fetch = parseToProd(rec, true);
            combined.add(fetch);
        }
        return combined;
    }

}



