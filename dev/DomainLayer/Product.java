package DomainLayer;

import java.util.ArrayList;

public class Product {

//   ***Fields***
    private String catName;
    private String subCatName;
    private String size;
    private Tuple<Integer, Integer> total;
    private String manuFactor;
    private int catalogNum;
    private double marketPriceConst;
    private double manuPriceConst;
    private double manuPriceCurr;
    private double marketPriceCurr;
    private salePrice mySalePrice;
    private double discount;
    private ArrayList<Item> items ;
    private int minimalAmount;

//    ***Contracture***

    public Product(String catName, String subCatName, String size, Tuple<Integer, Integer> total, String manuFactor, int catalogNum, double marketPriceConst, double manuPriceConst, double manuPriceCurr, double marketPriceCurr, salePrice mySalePrice, double discount, ArrayList<Item> items, int minimalAmount) {
        this.catName = catName;
        this.subCatName = subCatName;
        this.size = size;
        this.total = total;
        this.manuFactor = manuFactor;
        this.catalogNum = catalogNum;
        this.marketPriceConst = marketPriceConst;
        this.manuPriceConst = manuPriceConst;
        this.manuPriceCurr = manuPriceCurr;
        this.marketPriceCurr = marketPriceCurr;
        this.mySalePrice = mySalePrice;
        this.discount = discount;
        this.items = items;
        this.minimalAmount = minimalAmount;
    }

//      ***Getters***
    public String getCatName() {
        return catName;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public String getSize() {
        return size;
    }

    public int getTotalAmount() {
        return total.getVal1()+total.getVal2();
    }
    public int getStoreAmount() {
        return total.getVal1();
    }
    public int getWarehouseAmount() {
        return total.getVal2();
    }


    public String getManuFactor() {
        return manuFactor;
    }

    public int getCatalogNum() {
        return catalogNum;
    }

    public double getMarketPriceConst() {
        return marketPriceConst;
    }

    public double getManuPriceConst() {
        return manuPriceConst;
    }

    public double getManuPriceCurr() {
        return manuPriceCurr;
    }

    public double getMarketPriceCurr() {
        return marketPriceCurr;
    }

    public salePrice getMySalePrice() {
        return mySalePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

//    ***Setters***

    public void setMySalePrice(salePrice mySalePrice) {
        this.mySalePrice = mySalePrice;
        this.marketPriceCurr=this.marketPriceConst*mySalePrice.getDiscountRatio();
    }


    public void setDiscount(double discount) {
        this.discount = discount;
        this.manuPriceCurr=this.manuPriceConst*discount;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStoreAmount(int storeAmount) { this.total.setVal1(storeAmount);}

    public void setWarehouseAmount(int warehouseAmount) { this.total.setVal2(warehouseAmount);}

    public void setManuFactor(String manuFactor) {
        this.manuFactor = manuFactor;
    }

    public void setCatalogNum(int catalogNum) {
        this.catalogNum = catalogNum;
    }

    public void setMarketPriceConst(double marketPriceConst) {
        this.marketPriceConst = marketPriceConst;
    }

    public void setManuPriceConst(double manuPriceConst) {
        this.manuPriceConst = manuPriceConst;
    }

    public void setManuPriceCurr(double manuPriceCurr) {
        this.manuPriceCurr = manuPriceCurr;
    }

    public void setMarketPriceCurr(double marketPriceCurr) {
        this.marketPriceCurr = marketPriceCurr;
    }



    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }


//    ***Help Functions***
    public boolean isMinimal(){
        return getTotalAmount()<= minimalAmount;
    }

    public void addItemToLst(Item newItem){
        if (newItem.getCatalogNum() == catalogNum){
            items.add(newItem);
        }
    }
    public void addMeToInven(){
        Inventory inventory = DataObject.getInvenObj();
        inventory.addProduct(this);
    }

}
