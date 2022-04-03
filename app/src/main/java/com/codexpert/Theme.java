package com.codexpert;


public class Theme {

    private int image;
    private String nom;
    private String desc;
    private String lienQ;
    private boolean isNotAvaible;

    public Theme(int i, String n, String d, String lien){
        image = i;
        nom = n;
        desc = d;
        lienQ = lien;
    }

    public Theme(int i, String n, String d, String lien, boolean isNotAvaible){
        image = i;
        nom = n;
        desc = d;
        lienQ = lien;
        this.isNotAvaible = isNotAvaible;
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

    public boolean getIsNotAvaible() {
        return isNotAvaible;
    }
}
