package fr.ecam.color.timator_3000;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseManager extends SQLiteOpenHelper {
    private Context ctx;
    public static final String DATABASE_NAME ="Idees.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // création de la table IDEE
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
        List<IdeeData> idees =this.lireTable();
                contenu = contenu.replace("'", "''");
            nom = nom.replace("'", "''");
            String strSql = "insert into IDEE (idIdee,contenu,duree,nom,note) values(" + idIdee + ",'"
                    + contenu + "','" + duree + "','" +nom + "',"+note+")";
            this.getWritableDatabase().execSQL(strSql);
            Log.i("DATABASE", "insertIdee invoked");

        //}
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

    public void setNote (int nNote, int idIdee){
        String str = "update IDEE set note =" +nNote+" where idIdee = "+idIdee;
        this.getWritableDatabase().execSQL(str);

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

    public IdeeData lireIdSpecifique(int id){
        //System.out.println("lireIdSpecifique(i) avec i :" + id);
        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from CHALLENGE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        //cursor.moveToPosition(id);
        cursor.moveToFirst();
        IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getInt(4));
        boolean flag = false;
        while(idee.getIdIdee() != id || flag == false){


            idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getInt(4));
            //System.out.println("BAAZIINGA");
            //System.out.println(cursor.getPosition());
            //  System.out.println(idee.getNom());

            //System.out.println("this.getIdMaxChallenge() : " + this.getIdMaxChallenge());
            //System.out.println("idee.getIdIdee() : " + idee.getIdIdee());

            if(idee.getIdIdee() == id){
                //System.out.println("JE RETOURNE MON IDEE");
                return idee;
            }

            if(idee.getIdIdee() == this.getIdMaxChallenge()){
                flag = true;
                idee = new IdeeData(id,"none","none","none",666);
                return idee;
            }
            else{
                cursor.moveToNext();
            }




        }



        idee = new IdeeData(id,"none","none","none",666);
        return idee;



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

    public int getIdMaxChallengeSousCertainMax(int certainMax) {
        List<IdeeData> idees = this.lireTableChallenge();
        int max = 0;
        for (int i = 0; i < certainMax; i++) {
            if (max < idees.get(i).getIdIdee()) {
                max = idees.get(i).getIdIdee();
            }
        }
        return max;
    }


    public void changerDureeIdee(int idIdee, String duree){
        String contenuChallenge = this.lireIdSpecifique(idIdee).getContenu();
        String dureeChallenge = duree;
        int noteChallenge = this.lireIdSpecifique(idIdee).getNote();
        String strChallenge = "update CHALLENGE set contenu = '"+contenuChallenge+"' , duree = '"+dureeChallenge+"', note =" +noteChallenge+" where idIdee = "+idIdee;
        this.getWritableDatabase().execSQL(strChallenge);
    }


    public int convertisseurDuree(int idIdee){

        String[] arrayTempsDispo = ctx.getResources().getStringArray(R.array.arrayTempsDispo);
        int[] arrayTempsDispoConvertToMinutesInInteger = ctx.getResources().getIntArray(R.array.arrayTempsDispoConvertToMinutesInInteger);
        System.out.println("Bazinga");
        String duree = this.lireIdSpecifique(idIdee).getDuree();
        System.out.println(duree);

        int dureeConvert = getIntegers(duree);
        System.out.println("dureeConvert : " + dureeConvert);
        return dureeConvert;

    }


    public int determinerDureeChallenge(int idChallenge){

        int nombreSousIdees = 10;
        int dureeTotalMinutes = 0;

        if(idChallenge%nombreSousIdees != 0){
            return -1;
        }

        int max = this.getIdMaxChallenge();
        System.out.println("max : " + max);
        System.out.println("max%10 : " + max%10);


        ArrayList<Integer> trouverMaxId = new ArrayList<>();
        for(int j=0;j<10;j++){
            if(lireIdSpecifique(idChallenge+j).getNote() != 666){
                trouverMaxId.add(lireIdSpecifique(idChallenge+j).getIdIdee());
                //System.out.println("trouverMaxId (DataBaseManager) : " + trouverMaxId.get(j));
            }


        }
        max = Collections.max(trouverMaxId);
        System.out.println("max de trouverMaxId (DatabaseManager : " + max);


        int i = idChallenge+1;
        if(idChallenge != 0){
            while (i <= max ) {
                /*
                System.out.println("Je rentre dans la boucle");
                System.out.println("i : " + i + "et max : " + max);
                dureeTotalMinutes = dureeTotalMinutes + this.convertisseurDuree(i);
                System.out.println("dureeTotalMinutes in boucle : " + dureeTotalMinutes);
                 */

                dureeTotalMinutes = dureeTotalMinutes + getIntegers(lireIdSpecifique(i).getDuree());
                i++;
            }
        }

        if(idChallenge == 0){
            while (i <= max ) {
                /*
                dureeTotalMinutes = dureeTotalMinutes + this.convertisseurDuree(i);
                System.out.println("dureeTotalMinutes in boucle : " + dureeTotalMinutes);
                 */
                if(getIntegers(lireIdSpecifique(i).getDuree()) > 0){
                    dureeTotalMinutes = dureeTotalMinutes + getIntegers(lireIdSpecifique(i).getDuree());
                }
                //System.out.println(getIntegers(lireIdSpecifique(i).getDuree()));
                i++;
            }
        }



        System.out.println("dureeTotalMinutes : " + dureeTotalMinutes);
        return dureeTotalMinutes;

    }

    public void setTempsTotalChallenge(int idChallenge){
        int test = this.determinerDureeChallenge(idChallenge);
        //databaseManager.convertisseurDuree(11);
        this.changerDureeIdee(idChallenge,test + " minutes");
    }

    public void setTempsTotalTousLesChallenges(){
        int max = this.getIdMaxChallenge();
        for(int i=0;i<max;i = i + 10){
            setTempsTotalChallenge(i);
        }

    }

    public static int getIntegers(String str) {

        ArrayList<Integer> list = new ArrayList<Integer>();

        //découper la phrase en mots
        String[] splited = str.split(" ");

        //parcourir les mots
        for (String current : splited) {
            try {
                //tenter de convertir le mot en int
                int parsedInt = Integer.parseInt (current);
                //ajouter l Integer à la list
                list.add(parsedInt);                    //un "auto boxing", une instance de Integer est créée à partir d'un int
            } catch (NumberFormatException e) {
                //c est pas un int
            }
        }

        //construire le résultat
        int[] result = new int[list.size()];
        for (int i = 0 ; i < list.size() ; i++) {
            //parcourir la list de Integer créée
            result[i] = list.get(i);
        }

        //Lire le resultat (String)
        String strumon = "-1";
        for(int i = 0;i<result.length;i++){
            strumon = Integer.toString(result[i]);
        }

        //Convertir en INT
        int resultatFinal =  Integer.parseInt(strumon);

        return resultatFinal;
    }





}

