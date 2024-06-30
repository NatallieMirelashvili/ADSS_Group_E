package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;

public class ProductAccessObj implements IDataAccessObj{
    private static final String DATABASE_URL = "jdbc:sqlite:SuperMarket.db";

    @Override
    public JsonObject search(int UniqueToSearch) {
        String sql = "SELECT * FROM Product WHERE catalogNumProduct = ?";
        JsonObject result = new JsonObject();

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, UniqueToSearch);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result.addProperty("catalogNumProduct", rs.getInt("catalogNumProduct"));
                result.addProperty("Prodsize", rs.getString("Prodsize"));
                result.addProperty("catName", rs.getString("catName"));
                result.addProperty("subCatName", rs.getString("subCatName"));
                result.addProperty("total", rs.getString("total"));
                result.addProperty("manufactor", rs.getString("manufactor"));
                result.addProperty("marketPriceConst", rs.getDouble("marketPriceConst"));
                result.addProperty("manuPriceConst", rs.getDouble("manuPriceConst"));
                result.addProperty("manuPriceCurr", rs.getDouble("manuPriceCurr"));
                result.addProperty("marketPriceCurr", rs.getDouble("marketPriceCurr"));
                result.addProperty("discount", rs.getDouble("discount"));
                result.addProperty("minimalAmount", rs.getInt("minimalAmount"));
                result.addProperty("OrderAmount", rs.getInt("OrderAmount"));
                result.addProperty("isMinimal", rs.getString("isMinimal"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void insert(JsonObject recordToAdd) {
        String sql = "INSERT INTO Product (catalogNumProduct, Prodsize, catName, subCatName, total, manufactor, marketPriceConst, manuPriceConst, manuPriceCurr, marketPriceCurr, discount, minimalAmount, OrderAmount, isMinimal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, recordToAdd.get("catalogNumProduct").getAsInt());
            pstmt.setString(2, recordToAdd.get("Prodsize").getAsString());
            pstmt.setString(3, recordToAdd.get("catName").getAsString());
            pstmt.setString(4, recordToAdd.get("subCatName").getAsString());
            pstmt.setString(5, recordToAdd.get("total").getAsString());
            pstmt.setString(6, recordToAdd.get("manufactor").getAsString());
            pstmt.setDouble(7, recordToAdd.get("marketPriceConst").getAsDouble());
            pstmt.setDouble(8, recordToAdd.get("manuPriceConst").getAsDouble());
            pstmt.setDouble(9, recordToAdd.get("manuPriceCurr").getAsDouble());
            pstmt.setDouble(10, recordToAdd.get("marketPriceCurr").getAsDouble());
            pstmt.setDouble(11, recordToAdd.get("discount").getAsDouble());
            pstmt.setInt(12, recordToAdd.get("minimalAmount").getAsInt());
            pstmt.setInt(13, recordToAdd.get("OrderAmount").getAsInt());
            pstmt.setString(14, recordToAdd.get("isMinimal").getAsString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void remove(int UniqueToRemove) {
        String sql = "DELETE FROM Product WHERE catalogNumProduct = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, UniqueToRemove);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Product (" +
                "catalogNumPtoduct INTEGER PRIMARY KEY," +
                "Prodsize TEXT NOT NULL," +
                "catName TEXT NOT NULL," +
                "subCatName TEXT NOT NULL," +
                "total TEXT NOT NULL," +
                "manufactor TEXT NOT NULL," +
                "marketPriceConst DOUBLE NOT NULL," +
                "manuPriceConst DOUBLE NOT NULL," +
                "manuPriceCurr DOUBLE NOT NULL," +
                "marketPriceCurr DOUBLE NOT NULL," +
                "discount DOUBLE NOT NULL," +
                "minimalAmount INTEGER NOT NULL," +
                "OrderAmount INTEGER NOT NULL," +
                "isMininal TEXT NOT NULL)";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        Connection connection = null;
        try {
            // Load the SQLite JDBC driver (you don't need to do this explicitly with the latest drivers)
            Class.forName("org.sqlite.JDBC");
            // Establish a connection to the database
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void removeItemDB(int catNum, String fromWhere){}
    public ArrayList<JsonObject> findAllProductsByMainCatDB(String mainCat, ArrayList<Integer> alreadyHave){}
    public ArrayList<JsonObject> findAllProductsBySubCatDB(String mainCat, String subCat, ArrayList<Integer> alreadyHave){}
    public ArrayList<JsonObject> findAllProductsBySizeDB(String mainCat, String subCat, String size, ArrayList<Integer> alreadyHave){}
    public void updateSaleDB(ArrayList<Integer> catalogNumToUpdate, int idOfNewSale, double ratio){}
    public void updateDisDB(ArrayList<Integer> catalogNumbersToUpdate, double ratio){}
    public void updatePlacedItemDB(int catNum, String WareOrStore){}
}
