package fr.ecam.color.timator_3000;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="Idees.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String strSql = "create table IDEE ( idIdee integer primary key not null,"
                +"contenu text,"
                +" duree text not null,"
                + "nom text not null,"
                + "note integer not null)";
        db.execSQL(strSql);
        Log.i("DATABASE","zob");

        String strSqlChallenge = "create table CHALLENGE ( idIdee integer primary key not null,"
                +"contenu text,"
                +" duree text not null,"
                + "nom text not null,"
                + "note integer not null)";
        db.execSQL(strSqlChallenge);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    ///TABLE IDEE

    public void insertIdee (int idIdee, String contenu, String duree, String nom, int note) {
        boolean flag = true;
        List<IdeeData> idees = new ArrayList<>();
        String strSql1 = "select * from IDEE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql1,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        for (int i = 0; i < idees.size(); i++) {
            if ((idees.get(i).getIdIdee() == idIdee) & (idees.get(i).getContenu().equals(contenu) == true)) {
                flag = false;
            }
            else{
            }
        }
            if (flag == true){
                contenu = contenu.replace("'", "''");
            nom = nom.replace("'", "''");
            String strSql = "insert into IDEE (idIdee,contenu,duree,nom,note) values(" + idIdee + ",'"
                    + contenu + "','" + duree + "','" +nom + "',"+note+")";
            this.getWritableDatabase().execSQL(strSql);
            Log.i("DATABASE", "insertIdee invoked");

        }
    }
////////////////////////////////////////

    public List<IdeeData> lireTable(){

        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from IDEE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }

    ////////////////////////////////////

    public List<IdeeData> lireTableTemps(String temps){
        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from IDEE where duree = '"+ temps+ "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }

    ////////////////////////////////////////

    public List<IdeeData> lireTableTempsTri(String temps){
        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from IDEE where duree = '"+ temps+ "' order by note desc";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }

    ////////////////////////////////////

    public int getIdMax(){
        List<IdeeData> idees = this.lireTable();
        int max =0;
        for (int i =0;i<idees.size();i++){
            if (max<idees.get(i).getIdIdee()){
                max = idees.get(i).getIdIdee();
            }
        }
        return max;
    }
























    ///MEME CHOSE POUR TABLE CHALLENGE

    public void insertIdeeChallenge (int idIdee, String contenu, String duree, String nom, int note) {
        boolean flag = true;
        List<IdeeData> idees = new ArrayList<>();
        String strSql1 = "select * from CHALLENGE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql1,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        for (int i = 0; i < idees.size(); i++) {
            if ((idees.get(i).getIdIdee() == idIdee) & (idees.get(i).getContenu().equals(contenu) == true)) {
                flag = false;
            }
            else{
            }
        }
        if (flag == true){
            contenu = contenu.replace("'", "''");
            nom = nom.replace("'", "''");
            String strSql = "insert into CHALLENGE (idIdee,contenu,duree,nom,note) values(" + idIdee + ",'"
                    + contenu + "','" + duree + "','" +nom + "',"+note+")";
            this.getWritableDatabase().execSQL(strSql);
            Log.i("DATABASE", "insertIdee invoked");

        }
    }


    public List<IdeeData> lireTableChallenge(){

        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from CHALLENGE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }
    public List<IdeeData> lireTableTempsChallenge(String temps){
        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from CHALLENGE where duree ='" +temps +"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }

    public int getIdMaxChallenge() {
        List<IdeeData> idees = this.lireTableChallenge();
        int max = 0;
        for (int i = 0; i < idees.size(); i++) {
            if (max < idees.get(i).getIdIdee()) {
                max = idees.get(i).getIdIdee();
            }
        }
        return max;
    }


    public void setNote (int nNote, int idIdee){
        String str = "update IDEE set note =" +nNote+" where idIdee = "+idIdee;
        this.getWritableDatabase().execSQL(str);

    }
}
