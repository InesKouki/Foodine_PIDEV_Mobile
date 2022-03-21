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
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public class ServiceReview {
    
public ArrayList<Review> review;

    public static ServiceReview instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceReview() {
        req = new ConnectionRequest();
    }

    public static ServiceReview getInstance() {
        if (instance == null) {
            instance = new ServiceReview();
        }
        return instance;
    }
    
    //Fonction Ajout
     public boolean addReview(Review r) {
       String url = Statics.BASE_URL + "addRevJson?user_name="+r.getUser_name()+"&description="+r.getDescription()+"&stars="+r.getStars();
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
     
     
      public ArrayList<Review> parseReview(String jsonText){
        try {
            review=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Review t = new Review();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);

                  t.setUser_name(obj.get("UserName").toString());
                  float stars = Float.parseFloat(obj.get("Stars").toString());
                  t.setStars((int)stars);
                  t.setDescription(obj.get("Description").toString());
                review.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return review;
    }
       public ArrayList<Review> getAllReviews(){
        req = new ConnectionRequest();
        
        String url = Statics.BASE_URL+"showRevJson";
      
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                review = parseReview(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return review;
    }
       
     
       

     public boolean deleteReview(int i) {
        String url = Statics.BASE_URL + "deleteRevJson/" + i;
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