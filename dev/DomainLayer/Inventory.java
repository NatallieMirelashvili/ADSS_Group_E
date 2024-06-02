package DomainLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Inventory{

    //    ***Fields***
    private HashMap<String, HashMap<String, HashMap<String, ArrayList<Product>>>> myStock;
    private static int amountItems;
    private int amountProducts;


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

    public Inventory() {
        this.myStock = new HashMap<>();
        this.amountItems = 0;
        this.amountProducts = 0;
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

    // לשנות לדרך השלילה -- לבדוק אחד אחד ולהוסיף את הפרודקט במקום המתאים
    public void addProductToInventory(Product newP) {
        if (myStock.containsKey(newP.getCatName())) {
            if (myStock.get(newP.getCatName()).containsKey(newP.getSubCatName())) {
                if (myStock.get(newP.getCatName()).get(newP.getSubCatName()).containsKey(newP.getSize())) {
                    runProductBySize(newP.getCatName(), newP.getSubCatName(), newP.getSize()).add(newP);
                    amountProducts++;
                    return;
                }
            }
        }
        ArrayList<Product> proToAdd = new ArrayList<>();
        proToAdd.add(newP);
        myStock.putIfAbsent(newP.getCatName(), new HashMap<>());
        myStock.get(newP.getCatName()).putIfAbsent(newP.getSubCatName(), new HashMap<>());
        myStock.get(newP.getCatName()).get(newP.getSubCatName()).putIfAbsent(newP.getSize(), new ArrayList<>());
        myStock.get(newP.getCatName()).get(newP.getSubCatName()).get(newP.getSize()).addAll(proToAdd);
        amountProducts++;
        amountItems++;
    }

    static public Item popItem(int IDItem) {
        for (int i = 0; i < amountItems; i++) {
            if (DataObject.itemsObj.get(i).getId() == IDItem) {
                Item itemToRemove = DataObject.itemsObj.remove(i);
                amountItems--;
                return itemToRemove;
            }
        }
        System.out.println("Item Not Found please try again");
        return null;
    }

    static public void ItemSells(int IDItem){
        popItem(IDItem);
    }
    static public void ItemDefective(int IDItem){
        Item itemDefective = popItem(IDItem);
        //DataObject.defObj.products.put(itemDefective.getCatalogNum(),//אין לי גישה לפרןדקט)
    }
    static public void ItemExpired(int IDItem){
        Item itemExpired = popItem(IDItem);
        //DataObject.defObj.products.put(itemExpired.getCatalogNum(),//אין לי גישה לפרןדקט)
    }

    public void GenerateReports() {
//        for (int i = 1; i <= amountDefectives; i++) {
//            System.out.println(i + ". CatalogNum:" + items.get(i).getCatalogNum() + "Location:" + items.get(i).getPlace());
//        }
    }
    }

