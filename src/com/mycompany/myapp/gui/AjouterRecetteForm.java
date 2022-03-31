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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Planning;
import com.mycompany.myapp.entities.Recette;
import com.mycompany.myapp.services.servicerecette;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 *
 * @author PC
 */
public class AjouterRecetteForm extends Form {

    public AjouterRecetteForm(Resources res) {
        setTitle("Ajouter Recette");
        setLayout(BoxLayout.y());
        TextField nom = new TextField("", "Nom");
        nom.setUIID("TextFieldBlack");
        TextField description = new TextField("", "description");
        description.setUIID("TextFieldBlack");
        TextField ingredient = new TextField("", "ingredients");
        ingredient.setUIID("TextFieldBlack");
        
        Label jobIcon = new Label();
        
        Button upload = new Button("Choisir une image");

        final String[] image_name = {""};
        final String[] pathToBeStored = {""};
        
        upload.addActionListener(l -> {
            Display.getInstance().openGallery(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    if (ev != null && ev.getSource() != null) {
                        String filePath = (String) ev.getSource();
                        int fileNameIndex = filePath.lastIndexOf("/") + 1;
                        String fileName = filePath.substring(fileNameIndex);
                        Image img = null;
                        try {
                            img = Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        image_name[0] = System.currentTimeMillis() + ".jpg";
                        jobIcon.setIcon(img);
                        System.out.println(filePath);
                        System.out.println(image_name[0]);

                        try {
                            pathToBeStored[0] = FileSystemStorage.getInstance().getAppHomePath() + image_name[0];
                            OutputStream os = FileSystemStorage.getInstance().openOutputStream(pathToBeStored[0]);
                            ImageIO.getImageIO().save(img, os, ImageIO.FORMAT_JPEG, 0.9f);
                            os.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, Display.GALLERY_IMAGE);
        });

        //TextField date = new TextField("","Date");
        Button btnValider = new Button("Ajouter");
        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    try {
                        Recette r = new Recette(nom.getText(), description.getText(), ingredient.getText());

                        if (new servicerecette().add(r)) {
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

        addAll(nom, description, ingredient,upload, jobIcon, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new RecetteForm(res).showBack();
        });

    }
}
