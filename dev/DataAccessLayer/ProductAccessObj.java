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
                "isMininal TEXT NOT NULL," +
                " FOREIGN KEY (mySalePrice) REFERENCES salePrice(idSale))";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql)
        )
        {
            SQLStyle.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }


    @Override
    public JsonObject search(int UniqueToSearch) {
        String sql = "SELECT * FROM Product WHERE catalogNumProduct = ?";
        JsonObject result = new JsonObject();

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {

            SQLStyle.setInt(1, UniqueToSearch);
            ResultSet rs = SQLStyle.executeQuery();

            if (rs.next()) {
                return IDataAccessObj.parseRecToJson(rs, myField);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }

        return result;
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
            SQLStyle.setString(14, recordToAdd.get("isMinimal").getAsString());
            SQLStyle.setString(15, recordToAdd.get("mySalePrice").getAsString());
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
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
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size());
        String sql = "SELECT * FROM Product WHERE catName = ? AND catalogNumProduct NOT IN (" + subQuery + ")";
        String[] myCat = new String[]{mainCat};
        return findALLHelper(sql, alreadyHave, myCat);
    }
    public ArrayList<JsonObject> findAllProductsBySubCatDB(String mainCat, String subCat, ArrayList<Integer> alreadyHave){
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size());
        String sql = "SELECT * FROM Product WHERE catName = ? AND subCatName = ? AND catalogNumProduct NOT IN (" + subQuery + ")";
        String[] myCat = new String[]{mainCat, subCat};
        return findALLHelper(sql, alreadyHave, myCat);
    }
    public ArrayList<JsonObject> findAllProductsBySizeDB(String mainCat, String subCat, String size, ArrayList<Integer> alreadyHave){
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size());
        String sql = "SELECT * FROM Product WHERE catName = ? AND subCatName = ? AND ProdSize = ? AND catalogNumProduct NOT IN (" + subQuery + ")";
        String[] myCat = new String[]{mainCat, subCat, size};
        return findALLHelper(sql, alreadyHave, myCat);
    }
    public void updateSaleDB(ArrayList<Integer> catalogNumToUpdate, int idOfNewSale, int ratio){
        String subQuery = IDataAccessObj.innerQuery(catalogNumToUpdate.size());
        String javaStyle = "UPDATE Product SET marketPriceCurr = marketPriceConst * ?, mySalePrice = ?  " +
                            "WHERE catalogNumProduct IN (" + subQuery + ")";
        Integer[] constParameter = new Integer[]{ratio, idOfNewSale};
        try (Connection connection = Database.connect())
                {

                    runComplexQuery(connection,javaStyle,catalogNumToUpdate,constParameter);
                }

        catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }
    public void updateDisDB(ArrayList<Integer> catalogNumbersToUpdate, int ratio){
        String subQuery = IDataAccessObj.innerQuery(catalogNumbersToUpdate.size());
        String javaStyle = "UPDATE Product SET manuPriceCurr = manuPriceConst * ? WHERE catalogNumProduct IN (" + subQuery + ")";
        Integer[] constParameter = new Integer[]{ratio};
        try (Connection connection = Database.connect())
        {
            runComplexQuery(connection,javaStyle,catalogNumbersToUpdate,constParameter);
        }

        catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }
    public void updatePlacedItemDB(int catNum, String WareOrStore){
        updateProdAmount(catNum, WareOrStore, 2);
    }
    public void changeStatusMinimal(int catNum, boolean newStat){
        String sql = "UPDATE Product SET isMinimal = ?, WHERE catalogNumProduct = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
                )
                {
                    SQLStyle.setBoolean(1, newStat);
                    SQLStyle.setInt(1, catNum);
                    SQLStyle.executeUpdate();
                }
                catch (SQLException e) {
                throw new RuntimeException("Natallie check yourself");
                }
    }

// *****Help Functions*****
//    what = 0 -> remove
//    what = 1 -> add
    private void updateProdAmount(int catNum, String where, int what){
        String[] totalSTR = where.split(",");
        int[] totalInt = new int[]{Integer.parseInt(totalSTR[0]),Integer.parseInt(totalSTR[0])};
        String newTotal;
        switch (where){
            case "Warehouse":
            {
                if(what == 0)
                    totalInt[1] = totalInt[1] - 1;
                else if (what == 1) {
                    totalInt[1] = totalInt[1] + 1;

                }
                else
                {
                    totalInt[1] = totalInt[1] + 1;
                    totalInt[0] = totalInt[0] - 1;
                }

            }
            case "Store":
            {
                if(what == 0)
                {
                    totalInt[0] = totalInt[0] - 1;
                }
                else if (what == 1)
                {
                    totalInt[0] = totalInt[0] + 1;
                }
                else
                {
                    totalInt[0] = totalInt[0] + 1;
                    totalInt[1] = totalInt[1] - 1;
                }
            }
            default:
                newTotal = "";
        }
        String querySTR = "UPDATE Product SET total = ? WHERE id = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(querySTR);
        ) {
            SQLStyle.setString(1, newTotal);
            SQLStyle.setInt(2, catNum);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
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


}
