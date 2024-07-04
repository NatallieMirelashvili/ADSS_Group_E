package DomainLayer;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import static DomainLayer.ItemStatus.*;

public class ItemController {

    public ItemRepo myItemRepo = new ItemRepo();
    private int amountItems=0;
    public int getAmountItems() {
        return amountItems;
    }


    public Item searchItemCTR(int id){
        return myItemRepo.find(id);
    }
    public Item addItemCTR(JsonObject newRec){
        amountItems++;
        return myItemRepo.add(newRec);
    }
    public Item removeItemCTR(int idToReport){
        amountItems--;
        return myItemRepo.remove(idToReport);}
    public Item reportStatusCTR(ItemStatus status,int idToReport){
        return myItemRepo.reportStatus(status,idToReport);
    }
    public Item moveToWhere(int idToMove, Tuple<String, Integer> newPlace, String toWhere){
        return myItemRepo.move(idToMove,newPlace,toWhere);
    }
    public ArrayList<Item> checkAllItemsExpCTR() {
        ArrayList<Item> itemsForSale = myItemRepo.findAllByStatus(FOR_SALE);
        ArrayList<Item> itemsToUpdate = new ArrayList<>();
        for (Item item : itemsForSale) {
            if (item.getExpirationDate().isBefore(Facade.getCurrentDate())) {
                itemsToUpdate.add(item);
            }
        }
        return itemsToUpdate;
    }

    public ArrayList<Item> expReportCTR(){
        return myItemRepo.findAllByStatus(EXPIRED);
    }

    public ArrayList<Item> defectiveReportCTR(){
        return myItemRepo.findAllByStatus(DAMAGE);
    }


    public String HelperGenerateReportsItem(String name, ArrayList<Item> items) {
        int i=1;
        StringBuilder outputForController = new StringBuilder();
        String title = "Report " + name + " items:\n\n";
        outputForController.append(title);
        //all information
        for (Item item:items) {
            String Serial = (i)+".\n";
            String itemPrint = item.printItem();
            outputForController.append(Serial).append(itemPrint);
        }
        String summery = "Total " + name + " items: " + items.size() +"\n";
        outputForController.append(summery);
        return outputForController.toString();
    }
}
