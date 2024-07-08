package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Items_formDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:DeliveryDB.sqlite";

    public Items_formDAO() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS items_form (" +
                    "ID INTEGER PRIMARY KEY," +
                    "destenation_id INTEGER," +
                    "delivery_id INTEGER," +
                    "FOREIGN KEY(destenation_id) REFERENCES sites(ID)," +
                    "FOREIGN KEY(delivery_id) REFERENCES deliverys(ID)" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO items_form (ID, destenation_id, delivery_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("ID").getAsInt());
            pstmt.setInt(2, jsonObject.get("destenation_id").getAsInt());
            pstmt.setInt(3, jsonObject.get("delivery_id").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM items_form WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(JsonObject jsonObject) {
        String sql = "UPDATE items_form SET destenation_id = ?, delivery_id = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("destenation_id").getAsInt());
            pstmt.setInt(2, jsonObject.get("delivery_id").getAsInt());
            pstmt.setInt(3, jsonObject.get("ID").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> items = new ArrayList<>();
        String sql = "SELECT * FROM items_form";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("ID", rs.getInt("ID"));
                jsonObject.addProperty("destenation_id", rs.getInt("destenation_id"));
                jsonObject.addProperty("item_id", rs.getInt("item_id"));
                items.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public JsonObject get(int id) {
        String sql = "SELECT * FROM items_form WHERE ID = ?";
        JsonObject jsonObject = new JsonObject();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    jsonObject.addProperty("ID", rs.getInt("ID"));
                    jsonObject.addProperty("destenation_id", rs.getInt("destenation_id"));
                    jsonObject.addProperty("item_id", rs.getInt("item_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}

