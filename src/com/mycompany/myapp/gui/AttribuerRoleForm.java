/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
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
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceReclamation;
import com.mycompany.myapp.services.ServiceUser;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class AttribuerRoleForm extends SideMenuBaseBackForm {

     public AttribuerRoleForm(Resources res,User t) {
   
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
                                    new Label("Attribuer Role", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
       
       
        setupSideMenu(res);
        
        
        ComboBox<String> Type = new ComboBox<>();
        Type.addItem("Chef");
        Type.addItem("Livreur");
        Type.setSelectedIndex(0);
        Button btnValider = new Button("Valider");
        
       String role = Type.getSelectedItem();
       btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                           
                                t.setRoles(Type.getSelectedItem());
                        if( ServiceUser.getInstance().roleUser(t))    
               {
                   Dialog.show("Success","Role attribué avec succès",new Command("OK"));
                        new AfficherUtilisateurForm(res).show();
               }
                        
                        else
                        {
                           Dialog.show("ERROR", "Server error", new Command("OK"));  
                        }
         
             }
        }); 
       
       addAll(Type,btnValider);
     }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
} 

