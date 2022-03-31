/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.reservation;

import com.mycompany.myapp.services.servicereservation;
import java.util.Date;


/**
 *
 * @author ASUS
 */
public class addreservationform extends Form{
     public addreservationform(Resources res) {

        setTitle("Ajouter reservation");
        setLayout(BoxLayout.y());
       // TextField table_id = new TextField("", "table_id");
         TextField nom = new TextField("", "nom");
         nom.setUIID("TextFieldBlack");
        //Picker datereservation = new Picker();
        TextField mobile = new TextField("", "mobile");
        mobile.setUIID("TextFieldBlack");
        TextField email = new TextField("", "email");
        email.setUIID("TextFieldBlack");
        Button btnValider = new Button("Ajouter");
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (( nom.getText().length() == 0 /*|| datereservation.getText().length() == 0 */|| mobile.getText().length() == 0  || email.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                  
                    try {
                        reservation r = new reservation(/*Integer.parseInt(table_id.getText()),*/nom.getText(),Integer.parseInt(mobile.getText()),email.getText());
                        System.out.println("OBJEEEEECT: "+r);
                        if (new servicereservation().addd(r)) {
                            Dialog.show("Success", "votre reservation a ete effectue pour demain automatiquemet         Bienvenu", "OK", "");
                        } else {
                            System.out.println("OBJEEEEECT: "+r);
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                        Dialog.show("Error", "", "OK", "");
                    }
                }
            }

        });

        addAll(/*table_id,*/ nom /*,datereservation*/, mobile,email, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new listreservationform(res).showBack();
        });
        
        

    }
     
}
