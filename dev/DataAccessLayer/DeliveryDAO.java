package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:C:/Users/User/Desktop/לימודים/ניתוצ/עבודה 0/ADSS_Group_E/identifier.sqlite";

    public DeliveryDAO() {
        createDeliveryTable();
    }

    private void createDeliveryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS deliverys (" +
                "id INTEGER PRIMARY KEY, " +
                "date TEXT, " +
                "hour TEXT, " +
                "driver_id INTEGER, " +
                "truck_id INTEGER, " +
                "site_id INTEGER, " +
                "FOREIGN KEY(driver_id) REFERENCES drivers(id), " +
                "FOREIGN KEY(truck_id) REFERENCES trucks(id), " +
                "FOREIGN KEY(site_id) REFERENCES sites(id))";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO deliverys (ID, date, hour, driver_id, truck_id, site_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jsonObject.get("ID").getAsInt());
            pstmt.setString(2, jsonObject.get("date").getAsString());
            pstmt.setString(3, jsonObject.get("hour").getAsString());
            pstmt.setInt(4, jsonObject.get("driver_id").getAsInt());
            pstmt.setInt(5, jsonObject.get("truck_id").getAsInt());
            pstmt.setInt(6, jsonObject.get("site_id").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM deliverys WHERE ID = ?";
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
        String sql = "UPDATE deliverys SET date = ?, hour = ?, driver_id = ?, truck_id = ?, site_id = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jsonObject.get("date").getAsString());
            pstmt.setString(2, jsonObject.get("hour").getAsString());
            pstmt.setInt(3, jsonObject.get("driver_id").getAsInt());
            pstmt.setInt(4, jsonObject.get("truck_id").getAsInt());
            pstmt.setInt(5, jsonObject.get("site_id").getAsInt());
            pstmt.setInt(6, jsonObject.get("ID").getAsInt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliverys";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", rs.getInt("ID"));
                jsonObject.addProperty("date", rs.getString("date"));
                jsonObject.addProperty("hour", rs.getString("hour"));
                jsonObject.addProperty("driver_id", rs.getInt("driver_id"));
                jsonObject.addProperty("truck_id", rs.getInt("truck_id"));
                jsonObject.addProperty("site_id", rs.getInt("site_id"));
                deliveries.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    @Override
    public JsonObject get(int id) {
        String sql = "SELECT * FROM deliverys WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs == null) return null;
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", rs.getInt("ID"));
                    jsonObject.addProperty("date", rs.getString("date"));
                    jsonObject.addProperty("hour", rs.getString("hour"));
                    jsonObject.addProperty("driver_id", rs.getInt("driver_id"));
                    jsonObject.addProperty("truck_id", rs.getInt("truck_id"));
                    jsonObject.addProperty("site_id", rs.getInt("site_id"));
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int[] getIDS() {
        String sql = "SELECT ID FROM deliverys";
        List<Integer> ids = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getInt("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids.stream().mapToInt(i -> i).toArray();
    }
}
