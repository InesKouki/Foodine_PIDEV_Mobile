/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.table;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ASUS
 */
public class servicetable {
     public ArrayList<table> tables;
    
    public static servicetable instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public servicetable() {
         req = new ConnectionRequest();
    }
    
    public static servicetable getInstance() {
        if (instance == null) {
            instance = new servicetable();
        }
        return instance;
    }
    
    /*public boolean add(table p){
     String url=statics.BASE_URL+"/add?addresse="+p.getEtat()+"&email="+p.getImagetable()+"&details="+p.getNbplacetable()+"&phone="+p.getNumerotable();
           
     req.setUrl(url);
     req.addResponseListener(new ActionListener <NetworkEvent>(){
     @Override
     public void actionPerformed(NetworkEvent evt){
      resultOK=req.getResponseCode()==200;
     req.removeResponseListener(this); }
     });
     NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;

} */
    
    
    
    
    
   public boolean add (table a) {
        String url = Statics.BASE_URL+"/add?numerotable="+a.getNumerotable()+"&nbplacetable="+a.getNbplacetable()+"&imagetable="+a.getImagetable()+"&etat="+a.getEtat(); //création de l'URL
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
 
   public boolean modifier (table a) {
        String url = Statics.BASE_URL+"/updatemobil?id="+a.getId()+"&numerotable="+a.getNumerotable()+"&imagetable="+a.getImagetable()+"&nbplacetable="+a.getNbplacetable()+"&etat="+a.getEtat(); //création de l'URL
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

    
     public ArrayList<table> parseTasks(String jsonText){
        try {
            tables=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                table t = new table();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                 float numerotable = Float.parseFloat(obj.get("numerotable").toString());
                t.setNumerotable((int)numerotable);
                float nbplacetable = Float.parseFloat(obj.get("nbplacetable").toString());
                t.setNbplacetable((int)nbplacetable);
                
                 if (obj.get("imagetable")==null)
                t.setImagetable("null");
                else
                    t.setImagetable(obj.get("imagetable").toString());
                 
                 if (obj.get("etat")==null)
                t.setEtat("null");
                else
                    t.setEtat(obj.get("etat").toString());
                
                tables.add(t);
                System.out.println(tables);
            }
            
            
        } catch (IOException ex) {   }
        return tables;
    }
public ArrayList<table> getAllTasks(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/tt";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tables = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tables;
    }

public boolean delete(int t) {
        String url = Statics.BASE_URL + "/deletetable?id="+t;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
 

