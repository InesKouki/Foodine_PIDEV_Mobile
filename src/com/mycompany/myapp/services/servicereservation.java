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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.reservation;
import com.mycompany.myapp.entities.table;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class servicereservation {
    public ArrayList<reservation> tasks;
    
    public static servicereservation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public servicereservation() {
         req = new ConnectionRequest();
    }

    public static servicereservation getInstance() {
        if (instance == null) {
            instance = new servicereservation();
        }
        return instance;
    }
     public ArrayList<reservation> parseTasks(String jsonText){
         tasks=new ArrayList<>();
            JSONParser k = new JSONParser();
         try {
           
            Map<String,Object> tasksListJson = 
            k.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                reservation t = new reservation();
                 float id = Float.parseFloat(obj.get("id").toString());
                t.setIdd((int)id);
                

                //tableid:::
                /*float tableid = Float.parseFloat(obj.get("tableid").toString());
                t.setTableid((int)tableid);*/
                
                
                
                 //date:::::
                /* String dateconverter = obj.get("datereservation").toString().substring(obj.get("datereservation").toString().indexOf("datereservation")+10 ,obj.get("datereservation").toString().lastIndexOf("}") );
                 Date currenttime = new Date(Double.valueOf(dateconverter).longValue()*1000);
                 SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                 String dateString = formatter.format(currenttime);
                 t.setDatereservation(dateString);*/
                 
                 String datereservation = (obj.get("datereservation").toString());
                 t.setDatereservation(datereservation);
                 
                 if (obj.get("email")==null)
                t.setEmail("null");
                else
                    t.setEmail(obj.get("email").toString());
                 
                  if (obj.get("nom")==null)
                t.setNom("null");
                else
                    t.setNom(obj.get("nom").toString());
                 
                 
                 
                float mobile = Float.parseFloat(obj.get("mobile").toString());
                t.setMobile((int)mobile);
         
                tasks.add(t);
                
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
     
      public ArrayList<reservation> getreservation(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String ur2 = Statics.BASE_URL+"/rr/";
        System.out.println("===>"+ur2);
        req.setUrl(ur2);
        //System.out.println("*********>>>"+req);
        req.setPost(false);
          
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                tasks = parseTasks(new String(req.getResponseData()));
                System.out.println("--------->>>>"+tasks);
                req.removeResponseListener(this);
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    
     public boolean addd (reservation a) {
        String url = Statics.BASE_URL+"/hh?nom="+a.getNom()+"&mobile="+a.getMobile()+"&email="+a.getEmail(); //création de l'URL
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
   public boolean delete(int m) {
        String url = Statics.BASE_URL + "/deletereservationnnn?id="+m;
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
