package DomainLayer;

import java.util.ArrayList;
import java.util.Date;

public class Product {
    private String catName;
    private String subCatName;
    private double size;
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

    int minimalAmount;

    public Product(String catName, String subCatName, double size, Tuple<Integer, Integer> total, String manuFactor, int catalogNum, double marketPriceConst, double manuPriceConst, double manuPriceCurr, double marketPriceCurr, salePrice mySalePrice, double discount, ArrayList<Item> items, int minimalAmount) {
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

    public String getCatName() {
        return catName;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public double getSize() {
        return size;
    }

    public Tuple<Integer, Integer> getTotal() {
        return total;
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

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setTotal(Tuple<Integer, Integer> total) {
        this.total = total;
    }

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

    public void setMySalePrice(salePrice mySalePrice) {
        this.mySalePrice = mySalePrice;
    }



    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }
//    Requ
    public boolean isMinimal(){
        return this.total.getVal1() + this.total.getVal2() <= minimalAmount;
    }


}
