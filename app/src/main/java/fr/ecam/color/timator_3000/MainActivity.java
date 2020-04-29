package fr.ecam.color.timator_3000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Context context;


    private TextView tempsDispo;
    private Spinner spinnerTempsDispo;
    private Button giveAnIdeaButton;
    private Button challengeLongTerme;
    private Button activitePersoButton;
    private Button preferencesButton;

    private DatabaseManager databaseManager;
    private DatabaseManager databaseManagerChallenge;

    String inputTempsDispo;
    private boolean darkTheme;
    private boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("fr.ecam.color.timator_3000",MODE_PRIVATE);
        darkTheme = preferences.getBoolean("Dark_Theme",false);
        if (darkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DECLARATION VARIABLE LAYOUT
        tempsDispo = findViewById(R.id.tempsDispo);
        spinnerTempsDispo = findViewById(R.id.spinnerTempsDispo);
        giveAnIdeaButton = findViewById(R.id.giveAnIdeaButton);
        challengeLongTerme = findViewById(R.id.challengeLongTermeButton);
        activitePersoButton = findViewById(R.id.activitePersoButton);
        preferencesButton = findViewById(R.id.preferencesButton);

        //PROPRE AU SPINNER (LISTE DEROULANTE POUR SELECTION DU TEMPS)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayTempsDispo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTempsDispo.setAdapter(adapter);
        spinnerTempsDispo.setOnItemSelectedListener(this);

        //Création de la BDD IDEE
        databaseManager = new DatabaseManager(this);
        List<IdeeData> idees = databaseManager.lireTable();
        Log.i("DATABASE", String.valueOf(idees.size()));
        if (idees.size() ==0) {

            databaseManager.insertIdee(1, "la description", "20 minutes", "faire des pates", 3); //ajout des données
            databaseManager.insertIdee(2, "la description", "1 heure", "lire un livre", 3);
            databaseManager.insertIdee(3, "la description", "30 minutes", "faire du sport", 3);
            databaseManager.insertIdee(4, "", "1 heure", "faire le rapport java", 3);
            databaseManager.insertIdee(5,"","20 minutes","faire des nuggets pour ce soir", 5);
            databaseManager.insertIdee(6,"ouiiiiii","20 minutes","j'ai pas d'imagination",1);
        }
        //databaseManager.setNote(5,3);




        //Création de la BDD CHALLENGE
        //databaseManagerChallenge = new DatabaseManager(this);
        List<IdeeData> ideesChallenge = databaseManager.lireTableChallenge();
        Log.i("DATABASE", String.valueOf(ideesChallenge.size()));
        if (ideesChallenge.size() ==0) {

            databaseManager.insertIdeeChallenge(1, "Lien vers le site du zero", "24h", "Apprendre le JAVA", 3); //ajout des données
        }
        String contenuChallenge = "contenuChallenge";
        String dureeChallenge = "dureeChallenge";
        int noteChallenge = 1;
        int idChallenge = 1;
        String strChallenge = "update CHALLENGE set contenu = '"+contenuChallenge+"' , duree = '"+dureeChallenge+"', note =" +noteChallenge+" where idIdee = "+idChallenge;
        databaseManager.getWritableDatabase().execSQL(strChallenge);
        databaseManager.close();

    }

    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences("fr.ecam.color.timator_3000",MODE_PRIVATE);
        boolean darkThemeResumed = preferences.getBoolean("Dark_Theme",false);
        if (darkTheme!=darkThemeResumed) {
            recreate();
        }

        giveAnIdeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //PASSAGE A L'ACTIVITE "IDEE"
                if (inputTempsDispo.equals("1 minute")){
                    Intent WeatherActivityInt = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(WeatherActivityInt);
                }
                else {
                    List<IdeeData> idees = databaseManager.lireTableTemps(inputTempsDispo);
                    if (idees.size() > 0) {
                        Intent ideeActivity = new Intent(MainActivity.this, IdeeActivity.class);
                        ideeActivity.putExtra("inputTempsDispo", inputTempsDispo);
                        startActivity(ideeActivity);
                    } else {
                        Toast.makeText(getApplicationContext(), "il n'y a pas d'idée de cette durée pour l'instant", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        activitePersoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //PASSAGE A L'ACTIVITE "GERER PERSO"
                Intent gererPersoActivity = new Intent(MainActivity.this, GererPersoActivity.class);
                startActivity(gererPersoActivity);


            }
        });

        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //PASSAGE A L'ACTIVITE "PREFERENCES"
                Intent preferencesActivity = new Intent(MainActivity.this, Preferences.class);
                startActivity(preferencesActivity);


            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //RECUPERATION DE L'INTERIEUR DU SPINNER
        inputTempsDispo = parent.getItemAtPosition(position).toString();

        //PERMET D'AFFICHER LE TEMPS DISPO EN PETITE CASE QUI DISPARAIT RAPIDEMENT
        //Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
