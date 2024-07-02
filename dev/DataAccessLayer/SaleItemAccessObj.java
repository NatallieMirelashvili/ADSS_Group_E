package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.*;

public class SaleItemAccessObj implements IDataAccessObj {
    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS salePrice (" +
                "idSale INTEGER PRIMARY KEY," +
                "startSale DATE NOT NULL," +
                "endSale DATE NOT NULL," +
                "discountRatio DOUBLE NOT NULL,";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {
            SQLStyle.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }
    @Override
    public JsonObject search(int UniqueToSearch) {
        String sql = "SELECT * FROM salePrice WHERE idSale = ?";
        JsonObject result = null;

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {

            SQLStyle.setInt(1, UniqueToSearch);
            ResultSet rs = SQLStyle.executeQuery();

            if (rs.next()) {
                result = new JsonObject();
                result.addProperty("idSale", rs.getInt("idSale"));
                result.addProperty("startSale", rs.getString("startSale"));
                result.addProperty("endSale", rs.getString("endSale"));
                result.addProperty("discountRatio", rs.getDouble("discountRatio"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }

        return result;
    }

    @Override
    public void insert(JsonObject recordToAdd) {
        String sql = "INSERT INTO salePrice (idSale, startSale, endSale, discountRatio) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {
            SQLStyle.setInt(1, recordToAdd.get("idSale").getAsInt());
            SQLStyle.setString(2, recordToAdd.get("startSale").getAsString());
            SQLStyle.setString(3, recordToAdd.get("endSale").getAsString());
            SQLStyle.setDouble(4, recordToAdd.get("discountRatio").getAsDouble());

            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }

    @Override
    public void remove(int UniqueToRemove) {
        String sql = "DELETE FROM salePrice WHERE idSale = ?";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {

            SQLStyle.setInt(1, UniqueToRemove);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Natallie check yourself");
        }
    }
}
