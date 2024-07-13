package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiteDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public SiteDAO() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sites (" +
                    "id INTEGER PRIMARY KEY," +
                    "type TEXT," +
                    "name TEXT," +
                    "address TEXT," +
                    "contacts_name TEXT," +
                    "phone_num TEXT," +
                    "area TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO sites (id, type, name, address, contacts_name, phone_num, area) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("ID").getAsInt());
            pstmt.setString(2, jsonObject.get("type").getAsString());
            pstmt.setString(3, jsonObject.get("name").getAsString());
            pstmt.setString(4, jsonObject.get("address").getAsString());
            pstmt.setString(5, jsonObject.get("contacts_name").getAsString());
            pstmt.setString(6, jsonObject.get("phone_num").getAsString());
            pstmt.setString(7, jsonObject.get("area").getAsString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM sites WHERE id = ?";
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
        String sql = "UPDATE sites SET type = ?, name = ?, address = ?, contacts_name = ?, phone_num = ?, area = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jsonObject.get("type").getAsString());
            pstmt.setString(2, jsonObject.get("name").getAsString());
            pstmt.setString(3, jsonObject.get("address").getAsString());
            pstmt.setString(4, jsonObject.get("contacts_name").getAsString());
            pstmt.setString(5, jsonObject.get("phone_num").getAsString());
            pstmt.setString(6, jsonObject.get("area").getAsString());
            pstmt.setInt(7, jsonObject.get("ID").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> sites = new ArrayList<>();
        String sql = "SELECT * FROM sites";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("ID", rs.getInt("id"));
                jsonObject.addProperty("type", rs.getString("type"));
                jsonObject.addProperty("name", rs.getString("name"));
                jsonObject.addProperty("address", rs.getString("address"));
                jsonObject.addProperty("contacts_name", rs.getString("contacts_name"));
                jsonObject.addProperty("phone_num", rs.getString("phone_num"));
                jsonObject.addProperty("area", rs.getString("area"));
                sites.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sites;
    }

    @Override
    public JsonObject get(int id) {
        String sql = "SELECT * FROM sites WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("ID", rs.getInt("id"));
                    jsonObject.addProperty("type", rs.getString("type"));
                    jsonObject.addProperty("name", rs.getString("name"));
                    jsonObject.addProperty("address", rs.getString("address"));
                    jsonObject.addProperty("contacts_name", rs.getString("contacts_name"));
                    jsonObject.addProperty("phone_num", rs.getString("phone_num"));
                    jsonObject.addProperty("area", rs.getString("area"));
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
