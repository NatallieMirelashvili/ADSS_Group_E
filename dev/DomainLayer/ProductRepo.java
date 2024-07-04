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
        if (myProducts.get(cat) != null || myDAOProduct.findAllProductsByMainCatDB(cat, new ArrayList<>(0)).size() !=0) {
            if (myProducts.get(cat).get(subCat) != null||myDAOProduct.findAllProductsBySubCatDB(cat,subCat, new ArrayList<>(0)).size() !=0) {
                if (myProducts.get(cat).get(subCat).get(size) != null || myDAOProduct.findAllProductsBySizeDB(cat, subCat, size,new ArrayList<>(0)).size() !=0) {
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
        ArrayList<Product> res = new ArrayList<>();
        if (findIfExist(cat, "0", "0")) {
            HashMap<String, HashMap<String, ArrayList<Product>>> subCatToChange = myProducts.get(cat);
            for (String mySubCat : subCatToChange.keySet()) {
                res.addAll(findAllProductsBySubCat(cat, mySubCat));
            }
        }
            ArrayList<Integer> alreadyHave = new ArrayList<>();
            for (Product prod: res){
                alreadyHave.add(prod.getCatalogNumProduct());
            }
            ArrayList<JsonObject> theRestFromDB = myDAOProduct.findAllProductsByMainCatDB(cat, alreadyHave);
//            FETCH TO CATCH
            for(JsonObject record: theRestFromDB){
//                fromDB = true!
                Product parsedProdAndAdded = addToRepoOnly(record, true);
                res.add(parsedProdAndAdded);
            }
            return res;
    }

    //return an array list of all products by category and sub category
    public ArrayList<Product> findAllProductsBySubCat(String cat, String subCat) {
        ArrayList<Product> res = new ArrayList<>();
        if (findIfExist(cat, subCat, "0")) {
            HashMap<String, ArrayList<Product>> products1 = myProducts.get(cat).get(subCat);
            for (String mySize : products1.keySet()) {
                res.addAll(findAllProductsBySize(cat, subCat, mySize));
            }
        }
            ArrayList<Integer> alreadyHave = new ArrayList<>();
            for (Product prod: res){
                alreadyHave.add(prod.getCatalogNumProduct());
            }
            ArrayList<JsonObject> theRestFromDB = myDAOProduct.findAllProductsBySubCatDB(cat, subCat, alreadyHave);
//            FETCH TO CATCH
            for(JsonObject record: theRestFromDB){
    //                fromDB = true!
                Product parsedProdAndAdded = addToRepoOnly(record, true);
                res.add(parsedProdAndAdded);
            }
            return res;

    }

    //return an array list of all products by category, sub category and size
    public ArrayList<Product> findAllProductsBySize(String cat, String subCat, String size) {
        ArrayList<Product> res = new ArrayList<>();
        if (findIfExist(cat, subCat, size)) {
           res = myProducts.get(cat).get(subCat).get(size);
        }
            ArrayList<Integer> alreadyHave = new ArrayList<>();
            for (Product prod: res){
                alreadyHave.add(prod.getCatalogNumProduct());
            }
            ArrayList<JsonObject> theRestFromDB = myDAOProduct.findAllProductsBySizeDB(cat, subCat, size, alreadyHave);
//            FETCH TO CATCH
            for(JsonObject record: theRestFromDB){
                //                fromDB = true!
                Product parsedProdAndAdded = addToRepoOnly(record, true);
                res.add(parsedProdAndAdded);
            }
            return res;
    }

    //return an array list of all products by category only in catch memo
    public ArrayList<Product> findAll() {
        ArrayList<String> getAllPossibleCategories = myDAOProduct.findAllExistMainCategories();
        ArrayList<Product> productsRes = new ArrayList<>();
        for (String Category : getAllPossibleCategories) {
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
    public void updateSalePriceRepo(ArrayList<Product> products, JsonObject js) {
        int saleID = myDAOSaleItem.getLastSaleID() + 1;
        js.addProperty("idSale", saleID);
        salePrice newSale = new salePrice(LocalDate.parse(js.get("startSale").getAsString()),
                LocalDate.parse(js.get("endSale").getAsString()), js.get("discountRatio").getAsInt(), saleID);
        ArrayList<Integer> forDB = new ArrayList<>();
        for (Product product : products)
        {
            product.setMySalePrice(newSale);
            forDB.add(product.getCatalogNumProduct());
        }
//        DB stuff:
        myDAOSaleItem.insert(js);
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
                myDAOProduct.addItemDB(catNum, "Store");
            } else //warehouse
            {prodToUpdate.setWarehouseAmount(prodToUpdate.getWarehouseAmount() + 1);
                myDAOProduct.addItemDB(catNum, "Warehouse");
            }
        } else { //REMOVE
            if (place.equals("Store")) {
                prodToUpdate.setStoreAmount(prodToUpdate.getStoreAmount() - 1);
                myDAOProduct.removeItemDB(catNum, "Store");

            } else //warehouse
            {prodToUpdate.setWarehouseAmount(prodToUpdate.getWarehouseAmount() - 1);
                myDAOProduct.removeItemDB(catNum, "Warehouse");
            }
        }
    }

    public void updatePrice(Product product, double newPrice){
        product.setMarketPriceCurr(newPrice);
        myDAOProduct.updateMarketPrice(product.getCatalogNumProduct(), newPrice);
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
        if(record.has("mySalePrice") && !record.get("mySalePrice").isJsonNull()){
            JsonObject saleRec = myDAOSaleItem.search(record.get("mySalePrice").getAsInt());
        if(saleRec != null){
//            Setting sale price if there is one
            String start = saleRec.get("startSale").getAsString();
            LocalDate startDate = LocalDate.parse(start);
            String end = saleRec.get("endSale").getAsString();
            LocalDate endDate = LocalDate.parse(end);
            salePrice sale = new salePrice(startDate, endDate,saleRec.get("discountRatio").getAsInt(), saleRec.get("idSale").getAsInt());
            newProd.setMySalePrice(sale);
        }
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

    public void setIsMinimal(Product product, boolean b) {
        myDAOProduct.updateIsMinimal(product.getCatalogNumProduct(), b);
    }


}



