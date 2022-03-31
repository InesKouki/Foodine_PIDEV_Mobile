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
public class Livraison {
 
    int id ;
    String addresse , details , email,codepostal,phone ;

    public Livraison(int id, String codepostal, String phone, String addresse, String details, String email) {
        this.id = id;
        this.codepostal = codepostal;
        this.phone = phone;
        this.addresse = addresse;
        this.details = details;
        this.email = email;
    }

    public Livraison(String codepostal, String phone, String addresse, String details, String email) {
        this.codepostal = codepostal;
        this.phone = phone;
        this.addresse = addresse;
        this.details = details;
        this.email = email;
    }

    public Livraison() {
    }

    

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

    @Override
    public String toString() {
        return "Livraison{" + "id=" + id + ", codepostal=" + codepostal + ", phone=" + phone + ", addresse=" + addresse + ", details=" + details + ", email=" + email + '}';
    }
    
    



}
