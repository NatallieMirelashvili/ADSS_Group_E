package DomainLayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {
    private final Facade myFacade = new Facade();
    @Test
    public void ReportOnItem(){
        assertFalse(myFacade.searchItemService(6656));
        assertTrue(myFacade.searchItemService(1212));

    }
    @Test
    public void GenerateReport(){

    }
    @Test
    public void InventoryManagment(){

    }
    @Test
    public void NextDay(){

    }


    }