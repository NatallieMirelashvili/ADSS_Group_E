package PresentationLayer;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import ServiceLayer.ProductController;

public class ReadFileAndStock {
    private static JsonObject CreateJasonFromFile(int iteration, String[] memberLst,String[] valuesOfMembers){
        JsonObject myJson = new JsonObject();
        for (int i = 0; i <= iteration; i++){
            String member = memberLst[i];
            String value = valuesOfMembers[i];
            myJson.addProperty(member, value);
        }
        return myJson;
    }
    public static void readProducts(){
        readFileByType(1, "products line in market", "ProductData.csv");
    }
    public static void readItems(){
        readFileByType(2, "items for sell in market", "ItemsData.csv");
    }
    public static void readFileByType(int initCase, String msg, String filePath) {
        System.out.println("Loading "+ msg + "...\n");
        BufferedReader buffer;
        String line;
        try {
//            Read object fields:

            buffer = new BufferedReader((new FileReader(filePath)));
            line = buffer.readLine();
            String[] membersLst = line.split(",");

//            Read object field's data:

            line = buffer.readLine();
            while (line != null) {
                String[] membersValueByOrder = line.split(",");
                JsonObject record = CreateJasonFromFile(membersLst.length, membersLst, membersValueByOrder);

//                create needed object by Json using controller:
                switch (initCase){
                    case 1 -> ProductController.createNewProd(record);
                    case 2 -> ProductController.createNewItem(record);
                }
                line = buffer.readLine();
            }
        } catch (Exception e) {
            System.out.println("File Not Found please try again\n");
        }
    }

//    After creating products and items we want to assemble all data in Stock classes:

}
