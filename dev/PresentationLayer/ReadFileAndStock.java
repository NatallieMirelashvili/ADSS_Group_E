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
            String value = valuesOfMembers[0];
            myJson.addProperty(member, value);
        }
        return myJson;
    }
    public static void readFileByType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Yoy received two exel files: one for creating products and the other for create specific items." +
                "please chose one of the two options:" +
                "1. Read products from file." +
                "2. Read items from file");
        int choose = scanner.nextInt();
        System.out.println("Please enter the file path of the data type records you chosen:");
        String fileP = scanner.nextLine();
        BufferedReader buffer;
        String line;
        try {
//            Read object fields:

            buffer = new BufferedReader((new FileReader(fileP)));
            line = buffer.readLine();
            String[] membersLst = line.split(",");

//            Read object field's data:

            line = buffer.readLine();
            while (line != null) {
                String[] membersValueByOrder = line.split(",");
                JsonObject record = CreateJasonFromFile(membersLst.length, membersLst, membersValueByOrder);

//                create needed object by Json using controller:
                switch (choose){
                    case 1 -> ProductController.createNewProd(record);
                    case 2 -> ProductController.createNewItem(record);
                }
                line = buffer.readLine();
            }
        } catch (Exception e) {
            System.out.println("File Not Found please try again");
        }
    }

//    After creating products and items we want to assemble all data in Stock classes:

    public static void createStock() {

    }

}
