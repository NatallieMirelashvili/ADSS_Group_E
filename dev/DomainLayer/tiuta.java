package DomainLayer;


public class tiuta {};


 //find if item exist in inventory
//    static public boolean ItemExist(int IDItem) {
//        if (FindItemInInvent(IDItem)!=null)
//            return true;
//        return false;


//    static public Item FindItemInInvent(int IDItem) {
//        for (int i = 0; i < amountProducts; i++) {
//            Item item = runAllProducts().get(i).FindItemInPro(IDItem);
//            if (item != null) {
//                return item;
//            }
//        }
//        return null;
//    }



//    //remove item from inventory when he is sale
//    static public boolean ItemSells(int IDItem){
//        Item item = FindItemInInvent(IDItem);
//        if (item != null) {
//            Product product = item.getMyProduct();
//            product.removeItemToLst(item);
//            return true;
//        }
//        return false;
//    }

//    //remove item from inventory when he is defective and move to list of defectives with details important
//    static public boolean ItemDefective(int IDItem){
//        Tuple<String,Item> itemDefective = removeItemCTR(IDItem);
//        if(itemDefective.getVal2() == null)
//            return false;
//        getDefObj().getItems().add(itemDefective);
//        return true;
//    }
//
//    //remove item from inventory when he is expired and move to list of expired with details important
//    static public boolean ItemExpired(int IDItem){
//        Tuple<String,Item> itemExpired = removeItemCTR(IDItem);
//        if(itemExpired.getVal2() == null)
//            return false;
//        getExpObj().getItems().add(itemExpired);
//        return true;
//    }






//
//    //remove all items that expired to list of items that expired according to date
//    static public void checkAllItemsExp() {
//        int loc=0;
//        ArrayList<Product> products = runAllProducts();
//        for (Product product : products) {
//            int j=product.getItems().size();
//            for (int i=0; i<j; i++) {
//                if (product.getItems().get(loc).getExpirationDate().isBefore(Facade.getCurrentDate())) {
//                    ItemExpired(product.getItems().get(loc).getId());
//                }
//                else {
//                    loc++;
//                }
//            }
//            loc=0;
//        }

//    }
//
//    //update sale price according to date
//    static public void checkAllProdSale() {
//        ArrayList<Product> products = runAllProducts();
//        for (Product product : products) {
//            if(product.getMySalePrice()!=null){
//            if (product.getMySalePrice().getEndSale().isBefore(Facade.getCurrentDate())) {
//                product.setMarketPriceCurr(product.getMarketPriceConst());
//                }
//            if (product.getMySalePrice().getStartSale().isEqual(Facade.getCurrentDate())) {
//                product.setMarketPriceCurr(product.getMarketPriceConst()-
//                        (product.getMarketPriceConst()* product.getMySalePrice().getDiscountRatio()/100));
//            }
//            }
//        }
//    }


///**
// * Name: createPlaceItem - a method which turns a string which presents item's place by the instruction.
// * For example item 1234 is in the warehouse in aile A shelf 6, so the method gets the "A 6" input and returns the
// * proper tuple <A, 6>
// * Args: String placeStr
// * Returns: Tuple<String, Integer>
// * */
//
//
///**
// * Name: createNewItem - a method which turns a Json object which presents item's characters and adds the new item
// * to the inventory. Using Item's class methods.
// * Args: JsonObject record
// * Returns: None
// * */
//
//
///**
// * Name: createNewProd - a method which turns a Json object which presents product's characters and adds the new product
// * to the inventory. Using Product's class constructor and StockController methods.
// * Args: JsonObject record
// * Returns: None
// * */


