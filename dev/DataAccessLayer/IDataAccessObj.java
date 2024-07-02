package DataAccessLayer;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDataAccessObj {

    public JsonObject search(int UniqueToSearch);

    public void insert(JsonObject recordToAdd);

    public void remove(int UniqueRemove);

    public void createTable();

    static String innerQuery(int numOFUniques) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Item WHERE id IN (");
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
    }


