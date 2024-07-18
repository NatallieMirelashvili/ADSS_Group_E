package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements IDAO{
    private static final String URL = "jdbc:sqlite:C:/Users/User/Desktop/לימודים/ניתוצ/עבודה 0/ADSS_Group_E/identifier.sqlite";

    @Override
    public void add(JsonObject jsonObject) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void update(JsonObject jsonObject) {

    }

    @Override
    public List<JsonObject> getAll() {
        return List.of();
    }

    @Override
    public JsonObject get(int sup_id) {
        return null;
    }
    public ArrayList<JsonObject> get_items_by_destination(int sup_id){
        ArrayList<JsonObject> items = new ArrayList<>();
        String sql = "SELECT * FROM Orders o join items j on o.catalogNumber = j.ID WHERE manufacturer = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sup_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("item_id", rs.getInt("catalogNumber"));
                    jsonObject.addProperty("name", rs.getString("name"));
                    jsonObject.addProperty("amount_loaded", rs.getInt("orderAmount"));
                    jsonObject.addProperty("amount_unloaded", rs.getInt("orderAmount"));
                    items.add(jsonObject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
