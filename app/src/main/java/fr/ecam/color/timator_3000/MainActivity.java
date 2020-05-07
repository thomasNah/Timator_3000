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

import java.util.List;

import fr.ecam.color.timator_3000.pack_actu.Actu;
import fr.ecam.color.timator_3000.pack_actu.ActualiteActivity;
import fr.ecam.color.timator_3000.pack_actu.Article;
import fr.ecam.color.timator_3000.pack_actu.GetActuService;
import fr.ecam.color.timator_3000.pack_actu.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            //ATTENTION : NE PAS METTRE DE DUREE EN H ! QUE EN MINUTES POUR LES CHALLENGE (sinon pb l.215 - Choix)
            // NOM DU CHALLENGE - ID FINIT EN 0
            // SOUS ETAPES - ID FINIT DE 1 à 9

            //ID 0 - Touchez votre audience sur mobile (Learn Google)
            databaseManager.insertIdeeChallenge(00, "https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile", "none", "Touchez votre audience sur mobile", 3);
            databaseManager.insertIdeeChallenge(01, "https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile/module/17", "30 minutes", "Tirez profit des opportunités qu'offre le mobile", 3);
            databaseManager.insertIdeeChallenge(02, "https://learndigital.withgoogle.com/ateliersnumeriques/course/connect-with-mobile/module/16", "20 minutes", "Découvrez les possibilités offertes par le mobile", 3);

            //ID 10 - Lancez une activité en ligne (Learn Google)
            databaseManager.insertIdeeChallenge(10,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online","none","Lancez une activité en ligne",3);
            databaseManager.insertIdeeChallenge(11,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/1","15 minutes","Les opportunités qu'offre Internet",3);
            databaseManager.insertIdeeChallenge(12,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/2","30 minutes","Vos premiers pas vers le succès sur Internet",3);
            databaseManager.insertIdeeChallenge(13,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/137","30 minutes","Planifiez votre stratégie commerciale sur le Web",3);
            databaseManager.insertIdeeChallenge(14,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/22","25 minutes","Créez votre boutique en ligne",3);
            databaseManager.insertIdeeChallenge(15,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/23","35 minutes","Augmentez vos ventes en ligne",3);
            databaseManager.insertIdeeChallenge(16,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/3","40 minutes","Développez votre présence en ligne",3);
            databaseManager.insertIdeeChallenge(17,"https://learndigital.withgoogle.com/ateliersnumeriques/course/business-online/module/12","20 minutes","Faites-vous connaître localement",3);

            //ID 20 - Apprenez à apprendre (Open Classroom)
            databaseManager.insertIdeeChallenge(20,"https://openclassrooms.com/fr/courses/4312781-apprenez-a-apprendre","none","Apprenez à apprendre",3);
            databaseManager.insertIdeeChallenge(21,"https://openclassrooms.com/fr/courses/4312781-apprenez-a-apprendre/4789781-tirez-un-maximum-de-ce-cours","80 minutes","Préparez-vous à apprendre",3);
            databaseManager.insertIdeeChallenge(22,"https://openclassrooms.com/fr/courses/4312781-apprenez-a-apprendre/4789866-definissez-votre-objectif-strategique","80 minutes","Définissez votre objectif stratégique",3);
            databaseManager.insertIdeeChallenge(23,"https://openclassrooms.com/fr/courses/4312781-apprenez-a-apprendre/4790551-visez-le-bon-niveau","80 minutes","Visez le bon niveau",3);
            databaseManager.insertIdeeChallenge(24,"https://openclassrooms.com/fr/courses/4312781-apprenez-a-apprendre/4790751-mettez-en-place-votre-environnement-dapprentissage","120 minutes","Mettez en place votre environnement d'apprentissage",3);

            //ID 30 - Initiez-vous au Machine Learning (Open Classroom)
            databaseManager.insertIdeeChallenge(30,"https://openclassrooms.com/fr/courses/4011851-initiez-vous-au-machine-learning","none","Initiez-vous au Machine Learning",3);
            databaseManager.insertIdeeChallenge(31,"https://openclassrooms.com/fr/courses/4011851-initiez-vous-au-machine-learning/5869331-decouvrez-le-domaine-de-la-data-science","160 minutes","Identifiez les possibilités du Machine Learning",3);
            databaseManager.insertIdeeChallenge(32,"https://openclassrooms.com/fr/courses/4011851-initiez-vous-au-machine-learning/5868296-transformez-des-besoins-metiers-en-problemes-de-machine-learning","80 minutes","Identifiez les techniques et outils du Machine Learning",3);
            databaseManager.insertIdeeChallenge(33,"https://openclassrooms.com/fr/courses/4011851-initiez-vous-au-machine-learning/4120981-construisez-un-modele-statistique","200 minutes","Entraînez votre premier algorithme de Machine Learning",3);
            databaseManager.insertIdeeChallenge(34,"https://openclassrooms.com/fr/courses/4011851-initiez-vous-au-machine-learning/4020641-familiarisez-vous-avec-les-limites-des-algorithmes","160 minutes","Appréhendez les limites du Machine Learning",3);



            //IDEE CHALLENGE VIERGE
            //databaseManager.insertIdeeChallenge(00,"","","",3);





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
                    Intent ActuActivity = new Intent(MainActivity.this, ActualiteActivity.class);
                    startActivity(ActuActivity);
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
