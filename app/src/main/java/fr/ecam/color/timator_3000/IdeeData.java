package fr.ecam.color.timator_3000;

import androidx.annotation.NonNull;

public class IdeeData {
    private int idIdee;
    private String contenu;
    private int duree;
    private String type;

    public IdeeData(int idIdee, String contenu, int duree, String type) {
        this.idIdee = idIdee;
        this.contenu = contenu;
        this.duree = duree;
        this.type = type;
    }

    public int getIdIdee() {
        return idIdee;
    }

    public void setIdIdee(int idIdee) {
        this.idIdee = idIdee;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return idIdee + " : "+contenu+" , " + duree+" , "+type;
    }
}
