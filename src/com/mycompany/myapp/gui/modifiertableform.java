/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.table;
import com.mycompany.myapp.services.servicetable;

/**
 *
 * @author ASUS
 */
public class modifiertableform extends Form {

    Form current;

    public modifiertableform(Resources res, table t) {

        setTitle("modifer table");
        setLayout(BoxLayout.y());
        /* TextField id = new TextField(t.getId());*/
        TextField numero_table = new TextField(t.getNumerotable());
        numero_table.setUIID("TextFieldBlack");
        TextField nombre_de_place_table = new TextField(t.getNbplacetable());
        nombre_de_place_table.setUIID("TextFieldBlack");
        TextField image_table = new TextField(t.getImagetable());
        image_table.setUIID("TextFieldBlack");
        TextField etat = new TextField(t.getEtat());
        etat.setUIID("TextFieldBlack");

        Button btnValider = new Button("modifier");
        Button retour = new Button("retour");
        retour.addActionListener(e -> {
            new listtableform(res).show();
        });

        btnValider.addPointerPressedListener(m
                -> {//t.setId(Integer.parseInt(id.getText()));

            btnValider.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((/*numero_table.getText().length() == 0 ||*//* nombre_de_place_table.getText().length() == 0 ||*/image_table.getText().length() == 0 || etat.getText().length() == 0)) {
                        Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                    } else {

                        try {
                            t.setNumerotable(Integer.parseInt(numero_table.getText()));
                            t.setNbplacetable(Integer.parseInt(nombre_de_place_table.getText()));
                            t.setImagetable(image_table.getText());
                            t.setEtat(etat.getText());
                            System.out.println("OBJEEEEECT: " + t);
                            if (new servicetable().modifier(t)) {
                                Dialog.show("Success", "", "OK", "");
                                new listtableform(res).showBack();
                            } else {
                                Dialog.show("Error", "", "OK", "");
                            }
                        } catch (Exception e) {
                            Dialog.show("Error", "", "OK", "");
                        }
                    }
                }

            });

        }
        );

        addAll(/*id,*/numero_table, nombre_de_place_table, image_table, etat, btnValider, retour);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

    }

}
