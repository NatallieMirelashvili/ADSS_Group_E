package DomainLayer;

import com.google.gson.JsonObject;

public interface IRepository<T> {

     T find(int unique);
    T add(JsonObject newRec);
    T remove(int unique);
}
