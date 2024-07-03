package DataAccessLayer;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDataAccessObj {

    public JsonObject search(int UniqueToSearch);

    public void insert(JsonObject recordToAdd);

    public void remove(int UniqueRemove);

    public void createTable();

    static String innerQuery(int numOFUniques, String uniqueField, String tableName) {
        StringBuilder sql = new StringBuilder(" SELECT " + uniqueField + " FROM " + tableName + " WHERE " + uniqueField +" IN (");
        for (int i = 0; i < numOFUniques; i++) {
            sql.append("?");
            if (i < numOFUniques - 1) {
                sql.append(",");
            }
        }
        sql.append(")");
        return sql.toString();
    }
    static JsonObject parseRecToJson(ResultSet theSQLStyle, ArrayList<String> properties){
        JsonObject theJsonStyle = new JsonObject();
        try{
            if(theSQLStyle != null){
                for (String property : properties) {
                    theJsonStyle.addProperty(property, theSQLStyle.getString(property));
                }
                return theJsonStyle;
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Natallie check yourself, theSQLStyle is null -> there are no defectives");

        }
        return null;
    }

    static JsonObject runSearch(int UniqueToSearch, String tableToSearchOn, String primaryKeyField, ArrayList<String> fields) {
        String sql = "SELECT * FROM " + tableToSearchOn + " WHERE " + primaryKeyField + " = ?";
        JsonObject result = null;

        try (
                Connection connection = Database.connect();
                PreparedStatement SQLStyle = connection.prepareStatement(sql);
        )
        {
            SQLStyle.setInt(1, UniqueToSearch);
            ResultSet rs = SQLStyle.executeQuery();

            if (rs.next()) {
                result = IDataAccessObj.parseRecToJson(rs, fields);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }
    }


