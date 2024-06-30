package DataAccessLayer;
import DomainLayer.Tuple;
import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;


public class ItemAccessObj implements IDataAccessObj {
    private static final String DATABASE_URL = "jdbc:sqlite:SuperMarket.db";

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

    @Override
    public JsonObject search(int UniqueToSearch) {
        String sql = "SELECT * FROM Item WHERE id = ?";
        JsonObject result = new JsonObject();

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, UniqueToSearch);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result.addProperty("id", rs.getInt("id"));
                result.addProperty("expirationDate", rs.getString("expirationDate"));
                result.addProperty("place", rs.getString("place"));
                result.addProperty("StoreOrWare", rs.getString("StoreOrWare"));
                result.addProperty("status", rs.getInt("status"));
                result.addProperty("catalogNumItem", rs.getInt("catalogNumItem"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void insert(JsonObject recordToAdd) {
        String sql = "INSERT INTO Item (id, expirationDate, place, StoreOrWare, status, catalogNumItem) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, recordToAdd.get("id").getAsInt());
            pstmt.setString(2, recordToAdd.get("expirationDate").getAsString());
            pstmt.setString(3, recordToAdd.get("place").getAsString());
            pstmt.setString(4, recordToAdd.get("StoreOrWare").getAsString());
            pstmt.setInt(5, recordToAdd.get("status").getAsInt());
            pstmt.setInt(6, recordToAdd.get("catalogNumItem").getAsInt());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}

    @Override
    public void remove(int UniqueToRemove) {
        String sql = "DELETE FROM Item WHERE id = ?";

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
        String sql = "CREATE TABLE IF NOT EXISTS Item (" +
                "id INTEGER PRIMARY KEY," +
                "expirationDate DATE NOT NULL," +
                "place TEXT NOT NULL," +
                "StoreOrWare TEXT NOT NULL," +
                "status INTEGER NOT NULL" +
                "FOREIGN KEY (catalogNumItem) REFERENCES Product(catalogNumProduct))";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void moveStoreWareDB(int idToMove, Tuple<String, Integer> newPlace){}
    public void moveWareStoreDB(int idToMove, Tuple<String, Integer> newPlace){}
    public void reportDamageDB(int idToReport){}
    public void reportExpDB(int idToReport){}
    public ArrayList<JsonObject> findAllDefectiveDB(){}
    public ArrayList<JsonObject> findAllExpDB(){}
    public static void main(String[] args) {
        ItemAccessObj dao = new ItemAccessObj();
        dao.createTable();
    }
}
