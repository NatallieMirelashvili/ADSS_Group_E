package DataAccessLayer;


import com.google.gson.JsonObject;

import java.util.List;

public interface IDAO {
    void add(JsonObject jsonObject);
    void remove(int id);
    void update(JsonObject jsonObject);
    List<JsonObject> getAll();
    JsonObject get(int id);
}