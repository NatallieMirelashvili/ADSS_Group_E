package DomainLayer;

import DataAccessLayer.ItemAccessObj;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;

import static DomainLayer.ItemStatus.*;

public class ItemRepo implements IRepository<Item>{
    private ArrayList<Item> myItems = new ArrayList<>();
    private final ItemAccessObj myDAOItem = new ItemAccessObj();

    public ArrayList<Item> getMyItems() {
        return myItems;
    }

    //return item in inventory if he exists; otherwise return null.
    public Item find(int unique) {
        for (Item item : myItems) {
            if (item.getId() == unique)
                return item;
        }
        //case that item not in repo, search on DAO
        JsonObject itemInDAO = myDAOItem.search(unique);
        if (myDAOItem.search(unique)!=null) {
            return addToRepoOnly(itemInDAO, true);
        }
        return null;
    }

    public ArrayList<Item> findAllByStatus(ItemStatus status) {
        ArrayList<Item> itemByStatus = new ArrayList<>();
        for (Item item : myItems) {
            if (item.getStatus() == status)
                itemByStatus.add(item);
        }
        //search also in DAO
        ArrayList<Integer> alreadyHave = new ArrayList<>();
        ArrayList<JsonObject> itemToAdd=new ArrayList<>();
        for (Item item:itemByStatus)
            alreadyHave.add(item.getId());
        if (status==EXPIRED) {
            itemToAdd = myDAOItem.findAllExpDB(alreadyHave);
        }
        else if (status==DAMAGE) {
            itemToAdd = myDAOItem.findAllDefectiveDB(alreadyHave);
        }
        for (JsonObject item:itemToAdd) {
            Item itemInStatus = addToRepoOnly(item, true);
            itemByStatus.add(itemInStatus);
        }
        return itemByStatus;
    }

    public Item add(JsonObject newRec) {
//        Bran new item
        Item newItem = addToRepoOnly(newRec, false);
        myDAOItem.insert(newRec);
        return newItem;
     }

    //helper to add
    private Item addToRepoOnly(JsonObject newRec, boolean fromDB) {
        Tuple<String, Integer> place = Tuple.createPlaceItem(newRec.get("place").getAsString());
        String expD = newRec.get("expirationDate").getAsString();
        LocalDate expDate = LocalDate.parse(expD);
        Item newItem = new Item(newRec.get("id").getAsInt(), expDate, place, newRec.get("catalogNumItem").getAsInt());
        if(fromDB){
//            set status
            switch (newRec.get("status").getAsInt()) {
                case 0 -> newItem.setStatus(DAMAGE);
                case 1 -> newItem.setStatus(EXPIRED);
            }
            Product itemProd = ProductController.searchProdByCatNumCTR(newRec.get("catalogNumItem").getAsInt());
            newItem.setMyProduct(itemProd);

        }
        myItems.add(newItem);
        return newItem;
    }

    public Item remove(int unique) {
        Item itemMove = find(unique);
        myItems.remove(itemMove);
        myDAOItem.remove(unique);
        return itemMove;
    }

    //function update

    //report status: expired/damage/for_sale
    public Item reportStatus(ItemStatus status, int idToReport) {
        Item item = find(idToReport);
        item.setStatus(status);
        if (status==EXPIRED)
            myDAOItem.reportExpDB(idToReport);
        if (status==DAMAGE)
            myDAOItem.reportDamageDB(idToReport);
        if (status==FOR_SALE)
            myDAOItem.reportForSaleDB(idToReport);
        return item;
    }

    //move item from store to warehouse
    public Item move(int idToMove, Tuple<String, Integer> newPlace, String toWhere) {
        Item itemMove = find(idToMove);
        itemMove.setPlace(newPlace);
        itemMove.setStoreOrWare(toWhere);
        if (toWhere.equals("Store")){
            myDAOItem.moveWareStoreDB(idToMove,newPlace);
        }
        if (toWhere.equals("Warehouse")){
            myDAOItem.moveStoreWareDB(idToMove,newPlace);
        }
        return itemMove;
    }



}


//        Tuple<String,Item> itemToRemove= new Tuple<>();
//        Item item = FindItemInInvent(IDItem);
//        if(item!=null){
//            Product product = item.getMyProduct();
//            String detailsItem = product.printProduct();
//            itemToRemove.setVal1(detailsItem);
//            itemToRemove.setVal2(item);
//            product.removeItemToLst(item);
//        }
//        return itemToRemove;


