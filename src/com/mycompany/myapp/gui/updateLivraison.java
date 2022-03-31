/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.services.ServiceLivraison;

/**
 *
 * @author PC
 */
public class updateLivraison extends Form {

    public updateLivraison(Resources res, Livraison ev) {
        setTitle("Modifier livraison");
        setLayout(BoxLayout.y());
        TextField addresse = new TextField(ev.getAddresse(), "Addresse");
        addresse.setUIID("TextFieldBlack");
        TextField codepostal = new TextField(ev.getCodepostal(), "Code postal");
        codepostal.setUIID("TextFieldBlack");
        TextField email = new TextField(ev.getEmail(), "Addresse Email");
        email.setUIID("TextFieldBlack");
        TextField phone = new TextField(ev.getPhone(), "Phone");
        phone.setUIID("TextFieldBlack");
        TextField details = new TextField(ev.getDetails(), "Détails");
        details.setUIID("TextFieldBlack");
        Button btnValider = new Button("Passer");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((addresse.getText().length() == 0 || codepostal.getText().length() == 0 || email.getText().length() == 0 || phone.getText().length() == 0 || details.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {

                    try {
                        ev.setAddresse(addresse.getText());
                        ev.setCodepostal(codepostal.getText());
                        ev.setEmail(email.getText());
                        ev.setPhone(phone.getText());
                        ev.setDetails(details.getText());
                        if (new ServiceLivraison().update(ev)) {
                            Dialog.show("Success", "", "OK", "");
                            new ListLivraisonForm(res).showBack();
                        } else {
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                    }
                }
            }

        });

        addAll(addresse, codepostal, email, phone, details, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListLivraisonForm(res).showBack());

    }

}
