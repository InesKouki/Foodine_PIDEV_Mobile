/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
import static com.codename1.push.PushContent.setTitle;
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
import com.mycompany.myapp.entities.Recette;
import com.mycompany.myapp.services.servicerecette;
import java.util.Date;

/**
 *
 * @author PC
 */
public class ModifierRecetteForm extends Form {

    public ModifierRecetteForm(Resources res, Recette p) {
        setTitle("Modifier Recette");
        setLayout(BoxLayout.y());
        TextField nom = new TextField(p.getNom(), "Nom");
        TextField description = new TextField(p.getDescription(), "Description");
        TextField ingredients = new TextField(p.getIngredient(), "Ingredients");
        nom.setUIID("TextFieldBlack");
        description.setUIID("TextFieldBlack");
        ingredients.setUIID("TextFieldBlack");

        Button btnValider = new Button("Passer");
        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    try {
                        p.setNom(nom.getText());
                        p.setDescription(description.getText());
                        p.setIngredient(ingredients.getText());
                        if (new servicerecette().updateRec(p)) {
                            Dialog.show("Success", "", "OK", "");
                            new RecetteForm(res).showBack();
                        } else {
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                    }
                }
            }

        });

        addAll(nom, description, ingredients, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new RecetteForm(res).showBack();
        });
    }

}
