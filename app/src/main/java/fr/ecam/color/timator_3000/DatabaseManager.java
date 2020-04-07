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
                +"contenu text not null,"
                +" duree text not null,"
                + "type text not null )";
        db.execSQL(strSql);
        Log.i("DATABASE","onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertIdee (int idIdee, String contenu, String duree, String type) {
        boolean flag = true;
        List<IdeeData> idees = new ArrayList<>();
        String strSql1 = "select * from IDEE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql1,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        for (int i = 0; i < idees.size(); i++) {
            if ((idees.get(i).getIdIdee() == idIdee) & (idees.get(i).getContenu().equals(contenu) == true)) {
                Log.i("DATABASE", "c'est pas bon");
                flag = false;
            }
            else{
                Log.i("DATABASE", "c'est bon");
            }
        }
            if (flag == true){
                contenu = contenu.replace("'", "''");
            type = type.replace("'", "''");
            String strSql = "insert into IDEE (idIdee,contenu,duree,type) values(" + idIdee + ",'"
                    + contenu + "','" + duree + "','" + type + "')";
            this.getWritableDatabase().execSQL(strSql);
            Log.i("DATABASE", "insertIdee invoked");

        }
    }
    public List<IdeeData> lireTable(){

        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from IDEE ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }
    public List<IdeeData> lireTableTemps(String temps){
        List<IdeeData> idees = new ArrayList<>();
        String strSql = "select * from IDEE where duree = temps ";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            IdeeData idee = new IdeeData(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            idees.add(idee);
            cursor.moveToNext();
        }
        cursor.close();
        return idees;
    }
}
