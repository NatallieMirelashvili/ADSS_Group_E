package Domain;

import DataAccessLayer.TruckDAO;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TruckRepo implements IRepositoryDelivery<Truck> {
    private static HashMap<Integer, Truck> trucks_d = new HashMap<Integer, Truck>();
    private static TruckDAO truckDAO = new TruckDAO();

    @Override
    public void add(JsonObject obj) {
        truckDAO.add(obj);
    }

    @Override
    public void remove(int id) {
        trucks_d.remove(id);
        truckDAO.remove(id);
    }

    @Override
    public void update(Truck obj) {

    }

    @Override
    public List<Truck> getAll() {
        List<JsonObject> jsonTrucks = truckDAO.getAll();
        List<Truck> trucks = new ArrayList<>();
        for (JsonObject jsonTruck : jsonTrucks) {
            int id = jsonTruck.get("ID").getAsInt();
            String model = jsonTruck.get("model").getAsString();
            double max_weight = jsonTruck.get("max_weight").getAsDouble();
            String licence = jsonTruck.get("licence").getAsString();
            Truck truck = new Truck(id, model, max_weight, licence);
            trucks.add(truck);
            trucks_d.put(id, truck);
        }
        return trucks;
    }

    @Override
    public Truck get(int id) {
        if (trucks_d.containsKey(id)) {
            return trucks_d.get(id);
        }
        JsonObject jsonTruck = truckDAO.get(id);
        if (jsonTruck != null) {
            int truckId = jsonTruck.get("ID").getAsInt();
            String model = jsonTruck.get("model").getAsString();
            double max_weight = jsonTruck.get("max_weight").getAsDouble();
            String licence = jsonTruck.get("licence").getAsString();
            Truck truck = new Truck(truckId, model, max_weight, licence);
            trucks_d.put(truckId, truck);
            return truck;
        }
        return null;
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
