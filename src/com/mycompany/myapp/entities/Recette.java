/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author PC
 */
public class Recette {
    private int id;
    private String nom;
    private String description;
    private String image;
    private String ingredient;
    private Planning idp;

    public Recette(int id, String nom, String description, String image, String ingredient, Planning idp) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.ingredient = ingredient;
        this.idp = idp;
    }

    public Recette(String nom, String description, String image, String ingredient, Planning idp) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.ingredient = ingredient;
        this.idp = idp;
    }

    public Recette(String nom, String description, String ingredient) {
        this.nom = nom;
        this.description = description;
        this.ingredient = ingredient;
    }
    
    public Recette() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Planning getIdp() {
        return idp;
    }

    public void setIdp(Planning idp) {
        this.idp = idp;
    }

    @Override
    public String toString() {
        return "Recette{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", image=" + image + ", ingredient=" + ingredient + ", idp=" + idp + '}';
    }
    

    
}
