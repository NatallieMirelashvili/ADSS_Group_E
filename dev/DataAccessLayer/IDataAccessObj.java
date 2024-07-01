package DataAccessLayer;

import com.google.gson.JsonObject;

import java.sql.SQLException;

public interface IDataAccessObj {

    public JsonObject search(int UniqueToSearch) throws SQLException;

    public void insert(JsonObject recordToAdd) throws SQLException;

    public void remove(int UniqueRemove) throws SQLException;

    public void createTable() throws SQLException;
}
