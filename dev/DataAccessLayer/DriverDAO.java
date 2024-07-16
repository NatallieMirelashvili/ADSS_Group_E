package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO implements IDAO  {
    private static final String URL = "jdbc:sqlite:C:/Users/User/Desktop/לימודים/ניתוצ/עבודה 0/ADSS_Group_E/identifier.sqlite";
    public DriverDAO() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS drivers (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "licence TEXT," +
                    "phone_num TEXT," +
                    "password TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO drivers (id, name, licence, phone_num, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(jsonObject.get("ID").getAsString()));
            pstmt.setString(2, jsonObject.get("name").getAsString());
            pstmt.setString(3, jsonObject.get("licence").getAsString());
            pstmt.setString(4, jsonObject.get("phone_num").getAsString());
            pstmt.setString(5, jsonObject.get("password").getAsString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM drivers WHERE id = ?";
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
        String sql = "UPDATE drivers SET name = ?, licence = ?, phone_num = ?, password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jsonObject.get("name").getAsString());
            pstmt.setString(2, jsonObject.get("licence").getAsString());
            pstmt.setString(3, jsonObject.get("phone_num").getAsString());
            pstmt.setString(4, jsonObject.get("password").getAsString());
            pstmt.setInt(5, Integer.parseInt(jsonObject.get("ID").getAsString()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("ID", rs.getInt("id"));
                jsonObject.addProperty("name", rs.getString("name"));
                jsonObject.addProperty("licence", rs.getString("licence"));
                jsonObject.addProperty("phone_num", rs.getString("phone_num"));
                jsonObject.addProperty("password", rs.getString("password"));
                drivers.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    @Override
    public JsonObject get(int id) {
        String sql = "SELECT * FROM drivers WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("ID", rs.getInt("id"));
                    jsonObject.addProperty("name", rs.getString("name"));
                    jsonObject.addProperty("licence", rs.getString("licence"));
                    jsonObject.addProperty("phone_num", rs.getString("phone_num"));
                    jsonObject.addProperty("password", rs.getString("password"));
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}