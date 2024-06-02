package DomainLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Inventory{

    //    ***Fields***
    private HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock;
    private int amountIn;

    //    ***Constructor***
    public Inventory() {
            this.myStock = null;
            this.amountIn = 0;
        }

    //    ***Getters***
    public int getAmountIn() {
        return amountIn;
    }
    public HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> getMyStock() {
        return myStock;
    }


    //update sale price about products by category, sub category and size that given (all or some)
    public void setSalePrice(String cat, String subCat , String size, Date from, Date to, double ratioSale) {
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
    public void setSaleOnProducts (ArrayList<Product> products, salePrice newSale){
        for (Product product : products)
            product.setMySalePrice(newSale);
    }

    //update discount about products by category, sub category and size that given (all or some)
    public void setDiscount(String cat, String subCat , String size, double myDiscount, String myManufacturer) {
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
    public ArrayList<Product> runProductBySize (String cat, String subCat, String size) {
        return myStock.get(cat).get(subCat).get(size);
    }

    //return an array list of all products by category and sub category
    public ArrayList<Product> runProductBySubCat (String cat, String subCat) {
        ArrayList<Product> productsRes=new ArrayList<>();
        HashMap<String, ArrayList<Product>> products1 = myStock.get(cat).get(subCat);
        for (String mySize : products1.keySet()) {
            productsRes.addAll(runProductBySize (cat,subCat,mySize));
        }
        return productsRes;
    }

    //return an array list of all products by category
    public ArrayList<Product> runProductByCat (String cat) {
        ArrayList<Product> productsRes=new ArrayList<>();
        HashMap<String, HashMap<String, ArrayList<Product>>> subCatToChange = myStock.get(cat);
            for (String mySubCat : subCatToChange.keySet()) {
                productsRes.addAll(runProductBySubCat (cat, mySubCat));
            }
            return productsRes;
    }

//    ***Help Functions***

    //help function-update discount about list of products
    public void discountOnProducts (ArrayList<Product> products, double discount, String manufacturer) {
        for (Product product : products) {
            if (product.getManuFactor().equals(manufacturer))
                product.setDiscount(discount);
        }
    }
    public void addProduct(Product newP){
        this.runProductBySize(newP.getCatName(), newP.getSubCatName(), newP.getSize()).add(newP);
    }

    }

