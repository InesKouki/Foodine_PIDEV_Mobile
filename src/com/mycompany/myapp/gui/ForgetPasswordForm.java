/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author Asus
 */
public class ForgetPasswordForm extends Form {

    ForgetPasswordForm(Resources theme) {
       super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Récupérer Mot De Passe ", "WelcomeWhite"),
                new Label("", "WelcomeBlue")
        );
         getTitleArea().setUIID("Container");
        
         TextField email = new TextField("", "Email", 20, TextField.EMAILADDR) ;
          Button sendButton = new Button("Envoyer");
          Button RetourButton = new Button("Retour");
          
         sendButton.setUIID("CreateNewAccountButton");
         RetourButton.setUIID("CreateNewAccountButton");
          RetourButton.addActionListener(e -> {
           
            
             new LoginForm(theme).show();
        });
          
          sendButton.addActionListener(e -> {
            ServiceUser.getInstance().ForgetPass(email,theme);
             new ResetPasswordForm(theme).show();
        });
          
          Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                
                spaceLabel,
                email,
                sendButton,
                RetourButton
               
        );
        add(BorderLayout.CENTER, by);
       
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
    }
    

