/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author ASUS
 */
public class SignUpForm extends Form {
     public SignUpForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Nouvelle, ", "WelcomeWhite"),
                new Label("Inscription", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
        TextField prenom = new TextField("", "Nom", 20, TextField.ANY) ;
        TextField nom = new TextField("", "Prenom", 20, TextField.ANY) ;
        TextField username = new TextField("", "Username", 20, TextField.ANY) ;
        TextField email = new TextField("", "Email", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        TextField confirm_password = new TextField("", "Confirm Password", 20, TextField.PASSWORD) ;
        
        username.getAllStyles().setMargin(LEFT, 0);
        nom.getAllStyles().setMargin(LEFT, 0);
        prenom.getAllStyles().setMargin(LEFT, 0);
        email.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        
        
        Button signUp = new Button("SignUp");
        signUp.setUIID("LoginButton");
        signUp.addActionListener((e)->{
            ServiceUser.getInstance().signUp(username,password,email,confirm_password,nom,prenom);
            Dialog.show("Success", "Compte crée avec sucess", "Ok", null);
             new LoginForm(theme).show();
        });
        
        Button signIn = new Button("Already have an account?");
        signIn.setUIID("CreateNewAccountButton");
        signIn.addActionListener(e -> {
            new LoginForm(theme).show();
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
                prenom,
                nom,
                username,
                email,
                password,
                confirm_password,
                signUp,
                signIn
               
        );
        add(BorderLayout.CENTER, by);
       
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
