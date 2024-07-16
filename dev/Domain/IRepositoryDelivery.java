package Domain;
import com.google.gson.JsonObject;

import java.util.List;


public interface IRepositoryDelivery<T>{
    void add(JsonObject obj);
    void remove(int id);
    void update(T obj);
    List<T> getAll();
    T get(int id);
    boolean exists(int id);
}
