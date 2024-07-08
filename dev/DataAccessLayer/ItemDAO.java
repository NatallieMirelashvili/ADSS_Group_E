package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:DeliveryDB.sqlite";

    public ItemDAO() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS items (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO items (id, name) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("id").getAsInt());
            pstmt.setString(2, jsonObject.get("name").getAsString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonObject get(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", rs.getInt("id"));
                    jsonObject.addProperty("name", rs.getString("name"));
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(JsonObject jsonObject) {
        String sql = "UPDATE items SET name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jsonObject.get("name").getAsString());
            pstmt.setInt(2, jsonObject.get("id").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", rs.getInt("id"));
                jsonObject.addProperty("name", rs.getString("name"));
                items.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
