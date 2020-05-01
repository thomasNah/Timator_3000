package fr.ecam.color.timator_3000;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class IdeeDataList extends ArrayList<IdeeData> implements Parcelable {

    public IdeeDataList()
    {

    }

    public IdeeDataList(Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public IdeeDataList createFromParcel(Parcel in)
        {
            return new IdeeDataList(in);
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

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            IdeeData ideeData = this.get(i); //On vient lire chaque objet personne
            dest.writeString(ideeData.getNom());
            dest.writeString(ideeData.getContenu());
            dest.writeString(ideeData.getDuree());
            dest.writeInt(ideeData.getIdIdee());
            dest.writeInt(ideeData.getNote());
        }
    }

    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();

        //Récupération du nombre d'objet
        int size = in.readInt();

        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            IdeeData ideeData = new IdeeData();
            ideeData.setNom(in.readString());
            ideeData.setDuree(in.readString());
            ideeData.setDuree(in.readString());
            ideeData.setIdIdee(in.readInt());
            ideeData.setNote(in.readInt());
            this.add(ideeData);
        }

    }
}
