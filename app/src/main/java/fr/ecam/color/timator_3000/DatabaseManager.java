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

        String strSql = "create table IDEE ( idIdee integer primary key autoincrement,"
                +"contenu text not null,"
                +" duree interger not null,"
                + "type text not null )";
        db.execSQL(strSql);
        Log.i("DATABASE","onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertIdee (String contenu, int duree, String type){
        contenu = contenu.replace("'","''");
        type = type.replace("'","''");
        String strSql = "insert into IDEE (contenu,duree,type) values('"
                +contenu+"',"+duree+",'"+type+"')";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE","insertIdee invoked");

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

}
