package Domain;

import DataAccessLayer.DriverDAO;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DriverRepo implements IRepository<Driver> {
    private static HashMap<Integer, Driver> drivers_d = new HashMap<Integer, Driver>();
    private static DriverDAO driverDAO = new DriverDAO();

    @Override
    public void add(JsonObject obj) {
        driverDAO.add(obj);
    }

    @Override
    public void remove(int id) {
        drivers_d.remove(id);
    }

    @Override
    public void update(Driver obj) {
        drivers_d.put(obj.getID(), obj);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ID", obj.getID());
        jsonObject.addProperty("name", obj.getName());
        jsonObject.addProperty("licence", obj.getLicense());
        jsonObject.addProperty("phone_num", obj.getPhone_num());
        jsonObject.addProperty("password", obj.getPassword());
        driverDAO.update(jsonObject);
    }


    @Override
    public List<Driver> getAll() {
        List<Driver> drivers = new ArrayList<Driver>();
        for (JsonObject driver : driverDAO.getAll()) {
            String ID = driver.get("ID").getAsString();
            String name = driver.get("name").getAsString();
            String licence = driver.get("licence").getAsString();
            String phone_num = driver.get("phone_num").getAsString();
            String password = driver.get("password").getAsString();
            Driver new_driver = new Driver(Integer.parseInt(ID), name, licence, phone_num, Integer.parseInt(password));
            drivers.add(new_driver);
            drivers_d.put(new_driver.getID(), new_driver);
        }
        return drivers;
    }

    @Override
    public Driver get(int id) {
        Driver getter = drivers_d.get(id);
        if (getter == null) {
            JsonObject driver = driverDAO.get(id);
            String ID = driver.get("ID").getAsString();
            String name = driver.get("name").getAsString();
            String licence = driver.get("licence").getAsString();
            String phone_num = driver.get("phone_num").getAsString();
            String password = driver.get("password").getAsString();
            getter = new Driver(Integer.parseInt(ID), name, licence, phone_num, Integer.parseInt(password));
            drivers_d.put(getter.getID(), getter);
        }
        return getter;
    }

    @Override
    public boolean exists(int id) {
        if (driverDAO.get(id) != null) {
            return true;
        }
        return false;
    }

}
