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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Planning;
import com.mycompany.myapp.services.serviceplanning;
import java.util.Date;

/**
 *
 * @author PC
 */
public class AjouterPlanningForm extends Form {

    public AjouterPlanningForm(Resources res) {
        setTitle("Ajouter Planning");
        setLayout(BoxLayout.y());
        TextField nom = new TextField("", "Nom");
        nom.setUIID("TextFieldBlack");

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setUIID("TextFieldBlack");

        //TextField date = new TextField("","Date");
        Button btnValider = new Button("Passer");
        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Planning r = new Planning(formatter.format(new Date()), (nom.getText()));

                        if (new serviceplanning().add(r)) {
                            Dialog.show("Success", "", "OK", "");
                            new PlanningForm(res).showBack();
                        } else {
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                    }
                }
            }

        });

        addAll(nom, datePicker, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new PlanningForm(res).showBack();
        });
    }

}
