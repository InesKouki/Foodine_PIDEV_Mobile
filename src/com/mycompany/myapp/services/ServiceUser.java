/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.gui.ProfileForm;
import com.mycompany.myapp.gui.ResetPasswordForm;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class ServiceUser {
     public ArrayList<User> users;
    public static ServiceUser instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    String json;
    
    public ServiceUser() {
        req = new ConnectionRequest();
    }
    
     public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }
     
     
     
     public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");

            for (Map<String, Object> obj : list) {

                User u = new User();
                u.setId(((int) Float.parseFloat(obj.get("id").toString())));
                u.setNom(obj.get("nom").toString());
                u.setUsername(obj.get("username").toString());
                u.setPassword(obj.get("password").toString());
                u.setPrenom(obj.get("prenom").toString());
                u.setEmail(obj.get("email").toString());
                u.setRoles(obj.get("roles").toString());
              
                users.add(u);
            }

        } catch (IOException ex) {

        }
        return users;
    }
     
     
     public boolean signUp(TextField username, TextField password , TextField email , TextField confirm_password,
             TextField nom,TextField prenom){
         
String url = Statics.BASE_URL + "signUpJson?nom="+nom.getText()+"&prenom="+prenom.getText()+"&username="+username.getText()+"&email="+email.getText()+"&password="+password.getText();
       req.setUrl(url);
       
       
       req.addResponseListener((e)->{
                 byte[]data=(byte[])e.getMetaData();
                 String responseData = new String(data);
                 System.out.println("data ===>"+responseData);
             });
       
         NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
         
     }
     
     
     public void signIn(TextField username, TextField password,Resources res){
         String url = Statics.BASE_URL + "signInJson?username="+username.getText().toString()+"&password="+password.getText().toString();
       req.setUrl(url);
       req.addResponseListener((e)->{
            JSONParser j = new JSONParser();
             String json = new String(req.getResponseData()) + "";
            try{
            if(json.equals("failed")){
                Dialog.show("Echec d'authentification", "Verifier votre mot de passe ou username","Ok", null);
            }else {
                System.out.println("data=="+json);
                Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                if(user.size()>0)
                    new ProfileForm(res).show();
     
            }
            }catch(Exception ex)  {
                System.out.println(ex.getMessage());
            }
             });
       
       }
     
     
     public String ForgetPass(TextField email,Resources res){
         String url = Statics.BASE_URL+"oubliPassJSON?email="+email.getText();
       req.setUrl(url);
             req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
             json = new String(req.getResponseData()) + "";
            try {
                System.out.println("data =="+json);
                Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
      
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
   
}
}
