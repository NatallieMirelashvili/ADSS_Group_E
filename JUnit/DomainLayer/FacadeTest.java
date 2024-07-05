package DomainLayer;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Tests all system use cases!
class FacadeTest {
    private final Facade myFacade = new Facade();
    private final ProductRepo forDBChecks = new ProductRepo();
    JsonObject js = new JsonObject();
    JsonObject item = new JsonObject();


    @Test
    public void searchItem(){
//        search item test:
        assertFalse(myFacade.searchItemService(1010));
        assertTrue(myFacade.searchItemService(1234));
    }
    @Test
    public void searchProd(){
//        search item test:
        assertFalse(myFacade.searchItemService(5000));
        assertTrue(myFacade.searchItemService(1010));
    }
    @Test
     public void addProd(){
        js.addProperty("catalogNumProduct", 4040);
        js.addProperty("ProdSize", "500 gram");
        js.addProperty("catName", "Electric");
        js.addProperty("subCatName", "IPhone");
        js.addProperty("manuFactor", "Apple");
        js.addProperty("marketPriceConst", 5000);
        js.addProperty("manuPriceConst", 4000);
        js.addProperty("discount", 0);
        js.addProperty("minimalAmount", 50);
        js.addProperty("orderAmount", 50);
        myFacade.addProductService(js);
//        search product:
        assertTrue(myFacade.searchProdByCatNumService(4040));
    }

    @Test
    public void addItem(){
//        Trying to add not exist item:
        item.addProperty("id", 123456);
        item.addProperty("expirationDate", "2024-09-09");
        item.addProperty("place", "Electric,6");
        item.addProperty("catalogNumItem", 4040);
        myFacade.addItemService(item);
        assertTrue(myFacade.searchItemService(12345));
    }
    @Test
    public void reportDamage(){
        myFacade.reportDamageService(12345);
        assertEquals(0, forDBChecks.find(4040).getStoreAmount());
    }
    @Test
    public void reportExpired(){
        myFacade.reportExpService(7070);
        assertEquals(0, forDBChecks.find(4040).getTotalAmount());
    }
    @Test
    public void retrieveExpired(){
        myFacade.reportForSellService(7070);
        assertEquals(1, forDBChecks.find(4040).getTotalAmount());
    }
    @Test
    public void moveItemToWare(){
//        item 12345 is in the STORE
        myFacade.moveToWareService(12345, new Tuple<>("A", 8));
//        now 4040 has 0 item in Store
        assertEquals(0, forDBChecks.find(4040).getStoreAmount());
    }

    @Test
    public void updateSale(){
        String from = "2024-07-04";
        String to = "2024-07-07";
        myFacade.updateSaleService("Electric", "0", "0", LocalDate.parse(from), LocalDate.parse(to), 10);
//        current price updated:
        assertEquals(4500.0, forDBChecks.find(4040).getMarketPriceCurr());
//        Link product -> saleItem created:
        assertEquals(1, forDBChecks.find(4040).getMySalePrice().getId());

    }
    @Test
    public void updateDis(){
        myFacade.updateDiscountService("Electric", "0", "0",10,"Apple");
        assertEquals(3600.0, forDBChecks.find(4040).getManuPriceCurr());
    }

}