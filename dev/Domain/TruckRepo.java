package Domain;

import DataAccessLayer.TruckDAO;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

public class TruckRepo implements IRepository<Truck>{
    private static HashMap<Integer, Truck> trucks_d = new HashMap<Integer, Truck>();
    private static TruckDAO truckDAO = new TruckDAO();

    @Override
    public void add(JsonObject obj) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void remove(JsonObject obj) {

    }

    @Override
    public void update(Truck obj) {

    }

    @Override
    public List<Truck> getAll() {

    }

    @Override
    public Truck get(int id) {

    }

    @Override
    public boolean exists(int id) {
        if (truckDAO.get(id) != null) {
            return true;
        }
        return false;
    }

    public void update_weight(int id, double weight) {
        if (trucks_d.containsKey(id)) {
            trucks_d.get(id).setCurr_weight(weight);
        }
        else {
            JsonObject truckjo = truckDAO.get(id);
            Truck truck = new Truck(truckjo.get("ID").getAsInt(),truckjo.get("model").getAsString(),truckjo.get("max_weight").getAsDouble(), truckjo.get("licence").getAsString());
            truck.setCurr_weight(weight);
            trucks_d.put(id, truck);
        }
        truckDAO.updateCurrWeight(id, weight);
    }
}
