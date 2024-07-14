package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ProductToDelAccessObj implements IDataAccessObj{
    public void createOrder(){
        String sql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "catalogNumber INTEGER NOT NULL," +
                "manufacturer TEXT NOT NULL," +
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
                "manufacturer TEXT NOT NULL," +
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

    public void insertToSiteOf(int siteID, String manu){
        String sql = "INSERT INTO SiteOfOrder (orderAmount, manufacturer)" +
                " VALUES (?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.setInt(1, siteID);
            SQLStyle.setString(2, manu);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void insertIntoOrders(int amount, int storeID, int catNum){
        String sql = "INSERT INTO Oreders (orderAmount, idStore, catalogNumber)" +
                " VALUES (?, ?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.setInt(1, amount);
            SQLStyle.setInt(2, storeID);
            SQLStyle.setInt(3, catNum);
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
        insertToSiteOf(recordToAdd.get("idStore").getAsInt(), recordToAdd.get("manufacturer").getAsString());
        insertIntoOrders(recordToAdd.get("orderAmount").getAsInt(), recordToAdd.get("idStore").getAsInt(), recordToAdd.get("catalogNumber").getAsInt());
    }
    @Override
    public JsonObject search(int UniqueToSearch) {
        return null;
    }



    @Override
    public void remove(int UniqueRemove) {}




}
