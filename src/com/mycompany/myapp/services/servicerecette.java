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
import com.mycompany.myapp.entities.Recette;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class servicerecette {

    public ArrayList<Recette> tasks;

    public static servicerecette instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public servicerecette() {
        req = new ConnectionRequest();
    }

    public static servicerecette getInstance() {
        if (instance == null) {
            instance = new servicerecette();
        }
        return instance;
    }

    public ArrayList<Recette> parseTasks(String jsonText) {
        tasks = new ArrayList<>();
        JSONParser k = new JSONParser();
        try {

            Map<String, Object> tasksListJson
                    = k.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Recette t = new Recette();
                float id = Float.parseFloat(obj.get("id").toString());
                String nom = (obj.get("nom").toString());
                String description = (obj.get("description").toString());
                String ingredient = (obj.get("ingredient").toString());
                String image = (obj.get("imagerecette").toString());
                t.setId((int) id);
                t.setNom((String) nom);
                t.setDescription((String) description);
                t.setIngredient((String) ingredient);
                t.setImage((String) image);
                tasks.add(t);
            }

        } catch (IOException ex) {

        }
        return tasks;
    }

    public ArrayList<Recette> AfficheRecette() {
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String ur2 = Statics.BASE_URL + "/rec";
        System.out.println("===>" + ur2);
        req.setUrl(ur2);
        System.out.println("*********>>>"+req);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                tasks = parseTasks(new String(req.getResponseData()));
                System.out.println("--------->>>>" + tasks);
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    public boolean add(Recette p) {
        String url = Statics.BASE_URL + "/AjouterRecetteMobile?nom=" + p.getNom() + "&description=" + p.getDescription()+ "&ingredient=" + p.getIngredient();

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
    public boolean deleteReclamation(int t) {
        String url = Statics.BASE_URL + "/DeleteRecette?id="+t;
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
    
    public boolean updateRec(Recette ev) {
        String url = Statics.BASE_URL+"/updateRec?id="+ev.getId()+"&nom="+ev.getNom()+"&description="+ev.getDescription()+"&ingredient="+ev.getIngredient(); //création de l'URL
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
}
