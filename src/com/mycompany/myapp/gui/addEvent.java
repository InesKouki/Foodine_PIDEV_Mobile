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
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.services.ServiceEvenement;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import javafx.stage.FileChooser;

/**
 *
 * @author PC
 */
public class addEvent extends Form {

    public addEvent(Resources res) {

        setTitle("Ajouter événement");
        setLayout(BoxLayout.y());
        TextField name = new TextField("", "Nom");
        name.setUIID("TextFieldBlack");
        Picker dateDeb = new Picker();
        dateDeb.setType(Display.PICKER_TYPE_DATE);
        dateDeb.setUIID("TextFieldBlack");
        Picker dateFin = new Picker();
        dateFin.setType(Display.PICKER_TYPE_DATE);
        dateFin.setUIID("TextFieldBlack");
        TextField description = new TextField("","Description");
        description.setUIID("TextFieldBlack");

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

        Button btnValider = new Button("Ajouter");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((name.getText().length() == 0 || description.getText().length() == 0 || dateDeb.getText().length() == 0 || dateFin.getText().length() == 0)) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Evenement r = new Evenement(name.getText(), formatter.format(dateDeb.getDate()), formatter.format(dateFin.getDate()), description.getText());
                        System.out.println("OBJEEEEECT: " + r);
                        if (new ServiceEvenement().addEvent(r)) {
                            Dialog.show("Success", "", "OK", "");
                            new EventsForm(res).showBack();
                        } else {
                            Dialog.show("Error", "", "OK", "");
                        }
                    } catch (Exception e) {
                    }
                }
            }

        });

        addAll(name, dateDeb, dateFin, description, upload, jobIcon, btnValider);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.getToolbar().addCommandToLeftBar("", icon, ev -> {
            new EventsForm(res).showBack();
        });

    }

}
