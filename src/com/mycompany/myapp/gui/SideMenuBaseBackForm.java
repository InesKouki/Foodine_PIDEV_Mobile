/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.io.Storage;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.SessionManager;

/**
 *
 * @author Asus
 */
public abstract class SideMenuBaseBackForm extends Form {

    public SideMenuBaseBackForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseBackForm(String title) {
        super(title);
    }

    public SideMenuBaseBackForm() {
    }

    public SideMenuBaseBackForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }
    
    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("  Jennifer Wilson", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");
        
        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("Reclamation", FontImage.MATERIAL_TRENDING_UP,  e -> new AfficherReclamationForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Avis", FontImage.MATERIAL_ACCESS_TIME,  e -> new AfficherAvisAdminForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Utilisateurs", FontImage.MATERIAL_DASHBOARD,  e -> new AfficherUtilisateurForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP,  e ->{
            new LoginForm(res).show();
            SessionManager.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
        
                });
    }
    
    protected abstract void showOtherForm(Resources res);
}