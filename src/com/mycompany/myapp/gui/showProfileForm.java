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
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.SessionManager;

/**
 *
 * @author Asus
 */
public class showProfileForm extends SideMenuBaseFrontForm {
     public showProfileForm(Resources res){
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
                                    new Label("Informations Profile", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
       Container box1 = BoxLayout.encloseX(new Label("Nom: "), new Label(SessionManager.getNom()));
       add(box1);
       Container box2 = BoxLayout.encloseX(new Label("Prenom: "), new Label(SessionManager.getPrenom()));
       add(box2);
       Container box3 = BoxLayout.encloseX(new Label("Username: "), new Label(SessionManager.getUserName()));
       add(box3);
       Container box4 = BoxLayout.encloseX(new Label("Email: "), new Label(SessionManager.getEmail()));
       add(box4);
       Container box5 = BoxLayout.encloseX(new Label("Adresse: "), new Label(SessionManager.getAdresse()));
       add(box5);
       Container box6 = BoxLayout.encloseX(new Label("Telephone: "), new Label(SessionManager.getTelephone()));
       add(box6);
        
       Button btnModifier1 = new Button("Modifier Informations");
       add(btnModifier1);
       Button btnModifier2 = new Button("Modifier Mot de passe");
       add(btnModifier2);
        btnModifier1.addActionListener(e -> {
            new UpdateProfileForm(res).show();
        });
        btnModifier2.addActionListener(e -> {
            new updatePasswordForm(res).show();
        });
      
        setupSideMenu(res);
}

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
