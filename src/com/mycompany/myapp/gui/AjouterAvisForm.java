/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
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
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.services.ServiceReview;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class AjouterAvisForm extends SideMenuBaseFrontForm {
 public AjouterAvisForm(Resources res){
      
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
                                    new Label("Ajouter Avis", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        
        TextField UserName = new TextField("", "Nom", 50, TextField.ANY) ;
        TextField Description = new TextField("", "Description", 50, TextField.ANY) ;
        Label note = new Label("Stars: ");
         add(note);
         Slider s = new Slider();
         s.setMinValue(1);
         s.setMaxValue(6);
         s.setEditable(true);
         add(s);
         s.addDataChangedListener(new DataChangedListener(){
             @Override
             public void dataChanged(int type,int index){
                 note.setText("Note: "+s.getProgress());
             }
         });
        Description.getAllStyles().setMargin(LEFT, 0);
        Button btnValider = new Button("Envoyer");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((Description.getText().length()==0)|| (UserName.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                  
                        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
                       
                        
                        Review r = new Review(UserName.getText().toString(),format.format(new Date()),s.getProgress(),Description.getText().toString());
                        if( ServiceReview.getInstance().addReview(r))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                           new AfficherAvisForm(res).show();
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        
                         
                    
                }
                
                
            }
        });
     
        
        add(UserName);
        add(Description);
        
        add(btnValider);
        setupSideMenu(res);
 
    }
    
   
    

    @Override
    protected void showOtherForm(Resources res) {
       new ProfileForm(res).show();
    }

    
    
}
