package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.utils.Statics;
//import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceProduit {

    public static ServiceProduit instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Produit> events;

    public ServiceProduit() {
        req = new ConnectionRequest();
    }

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }

    public boolean addEvent(Produit a) {
        String url = Statics.BASE_URL + "/addProd?name=" + a.getName() + "&price=" + a.getPrice() + "&quantitie=" + a.getQuantite(); //création de l'URL
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

    public boolean updateEvent(Produit ev) {
        String url = Statics.BASE_URL + "/updateProd?id=" + ev.getId() + "&name=" + ev.getName() + "&price=" + ev.getPrice() + "&quantitie=" + ev.getQuantite();//création de l'URL
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
        String url = Statics.BASE_URL + "/deleteProd?id=" + id; //création de l'URL
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

    public ArrayList<Produit> parseEvents(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) eventsListJson.get("root");
            System.out.println(list);
            for (Map<String, Object> obj : list) {
                Produit ev = new Produit();
                float id = Float.parseFloat(obj.get("id").toString());
                String name = obj.get("name").toString();
                float quantite = Float.parseFloat(obj.get("quantitie").toString());
                double price = (double) obj.get("price");
                String image = obj.get("image").toString();

                ev.setId((int) id);
                ev.setName(name);
                ev.setQuantite((int) quantite);
                ev.setPrice(price);
                ev.setImage(image);

                events.add(ev);
//                System.out.println(events);
            }

        } catch (IOException ex) {
        }
        return events;
    }

    public ArrayList<Produit> getAllEvents() {
        req = new ConnectionRequest();
        String url = Statics.BASE_URL + "/produits";
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
