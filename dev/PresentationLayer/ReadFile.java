package PresentationLayer;
import com.google.gson.JsonObject;
import java.io.*;
import java.nio.file.Paths;
import static PresentationLayer.MainMenu.myFacade;



public class ReadFile {
//    Help function which fit field to its proper value which got from file to Json object.
    private static JsonObject CreateJasonFromFile(int iteration, String[] memberLst,String[] valuesOfMembers){
        JsonObject myJson = new JsonObject();
        for (int i = 0; i < iteration; i++){
            String member = memberLst[i];
            String value = valuesOfMembers[i];
            myJson.addProperty(member, value);
        }
        return myJson;
    }
//    Help function which combine the proper file to the csv files which provided from the developers.
    private static String getPathToFiles(String relevanceFile){
        String currDir = System.getProperty("user.dir");
        File currDirF = new File(currDir);
        return Paths.get(currDirF.getParent(), "dev","DataFiles",relevanceFile).toString();
    }

      /***
     *Name:readProducts - A method which starts the reading of products from the file.
     * Args: None
     * Returns: boolean - file scanned successfully?
     */
    public static boolean readProducts(){
        String fileToRead = getPathToFiles("ProductData.csv");
        return readFileByType(1, "products line in market", fileToRead);
    }

    /***
     *Name:readItems - A method which starts the reading of items from the file.
     * Args: None
     * Returns: boolean - file scanned successfully?
     */
    public static boolean readItems(){
        String fileToRead = getPathToFiles("ItemsData.csv");
        return readFileByType(2, "items for sell in market", fileToRead);
    }

    /***
     *Name:readFileByType - A method which reads files filled with data on products or items and create a Product
     * or Item java object.
     * Args: int initCase: if you want to the ProductsData file input will be 1, else (ItemsData) input will be 2.
     *       String msg: for let the user know from which file the function reads.
     *       String filePath: the file path.
     * Returns: boolean - file scanned successfully?
     */
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
                    case 1 -> myFacade.addProductService(record);
                    case 2 -> myFacade.addItemService(record);
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
