package Domain;
import Controller.controller;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test_Marging {

    ProductController CTRTest = new ProductController();

    @Test
    public void MargeTest() throws SQLException {

        String myManufacturer = "1";
        ArrayList<Product> proInStorage;
        proInStorage=CTRTest.RequestDeliveryCTR(myManufacturer);
        ArrayList<Integer> proToCheck = new ArrayList<>();
        for (Product product: proInStorage) {
            proToCheck.add(product.getCatalogNumProduct());
        }
        ArrayList<String> orders = controller.get_pending_orders();
        ArrayList<Integer> Check = new ArrayList<>();
        for (String order: orders) {
            String[] items = order.split("items:")[1].split(",");
            for (String item: items) {
                if (item.contains("itemId=")) {
                    String itemIdStr = item.split("itemId='")[1].split("'")[0];
                    int itemId = Integer.parseInt(itemIdStr);
                    Check.add(itemId);
                }
            }
        }
        assert Check.containsAll(proToCheck);
        //proToCheck
    }
}
