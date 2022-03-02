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
public class Reclamation {
    private int id;
    private int user_id;
    private String created_at;
    private int etat;
    private String type;
    private String description;

    public Reclamation (){
        
    }

    public Reclamation(int id_user, String created_at, int etat, String type, String description) {
        this.user_id = id_user;
        this.created_at = created_at;
        this.etat = etat;
        this.type = type;
        this.description = description;
    }
    
    
    public Reclamation(int id, int id_user, String created_at, String type, String description) {
        this.id = id;
        this.user_id = id_user;
        this.created_at = created_at;
        this.etat = 0;
        this.type = type;
        this.description = description;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return user_id;
    }

    public void setId_user(int id_user) {
        this.user_id = id_user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", id_user=" + user_id + ", created_at=" + created_at + ", etat=" + etat + ", type=" + type + ", description=" + description + '}';
    }
    
    
}