/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.io.FileSystemStorage;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Promotion;
import com.mycompany.myapp.services.ServicePromotion;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import javafx.stage.FileChooser;

/**
 *
 * @author PC
 */
public class addPromotion extends Form {

    public addPromotion(Resources res) {

        setTitle("Ajouter promotion");
        setLayout(BoxLayout.y());
        TextField name = new TextField("", "Pourcentage", 2, TextField.NUMERIC);
        name.setUIID("TextFieldBlack");

        Button btnValider = new Button("Ajouter");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((name.getText().length() == 0 )) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    try {
                        Promotion r = new Promotion(Double.parseDouble(name.getText()));    
                        System.out.println("OBJEEEEECT: " + r);
                        if (new ServicePromotion().addPromotion(r)) {
                            Dialog.show("Success", "", "OK", "");
                            new PromotionsForm(res).showBack();
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
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new PromotionsForm(res).showBack();
        });

    }

}
