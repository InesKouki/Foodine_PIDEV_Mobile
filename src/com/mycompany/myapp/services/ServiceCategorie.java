package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.utils.Statics;
//import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceCategorie {

    public static ServiceCategorie instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Categorie> events;

    public ServiceCategorie() {
        req = new ConnectionRequest();
    }

    public static ServiceCategorie getInstance() {
        if (instance == null) {
            instance = new ServiceCategorie();
        }
        return instance;
    }

    public boolean addEvent(Categorie a) {
        String url = Statics.BASE_URL + "/addCategory?name=" + a.getName(); //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean updateEvent(Categorie ev) {
        String url = Statics.BASE_URL + "/updateCateg?id=" + ev.getId() + "&name=" + ev.getName(); //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteEvent(int id) {
        String url = Statics.BASE_URL + "/deleteCateg?id=" + id; //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Categorie> parseEvents(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) eventsListJson.get("root");
//            System.out.println(list);
            for (Map<String, Object> obj : list) {
                Categorie ev = new Categorie();
                float id = Float.parseFloat(obj.get("id").toString());
                String name = obj.get("name").toString();
                String image = obj.get("image").toString();

                ev.setId((int) id);
                ev.setName(name);
                ev.setImage(image);
                
                events.add(ev);
//                System.out.println(events);
            }

        } catch (IOException ex) {
        }
        return events;
    }

    public ArrayList<Categorie> getAllEvents() {
        req = new ConnectionRequest();
        String url = Statics.BASE_URL + "/category";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }

}
