package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailsDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public ItemDetailsDAO() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS items_in_IF (" +
                    "item_id INTEGER," +
                    "items_form_id INTEGER," +
                    "amount_loaded INTEGER," +
                    "amount_unloaded INTEGER," +
                    "PRIMARY KEY (item_id, items_form_id))";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO items_in_IF (item_id, item_form_id, amount_loaded, amount_unloaded) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("item_id").getAsInt());
            pstmt.setInt(2, jsonObject.get("item_form_id").getAsInt());
            pstmt.setInt(3, jsonObject.get("amount_loaded").getAsInt());
            pstmt.setInt(4, jsonObject.get("amount_unloaded").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM items_in_IF WHERE item_form_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int itemId, int itemsFormId) {
        String sql = "DELETE FROM items_in_IF WHERE item_id = ? AND items_form_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            pstmt.setInt(2, itemsFormId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JsonObject get(int itemId, int itemsFormId) {
        String sql = "SELECT * FROM items_in_IF WHERE item_id = ? AND items_form_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            pstmt.setInt(2, itemsFormId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("item_id", rs.getInt("item_id"));
                    jsonObject.addProperty("items_form_id", rs.getInt("items_form_id"));
                    jsonObject.addProperty("amount_loaded", rs.getInt("amount_loaded"));
                    jsonObject.addProperty("amount_unloaded", rs.getInt("amount_unloaded"));
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(JsonObject jsonObject) {
        String sql = "UPDATE items_in_IF SET amount_loaded = ?, amount_unloaded = ? WHERE item_id = ? AND items_form_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("amount_loaded").getAsInt());
            pstmt.setInt(2, jsonObject.get("amount_unloaded").getAsInt());
            pstmt.setInt(3, jsonObject.get("item_id").getAsInt());
            pstmt.setInt(4, jsonObject.get("items_form_id").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JsonObject> getAll() {
        List<JsonObject> itemDetails = new ArrayList<>();
        String sql = "SELECT * FROM items_in_IF";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("item_id", rs.getInt("item_id"));
                jsonObject.addProperty("items_form_id", rs.getInt("items_form_id"));
                jsonObject.addProperty("amount_loaded", rs.getInt("amount_loaded"));
                jsonObject.addProperty("amount_unloaded", rs.getInt("amount_unloaded"));
                itemDetails.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemDetails;
    }

    @Override
    public JsonObject get(int id) {
        return null;
    }

    public void setItemAmountLoaded(int item_form_id, int item_id, int amount) {
        String sql = "UPDATE items_in_IF SET amount_loaded = ? WHERE item_id = ? AND item_form_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setInt(2, item_id);
            pstmt.setInt(3, item_form_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getItemAmount(int item_form_id, int item_id) {
        String sql = "SELECT amount_loaded FROM items_in_IF WHERE item_form_id = ? AND item_id = ?";
        int amount = 0;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item_form_id);
            pstmt.setInt(2, item_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    amount = rs.getInt("amount_loaded");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }
    public void increaseItemAmountLoaded(int item_form_id, int item_id, int amount) {
        int currentAmount = getItemAmount(item_form_id, item_id);
        setItemAmountLoaded(item_form_id, item_id, currentAmount + amount);
    }

    public void decreaseItemAmountLoaded( int item_form_id, int item_id, int amount) {
        int currentAmount = getItemAmount( item_form_id, item_id);
        setItemAmountLoaded(item_form_id, item_id, currentAmount - amount);
    }

    public ArrayList<JsonObject> get_items_by_item_form_id(int itemFormID) {
        ArrayList<JsonObject> items = new ArrayList<>();
        String sql = "SELECT * FROM items_in_IF i join items j on i.item_id = j.ID WHERE items_form_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemFormID);
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







