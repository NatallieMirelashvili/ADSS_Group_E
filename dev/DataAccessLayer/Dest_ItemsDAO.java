package DataAccessLayer;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dest_ItemsDAO implements IDAO{
    private static final String URL = "jdbc:sqlite:C:\\Users\\tomba\\IdeaProjects\\ADSS_Group_E\\identifier.sqlite";

    @Override
    public void add(JsonObject jsonObject) {

    }

    @Override
    public void remove(int id) {
    }

    public void remove(int site_id, int sup_id) {
        String sql = "DELETE FROM items_in_IF WHERE site_id = ? AND sup_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, site_id);
            pstmt.setInt(2, sup_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(JsonObject jsonObject) {

    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> Dest_items = new ArrayList<>();
        String sql = "SELECT * FROM SiteOfOrder";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("site_id", rs.getInt("idStore"));
                jsonObject.addProperty("sup_id", rs.getInt("manufacturer"));
                Dest_items.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Dest_items;
    }

    @Override
    public JsonObject get(int id) {
        return null;
    }
}
