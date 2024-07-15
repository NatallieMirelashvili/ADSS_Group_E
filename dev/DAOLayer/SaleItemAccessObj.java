package DAOLayer;

import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;

public class SaleItemAccessObj implements IDataAccessObj {
    final ArrayList<String> myField = new ArrayList<>() {{
        add("idSale");
        add("startSale");
        add("endSale");
        add("discountRatio");
    }};
    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS salePrice (" +
                "idSale INTEGER PRIMARY KEY," +
                "startSale DATE NOT NULL," +
                "endSale DATE NOT NULL," +
                "discountRatio INTEGER NOT NULL)";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public JsonObject search(int UniqueToSearch) {
        return IDataAccessObj.runSearch(UniqueToSearch, "salePrice", "idSale", myField);
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
            SQLStyle.setDouble(4, recordToAdd.get("discountRatio").getAsInt());

            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e.getMessage());
        }
    }
    public int getLastSaleID(){
        String sql = "SELECT MAX(idSale) AS max_id FROM salePrice";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
                )
                {
                    ResultSet maxIdx = SQLStyle.executeQuery();
                    if(maxIdx.next())
                        return maxIdx.getInt("max_id");
                }

                catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
                }
        return 0;

    }
    public void dropTable(){
        String sql = "DROP TABLE IF EXISTS salePrice";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {

            SQLStyle.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
