/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.services.ServiceEvenement;
import com.mycompany.myapp.services.ServiceLivraison;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a user profile in the app, the first form we open after the
 * walkthru
 *
 * @author Shai Almog
 */
public class ListLivraisonForm extends SideMenuBaseForm {

    public ListLivraisonForm(Resources res) {
        super(BoxLayout.y());

        ArrayList<Livraison> events = ServiceLivraison.getInstance().getAllreservations();
        String totalEvents = String.valueOf(events.size());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
//        Image profilePic = res.getImage("portrait.jpg");
//        Image mask = res.getImage("round-mask.png");
//        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
//        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
//        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container remainingTasks = BoxLayout.encloseY(
                new Label(totalEvents, "CenterTitle"),
                new Label("Livraisons", "CenterSubTitle")
        );
        remainingTasks.setUIID("RemainingTasks");

        Container completedTasks = BoxLayout.encloseY(
                new Label("32", "CenterTitle"),
                new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
//                BorderLayout.centerAbsolute(
//                        BoxLayout.encloseY(
//                                new Label("Aziz Msaddek", "Title")
//                        //                                new Label("UI/UX Designer", "SubTitle")
//                        )
//                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(1, remainingTasks)
        );

        // BUTTON AJOUTER
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(ee -> {
            new LivraisonForm(res).show();
        });

        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));

        // ------------- AFFICHAGE DES EVENEMENTS --------------
        for (Livraison ev : events) {
            Label addresse = new Label("Addresse : " + ev.getAddresse());
        Label codepostal = new Label("Code postal : " + ev.getCodepostal());
        Label email = new Label("Addresse Email : " + ev.getEmail());
        Label phone = new Label("Phone : " + ev.getPhone());
        Label details = new Label("Détails : " + ev.getDetails());
            
            addAll(addresse,codepostal,email,phone,details);
            Button btnupdate = new Button("");
            Style updateIconStyle = new Style(btnupdate.getUnselectedStyle());
            updateIconStyle.setFgColor(0x212121);
            FontImage updateIcon = FontImage.createMaterial(FontImage.MATERIAL_EDIT, updateIconStyle, 4);
            btnupdate.setIcon(updateIcon);
            btnupdate.addActionListener(u -> {
                new updateLivraison(res, ev).show();
            });

            Button btnsupprimer = new Button("");
            Style deleteIconStyle = new Style(btnsupprimer.getUnselectedStyle());
            deleteIconStyle.setFgColor(0xf21f1f);
            FontImage deleteIcon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteIconStyle, 4);
            btnsupprimer.setIcon(deleteIcon);
            btnsupprimer.addActionListener(d -> {
                Dialog diag = new Dialog("Suppression");
                if (diag.show("Suppression", "Vous êtes sur de supprimer cette livraison ?", "Annuler", "Oui")) {
                    diag.dispose();
                } else {
                    diag.dispose();
                    ServiceLivraison.getInstance().deleteReclamation(ev.getId());
                    new ListLivraisonForm(res).showBack();
                }
            });
            add(FlowLayout.encloseCenter(BoxLayout.encloseX(btnupdate, btnsupprimer)));
            add(new Label("_______________________________________________________________________________________________________________"));

        }

//        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
//
//        addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1, true);
//        addButtonBottom(arrowDown, "Design app illustrations", 0x5ae29d, false);
//        addButtonBottom(arrowDown, "Javascript training ", 0x4dc2ff, false);
//        addButtonBottom(arrowDown, "Surprise Party for Matt", 0xffc06f, false);
        setupSideMenu(res);
    }

//    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
//        MultiButton finishLandingPage = new MultiButton(text);
//        finishLandingPage.setEmblem(arrowDown);
//        finishLandingPage.setUIID("Container");
//        finishLandingPage.setUIIDLine1("TodayEntry");
//        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
//        finishLandingPage.setIconUIID("Container");
//        add(FlowLayout.encloseIn(finishLandingPage));
//    }
//
//    private Image createCircleLine(int color, int height, boolean first) {
//        Image img = Image.createImage(height, height, 0);
//        Graphics g = img.getGraphics();
//        g.setAntiAliased(true);
//        g.setColor(0xcccccc);
//        int y = 0;
//        if (first) {
//            y = height / 6 + 1;
//        }
//        g.drawLine(height / 2, y, height / 2, height);
//        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
//        g.setColor(color);
//        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
//        return img;
//    }
    @Override
    protected void showOtherForm(Resources res) {
    }

}
