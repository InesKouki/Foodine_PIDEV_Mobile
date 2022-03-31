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
import com.mycompany.myapp.entities.table;
import com.mycompany.myapp.services.servicetable;

/**
 *
 * @author ASUS
 */
public class addtableform extends Form{
    
    public addtableform(Resources res) {

        setTitle("Ajouter table");
        setLayout(BoxLayout.y());
        TextField numero_table = new TextField("", "numero_table");
        numero_table.setUIID("TextFieldBlack");
         TextField nombre_de_place_table = new TextField("", "nombre_de_place");
         nombre_de_place_table.setUIID("TextFieldBlack");
        TextField image_table = new TextField("", "image_table");
        image_table.setUIID("TextFieldBlack");
        TextField etat = new TextField("", "etat");
        etat.setUIID("TextFieldBlack");
     
        Button btnValider = new Button("Ajouter");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((numero_table.getText().length() == 0 || nombre_de_place_table.getText().length() == 0 || image_table.getText().length() == 0 || etat.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                  
                    try {
                        table r = new table(Integer.parseInt(numero_table.getText()),Integer.parseInt(nombre_de_place_table.getText()),image_table.getText(),etat.getText());
                        System.out.println("OBJEEEEECT: "+r);
                        if (new servicetable().add(r)) {
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

        addAll(numero_table, nombre_de_place_table, image_table, etat, btnValider);

         FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new listtableform(res).showBack();
        });
        
        

    }
    
}
    
    
