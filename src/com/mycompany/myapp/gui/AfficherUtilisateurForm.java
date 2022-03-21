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
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author Asus
 */
public class AfficherUtilisateurForm extends SideMenuBaseForm{

     public AfficherUtilisateurForm(Resources res){
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
                                    new Label("Listes des Utilisateurs", "Title"),
                                    new Label("", "SubTitle")
                                )
                            )
                       
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
      
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
       
        SpanLabel sp = new SpanLabel();
        for (User r : ServiceUser.getInstance().getAllUsers()) {
            add(addItem(r,res));
        }
        add(sp);
        setupSideMenu(res);
 
    }
   
    private void refresh(Resources res) {
        new AfficherUtilisateurForm(res).show();
    }
   
    private Container addItem(User r, Resources res) {
        String etat=Integer.toString(r.getEtat());
        Container cnt1 = new Container(BoxLayout.y());
        SpanLabel L1 = new SpanLabel(r.getNom());
        SpanLabel L2 = new SpanLabel(r.getPrenom());
        SpanLabel L3 = new SpanLabel(r.getUsername());
        SpanLabel L4 = new SpanLabel(r.getEmail());
        SpanLabel L5 = new SpanLabel(etat);
       
        //SpanLabel L2 = new SpanLabel(r.getDate());
      
        Button btnbloquer = new Button("Bloquer");
        Button btndebloquer = new Button("Débloquer");
        Button btnsupprimer = new Button("Supprimer");
        Button RoleButton = new Button("Attribuer Role");
        cnt1.add(L1);
        cnt1.add(L2);
        cnt1.add(L3);
        cnt1.add(L4);
        cnt1.add(L5);
        Container cnt3 = new Container(BoxLayout.x());
        if (r.getEtat()==1) {
            cnt3.add(btnbloquer);
        }else if(r.getEtat()==0){
             cnt3.add(btndebloquer);
        }
        cnt3.add(btnsupprimer);
        cnt3.add(RoleButton);
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(0));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);
        
  btnsupprimer.addPointerPressedListener(l->{
           Dialog dig = new Dialog("Suppresion");
           if(dig.show("Supprimer","Voulez-vous supprimer cette utilisateur","Annuler","Oui")){
               dig.dispose();
           }else {
               dig.dispose();
               if (ServiceUser.getInstance().deleteUser(r.getId())) {
                refresh(res);
            } else {
                System.out.println("erreur");
            }
           }
       });
      
        
       btnbloquer.addPointerPressedListener(l->{
           Dialog dig = new Dialog("Bloquer");
           if(dig.show("Bloquer","Voulez-vous bloquer cette utilisateur","Annuler","Oui")){
               dig.dispose();
           }else {
               dig.dispose();
               if (ServiceUser.getInstance().bloquerUser(r.getId())) {
                refresh(res);
            } else {
                System.out.println("erreur");
            }
           }
       });
       
       btndebloquer.addPointerPressedListener(l->{
           Dialog dig = new Dialog("Bloquer");
           if(dig.show("Débloquer","Voulez-vous débloquer cette utilisateur","Annuler","Oui")){
               dig.dispose();
           }else {
               dig.dispose();
               if (ServiceUser.getInstance().debloquerUser(r.getId())) {
                refresh(res);
            } else {
                System.out.println("erreur");
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
    

