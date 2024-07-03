package DataAccessLayer;
import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;

//            try (
//                Connection connection = Database.connect();
//                PreparedStatement SQLStyle = connection.prepareStatement(sql);
//                )
//                {}
//
//                catch (SQLException e) {
//                throw new RuntimeException("Natallie check yourself");
//                }
public class ProductAccessObj implements IDataAccessObj{
    final ArrayList<String> myField = new ArrayList<>() {{
        add("catalogNumProduct");
        add("ProdSize");
        add("catName");
        add("subCatName");
        add("total");
        add("manuFactor");
        add("marketPriceConst");
        add("manuPriceConst");
        add("manuPriceCurr");
        add("marketPriceCurr");
        add("discount");
        add("minimalAmount");
        add("OrderAmount");
        add("isMinimal");
        add("mySalePrice");
    }};
    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Product (" +
                "catalogNumProduct INTEGER PRIMARY KEY," +
                "ProdSize TEXT NOT NULL," +
                "catName TEXT NOT NULL," +
                "subCatName TEXT NOT NULL," +
                "total TEXT NOT NULL," +
                "manuFactor TEXT NOT NULL," +
                "marketPriceConst DOUBLE NOT NULL," +
                "manuPriceConst DOUBLE NOT NULL," +
                "manuPriceCurr DOUBLE NOT NULL," +
                "marketPriceCurr DOUBLE NOT NULL," +
                "discount DOUBLE NOT NULL," +
                "minimalAmount INTEGER NOT NULL," +
                "OrderAmount INTEGER NOT NULL," +
                "isMinimal INTEGER NOT NULL," +
                "mySalePrice INTEGER," +
                " FOREIGN KEY (mySalePrice) REFERENCES salePrice(idSale))";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql)
        )
        {
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public JsonObject search(int UniqueToSearch) {
        return IDataAccessObj.runSearch(UniqueToSearch, "Product", "catalogNumProduct", myField);

    }
// total = <15,20> == "15,20" == "Store,Warehouse"
    @Override
    public void insert(JsonObject recordToAdd) {
        String sql = "INSERT INTO Product (catalogNumProduct, ProdSize, catName, subCatName, total, manuFactor, " +
                "marketPriceConst, manuPriceConst, manuPriceCurr, marketPriceCurr, discount, minimalAmount," +
                " OrderAmount, isMinimal, mySalePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql)
        )
        {
            SQLStyle.setInt(1, recordToAdd.get("catalogNumProduct").getAsInt());
            SQLStyle.setString(2, recordToAdd.get("ProdSize").getAsString());
            SQLStyle.setString(3, recordToAdd.get("catName").getAsString());
            SQLStyle.setString(4, recordToAdd.get("subCatName").getAsString());
            SQLStyle.setString(5, recordToAdd.get("total").getAsString());
            SQLStyle.setString(6, recordToAdd.get("manuFactor").getAsString());
            SQLStyle.setDouble(7, recordToAdd.get("marketPriceConst").getAsDouble());
            SQLStyle.setDouble(8, recordToAdd.get("manuPriceConst").getAsDouble());
            SQLStyle.setDouble(9, recordToAdd.get("manuPriceCurr").getAsDouble());
            SQLStyle.setDouble(10, recordToAdd.get("marketPriceCurr").getAsDouble());
            SQLStyle.setDouble(11, recordToAdd.get("discount").getAsDouble());
            SQLStyle.setInt(12, recordToAdd.get("minimalAmount").getAsInt());
            SQLStyle.setInt(13, recordToAdd.get("OrderAmount").getAsInt());
            SQLStyle.setBoolean(14, recordToAdd.get("isMinimal").getAsBoolean());
            SQLStyle.setString(15, recordToAdd.get("mySalePrice").getAsString());
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void remove(int UniqueToRemove) {
        String sql = "DELETE FROM Product WHERE catalogNumProduct = ?";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {

            SQLStyle.setInt(1, UniqueToRemove);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }


    public void removeItemDB(int catNum, String fromWhere){
        updateProdAmount(catNum, fromWhere, 0);

    }
    public void addItemDB(int catNum, String fromWhere){
        updateProdAmount(catNum, fromWhere, 1);
    }

    public ArrayList<JsonObject> findAllProductsByMainCatDB(String mainCat, ArrayList<Integer> alreadyHave){
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size(), "catalogNumProduct", "Product");
        String sql = "SELECT * FROM Product WHERE catName = ? AND catalogNumProduct NOT IN (" + subQuery + ")";
        String[] myCat = new String[]{mainCat};
        return findALLHelper(sql, alreadyHave, myCat);
    }
    public ArrayList<JsonObject> findAllProductsBySubCatDB(String mainCat, String subCat, ArrayList<Integer> alreadyHave){
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size(), "catalogNumProduct", "Product");
        String sql = "SELECT * FROM Product WHERE catName = ? AND subCatName = ? AND catalogNumProduct NOT IN (" + subQuery + ")";
        String[] myCat = new String[]{mainCat, subCat};
        return findALLHelper(sql, alreadyHave, myCat);
    }
    public ArrayList<JsonObject> findAllProductsBySizeDB(String mainCat, String subCat, String size, ArrayList<Integer> alreadyHave){
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size(), "catalogNumProduct", "Product");
        String sql = "SELECT * FROM Product WHERE catName = ? AND subCatName = ? AND ProdSize = ? AND catalogNumProduct NOT IN (" + subQuery + ")";
        String[] myCat = new String[]{mainCat, subCat, size};
        return findALLHelper(sql, alreadyHave, myCat);
    }
    public void updateSaleDB(ArrayList<Integer> catalogNumToUpdate, int idOfNewSale, int ratio){
        String subQuery = IDataAccessObj.innerQuery(catalogNumToUpdate.size(), "catalogNumProduct", "Product");
        String javaStyle = "UPDATE Product SET marketPriceCurr = marketPriceConst * (1 - (? / 100.0)), mySalePrice = ?  " +
                            "WHERE catalogNumProduct IN (" + subQuery + ")";
        Integer[] constParameter = new Integer[]{ratio, idOfNewSale};
        try (Connection connection = Database.connect())
                {

                    runComplexQuery(connection,javaStyle,catalogNumToUpdate,constParameter).executeUpdate();
                }

        catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }
    public void updateDisDB(ArrayList<Integer> catalogNumbersToUpdate, int ratio){
        String subQuery = IDataAccessObj.innerQuery(catalogNumbersToUpdate.size(), "catalogNumProduct","Product");
        String javaStyle = "UPDATE Product SET manuPriceCurr = manuPriceConst * (1 - (? / 100.0)) WHERE catalogNumProduct IN (" + subQuery + ")";
        Integer[] constParameter = new Integer[]{ratio};
        try (Connection connection = Database.connect())
        {
            runComplexQuery(connection,javaStyle,catalogNumbersToUpdate,constParameter).executeUpdate();

        }

        catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }
    public void updatePlacedItemDB(int catNum, String WareOrStore){
        updateProdAmount(catNum, WareOrStore, 2);
    }
    public void changeStatusMinimal(int catNum, boolean newStat){
        String sql = "UPDATE Product SET isMinimal = ? WHERE catalogNumProduct = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
                )
                {
                    SQLStyle.setBoolean(1, newStat);
                    SQLStyle.setInt(2, catNum);
                    SQLStyle.executeUpdate();
                }
                catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
                }
    }



// *****Help Functions*****



//    what = 0 -> remove
//    what = 1 -> add
    private void updateProdAmount(int catNum, String where, int what){
        JsonObject prodToUpdate = search(catNum);
        String oldTotal = prodToUpdate.get("total").getAsString();
        if (oldTotal.equals(""))
            {
                return;
            }
        String[] totalSTR = oldTotal.split(",");
        int[] totalInt = new int[]{Integer.parseInt(totalSTR[0]),Integer.parseInt(totalSTR[1])};
        String newTotal;
        switch (where) {
            case "Warehouse" -> {
                if (what == 0)
                    totalInt[1] = totalInt[1] - 1;
                else if (what == 1) {
                    totalInt[1] = totalInt[1] + 1;

                } else {
                    totalInt[1] = totalInt[1] + 1;
                    totalInt[0] = totalInt[0] - 1;
                }
            }
            case "Store" -> {
                if (what == 0) {
                    totalInt[0] = totalInt[0] - 1;
                } else if (what == 1) {
                    totalInt[0] = totalInt[0] + 1;
                } else {
                    totalInt[0] = totalInt[0] + 1;
                    totalInt[1] = totalInt[1] - 1;
                }
            }
            default -> newTotal = "0,0";
        }
        newTotal = "" + totalInt[0] + "," + totalInt[1];
        String querySTR = "UPDATE Product SET total = ? WHERE catalogNumProduct = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(querySTR);
        ) {
            SQLStyle.setString(1, newTotal);
            SQLStyle.setInt(2, catNum);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private ArrayList<JsonObject> findALLHelper(String ask,ArrayList<Integer> alreadyHave, String[] categories){
        ArrayList<JsonObject> relevanceRecords = new ArrayList<>();
        try (Connection connection= Database.connect())
        {
            ResultSet matchedRecords = runComplexQuery(connection, ask, alreadyHave, categories).executeQuery();
            while (matchedRecords.next()) {
                JsonObject defectiveRec = IDataAccessObj.parseRecToJson(matchedRecords, myField);
                relevanceRecords.add(defectiveRec);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
        return relevanceRecords;
    }


    private <T> PreparedStatement runComplexQuery(Connection connection, String ask, ArrayList<Integer> fetchedProducts,T[] fieldsToMatch){
    PreparedStatement SQLStyle;
    try{
        SQLStyle = connection.prepareStatement(ask);
        for (int i = 0; i < fieldsToMatch.length; i++) {
            if (fieldsToMatch[i] instanceof Integer) {
                SQLStyle.setInt(i + 1, (Integer) fieldsToMatch[i]);
            }
            else if (fieldsToMatch[i] instanceof Double) {
                SQLStyle.setDouble(i + 1, (Double) fieldsToMatch[i]);
            }
            else if (fieldsToMatch[i] instanceof Date) {
                SQLStyle.setDate(i + 1, (Date) fieldsToMatch[i]);
            }
            else
                {SQLStyle.setString(i + 1, (String) fieldsToMatch[i]);}

            }
        for (int i = 0; i < fetchedProducts.size(); i++){
            SQLStyle.setInt(i + fieldsToMatch.length + 1, fetchedProducts.get(i));
        }
    }
    catch (SQLException e){
        throw new RuntimeException("Natallie check yourself", e);

    }
        return SQLStyle;
    }


    public static void main(String[] args) {
        ProductAccessObj dao = new ProductAccessObj();
        JsonObject newRec1 = new JsonObject();
        newRec1.addProperty("catalogNumProduct", 1212);
        newRec1.addProperty("ProdSize", "7 litters");
        newRec1.addProperty("catName", "diary");
        newRec1.addProperty("subCatName", "milk");
        newRec1.addProperty("total", "5,8");
        newRec1.addProperty("manuFactor", "tara");
        newRec1.addProperty("marketPriceConst", 444);
        newRec1.addProperty("manuPriceConst", 8765);
        newRec1.addProperty("manuPriceCurr", 98);
        newRec1.addProperty("marketPriceCurr", 444);
        newRec1.addProperty("discount", 8);
        newRec1.addProperty("minimalAmount", 99);
        newRec1.addProperty("OrderAmount", 6543);
        newRec1.addProperty("isMinimal", false);
        newRec1.addProperty("mySalePrice", 123467);
        JsonObject newRec2 = new JsonObject();
        newRec2.addProperty("catalogNumProduct", 1414);
        newRec2.addProperty("ProdSize", "7 litters");
        newRec2.addProperty("catName", "diary");
        newRec2.addProperty("subCatName", "milk");
        newRec2.addProperty("total", "5,8");
        newRec2.addProperty("manuFactor", "tara");
        newRec2.addProperty("marketPriceConst", 500);
        newRec2.addProperty("manuPriceConst", 8765);
        newRec2.addProperty("manuPriceCurr", 98);
        newRec2.addProperty("marketPriceCurr", 500);
        newRec2.addProperty("discount", 8);
        newRec2.addProperty("minimalAmount", 99);
        newRec2.addProperty("OrderAmount", 6543);
        newRec2.addProperty("isMinimal", false);
        newRec2.addProperty("mySalePrice", 123467);
        JsonObject newRec3 = new JsonObject();
        newRec3.addProperty("catalogNumProduct", 1515);
        newRec3.addProperty("ProdSize", "6 litters");
        newRec3.addProperty("catName", "diary");
        newRec3.addProperty("subCatName", "Kotech");
        newRec3.addProperty("total", "5,8");
        newRec3.addProperty("manuFactor", "tara");
        newRec3.addProperty("marketPriceConst", 600);
        newRec3.addProperty("manuPriceConst", 8765);
        newRec3.addProperty("manuPriceCurr", 98);
        newRec3.addProperty("marketPriceCurr", 600);
        newRec3.addProperty("discount", 8);
        newRec3.addProperty("minimalAmount", 99);
        newRec3.addProperty("OrderAmount", 6543);
        newRec3.addProperty("isMinimal", false);
        newRec3.addProperty("mySalePrice", 123467);
        dao.remove(1212);
        dao.remove(1414);
        dao.remove(1515);
        dao.insert(newRec1);
        dao.insert(newRec2);
        dao.insert(newRec3);

//        ArrayList<Integer> toUpdate = new ArrayList<>(3);
//        toUpdate.add(1212);
//        toUpdate.add(1414);
//        toUpdate.add(1515);
        dao.changeStatusMinimal(1515, true);
        JsonObject found = dao.search(1515);
        System.out.println(found);

    }
}
