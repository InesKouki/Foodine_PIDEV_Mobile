/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Planning {
    private int id;
    private String date;
    private String nom;
    
    public Planning(String date, String nom) {
        this.date = date;
        this.nom = nom;
    }

    public Planning() {
    }

    public Planning(int id, String date, String nom) {
        this.id = id;
        this.date = date;
        this.nom = nom;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Planning{" + "id=" + id + ", date=" + date + ", nom=" + nom + '}';
    }

    
    
}
