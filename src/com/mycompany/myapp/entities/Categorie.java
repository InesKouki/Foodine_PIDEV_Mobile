package com.mycompany.myapp.entities;

public class Categorie {

    private int id;
    private String name;
    private String image;

    public Categorie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Categorie(String name) {
        this.name = name;
    }

    public Categorie() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", name=" + name + '}';
    }

}
