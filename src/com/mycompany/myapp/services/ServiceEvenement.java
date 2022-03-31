package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.utils.Statics;
//import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceEvenement {

    public static ServiceEvenement instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Evenement> events;

    public ServiceEvenement() {
        req = new ConnectionRequest();
    }

    public static ServiceEvenement getInstance() {
        if (instance == null) {
            instance = new ServiceEvenement();
        }
        return instance;
    }

    public boolean addEvent (Evenement a) {
        String url = Statics.BASE_URL+"/addEvent?name="+a.getName()+"&date_deb="+a.getDate_deb()+"&date_fin="+a.getDate_fin()+"&description="+a.getDescription(); //création de l'URL
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
    
    public boolean updateEvent(Evenement ev) {
        String url = Statics.BASE_URL+"/updateEvent?id="+ev.getId()+"&name="+ev.getName()+"&date_deb="+ev.getDate_deb()+"&date_fin="+ev.getDate_fin()+"&description="+ev.getDescription()+"&image="+ev.getImage(); //création de l'URL
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
    
    public boolean deleteEvent(int id){
        String url = Statics.BASE_URL+"/deleteEvent?id="+id; //création de l'URL
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
    
    public ArrayList<Evenement> parseEvents(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) eventsListJson.get("root");
//            System.out.println(list);
            for (Map<String, Object> obj : list) {
                Evenement ev = new Evenement();
                float id = Float.parseFloat(obj.get("id").toString());
                String name = obj.get("name").toString();
                String description = obj.get("description").toString();
                String image = obj.get("image").toString();
                String dateDeb = obj.get("dateDeb").toString();
                String dateFin = obj.get("dateFin").toString();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateDebString = formatter.format(dateDeb);
                String dateFinString = formatter.format(dateFin);

//                String DateConverter = obj.get("dateDeb").toString().substring(obj.get("dateDeb").toString().indexOf("timestamp") + 10, obj.get("dateDeb").toString().lastIndexOf(")"));   
//                String DateConverter2 = obj.get("dateFin").toString().substring(obj.get("dateFin").toString().indexOf("timestamp") + 10, obj.get("dateFin").toString().lastIndexOf(")"));    
//                Date dateDeb = new Date (Double.valueOf(DateConverter).longValue() * 1000);
//                Date dateFin = new Date (Double.valueOf(DateConverter2).longValue() * 1000);
                ev.setId((int) id);
                ev.setName(name);
                ev.setDescription(description);
                ev.setDate_deb(dateDebString);
                ev.setDate_fin(dateFinString);
                ev.setImage(image);

                events.add(ev);
//                System.out.println(events);
            }

        } catch (IOException ex) {
        }
        return events;
    }

    public ArrayList<Evenement> getAllEvents() {
        req = new ConnectionRequest();
        String url = Statics.BASE_URL + "/events";
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
