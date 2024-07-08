package Domain;

import DataAccessLayer.SiteDAO;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SiteRepo implements IRepository<Site> {
    private static HashMap<Integer, Site> sites_d = new HashMap<Integer, Site>();
    private static SiteDAO siteDAO = new SiteDAO();

    @Override
    public void add(JsonObject obj) {
        siteDAO.add(obj);
    }

    @Override
    public void remove(int id) {
        siteDAO.remove(id);
        sites_d.remove(id);
    }

    @Override
    public void update(Site obj) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ID", obj.getSite_ID());
        jsonObject.addProperty("type", obj.get_type());
        jsonObject.addProperty("name", obj.getSite_name());
        jsonObject.addProperty("address", obj.getSite_address());
        jsonObject.addProperty("contacts_name", obj.getSite_contact_name());
        jsonObject.addProperty("phone_num", obj.getSite_contact_phone());
        jsonObject.addProperty("area", obj.getArea());
        siteDAO.update(jsonObject);
        sites_d.put(obj.getSite_ID(), obj);
    }

    @Override
    public List<Site> getAll() {
        List<Site> sites = new ArrayList<>();
        List<JsonObject> jsonSites = siteDAO.getAll();
        for (JsonObject site : jsonSites) {
            int ID = site.get("ID").getAsInt();
            String type = site.get("type").getAsString();
            String name = site.get("name").getAsString();
            String address = site.get("address").getAsString();
            String contacts_name = site.get("contacts_name").getAsString();
            String phone_num = site.get("phone_num").getAsString();
            String area = site.get("area").getAsString();
            Site new_site = new Site(ID, type, name, address, contacts_name, phone_num, area);
            sites_d.put(ID, new_site);
            sites.add(new_site);
        }
        return sites;
    }

    @Override
    public Site get(int id) {
        if (sites_d.get(id) != null) {
            return sites_d.get(id);
        } else {
            JsonObject site = siteDAO.get(id);
            int ID = site.get("ID").getAsInt();
            String type = site.get("type").getAsString();
            String name = site.get("name").getAsString();
            String address = site.get("address").getAsString();
            String contacts_name = site.get("contacts_name").getAsString();
            String phone_num = site.get("phone_num").getAsString();
            String area = site.get("area").getAsString();
            Site new_site = new Site(ID, type, name, address, contacts_name, phone_num, area);
            sites_d.put(id, new_site);
            return new_site;
        }
    }

    @Override
    public boolean exists(int id) {
        if (siteDAO.get(id) != null) {
            return true;
        }
        return false;
    }

    public void setArea(String new_area, int id) {
        if (sites_d.get(id) != null) {
            sites_d.get(id).setArea(new_area);
        } else {
            JsonObject site = siteDAO.get(id);
            int ID = site.get("ID").getAsInt();
            String type = site.get("type").getAsString();
            String name = site.get("name").getAsString();
            String address = site.get("address").getAsString();
            String contacts_name = site.get("contacts_name").getAsString();
            String phone_num = site.get("phone_num").getAsString();
            String area = site.get("area").getAsString();
            Site new_site = new Site(ID, type, name, address, contacts_name, phone_num, area);
            new_site.setArea(new_area);
            sites_d.put(id, new_site);
        }
    }
}
