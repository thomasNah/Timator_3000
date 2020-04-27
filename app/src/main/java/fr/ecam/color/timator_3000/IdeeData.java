package fr.ecam.color.timator_3000;

import androidx.annotation.NonNull;

public class IdeeData {
    private int idIdee;
    private String contenu;
    private String duree;
    private String nom;
    private int note;



    public IdeeData(int idIdeeP, String contenuP, String dureeP, String nomP, int noteP) {
        this.idIdee = idIdeeP;
        this.contenu = contenuP;
        this.duree = dureeP;
        this.nom = nomP;
        this.note = noteP;
    }

    public int getIdIdee() {
        return idIdee;
    }

    public String getContenu() {
        return contenu;
    }

    public String getDuree() {
        return duree;
    }

    public String getNom() {
        return nom;
    }

    public int getNote() {
        return note;
    }


    @NonNull
    @Override
    public String toString() {
        return idIdee + " : "+nom+" , " + duree+" , "+contenu+" , "+note;
    }

    public String afficher() {
        if (contenu.equals("")==true ) {
            return nom;
        }
        else{
           return nom + " : " + "\n"+contenu;
        }
    }
}
