package com.mycompany.myapp.entities;

public class Produit {

    private int id;
    private String name;
    private double price;
    private int quantite;
    private String image;

    
    public Produit(int id, String name, double price, int quantite) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantite = quantite;
    }

    public Produit(String name, double price, int quantite) {
        this.name = name;
        this.price = price;
        this.quantite = quantite;
    }

    public Produit() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantite=" + quantite + '}';
    }

}
