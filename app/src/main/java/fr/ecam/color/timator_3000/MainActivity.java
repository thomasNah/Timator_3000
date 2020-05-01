package fr.ecam.color.timator_3000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import fr.ecam.color.timator_3000.model.Actu;
import fr.ecam.color.timator_3000.model.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


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
            databaseManager.insertIdee(2, "la description", "1h", "lire un livre", 3);
            databaseManager.insertIdee(3, "la description", "30 minutes", "faire du sport", 3);
            databaseManager.insertIdee(4, "", "1h", "faire le rapport java", 3);
            databaseManager.insertIdee(5,"","20 minutes","faire des nuggets pour ce soir", 5);
            databaseManager.insertIdee(6,"ouiiiiii","20 minutes","j'ai pas d'imagination",1);
        }


        //Création de la BDD CHALLENGE
        //databaseManagerChallenge = new DatabaseManager(this);
        List<IdeeData> ideesChallenge = databaseManager.lireTableChallenge();
        Log.i("DATABASE", String.valueOf(ideesChallenge.size()));
        if (ideesChallenge.size() ==0) {

            //ID 0 A 10 - Touchez votre audience sur mobile - https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile
            databaseManager.insertIdeeChallenge(00, "https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile", "none", "Touchez votre audience sur mobile", 3);
            databaseManager.insertIdeeChallenge(01, "https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile/module/17", "30 minutes", "Tirez profit des opportunités qu'offre le mobile", 3);
            databaseManager.insertIdeeChallenge(02, "https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile/module/16", "20 minutes", "Découvrez les possibilités offertes par le mobile", 3);


            //ID 10 A 20 - Test

            databaseManager.insertIdeeChallenge(10,"","none","Test",3);
            databaseManager.insertIdeeChallenge(11,"","45 minutes","Sousa",3);
            databaseManager.insertIdeeChallenge(12,"","1h","Sousa deux",3);



        }

        // POUR MODIFIER IDEE
        databaseManager.setTempsTotalTousLesChallenges();
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
                } else if (inputTempsDispo.equals("2 minutes")) {
                    GetActuService ws = RetrofitBuilder.getSimpleClient();
                    ws.getArticle().enqueue(new Callback<Actu>() {
                        @Override
                        public void onResponse(Call<Actu> call, Response<Actu> response) {
                            if (response.code()==200) {
                                System.out.println(response.body().getArticles().size());
                                //Récupération du 1er article
                                Article article = response.body().getArticles().get(0);
                                System.out.println("onResponse " + article.getTitle());
                                System.out.println("onResponse " + article.getDescription());
                                System.out.println("onResponse " + article.getPublishedAt());
                            } else {
                                System.out.println("Une erreur est survenue " + response.code());
                            }
                            System.out.println(response.body().toString());
                        }
                        @Override
                        public void onFailure(Call<Actu> call, Throwable t) {
                            System.out.println("onFailure " + t.getMessage());
                        }
                    });
                    /*Intent GetActuServiceActivity = new Intent(MainActivity.this, GetActuService.class);
                    startActivity(GetActuServiceActivity);*/
                } else {
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


        challengeLongTerme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent choixIdeeLongTermeActivity = new Intent(MainActivity.this, ChoixIdeeLongTermeActivity.class);
                startActivity(choixIdeeLongTermeActivity);

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
        System.out.println("inputTempsDispo : " + inputTempsDispo);

        //PERMET D'AFFICHER LE TEMPS DISPO EN PETITE CASE QUI DISPARAIT RAPIDEMENT
        //Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
