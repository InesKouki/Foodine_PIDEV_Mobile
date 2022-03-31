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
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ServiceLivraison {

    public static ServiceLivraison instance;

    public ServiceLivraison() {
        req = new ConnectionRequest();
    }

    public static ServiceLivraison getInstance() {
        if (instance == null) {
            instance = new ServiceLivraison();
        }
        return instance;
    }
    public ArrayList<Livraison> reservations;
    private final ConnectionRequest req;
    public boolean resultOK;

    public boolean add(Livraison p) {
        String url = Statics.BASE_URL + "/Ajouterlivraison?addresse=" + p.getAddresse() + "&email=" + p.getEmail() + "&details=" + p.getDetails() + "&phone=" + p.getPhone() + "&codepostal=" + p.getCodepostal();

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
    
    public boolean update(Livraison p) {
        String url = Statics.BASE_URL + "/updateLivr?id=" + p.getId() + "&addresse=" + p.getAddresse() + "&email=" + p.getEmail() + "&details=" + p.getDetails() + "&phone=" + p.getPhone() + "&codepostal=" + p.getCodepostal();

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

    public ArrayList<Livraison> parseLivraison(String jsonText) {
        try {
            reservations = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> reservationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) reservationListJson.get("root");
            for (Map<String, Object> obj : list) {
                Livraison r = new Livraison();
                float id = Float.parseFloat(obj.get("id").toString());
                r.setId((int) id);
                r.setAddresse(obj.get("addresse").toString());
                r.setCodepostal(obj.get("codepostal").toString());
                r.setEmail(obj.get("email").toString());
                r.setPhone(obj.get("phone").toString());
                r.setDetails(obj.get("details").toString());
                reservations.add(r);
            }
        } catch (IOException ex) {
        }
        return reservations;
    }

    public ArrayList<Livraison> getAllreservations() {
        String url = Statics.BASE_URL + "/livraisons/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservations = parseLivraison(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservations;
    }

    public boolean deleteReclamation(int t) {
        String url = Statics.BASE_URL + "/Deletelivraison?id=" + t;
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
