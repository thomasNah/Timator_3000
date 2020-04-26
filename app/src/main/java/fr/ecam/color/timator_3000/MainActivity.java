package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tempsDispo;
    private Spinner spinnerTempsDispo;
    private Button giveAnIdeaButton;
    private Button challengeLongTerme;
    private Button activitePersoButton;
    private Button preferencesButton;
    private DatabaseManager databaseManager;
    String inputTempsDispo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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



        //Création de la BDD
        databaseManager = new DatabaseManager(this);
        databaseManager.insertIdee(1,"la description","20 minutes","faire des pates",5); //ajout des données
        databaseManager.insertIdee(2,"la description","1 heure","lire un livre",5);
        databaseManager.insertIdee(3,"la description","30 minutes","faire du sport",5);
        databaseManager.insertIdee(4,"","1 heure","faire le rapport java",5);
        String contenu = "ceci est le contenus";
        String duree = "oue";
        int note = 1;
        int id = 2;
        String str = "update IDEE set contenu = '"+contenu+"' , duree = '"+duree+"', note =" +note+" where idIdee = "+id;
        //String str = "update IDEE set contenu = 'la bite' , duree = 'ouiiiiii', note = 0 where idIdee = 1";
        databaseManager.getWritableDatabase().execSQL(str);
        databaseManager.close();

    }

    protected void onResume() {
        super.onResume();



        giveAnIdeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //PASSAGE A L'ACTIVITE "IDEE"
                Intent ideeActivity = new Intent(MainActivity.this, IdeeActivity.class);
                ideeActivity.putExtra("inputTempsDispo",inputTempsDispo);
                startActivity(ideeActivity);


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
