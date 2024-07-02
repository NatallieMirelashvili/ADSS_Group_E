package DomainLayer;

public class Product {

//   ***Fields***
    private final String catName;
    private final String subCatName;
    private final String prodSize;
    private Tuple<Integer, Integer> total;
    private final String manufacture;
    private final int catalogNumProduct;
    private final double marketPriceConst;
    private final double manuPriceConst;
    private double manuPriceCurr;
    private double marketPriceCurr;
    private salePrice mySalePrice;
    private int discount;
    private final int minimalAmount;
    private int orderAmount;
    private boolean isMinimal;

//    ***Contracture***
    public Product(String catName, String subCatName, String size, String manuFactor, int catalogNumProduct,
                   double marketPriceConst, double manuPriceConst, int discount, int minimalAmount,int orderAmount) {
        this.catName = catName;
        this.subCatName = subCatName;
        this.prodSize = size;
        this.manufacture = manuFactor;
        this.catalogNumProduct = catalogNumProduct;
        this.minimalAmount = minimalAmount;
        this.discount = discount;

        this.marketPriceConst = marketPriceConst;
        this.marketPriceCurr = marketPriceConst;

        this.manuPriceConst = manuPriceConst;
        this.manuPriceCurr = manuPriceConst-(manuPriceConst*discount/100);

        this.mySalePrice=null;
        this.total= new Tuple<>(0,0);
        this.orderAmount=orderAmount;
        this.isMinimal =false;
    }

//      ***Getters***
    public String getCatName() {
        return catName;
    }
    public String getSubCatName() {
        return subCatName;
    }
    public String getProdSize() {
        return prodSize;
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
    public String getManufacture() {
        return manufacture;
    }
    public int getCatalogNumProduct() {
        return catalogNumProduct;
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
    public int getOrderAmount() {return orderAmount; }
    public boolean isMinimal() { return isMinimal; }

    //    ***Setters***
    public void setMySalePrice(salePrice mySalePrice) {
        this.mySalePrice = mySalePrice;
        if (mySalePrice.getStartSale().isEqual(Facade.getCurrentDate())) {
            this.setMarketPriceCurr(this.getMarketPriceConst() - (this.getMarketPriceConst() * mySalePrice.getDiscountRatio() / 100));
        }
    }
    public void setDiscount(int discount) {
        this.discount = discount;
        this.setManuPriceCurr(this.getManuPriceConst()-(this.getManuPriceConst()*discount/100));
    }
    public void setStoreAmount(int storeAmount) { this.total.setVal1(storeAmount);}
    public void setWarehouseAmount(int warehouseAmount) { this.total.setVal2(warehouseAmount);}
    public void setManuPriceCurr(double manuPriceCurr) {
        this.manuPriceCurr = manuPriceCurr;
    }
    public void setMarketPriceCurr(double marketPriceCurr) {
        this.marketPriceCurr = marketPriceCurr;
    }
    public void setMinimal(boolean isMinimal) { this.isMinimal = isMinimal; }

    //    ***Help Functions***
    public boolean checkMinimalProd(){
        if (getTotalAmount()-1 <= minimalAmount) {
            setMinimal(true);
            return true;
        }
        else return false;
    }

    public String printProduct(){
        String category = "Category:" + this.getCatName()+"\n";
        String subCategory = "Sub Category:" + this.getSubCatName()+"\n";
        String size = "Size:" + this.getProdSize()+"\n";
        String manufacturer = "Manufacturer:" + this.getManufacture()+"\n";
        return  category+subCategory+size+manufacturer;
    }
}
