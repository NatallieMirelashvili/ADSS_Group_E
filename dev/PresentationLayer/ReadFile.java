package PresentationLayer;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Paths;

import ServiceLayer.ProductController;

public class ReadFile {
    private static JsonObject CreateJasonFromFile(int iteration, String[] memberLst,String[] valuesOfMembers){
        JsonObject myJson = new JsonObject();
        for (int i = 0; i < iteration; i++){
            String member = memberLst[i];
            String value = valuesOfMembers[i];
            myJson.addProperty(member, value);
        }
        return myJson;
    }
    private static String getPathToFiles(String relevanceFile){
        String currDir = System.getProperty("user.dir");
        File currDirF = new File(currDir);
        return Paths.get(currDirF.getParent(), "dev","DataFiles",relevanceFile).toString();
    }
    public static boolean readProducts(){
        String fileToRead = getPathToFiles("ProductData.csv");
        return readFileByType(1, "products line in market", fileToRead);
    }
    public static boolean readItems(){
        String fileToRead = getPathToFiles("ItemsData.csv");
        return readFileByType(2, "items for sell in market", fileToRead);
    }
    public static boolean readFileByType(int initCase, String msg, String filePath) {
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
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found please try again\n");
            return false;
        }
        catch (IOException e){
            System.out.println("Some IO Exception has occurred\n");
            return false;
        }
    }
}
