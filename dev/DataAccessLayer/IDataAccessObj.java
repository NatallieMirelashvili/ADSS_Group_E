package DataAccessLayer;

import com.google.gson.JsonObject;

public interface IDataAccessObj {

    public JsonObject search(int UniqueToSearch);

    public void insert(JsonObject recordToAdd);

    public void remove(int UniqueRemove);

    public void createTable();
}
