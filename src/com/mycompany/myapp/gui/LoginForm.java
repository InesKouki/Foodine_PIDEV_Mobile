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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author ASUS
 */
public class LoginForm extends Form {
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenue ", "WelcomeWhite"),
                new Label("", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
     
        
        TextField login = new TextField("", "Login", 20, TextField.ANY) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(new ActionListener()  {
             @Override
            public void actionPerformed(ActionEvent evt) {
          if(login.getText().length()==0 || password.getText().length()==0){
           
           Dialog.show("Erreur","Veuillez remplir les champs","OK",null);  
          }else {
                   ServiceUser.getInstance().signIn(login,password,theme);
                
                 
          }
            }
        });
        
        Button createNewAccount = new Button("Cr??er un nouveau compte");
        Button forgetPassword = new Button("Mot de passe oubli??");
        createNewAccount.addActionListener(e -> {
            new SignUpForm(theme).show();
        });
        createNewAccount.setUIID("CreateNewAccountButton");
        forgetPassword.setUIID("CreateNewAccountButton");
        
         forgetPassword.addActionListener(e -> {
            new ForgetPasswordForm(theme).show();
        });
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
              
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount,
                forgetPassword
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
