/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
    import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ServiceEvenement;
import com.mycompany.myapp.services.ServiceProduit;
import java.util.Date;

/**
 *
 * @author PC
 */
public class updateProduct extends Form {

    public updateProduct(Resources res, Produit ev) {

        setTitle("Modifier produit");
        setLayout(BoxLayout.y());
        TextField name = new TextField(ev.getName(), "Nom");
        name.setUIID("TextFieldBlack");
        TextField qtt = new TextField(String.valueOf(ev.getQuantite()), "Quantité", 2, TextField.NUMERIC);
        qtt.setUIID("TextFieldBlack");
        TextField price = new TextField(String.valueOf(ev.getPrice()), "Prix", 2, TextField.NUMERIC);
        price.setUIID("TextFieldBlack");

        Button btnValider = new Button("Modifier");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((name.getText().length() == 0 )) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    try {
                        ev.setName(name.getText());
                        ev.setPrice(Double.parseDouble(price.getText()));
                        ev.setPrice(Integer.parseInt(qtt.getText()));

                        System.out.println("OBJEEEEECT: " + ev);
                        if (new ServiceProduit().updateEvent(ev)) {
                            Dialog.show("Success", "", "OK", "");
                            new ProductForm(res).showBack();
                        } else {
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                    }
                }
            }

        });

        addAll(name, qtt, price, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, e -> {
            new ProductForm(res).showBack();
        });

    }

}
