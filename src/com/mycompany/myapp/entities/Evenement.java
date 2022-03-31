package com.mycompany.myapp.entities;

import java.util.Date;

public class Evenement {
    private int id;
    private String name;
    private String date_deb;
    private String date_fin;
    private String description;
    private String image;

    public Evenement() {
    }

    public Evenement(String name, String date_deb, String date_fin, String description, String image) {
        this.name = name;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
    }

    public Evenement(int id, String name, String date_deb, String date_fin, String description, String image) {
        this.id = id;
        this.name = name;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
    }

    public Evenement(String name, String date_deb, String date_fin, String description) {
        this.name = name;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.description = description;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(String date_deb) {
        this.date_deb = date_deb;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", name=" + name + ", date_deb=" + date_deb + ", date_fin=" + date_fin + ", description=" + description + ", image=" + image + '}';
    }

    
    
}
