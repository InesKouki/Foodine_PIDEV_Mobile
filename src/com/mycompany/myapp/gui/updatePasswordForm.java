/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
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
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.services.ServiceReview;
import com.mycompany.myapp.services.ServiceUser;
import com.mycompany.myapp.services.SessionManager;

/**
 *
 * @author Asus
 */
public class updatePasswordForm extends SideMenuBaseFrontForm{
    public updatePasswordForm(Resources res){
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
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        TextField confirm_password = new TextField("", "Confirm Password", 20, TextField.PASSWORD) ;
        add(password);
        add(confirm_password);
        Button btnAjouter = new Button("Confirmer");
        add(btnAjouter);
        btnAjouter.addActionListener((e)->{
           if( password.getText().length()==0&&confirm_password.getText().length()==0){
           
           Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
       }else if(!password.getText().equals(confirm_password.getText())){
            Dialog.show("Erreur","Verifiez votre mot de passe","OK",null);
       }else{
            ServiceUser.getInstance().updatePass(SessionManager.getId(),password);
            Dialog.show("Success", "Mot de passe modifié avec succes", "Ok", null);
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
