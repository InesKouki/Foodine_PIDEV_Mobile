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
 * @author ASUS
 */
public abstract class SideMenuBaseFrontForm extends Form {

    public SideMenuBaseFrontForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseFrontForm(String title) {
        super(title);
    }

    public SideMenuBaseFrontForm() {
    }

    public SideMenuBaseFrontForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }
    
    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("duke.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("", profilePic, "");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");
        
        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("Accueil", FontImage.MATERIAL_DASHBOARD,  e -> new ProfileForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Reclamation", FontImage.MATERIAL_TRENDING_UP,  e -> new AjouterReclamationForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Avis", FontImage.MATERIAL_ACCESS_TIME,  e -> new AfficherAvisForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Account Settings", FontImage.MATERIAL_SETTINGS,  e -> new showProfileForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Deconnexion", FontImage.MATERIAL_EXIT_TO_APP,  e ->{
            new LoginForm(res).show();
            SessionManager.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
        
                });
    }
    
    protected abstract void showOtherForm(Resources res);
}
