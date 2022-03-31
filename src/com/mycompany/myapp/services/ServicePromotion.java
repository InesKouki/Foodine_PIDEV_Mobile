package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.entities.Promotion;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicePromotion {
    public static ServicePromotion instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Promotion> events;
    
    public ServicePromotion() {
         req = new ConnectionRequest();
    }

    public static ServicePromotion getInstance() {
        if (instance == null) {
            instance = new ServicePromotion();
        }
        return instance;
    }
    
    public boolean addPromotion (Promotion a) {
        String url = Statics.BASE_URL+"/addPromotion?pourcentage="+a.getPourcentage(); //création de l'URL
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
    
    public boolean updatePromotion(Promotion ev) {
        String url = Statics.BASE_URL+"/updatePromotion?id="+ev.getId()+"&pourcentage="+ev.getPourcentage(); //création de l'URL
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
    
    public boolean deletePromotion(int id){
        String url = Statics.BASE_URL+"/deletePromotion?id="+id; //création de l'URL
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
    
    public ArrayList<Promotion> parseEvents(String jsonText){
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map <String,Object> eventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List <Map<String,Object>> list = (List<Map<String,Object>>)eventsListJson.get("root");
//            System.out.println(list);
            for(Map<String,Object> obj : list){
                Promotion ev = new Promotion();
                float id = Float.parseFloat(obj.get("id").toString());
                double pourcentage = (double) obj.get("pourcentage");
//                System.out.println("aaaaaaaaaaaaa" + obj.get("evnement"));
//                float evenement = Float.parseFloat(obj.get("evenement_id").toString());
//                float produit = Float.parseFloat(obj.get("produit_id").toString());
                
                ev.setId((int)id);
                ev.setPourcentage(pourcentage);
//                ev.setEvenement_id((int) evenement);
//                ev.setProduit_id((int) produit);
                
                events.add(ev);
//                System.out.println(events);
            }
            
            
        } catch (IOException ex) {   }
        return events;
    }
    public ArrayList<Promotion> getAllPromotions(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/promotions";
        System.out.println("===>"+url);
        req.setUrl(url);
//        req.setPost(false);
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
