package fr.ecam.color.timator_3000;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class IdeeData implements Parcelable {
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

    public IdeeData(){

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

    public void setIdIdee(int idIdee) {
        this.idIdee = idIdee;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @NonNull
    @Override
    public String toString() {
        return idIdee + " : "+nom+" , " + duree+" , "+contenu+" , "+note;
    }
    public String toString1() { return contenu + " , " + duree + " , " + note +"/5";}

    public String afficher() {
        if (contenu.equals("")==true ) {
            return nom;
        }
        else{
           return nom + " : " + "\n"+contenu;
        }
    }


    //Second constructeur qui sera appelé lors de la "Deparcelablisation"
    public IdeeData(Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public IdeeData createFromParcel(Parcel in)
        {
            return new IdeeData(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //On ecrit dans le parcel les données de notre objet
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.nom);
        dest.writeString(this.duree);
        dest.writeInt(this.idIdee);
        dest.writeInt(this.note);
        dest.writeString(this.contenu);
    }

    //On va ici hydrater notre objet à partir du Parcel
    public void getFromParcel(Parcel in)
    {
        this.setNom(in.readString());
        this.setDuree(in.readString());
        this.setContenu(in.readString());
        this.setNote(in.readInt());
        this.setIdIdee(in.readInt());
    }

}
