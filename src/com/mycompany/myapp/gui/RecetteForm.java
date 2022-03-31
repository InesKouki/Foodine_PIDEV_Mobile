/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Recette;
import com.mycompany.myapp.services.servicerecette;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class RecetteForm extends SideMenuBaseForm {

    public RecetteForm(Resources res) {
        super(BoxLayout.y());
        ArrayList<Recette> p = servicerecette.getInstance().AfficheRecette();
        String totalEvents = String.valueOf(p.size());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
//        Image profilePic = res.getImage("pasta.png");
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
                new Label("Recettes", "CenterSubTitle")
        );
        remainingTasks.setUIID("RemainingTasks");

        Container completedTasks = BoxLayout.encloseY(
                new Label("32", "CenterTitle"),
                new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(1, remainingTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(e -> {
            //Toolbar.setGlobalToolbar(false);
            new AjouterRecetteForm(res).show();
        });
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));

        for (Recette pl : p) {
            add(new Label("Nom : " + pl.getNom()));
            add(new Label("Description : " + pl.getDescription()));
            add(new Label("Ingredient : " + pl.getIngredient()));
            // IMAAAAAAAAAAAAAAAGE
            Image img = null;
            try {
                img = Image.createImage("file://C://Users//azizm//Desktop//SEM2//PIDEV//Foodine_PIDEV//public//uploads//" + pl.getImage());
            } catch (IOException ex) {
            }
            add(new ImageViewer(img));
            Button btnsupprimer = new Button("");
            Style deleteIconStyle = new Style(btnsupprimer.getUnselectedStyle());
            deleteIconStyle.setFgColor(0xf21f1f);
            FontImage deleteIcon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteIconStyle, 4);
            btnsupprimer.setIcon(deleteIcon);
            btnsupprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Dialog diag = new Dialog("Suppression");
                if (diag.show("Suppression", "Vous êtes sur de supprimer cette recette ?", "Annuler", "Oui")) {
                    diag.dispose();
                } else {
                    diag.dispose();
                    servicerecette.getInstance().deleteReclamation(pl.getId());
                    new RecetteForm(res).show();
                }
                }
            });
            Button btnupdate = new Button("");
            Style updateIconStyle = new Style(btnupdate.getUnselectedStyle());
            updateIconStyle.setFgColor(0x212121);
            FontImage updateIcon = FontImage.createMaterial(FontImage.MATERIAL_EDIT, updateIconStyle, 4);
            btnupdate.setIcon(updateIcon);
            btnupdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ModifierRecetteForm(res, pl).show();
                }
            });
            add(FlowLayout.encloseCenter(BoxLayout.encloseX(btnupdate, btnsupprimer)));
            add(new Label("_______________________________________________________________________________________________________________"));

        }
        setupSideMenu(res);
    }

    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    @Override
    protected void showOtherForm(Resources res) {
    }
}
