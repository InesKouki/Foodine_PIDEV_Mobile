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
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.services.ServiceCategorie;
import java.util.Date;

/**
 *
 * @author PC
 */
public class updateCategory extends Form {

    public updateCategory(Resources res, Categorie ev) {

        setTitle("Modifier événement");
        setLayout(BoxLayout.y());
        TextField name = new TextField(ev.getName(), "Nom");
        name.setUIID("TextFieldBlack");

        Button btnValider = new Button("Modifier");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((name.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    try {

                        ev.setName(name.getText());

                        System.out.println("OBJEEEEECT: " + ev);
                        if (new ServiceCategorie().updateEvent(ev)) {
                            Dialog.show("Success", "", "OK", "");
                            new CategoryForm(res).showBack();
                        } else {
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                    }
                }
            }

        });

        addAll(name, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, e -> {
            new CategoryForm(res).showBack();
        });

    }

}
