package PresentationLayer;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
//    Flag
    private static int initProgram = 0;
//    help functions
    private static boolean isInit(){
        if (initProgram == 0){
            System.out.println("Please choose option 1 first");
            return false;
        }
        return true;
    }

//    Read file help functions:
    private static JsonObject CreateJasonFromFile(int iteration, String[] memberLst,String[] valuesOfMembers){
        JsonObject myJson = new JsonObject();
        for (int i = 0; i <= iteration; i++){
            String member = memberLst[i];
            String value = valuesOfMembers[0];
            myJson.addProperty(member, value);
        }
        return myJson;
    }
// ***Menu Functions***
    public static void initProg(){
        boolean bool = isInit();
        if (bool){
            System.out.println("System initialed");
        }
        System.out.println("Please enter the file path you want to fetch");
        Scanner scanner = new Scanner(System.in);
        String fileP = scanner.nextLine();
        BufferedReader buffer;
        String line;
        try {
            buffer = new BufferedReader((new FileReader(fileP)));
            line = buffer.readLine();
            String[] membersLst = line.split(",");
            line =  buffer.readLine();
            while (line != null){
                String[] membersValueByOrder = line.split(",");
                JsonObject record = CreateJasonFromFile(membersLst.length, membersLst, membersValueByOrder);
//                turn record to object with switch case: product or item
                line = buffer.readLine();
            }
        } catch (Exception e) {
            System.out.println("File Not Found please try again");}

        initProgram = 1;
    }
//    move all read file to another class and add reaction of stock

    public static void GenerateReports(){
        boolean bool = isInit();
        if (!bool){return;}
        ProdReport.runMenu();
    }
    public static void inventory(){
        boolean bool = isInit();
        if (!bool){return;}
        Management.runMenu();
    }
    public static void ThrowItem(){
        boolean bool = isInit();
        if (!bool){return;}
        Report.runMenu();
    }
    public static void managerDetails(){
        boolean bool = isInit();
        if (!bool){return;}
        System.out.println("Warehouse manager: Naveh Gershoni" +
                "email: ng@mailto.yossi" +
                "phone number: 0525381648");
    }
    public static void exit(){
        boolean bool = isInit();
        if (!bool){return;}
        System.out.println("GoodBye...");
    }
    public static String printMenu() {
        return "What would you like to do today?" +
                "1. fetch program" +
                "2. Generate reports" +
                "3. Decommissioning an items" +
                "4. Preform an inventory actions" +
                "5. Show warehouse manager's contact details" +
                "6. Exit program";
    }

    public static void runMenu(){
        int choice = 0;
        while (choice!=5){
            choice = GetChoice();
        }
    }
    public static int GetChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.print(printMenu());
        int userInput = scanner.nextInt();
        switch (userInput) {
            case 1 -> initProg();
            case 2 -> GenerateReports();
            case 3 -> ThrowItem();
            case 4 -> inventory();
            case 5 -> managerDetails();
            case 6 -> exit();
            default -> {
                //there is outside while or not? that runs the main menu
                System.out.println("Please choose valid number between 1-3");

            }
        }
        return userInput;
    }

    public static void main(String[] args){
        runMenu();

    }
}
