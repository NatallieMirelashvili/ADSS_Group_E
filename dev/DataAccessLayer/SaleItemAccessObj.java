package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.*;

public class SaleItemAccessObj implements IDataAccessObj {
    private static final String DATABASE_URL = "jdbc:sqlite:SuperMarket.db";
    @Override
    public JsonObject search(int UniqueToSearch) {
        String sql = "SELECT * FROM salePrice WHERE idSale = ?";
        JsonObject result = new JsonObject();

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, UniqueToSearch);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result.addProperty("idSale", rs.getInt("idSale"));
                result.addProperty("startSale", rs.getString("startSale"));
                result.addProperty("endSale", rs.getString("endSale"));
                result.addProperty("discountRatio", rs.getDouble("discountRatio"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void insert(JsonObject recordToAdd) {
        String sql = "INSERT INTO salePrice (idSale, startSale, endSale, discountRatio) VALUES (?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, recordToAdd.get("idSale").getAsInt());
            pstmt.setString(2, recordToAdd.get("startSale").getAsString());
            pstmt.setString(3, recordToAdd.get("endSale").getAsString());
            pstmt.setDouble(4, recordToAdd.get("discountRatio").getAsDouble());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void remove(int UniqueToRemove) {
        String sql = "DELETE FROM salePrice WHERE idSale = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, UniqueToRemove);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS salePrice (" +
                "idSale INTEGER PRIMARY KEY," +
                "startSale DATE NOT NULL," +
                "endSale DATE NOT NULL," +
                "discountRatio DOUBLE NOT NULL,";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Connection connect() {
        Connection connection = null;
        try {
            // Load the SQLite JDBC driver (you don't need to do this explicitly with the latest drivers)
            Class.forName("org.sqlite.JDBC");
            // Establish a connection to the database
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
