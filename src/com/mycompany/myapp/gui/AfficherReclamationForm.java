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
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class AfficherReclamationForm extends SideMenuBaseBackForm{
    
   public AfficherReclamationForm(Resources res){
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
                                    new Label("Listes des Reclamation", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
       
        SpanLabel sp = new SpanLabel();
        for (Reclamation r : ServiceReclamation.getInstance().getAllReclamations()) {
            add(addItem(r,res));
        }
        add(sp);
        setupSideMenu(res);
 
    }
   
    private void refresh(Resources res) {
        new AfficherReclamationForm(res).show();
    }
   
    private Container addItem(Reclamation r, Resources res) {
          String etat=Integer.toString(r.getEtat());
        Container cnt1 = new Container(BoxLayout.y());
      Container box1 = BoxLayout.encloseX(new Label("Type de la reclamation: "), new Label(r.getType()));
       Container box2 = BoxLayout.encloseX(new Label("Description: "), new Label(r.getDescription()));
       Container box3 = BoxLayout.encloseX(new Label("Etat: "), new Label(etat));
        Button btnsupprimer = new Button("Supprimer");
        Button btnrepondre = new Button("Repondre");
        cnt1.add(box1);
        cnt1.add(box2);
        cnt1.add(box3);
        Container cnt3 = new Container(BoxLayout.x());
        if (r.getEtat()==1) {
            cnt3.add(btnsupprimer);
        }else if(r.getEtat()==0){
             cnt3.add(btnrepondre);
        }
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(0));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);

        btnrepondre.addActionListener((e) -> {
           new ReponseForm(res, r).show();
        }
        );
       btnsupprimer.addPointerPressedListener(l->{
           Dialog dig = new Dialog("Suppression");
           if(dig.show("Suppression","Voulez-vous supprimer la reclamation?","Annuler","Oui")){
               dig.dispose();
           }else {
               dig.dispose();
               if (ServiceReclamation.getInstance().deleteReclamation(r.getId())) {
                refresh(res);
            } else {
                System.out.println("erreur dans la suppression");
            }
           }
       });
        return cnt2;
    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
