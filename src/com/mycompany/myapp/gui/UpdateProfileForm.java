/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceUser;
import com.mycompany.myapp.services.SessionManager;

/**
 *
 * @author ASUS
 */
//public class UpdateProfileForm extends SideMenuBaseForm {
    

public class UpdateProfileForm extends SideMenuBaseFrontForm{
    public UpdateProfileForm(Resources res){
       super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Modifier Mot de passe", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
       tb.setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
        TextField nom = new TextField(SessionManager.getNom(), "Nom", 20, TextField.ANY) ;
        TextField prenom = new TextField(SessionManager.getPrenom(), "Prenom", 20, TextField.ANY) ;
        TextField email = new TextField(SessionManager.getEmail(), "Email", 20, TextField.EMAILADDR) ;
        TextField telephone = new TextField("", "Telephone", 20, TextField.ANY) ;
        TextField adresse = new TextField("", "Adresse", 20, TextField.ANY) ;
        add(nom);
         add(prenom);
          add(email); 
           add(telephone);
           add(adresse);
          
        Button btnAjouter = new Button("Confirmer");
        add(btnAjouter);
        btnAjouter.addActionListener((e)->{
           if( adresse.getText().length()==0&&telephone.getText().length()==0){
           
           Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
       
       }else{
            ServiceUser.getInstance().updateprofile(SessionManager.getId(),nom,prenom,email,telephone,adresse);
            Dialog.show("Success", "Compte modifié  avec sucess", "Ok", null);
             new showProfileForm(res).show();
       }
        });
        setupSideMenu(res);
 }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}