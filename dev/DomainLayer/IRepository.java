package DomainLayer;

import com.google.gson.JsonObject;

public interface IRepository {

    public <T> T find(int unique);
    public <T> T add(JsonObject newRec);
    public <T> T remove(int unique);
}
