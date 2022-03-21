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
import com.mycompany.myapp.entities.Reclamation;
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
public class ServiceReclamation {
    public ArrayList<Reclamation> reclamation;

    public static ServiceReclamation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceReclamation() {
        req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }
    
    //Fonction Ajout
     public boolean addReclamation(Reclamation r) {
       String url = Statics.BASE_URL + "addRecJson?type="+r.getType()+"&description="+r.getDescription();
       req.setUrl(url);
    
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     
     
      public ArrayList<Reclamation> parseReclamation(String jsonText){
        try {
            reclamation=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Reclamation t = new Reclamation();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                // float idUser = Float.parseObject(obj.get("user_id").toString());
               // t.setId((int)idUser);
                  /*String DateConverter = obj.get("created_at").toString().substring(obj.get("created_at").toString().indexOf("timestamp")+10,obj.get("created_at").toString().lastIndexOf("}"));
                  Date currentTime = new Date(Double.valueOf(DateConverter).longValue()*1000);
                  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                  String dateString = formatter.format(currentTime);
                  t.setCreated_at(dateString);*/
                
                  t.setType(obj.get("Type").toString());
                  float etat = Float.parseFloat(obj.get("Etat").toString());
                  t.setEtat((int)etat);
                  t.setDescription(obj.get("Description").toString());
                reclamation.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return reclamation;
    }
       public ArrayList<Reclamation> getAllReclamations(){
        req = new ConnectionRequest();
        
        String url = Statics.BASE_URL+"showRecJson";
      
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamation = parseReclamation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamation;
    }
       
     
       public Reclamation DetailReclamaion(int id , Reclamation reclamation){
           String url = Statics.BASE_URL + "detailRecJson?id="+id;
       req.setUrl(url);
       String str = new String(req.getResponseData()) ;
       req.addResponseListener(((evt)->{
            JSONParser j = new JSONParser();
            try{
                  Map<String,Object>obj = j.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                  reclamation.setDescription(obj.get("description").toString());
                  reclamation.setType(obj.get("type").toString());
                  reclamation.setEtat(Integer.parseInt(obj.get("etat").toString()));
                  
                  
            }catch(IOException ex){
                System.out.println("Error related to sql: {"+ex.getMessage());
            }
            System.out.println("data ==="+str);
           
       }));
       NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamation;
       
       }

     public boolean deleteReclamation(int i) {
        String url = Statics.BASE_URL + "deleteRecJson/" + i;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
       
     
}