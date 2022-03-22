/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author Asus
 */
public class ResetPasswordForm extends Form {
  public ResetPasswordForm(Resources theme,User r) {
       super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Modifier Mot De Passe ", "WelcomeWhite"),
                new Label("", "WelcomeBlue")
        );
         getTitleArea().setUIID("Container");
        
         TextField code = new TextField("", "Code", 20, TextField.ANY) ;
          TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
          TextField confirm_password = new TextField("", "Password", 20, TextField.PASSWORD) ;
          Button sendButton = new Button("Envoyer");
          Button RetourButton = new Button("Retour");
          
         sendButton.setUIID("CreateNewAccountButton");
         RetourButton.setUIID("CreateNewAccountButton");
          RetourButton.addActionListener(e -> {
             new LoginForm(theme).show();
        });
          
          
         sendButton.addActionListener((e)->{
           if(code.getText().length()==0 || password.getText().length()==0&&confirm_password.getText().length()==0){
           
           Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
       }else if(!password.getText().equals(confirm_password.getText())){
            Dialog.show("Erreur","Verifiez votre mot de passe","OK",null);
       }else{
            ServiceUser.getInstance().resetPass(code,password);
            Dialog.show("Success", "Mot de passe modifié avec sucess", "Ok", null);
             new LoginForm(theme).show();
       }
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
                code,
                password,
                confirm_password,
                sendButton,
                RetourButton
               
        );
        add(BorderLayout.CENTER, by);
       
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
    }