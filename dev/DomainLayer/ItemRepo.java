package DomainLayer;

import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;

import static DomainLayer.ItemStatus.*;

public class ItemRepo implements IRepository{
    private ArrayList<Item> myItems;

    public ArrayList<Item> getMyItems() {
        return myItems;
    }

    //return item in inventory if he exists; otherwise return null.
    public Item find(int unique) {
        for (Item item : myItems) {
            if (item.getId() == unique)
                return item;
        }
        return null;
    }

    public ArrayList<Item> findAllByStatus(ItemStatus status) {
        ArrayList<Item> productByStatus = new ArrayList<>();
        for (Item item : myItems) {
            if (item.getStatus() == status)
                productByStatus.add(item);
        }
        return productByStatus;
    }

    public Item add(JsonObject newRec) {
        Tuple<String, Integer> place = createPlaceItem(newRec.get("place").getAsString());
        String expD = newRec.get("expirationDate").getAsString();
        LocalDate expDate = LocalDate.parse(expD);
        Item newItem = new Item(newRec.get("id").getAsInt(), expDate, place, newRec.get("catalogNumItem").getAsInt());
        myItems.add(newItem);
        return newItem;
    }

    //helper to add
    public Tuple<String, Integer> createPlaceItem(String placeStr) {
        String[] passShelf = placeStr.split(" ");
        String pass = passShelf[0];
        Integer shelf = Integer.parseInt(passShelf[1]);
        return new Tuple<>(pass, shelf);
    }

    public Item remove(int unique) {
        Item itemMove = find(unique);
        myItems.remove(itemMove);
        return itemMove;
    }

    //function update

    //report status: expired/damage/for_sale
    public Item reportStatus(ItemStatus status, int idToReport) {
        Item item = find(idToReport);
        item.setStatus(status);
        return item;
    }

    //move item from store to warehouse
    public Item moveToWhere(int idToMove, Tuple<String, Integer> newPlace, String toWhere) {
        Item itemMove = find(idToMove);
        itemMove.setPlace(newPlace);
        itemMove.setStoreOrWare(toWhere);
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


