/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.Reponse;
import com.mycompany.myapp.services.ServiceReclamation;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class ReponseForm extends SideMenuBaseForm{

    ReponseForm(Resources res, Reclamation r) {
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
                                    new Label("Réponse Reclamation", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
      TextField Description = new TextField("", "Description", 50, TextField.ANY) ;
        Description.getAllStyles().setMargin(LEFT, 0);
        Button btnValider = new Button("Envoyer");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Description.getText().length()==0)
                      Dialog.show("Veuillez remplir les champs", "", "Ok", "Annuler");
                
                else
                {
                 
                        Reponse rep = new Reponse(r.getId(),Description.getText().toString());
                        if( ServiceReclamation.getInstance().addReponse(rep))
                        {
                            Dialog.show("Succes", "", "Ok", "Annuler");
                           new AfficherReclamationForm(res).show();
                        }else
                              Dialog.show("Error", "", "Ok", "Annuler");
                  
                }
                
                
            }
        });
     
        add(Description);
        add(btnValider);
        setupSideMenu(res);
    }

    
    
    
    
    @Override
    protected void showOtherForm(Resources res) {
       
    }
    
}
