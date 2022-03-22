/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Asus
 */
public class Reponse {
    int id;
    int id_rec;
    String message ;

    public Reponse() {
    }

    
    public Reponse(int id, int id_rec, String message) {
        this.id = id;
        this.id_rec = id_rec;
        this.message = message;
    }

    public Reponse(int id_rec, String message) {
        this.id_rec = id_rec;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", id_rec=" + id_rec + ", message=" + message + '}';
    }
    
    
}
