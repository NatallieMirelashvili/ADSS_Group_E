package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsLoadedDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO ItemsLoaded (delivery_id, item_id, amount_loaded, amount_unloaded) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("delivery_id").getAsInt());
            pstmt.setInt(2, jsonObject.get("item_id").getAsInt());
            pstmt.setInt(3, jsonObject.get("amount_loaded").getAsInt());
            pstmt.setInt(4, jsonObject.get("amount_unloaded").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
// not needed
    }
    public void remove(int deliveryID, int itemID) {
        String sql = "DELETE FROM ItemsLoaded WHERE delivery_id = ? AND item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryID);
            pstmt.setInt(2, itemID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(JsonObject jsonObject) {
        String sql = "UPDATE ItemsLoaded SET amount_loaded = ?, amount_unloaded = ? WHERE delivery_id = ? AND item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("amount_loaded").getAsInt());
            pstmt.setInt(2, jsonObject.get("amount_unloaded").getAsInt());
            pstmt.setInt(3, jsonObject.get("delivery_id").getAsInt());
            pstmt.setInt(4, jsonObject.get("item_id").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> itemsLoadedList = new ArrayList<>();
        String sql = "SELECT * FROM ItemsLoaded";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("delivery_id", rs.getInt("delivery_id"));
                jsonObject.addProperty("item_id", rs.getInt("item_id"));
                jsonObject.addProperty("amount_loaded", rs.getInt("amount_loaded"));
                jsonObject.addProperty("amount_unloaded", rs.getInt("amount_unloaded"));
                itemsLoadedList.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsLoadedList;
    }

    @Override
    public JsonObject get(int id) {
        // This method is not needed based on your description, so returning null
        return null;
    }

    public JsonObject get(int deliveryID, int itemID) {
        String sql = "SELECT il.delivery_id, il.item_id, il.amount_loaded, il.amount_unloaded, i.name " +
                "FROM ItemsLoaded il " +
                "JOIN items i ON il.item_id = i.id " +
                "WHERE il.delivery_id = ? AND il.item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryID);
            pstmt.setInt(2, itemID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("delivery_id", rs.getInt("delivery_id"));
                    jsonObject.addProperty("item_id", rs.getInt("item_id"));
                    jsonObject.addProperty("amount_loaded", rs.getInt("amount_loaded"));
                    jsonObject.addProperty("amount_unloaded", rs.getInt("amount_unloaded"));
                    jsonObject.addProperty("item_name", rs.getString("name"));
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getAmountLoaded(int deliveryID, int itemID) {
        String sql = "SELECT amount_loaded FROM ItemsLoaded WHERE delivery_id = ? AND item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryID);
            pstmt.setInt(2, itemID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("amount_loaded");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAmountUnLoaded(int deliveryID, int itemID) {
        String sql = "SELECT amount_unloaded FROM ItemsLoaded WHERE delivery_id = ? AND item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryID);
            pstmt.setInt(2, itemID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("amount_unloaded");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setAmountLoaded(int deliveryID, int itemID, int amount) {
        String sql = "UPDATE ItemsLoaded SET amount_loaded = ? WHERE delivery_id = ? AND item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setInt(2, deliveryID);
            pstmt.setInt(3, itemID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAmountUnLoaded(int deliveryID, int itemID, int amount) {
        String sql = "UPDATE ItemsLoaded SET amount_unloaded = ? WHERE delivery_id = ? AND item_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setInt(2, deliveryID);
            pstmt.setInt(3, itemID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<JsonObject> get_items_by_delivery_id(int id) {
        ArrayList<JsonObject> items = new ArrayList<>();
        String sql = "SELECT * FROM ItemsLoaded il join items i on il.item_id = i.ID WHERE delivery_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("item_id", rs.getInt("item_id"));
                    jsonObject.addProperty("name", rs.getString("name"));
                    jsonObject.addProperty("amount_loaded", rs.getInt("amount_loaded"));
                    jsonObject.addProperty("amount_unloaded", rs.getInt("amount_unloaded"));
                    items.add(jsonObject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return items;
    }
}
