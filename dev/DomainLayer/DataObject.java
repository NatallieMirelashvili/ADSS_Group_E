package DomainLayer;

import java.util.ArrayList;

public class DataObject {
    private static Inventory inventObj = new Inventory();
    private static AReport defObj=new Defective();
    private static AReport expObj=new Expired();

//    *** Getters ***

    public static Inventory getInventObj() {
        return inventObj;
    }

    public static AReport getDefObj() {
        return defObj;
    }

    public static AReport getExpObj() {
        return expObj;
    }


}
