package DAOLayer;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;


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
        add("orderAmount");
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
                "discount INTEGER NOT NULL," +
                "minimalAmount INTEGER NOT NULL," +
                "orderAmount INTEGER NOT NULL," +
                "isMinimal INTEGER NOT NULL," +
                "mySalePrice INTEGER," +
                " FOREIGN KEY (mySalePrice) REFERENCES salePrice(idSale))";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql)
        )
        {
            SQLStyle.executeUpdate();
        }
        catch (SQLException e) {
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
                " orderAmount, isMinimal, mySalePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql)
        )
        {
            SQLStyle.setInt(1, recordToAdd.get("catalogNumProduct").getAsInt());
            SQLStyle.setString(2, recordToAdd.get("ProdSize").getAsString());
            SQLStyle.setString(3, recordToAdd.get("catName").getAsString());
            SQLStyle.setString(4, recordToAdd.get("subCatName").getAsString());
//            At start total = "0,0"
            SQLStyle.setString(5, "0,0");
            SQLStyle.setString(6, recordToAdd.get("manuFactor").getAsString());
            SQLStyle.setDouble(7, recordToAdd.get("marketPriceConst").getAsDouble());
            SQLStyle.setDouble(8, recordToAdd.get("manuPriceConst").getAsDouble());
//            At start -> const == curr
            SQLStyle.setDouble(9, recordToAdd.get("manuPriceConst").getAsDouble());
            SQLStyle.setDouble(10, recordToAdd.get("marketPriceConst").getAsDouble());
            SQLStyle.setInt(11, recordToAdd.get("discount").getAsInt());
            SQLStyle.setInt(12, recordToAdd.get("minimalAmount").getAsInt());
            SQLStyle.setInt(13, recordToAdd.get("orderAmount").getAsInt());
//            At start -> is minimal == false
            SQLStyle.setBoolean(14, false);
//            At start -> there is no sale price
            SQLStyle.setInt(15, 0);
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
            throw new RuntimeException(e.getMessage());
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
    public ArrayList<String> findAllExistMainCategories(){
        ArrayList<String> res = new ArrayList<>();
        String sql = "SELECT catName FROM Product GROUP BY catName";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
                )
                {
                    ResultSet categories = SQLStyle.executeQuery();
                    while (categories.next()){
                        res.add(categories.getString("catName"));
                    }
                }

                catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
                }
        return res;
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
            throw new RuntimeException(e.getMessage());
        }
    }
    public void updateMarketPrice(int catNum, double newPrice){
        String sql = "UPDATE Product SET marketPriceCurr = ? WHERE catalogNumProduct = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
                )
                {
                    SQLStyle.setDouble(1, newPrice);
                    SQLStyle.setInt(2, catNum);
                    SQLStyle.executeUpdate();
                }

                catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateIsMinimal(int catNum, boolean bool){
            String sql = "UPDATE Product SET isMinimal = ? WHERE catalogNumProduct = ?";
            try (
                    Connection connection = Database.connect();
                    PreparedStatement SQLStyle = connection.prepareStatement(sql);
            )
            {
                SQLStyle.setBoolean(1, bool);
                SQLStyle.setInt(2, catNum);
                SQLStyle.executeUpdate();
            }

            catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
    }
    public void updatePlacedItemDB(int catNum, String WareOrStore){
        updateProdAmount(catNum, WareOrStore, 2);
    }

    public void dropTable(){
        String sql = "DROP TABLE IF EXISTS Product";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {
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
            throw new RuntimeException(e.getMessage());
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
        throw new RuntimeException(e.getMessage());

    }
        return SQLStyle;
    }

}
