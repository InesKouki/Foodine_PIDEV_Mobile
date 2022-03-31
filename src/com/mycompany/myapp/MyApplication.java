package com.mycompany.myapp;


import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Command;
import com.mycompany.myapp.gui.AfficherAvisAdminForm;
import com.mycompany.myapp.gui.AfficherAvisForm;
import com.mycompany.myapp.gui.AfficherReclamationForm;
import com.mycompany.myapp.gui.AfficherUtilisateurForm;
import com.mycompany.myapp.gui.AjouterReclamationForm;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.ProfileForm;
import com.mycompany.myapp.gui.showProfileForm;
import com.mycompany.myapp.services.SessionManager;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        if(SessionManager.getUserName()==null)
              new LoginForm(theme).show();
        else if (SessionManager.getEtat()==0){
            Dialog.show("Alert", "Vous etes bloqué", new Command("OK"));
             new LoginForm(theme).show();
        }
            
        else if( SessionManager.getRole() != null && SessionManager.getRole().contains("ROLE_ADMIN"))
            new AfficherUtilisateurForm(theme).show();
        else 
             try{
                 new showProfileForm(theme).show();
            }catch(IOException ex){
            Dialog.show("Error",ex.getMessage(),"OK",null);
        }
       
   
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

}
