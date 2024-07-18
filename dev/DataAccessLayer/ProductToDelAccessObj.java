package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ProductToDelAccessObj implements IDataAccessObj{
    public void createOrder(){
        String sql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "catalogNumber INTEGER NOT NULL," +
                "manufacturer INTEGER NOT NULL," +
                "idStore INTEGER," +
                "orderAmount INTEGER NOT NULL," +
                "PRIMARY KEY(catalogNumber, manufacturer))";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public void createSupOf(){
        String sql = "CREATE TABLE IF NOT EXISTS SiteOfOrder (" +
                "idStore INTEGER," +
                "manufacturer INTEGER NOT NULL," +
                "PRIMARY KEY(idStore, manufacturer))";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void insertToSiteOf(int siteID, int manu){
        String sql = "INSERT INTO SiteOfOrder (idStore, manufacturer)" +
                " VALUES (?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.setInt(1, siteID);
            SQLStyle.setInt(2, manu);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void insertIntoOrders(int amount, int storeID, int catNum, int manu){
        String sql = "INSERT INTO Orders (orderAmount, idStore, catalogNumber, manufacturer)" +
                " VALUES (?, ?, ?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.setInt(1, amount);
            SQLStyle.setInt(2, storeID);
            SQLStyle.setInt(3, catNum);
            SQLStyle.setInt(4, manu);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createTable() {
        createOrder();
        createSupOf();
    }
    @Override
    public void insert(JsonObject recordToAdd) {
        int manu = Integer.parseInt(recordToAdd.get("manufacturer").getAsString());
        insertToSiteOf(recordToAdd.get("idStore").getAsInt(), manu);
        insertIntoOrders(recordToAdd.get("orderAmount").getAsInt(), recordToAdd.get("idStore").getAsInt(), recordToAdd.get("catalogNumber").getAsInt(), manu);
    }
    @Override
    public JsonObject search(int UniqueToSearch) {
        return null;
    }



    @Override
    public void remove(int UniqueRemove) {}




}
