package com.codexpert;


public class Theme {

    private int image;
    private String nom;
    private String desc;
    private String lienQ;
    private boolean isAvaible;

    public Theme(int i, String n, String d, String lien){
        image = i;
        nom = n;
        desc = d;
        lienQ = lien;
        isAvaible = false;
    }

    public Theme(int i, String n, String d, String lien, boolean isAvaible){
        image = i;
        nom = n;
        desc = d;
        lienQ = lien;
        this.isAvaible = isAvaible;
    }

    public int getImage(){
        return image;
    }

    public String getDesc() {
        return desc;
    }

    public String getNom() {
        return nom;
    }

    public String getLien() {
        return lienQ;
    }
}
