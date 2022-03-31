package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Planning;
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
public class serviceplanning {

    public ArrayList<Planning> tasks;

    public static serviceplanning instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public serviceplanning() {
        req = new ConnectionRequest();
    }

    public static serviceplanning getInstance() {
        if (instance == null) {
            instance = new serviceplanning();
        }
        return instance;
    }

    public ArrayList<Planning> parseTasks(String jsonText) {
        tasks = new ArrayList<>();
        JSONParser k = new JSONParser();
        try {

            Map<String, Object> tasksListJson
                    = k.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Planning t = new Planning();
                float id = Float.parseFloat(obj.get("id").toString());
                String nom = (obj.get("nom").toString());
                String date = (obj.get("date").toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                t.setId((int) id);
                t.setNom((String) nom);
                t.setDate(formatter.format(date));

                tasks.add(t);
            }

        } catch (IOException ex) {

        }
        return tasks;
    }

    public ArrayList<Planning> AffichePlanning() {
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String ur2 = Statics.BASE_URL + "/plan";
        System.out.println("===>" + ur2);
        req.setUrl(ur2);
        //System.out.println("*********>>>"+req);
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

    public boolean add(Planning p) {
        String url = Statics.BASE_URL + "/AjouterPlanningMobile?nom=" + p.getNom() + "&Date=" + p.getDate();

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
        String url = Statics.BASE_URL + "/DeletePlanning?id=" + t;
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

    public boolean updatePlan(Planning ev) {
        String url = Statics.BASE_URL + "/updatePlan?id=" + ev.getId() + "&nom=" + ev.getNom() + "&date=" + ev.getDate(); //création de l'URL
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
