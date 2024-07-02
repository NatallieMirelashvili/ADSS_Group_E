package DomainLayer;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductRepo implements IRepository{
    private HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myProducts;

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
        } else { // case where cat does not exist
            return false;
        }
    }

    //return product in inventory if he exists; otherwise return null.
    public Product find(int catNumber) {
        Product productRes = null;
        ArrayList<Product> AllProducts = findAll();
        for (Product product : AllProducts) {
            if (product.getCatalogNumProduct() == catNumber) {
                productRes = product;
                break;
            }
        }
        return productRes;
    }

    //return an array list of all products by category
    public ArrayList<Product> findAllProductsByMainCat(String cat) {
        if (findIfExist(cat, "0", "0")) {
            ArrayList<Product> productsRes = new ArrayList<>();
            HashMap<String, HashMap<String, ArrayList<Product>>> subCatToChange = myProducts.get(cat);
            for (String mySubCat : subCatToChange.keySet()) {
                productsRes.addAll(findAllProductsBySubCat(cat, mySubCat));
            }
            return productsRes;
        }
        return new ArrayList<Product>();
    }

    //return an array list of all products by category and sub category
    public ArrayList<Product> findAllProductsBySubCat(String cat, String subCat) {
        if (findIfExist(cat, subCat, "0")) {
            ArrayList<Product> productsRes = new ArrayList<>();
            HashMap<String, ArrayList<Product>> products1 = myProducts.get(cat).get(subCat);
            for (String mySize : products1.keySet()) {
                productsRes.addAll(findAllProductsBySize(cat, subCat, mySize));
            }
            return productsRes;
        }
        return new ArrayList<Product>();
    }

    //return an array list of all products by category, sub category and size
    public ArrayList<Product> findAllProductsBySize(String cat, String subCat, String size) {
        if (findIfExist(cat, subCat, size)) {
            return myProducts.get(cat).get(subCat).get(size);
        }
        return new ArrayList<Product>();
    }

    //return an array list of all products by category
    public ArrayList<Product> findAll() {
        ArrayList<Product> productsRes = new ArrayList<>();
        for (String Category : myProducts.keySet()) {
            productsRes.addAll(findAllProductsByMainCat(Category));
        }
        return productsRes;
    }

    //add a new product to inventory that didn't exist
    public Product add(JsonObject newRec) {
        Product newProd = new Product(newRec.get("catName").getAsString(), newRec.get("subCatName").getAsString(),
        newRec.get("size").getAsString(), newRec.get("manuFactor").getAsString(),
        newRec.get("catalogNumProduct").getAsInt(),
        newRec.get("marketPriceConst").getAsDouble(), newRec.get("manuPriceConst").getAsDouble(),
        newRec.get("discount").getAsInt(), newRec.get("minimalAmount").getAsInt(),newRec.get("orderAmount").getAsInt() );
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
        return myProd;
    }

    //update function:
    public void updateSalePriceRepo(ArrayList<Product> products, salePrice newSale) {
        for (Product product : products)
            product.setMySalePrice(newSale);
    }

    public boolean updateDisRepo(ArrayList<Product> products, int ratio, String manufacturer) {
        int success = 0;
        for (Product product : products) {
            if (product.getManufacture().equals(manufacturer)) {
                product.setDiscount(ratio);
                success++;
            }
        }
        if (success == 0)
            return false;
        return true;
    }

    public void updateAmountItem(String addOrRemove, int catNum, String place) {
        Product prodToUpdate = find(catNum);
        if (addOrRemove == "ADD") {
            if (place == "store") {
                prodToUpdate.setStoreAmount(prodToUpdate.getStoreAmount() + 1);
            } else //warehouse
                prodToUpdate.setWarehouseAmount(prodToUpdate.getWarehouseAmount() + 1);
        } else { //REMOVE
            if (place == "store") {
                prodToUpdate.setStoreAmount(prodToUpdate.getStoreAmount() - 1);
            } else //warehouse
                prodToUpdate.setWarehouseAmount(prodToUpdate.getWarehouseAmount() - 1);
        }
    }

    public void updatePrice(Product product, double newPrice){
        product.setMarketPriceCurr(newPrice);
    }
}



