package DataAccessLayer;
import DomainLayer.Tuple;
import com.google.gson.JsonObject;
import java.sql.*;
import java.util.ArrayList;


public class ItemAccessObj implements IDataAccessObj {
    final ArrayList<String> myField = new ArrayList<>() {{
        add("id");
        add("expirationDate");
        add("place");
        add("StoreOrWare");
        add("status");
        add("catalogNumItem");
    }};
    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Item (" +
                "id INTEGER PRIMARY KEY," +
                "expirationDate TEXT NOT NULL," +
                "place TEXT NOT NULL," +
                "StoreOrWare TEXT," +
                "status INTEGER," +
                "catalogNumItem INTEGER," +
                "FOREIGN KEY (catalogNumItem) REFERENCES Product(catalogNumProduct))";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public JsonObject search(int UniqueToSearch) {
        return IDataAccessObj.runSearch(UniqueToSearch, "Item", "id", myField);
    }

    @Override
    public void insert(JsonObject recordToAdd) {
        String sql = "INSERT INTO Item (id, expirationDate, place, StoreOrWare, status, catalogNumItem)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.setInt(1, recordToAdd.get("id").getAsInt());
            SQLStyle.setString(2, recordToAdd.get("expirationDate").getAsString());
            SQLStyle.setString(3, recordToAdd.get("place").getAsString());
//            find out item location:
            String[] split = recordToAdd.get("place").getAsString().split(",");
            if(split[0].length() == 1)
                SQLStyle.setString(4, "Warehouse");
            else
                SQLStyle.setString(4, "Store");
            // At start all items added with status 2
            SQLStyle.setInt(5, 2);
            SQLStyle.setInt(6, recordToAdd.get("catalogNumItem").getAsInt());
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void remove(int UniqueToRemove) {
        String sql = "DELETE FROM Item WHERE id = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql)
        ) {

            SQLStyle.setInt(1, UniqueToRemove);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void moveStoreWareDB(int idToMove, Tuple<String, Integer> newPlace) {
        movesHelper(idToMove, newPlace, "Warehouse");

    }

    public void moveWareStoreDB(int idToMove, Tuple<String, Integer> newPlace) {
        movesHelper(idToMove, newPlace, "Store");
    }

    public void reportDamageDB(int idToReport) {
        reportHelper(idToReport, 0);

    }

    public void reportExpDB(int idToReport) {
        reportHelper(idToReport, 1);
    }
    public void reportForSaleDB(int idToReport) {
        reportHelper(idToReport, 2);
    }


    public ArrayList<JsonObject> findAllDefectiveDB(ArrayList<Integer> alreadyHave) {
        return findALLHelper(0, alreadyHave);

    }

    public ArrayList<JsonObject> findAllExpDB(ArrayList<Integer> alreadyHave) {
        return findALLHelper(1, alreadyHave);
    }


    //    ******Help functions*****


    private String parsePlace(Tuple<String, Integer> newPlace) {
//        getVal1() == pass
//        getVal2() == num of shelf
        return "" + newPlace.getVal1() + "," + newPlace.getVal2();
    }

    private void movesHelper(int idToMove, Tuple<String, Integer> newPlace, String place) {
        String newPlaceSTR = parsePlace(newPlace);
        String querySTR = "UPDATE Item SET place = ?, StoreOrWare = ? WHERE id = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(querySTR);
        ) {
            SQLStyle.setString(1, newPlaceSTR);
            SQLStyle.setString(2, place);
            SQLStyle.setInt(3, idToMove);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void reportHelper(int id, int newStat) {
        String querySTR = "UPDATE Item SET status = ? WHERE id = ?";
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(querySTR);
        ) {
            SQLStyle.setInt(1, newStat);
            SQLStyle.setInt(2, id);
            SQLStyle.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private ArrayList<JsonObject> findALLHelper(int status,ArrayList<Integer> alreadyHave){
        String subQuery = IDataAccessObj.innerQuery(alreadyHave.size(), "id", "Item");
        String sql = "SELECT * FROM Item WHERE status = ? AND id NOT IN (" + subQuery + ")";
        ArrayList<JsonObject> relevanceRecords = new ArrayList<>();
        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        ) {
            SQLStyle.setInt(1, status);
            for (int i = 0; i < alreadyHave.size(); i++) {
                SQLStyle.setInt(i + 2, alreadyHave.get(i));
            }
            ResultSet matchedRecords = SQLStyle.executeQuery();
            while (matchedRecords.next()) {
                JsonObject defectiveRec = IDataAccessObj.parseRecToJson(matchedRecords, myField);
                relevanceRecords.add(defectiveRec);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return relevanceRecords;
    }
    public void dropTable(){
        String sql = "DROP TABLE IF EXISTS Item";
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