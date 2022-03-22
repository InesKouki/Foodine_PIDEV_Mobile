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
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Review;
import com.mycompany.myapp.services.ServiceReview;

/**
 *
 * @author Asus
 */
public class AfficherAvisAdminForm extends SideMenuBaseBackForm {
 public AfficherAvisAdminForm(Resources res){
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
                                    new Label("Listes des Avis", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
       
        SpanLabel sp = new SpanLabel();
        for (Review r : ServiceReview.getInstance().getAllReviews()) {
            add(addItem(r,res));
        }
        add(sp);
       
        setupSideMenu(res);
 
    }
   
    private void refresh(Resources res) {
        new AfficherAvisAdminForm(res).show();
    }
   
    private Container addItem(Review r, Resources res) {
       String stars=Integer.toString(r.getStars());
        Container cnt1 = new Container(BoxLayout.y());
         Container box1 = BoxLayout.encloseX(new Label("Nom Utilisateur: "), new Label(r.getUser_name()));
        //SpanLabel L2 = new SpanLabel(r.getDate());
       Container box2 = BoxLayout.encloseX(new Label("Description: "), new Label(r.getDescription()));
       Container box3 = BoxLayout.encloseX(new Label("Note: "), new Label(stars), new Label(" Etoiles"));
        Button btnsupprimer = new Button("Supprimer");
        cnt1.add(box1);
        cnt1.add(box2);
        cnt1.add(box3);
        Container cnt3 = new Container(BoxLayout.x());
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(0));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);
       cnt3.add(btnsupprimer);
       btnsupprimer.addPointerPressedListener(l->{
           Dialog dig = new Dialog("Suppression");
           if(dig.show("Suppression","Voulez-vous supprimer l'avis?","Annuler","Oui")){
               dig.dispose();
           }else {
               dig.dispose();
               if (ServiceReview.getInstance().deleteReview(r.getId())) {
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