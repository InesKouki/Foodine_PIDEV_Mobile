/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class reservation {
    int id;
            int tableid;
    String nom;
   
    String datereservation;
   int mobile;
   String email;

    public reservation(int id, int tableid, String nom, String datereservation, int mobile, String email) {
        this.id = id;
        this.tableid = tableid;
        this.nom = nom;
        this.datereservation = datereservation;
        this.mobile = mobile;
        this.email = email;
    }

    public reservation(int tableid, String nom, String datereservation, int mobile, String email) {
       this.tableid = tableid;
        this.nom = nom;
        this.datereservation = datereservation;
        this.mobile = mobile;
        this.email = email;
    }

    public reservation(String nom, int mobile, String email) {
        this.nom = nom;
        this.mobile = mobile;
        this.email = email;
    }

    public reservation(String nom, String datereservation, int mobile, String email) {
        this.nom = nom;
        this.datereservation = datereservation;
        this.mobile = mobile;
        this.email = email;
    }

    public reservation() {
    }

    public int getId() {
        return id;
    }

    public void setIdd(int id) {
        this.id = id;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(String datereservation) {
        this.datereservation = datereservation;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "reservation{" + "id=" + id + ", tableid=" + tableid + ", nom=" + nom + ", datereservation=" + datereservation + ", mobile=" + mobile + ", email=" + email + '}';
    }
   
    
}
