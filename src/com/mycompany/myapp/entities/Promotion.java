package com.mycompany.myapp.entities;

public class Promotion {

    private int id;
    private double pourcentage;
    private int evenement_id;
    private int produit_id;

    public Promotion() {
    }

    public Promotion(int evenement_id, double pourcentage, int produit_id) {
        this.evenement_id = evenement_id;
        this.pourcentage = pourcentage;
        this.produit_id = produit_id;
    }

    public Promotion(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Promotion(int id, int evenement_id, double pourcentage, int produit_id) {
        this.id = id;
        this.evenement_id = evenement_id;
        this.pourcentage = pourcentage;
        this.produit_id = produit_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", evenement_id=" + evenement_id + ", pourcentage=" + pourcentage + ", produit_id=" + produit_id + '}';
    }

}
