package DataAccessLayer;

import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TruckDAO implements IDAO {
    private static final String URL = "jdbc:sqlite:C:\\Users\\tomba\\IdeaProjects\\ADSS_Group_E\\identifier.sqlite";

    public TruckDAO() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS trucks (" +
                    "id INTEGER PRIMARY KEY," +
                    "model TEXT," +
                    "max_weight REAL," +
                    "licence TEXT," +
                    "curr_weight REAL DEFAULT 0)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(JsonObject jsonObject) {
        String sql = "INSERT INTO trucks (id, model, max_weight, licence, curr_weight) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(jsonObject.get("ID").getAsString()));
            pstmt.setString(2, jsonObject.get("model").getAsString());
            pstmt.setDouble(3, Double.parseDouble(jsonObject.get("max_weight").getAsString()));
            pstmt.setString(4, jsonObject.get("licence").getAsString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM trucks WHERE id = ?";
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
        String sql = "SELECT * FROM trucks WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("ID", rs.getInt("id"));
                    jsonObject.addProperty("model", rs.getString("model"));
                    jsonObject.addProperty("max_weight", rs.getDouble("max_weight"));
                    jsonObject.addProperty("licence", rs.getString("licence"));
                    jsonObject.addProperty("curr_weight", rs.getDouble("curr_weight"));
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
        String sql = "UPDATE trucks SET model = ?, max_weight = ?, licence = ?, curr_weight = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jsonObject.get("model").getAsString());
            pstmt.setDouble(2, Double.parseDouble(jsonObject.get("max_weight").getAsString()));
            pstmt.setString(3, jsonObject.get("licence").getAsString());
            pstmt.setDouble(4, Double.parseDouble(jsonObject.get("curr_weight").getAsString()));
            pstmt.setInt(5, Integer.parseInt(jsonObject.get("ID").getAsString()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonObject> getAll() {
        List<JsonObject> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("ID", rs.getInt("id"));
                jsonObject.addProperty("model", rs.getString("model"));
                jsonObject.addProperty("max_weight", rs.getDouble("max_weight"));
                jsonObject.addProperty("licence", rs.getString("licence"));
                jsonObject.addProperty("curr_weight", rs.getDouble("curr_weight"));
                trucks.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trucks;
    }

    public void updateCurrWeight(int id, double currWeight) {
        String sql = "UPDATE trucks SET curr_weight = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, currWeight);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
