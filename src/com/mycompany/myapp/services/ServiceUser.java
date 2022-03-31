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
import static com.codename1.processing.Result.JSON;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.gui.AfficherUtilisateurForm;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.ProfileForm;
import com.mycompany.myapp.gui.ResetPasswordForm;
import com.mycompany.myapp.gui.showProfileForm;
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
                u.setEtat(((int) Float.parseFloat(obj.get("Etat").toString())));
                u.setNom(obj.get("Nom").toString());
                u.setUsername(obj.get("Username").toString());
                //u.setPassword(obj.get("password").toString());
                u.setPrenom(obj.get("Prenom").toString());
                u.setEmail(obj.get("email").toString());
                u.setRoles(obj.get("Roles").toString());
              
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
         String url = Statics.BASE_URL + "signInJson?username="+username.getText()+"&password="+password.getText();
        req = new ConnectionRequest(url, false); 
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
            
            if(json.equals("Utilisteur invalide")) {
                Dialog.show("Echec d'authentification","Username éronné","OK",null);
            }else if (json.equals("Mot de passe invalide"))
                Dialog.show("Echec d'authentification"," mot de passe éronné","OK",null);
            else {
                System.out.println("data =="+json);
                
                 Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
  
                if(user.size() >0 ){     
                float id = Float.parseFloat(user.get("id").toString());
                SessionManager.setId((int)id);
                SessionManager.setPassowrd(user.get("Password").toString());
                SessionManager.setUserName(user.get("Username").toString());
                SessionManager.setEmail(user.get("email").toString());
                SessionManager.setRole(user.get("Roles").toString());
                SessionManager.setNom(user.get("Nom").toString());
                SessionManager.setPrenom(user.get("Prenom").toString());
                float etat = Float.parseFloat(user.get("Etat").toString());
                SessionManager.setEtat((int)etat);
                
                  if(user.get("phone")!=null)
                  {
                      SessionManager.setTelephone(user.get("phone").toString());
                
                  }
                   if(user.get("Address")!=null)
                  {
                     SessionManager.setAdresse(user.get("Address").toString());
                  }
                 if(user.get("file") != null)
                    SessionManager.setFile(user.get("file").toString());
                
                System.out.println(SessionManager.getUserName()+SessionManager.getId()+SessionManager.getRole()+SessionManager.getRole().contains("ROLE_ADMIN")+SessionManager.getTelephone()+SessionManager.getFile()+SessionManager.getEtat());
                
                    if (SessionManager.getUserName()==null)
                     new LoginForm(res).show(); 
                  if (SessionManager.getUserName()!= null && SessionManager.getRole().contains("ROLE_ADMIN"))
                      new AfficherUtilisateurForm(res).show();
                  else if (SessionManager.getUserName()!= null && SessionManager.getRole().contains("ROLE_USER")&& SessionManager.getEtat()==1)
                       new showProfileForm(res).show();
                  else if (SessionManager.getUserName()!= null && SessionManager.getRole().contains("ROLE_USER")&& SessionManager.getEtat()==0)
                  {
                        Dialog.show("Alert", "Vous etes bloqué", new Command("OK"));
                        new LoginForm(res).show(); 
                  }
                  
                }
                    }
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    

        NetworkManager.getInstance().addToQueueAndWait(req);
    }
     
     
     public String ForgetPass(TextField email,Resources res){
         String url = Statics.BASE_URL+"oubliPassJSON?email="+email.getText();
       req.setUrl(url);
             req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
             json = new String(req.getResponseData()) + "";
             System.out.println("data =="+json);
     
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
   
}
        public Boolean resetPass(TextField code,TextField password){
         String url = Statics.BASE_URL+"resetPasswordJSON/"+code.getText()+"/"+password.getText();
       req.setUrl(url);
       
       
       req.addResponseListener((e)->{
                 byte[]data=(byte[])e.getMetaData();
                 String responseData = new String(data);
                 System.out.println("data ===>"+responseData);
             });
       
         NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
   
}
     
      public Boolean updatePass(int id,TextField password){
         String url = Statics.BASE_URL+"updatePassJson?id="+id+"&password="+password.getText();
       req.setUrl(url);
       
       
       req.addResponseListener((e)->{
                 byte[]data=(byte[])e.getMetaData();
                 String responseData = new String(data);
                 System.out.println("data ===>"+responseData);
             });
       
         NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
   
}
      
       public Boolean updateprofile(int id,TextField nom,TextField prenom,TextField email,TextField telephone,TextField adresse){
         String url = Statics.BASE_URL+"editUserJson?id="+id+"&nom="+nom.getText()+"&prenom="+prenom.getText()+"&email="+email.getText()+"&telephone="+telephone.getText()+"&adresse="+adresse.getText();
       req.setUrl(url);
       
       
       req.addResponseListener((e)->{
                 byte[]data=(byte[])e.getMetaData();
                 String responseData = new String(data);
                 System.out.println("data ===>"+responseData);
             });
       
       
               
                SessionManager.setEmail(email.getText());
                SessionManager.setTelephone(telephone.getText());
                SessionManager.setAdresse(adresse.getText());
                SessionManager.setNom(nom.getText());
                SessionManager.setPrenom(prenom.getText());
       
         NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
   
}
     
     
     public ArrayList<User> getAllUsers(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"showUserJson";
      
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
     
     
    public ArrayList<User> getAllUsersTriNom(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"showUserTriNomJson";
      
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    } 
    
    public ArrayList<User> getAllUsersTriEmail(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"showUserTriEmailJson";
      
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    } 
     
     
      public boolean bloquerUser(int i) {
        String url = Statics.BASE_URL + "blockUserJson/" + i;
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
      
      public boolean debloquerUser(int i) {
        String url = Statics.BASE_URL + "unblockUserJson/" + i;
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
   
    public boolean deleteUser(int id) {
      String url = Statics.BASE_URL + "deleteUserJson?id=" + id;
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
    
    public boolean roleUser(User t) { 
        String url = Statics.BASE_URL + "roleJson?id="+t.getId()+"&Role="+t.getRoles();

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
