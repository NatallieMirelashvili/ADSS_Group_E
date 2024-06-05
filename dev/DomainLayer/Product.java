package DomainLayer;

import java.util.ArrayList;

public class Product {

//   ***Fields***
    private String catName;
    private String subCatName;
    private String size;
    private Tuple<Integer, Integer> total;
    private String manuFactor;
    private int catalogNumProduct;
    private double marketPriceConst;
    private double manuPriceConst;
    private double manuPriceCurr;
    private double marketPriceCurr;
    private salePrice mySalePrice;
    private double discount;
    private ArrayList<Item> items ;
    private int minimalAmount;

//    ***Contracture***
    public Product(String catName, String subCatName, String size, String manuFactor, int catalogNumProduct,
                   double marketPriceConst, double manuPriceConst, double discount, int minimalAmount) {
        this.catName = catName;
        this.subCatName = subCatName;
        this.size = size;
        this.manuFactor = manuFactor;
        this.catalogNumProduct = catalogNumProduct;
        this.minimalAmount = minimalAmount;
        this.discount = discount;

        this.marketPriceConst = marketPriceConst;
        this.marketPriceCurr = marketPriceConst;

        this.manuPriceConst = manuPriceConst;
        this.manuPriceCurr = manuPriceConst-(manuPriceConst*discount/100);

        this.items = new ArrayList<>(0);
        this.mySalePrice=null;
        this.total= new Tuple<>(0,0);
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
    public double getDiscount() {
        return discount;
    }
    public ArrayList<Item> getItems() {
        return items;
    }

//    ***Setters***
    public void setMySalePrice(salePrice mySalePrice) {
        this.mySalePrice = mySalePrice;
        if (mySalePrice.getStartSale().isEqual(Inventory.currentDate)) {
            this.setMarketPriceCurr(this.getMarketPriceConst() - (this.getMarketPriceConst() * mySalePrice.getDiscountRatio() / 100));
        }
    }
    public void setDiscount(double discount) {
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
    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }


//    ***Help Functions***
    public boolean isMinimal(){
        return getTotalAmount()-1 <= minimalAmount;
    }

    //add item
    public void addItemToLst(Item newItem){
            items.add(newItem);
            if (newItem.getStoreOrWare().equals("Store")){
                setStoreAmount(getStoreAmount()+1);
            }else //the item in warehouse
                setWarehouseAmount(getWarehouseAmount()+1);
    }

    //remove item
    public void removeItemToLst(Item removeItem) {
        items.remove(removeItem);
        if (removeItem.getStoreOrWare().equals("Store")) {
            setStoreAmount(getStoreAmount() - 1);
        } else //the item in warehouse
            setWarehouseAmount(getWarehouseAmount() - 1);
    }

    //help function when item move from store to warehouse or vice versa
    public void ProFromStoreToWare(int idToMove, Tuple<String, Integer> placeNew) {
        Item item = FindItemInPro(idToMove);
        if (item != null) {
            item.setPlace(placeNew);
            item.setStoreOrWare("Warehouse");
            setWarehouseAmount(getWarehouseAmount()+1);
            setStoreAmount(getStoreAmount()-1);
        }
    }
    public void ProFromWareToStore(int idToMove, Tuple<String, Integer> placeNew) {
        Item item = FindItemInPro(idToMove);
        if (item != null) {
            item.setPlace(placeNew);
            item.setStoreOrWare("Store");
            setWarehouseAmount(getWarehouseAmount()-1);
            setStoreAmount(getStoreAmount()+1);
        }
    }

    //return item by id; if he didn't exist return null
    public Item FindItemInPro(int IDItem) {
        for (Item items : items) {
            if (items.getId() == IDItem) {
                return items;
            }
        }
        return null;
    }

    //add product to inventory
    public void addMeToInvent(){
        Inventory.addProductToInventory(this);
    }

    public String printProduct(){
        String category = "Category:" + this.getCatName()+"\n";
        String subCategory = "Sub Category:" + this.getSubCatName()+"\n";
        String size = "Size:" + this.getSize()+"\n";
        String manufacturer = "Manufacturer:" + this.getManuFactor()+"\n";
        return  category+subCategory+size+manufacturer;
    }
}
