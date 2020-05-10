package fr.ecam.color.timator_3000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;

import java.util.List;

import fr.ecam.color.timator_3000.pack_actu.ActualiteActivity;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener {

    public Context context;


    private TextView tempsDispo;
    private Spinner spinnerTempsDispo;
    private Button giveAnIdeaButton;
    private Button challengeLongTerme;
    private Button activitePersoButton;
    private ImageButton preferencesButton;

    private DatabaseManager databaseManager;
    private DatabaseManager databaseManagerChallenge;
    String inputTempsDispo;
    private boolean darkTheme;
    private boolean flag = false;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String ville;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("fr.ecam.color.timator_3000",MODE_PRIVATE);
        darkTheme = preferences.getBoolean("Dark_Theme",false);
        if (darkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ville = "Lyon";
        //NavigationView
        toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

        //IDEE VIERGE
        //databaseManager.insertIdee(1, "", "", "", 3);

        //<item>1 minute</item>
        databaseManager.insertIdee(0, "", "1 minute", "Contempler le monde autour de vous", 3);
        databaseManager.insertIdee(1, "", "1 minute", "Lister votre TO-DO list d'aujourd'hui", 3);
        databaseManager.insertIdee(2, "", "1 minute", "Regarder notre météo dans le volet du menu !", 3);
        databaseManager.insertIdee(3, "", "1 minute", "Faire le lit", 3);
        databaseManager.insertIdee(4, "", "1 minute", "Répondre à quelqu'un par SMS", 3);
        databaseManager.insertIdee(5, "(ou à votre Papa d'ailleurs)", "1 minute", "Dire à votre maman que vous l'aimez", 3);
        databaseManager.insertIdee(6, "Covid-19 oblige", "1 minute", "Lavez vous les mains", 3);
        databaseManager.insertIdee(7, "Ca nous aiderait bien vis-à-vis des autres matières dans la coloration ...", "1 minute", "Mettre un 20 à ceux qui ont créé cette application", 3);
        databaseManager.insertIdee(8, "C'est toujours mieux qu'il soit toujours prêt à être jouer", "1 minute", "Ré-accorder votre instrument de musique", 3);
        databaseManager.insertIdee(9, "", "1 minute", "Réfléchisser à votre prochain repas", 3);
        databaseManager.insertIdee(10, "", "1 minute", "Vider vos poches de pantalon des vieux tickets de caisse", 3);

        //<item>2 minutes</item>
        databaseManager.insertIdee(11, "", "2 minutes", "Trier vos photos sur votre téléphone", 3);
        databaseManager.insertIdee(12, "", "2 minutes", "Regarder dans vos anciens mémos", 3);
        databaseManager.insertIdee(13, "Et si vous n'avez pas de plantes, rechercher une plante pour chez vous !", "2 minutes", "Arrosez vos plantes", 3);
        databaseManager.insertIdee(14, "On a jamais assez de vitamines", "2 minutes", "Manger un fruit", 3);
        databaseManager.insertIdee(15, "", "2 minutes", "Regarder nos news dans le volet du menu !", 3);
        databaseManager.insertIdee(16, "https://www.youtube.com/watch?v=UO8tcf3U0dY&list=PLlFikkv2B2ffwYiFQJmcao3RKtw1DFMz5", "2 minutes", "Regarder un épisode de Bref", 3);
        databaseManager.insertIdee(17, "", "2 minutes", "Nettoyer votre écran et clavier d'ordinateur", 3);
        databaseManager.insertIdee(18, "Je recommande 'Steak' de Q. Dupieux", "2 minutes", "Trouver un bon film pour ce soir", 3);
        databaseManager.insertIdee(19, "Et retenez votre record pour la prochaine fois !", "2 minutes", "Faire le maximum de pompes", 3);
        databaseManager.insertIdee(20, "https://secouchermoinsbete.fr/random", "2 minutes", "Apprendre une anecdote pour briller en société", 3);


        //<item>5 minutes</item>
        databaseManager.insertIdee(21, "Si vous n'avez pas d'idée, tentez https://www.youtube.com/watch?v=ee6pty2r0Lo", "5 minutes", "Ecouter une musique", 3);
        databaseManager.insertIdee(22, "C'est bon pour tout !", "5 minutes", "Faire du gainage", 3);
        databaseManager.insertIdee(23, "https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Page_au_hasard", "5 minutes", "Lire une page Wikipédia au hasard", 3);
        databaseManager.insertIdee(24, "", "5 minutes", "Dessiner quelque chose devant vous", 3);
        databaseManager.insertIdee(25, "", "5 minutes", "Répondre à vos e-mails", 3);
        databaseManager.insertIdee(26, "Si vous en avez marre de la caféine, essayez le Yorzo", "5 minutes", "Boire un café / thé ou autre", 3);
        databaseManager.insertIdee(27, "https://www.youtube.com/playlist?list=OLAK5uy_n6jZma6JhPGUkbnpieXqShfN9LMOXUEk0", "5 minutes", "Regarder un résumé de film humoristique", 3);
        databaseManager.insertIdee(28, "Ca sera toujours utile dans une soirée, ou pour votre neveu - https://www.blablagues.net/blague.html", "5 minutes", "Apprendre une blague", 3);
        databaseManager.insertIdee(29, "", "5 minutes", "Ranger le Bureau et les Téléchargements dans votre ordinateur", 3);
        databaseManager.insertIdee(30, "C'est important de s'aérer", "5 minutes", "Ouvrir la fenêtre", 3);


        //<item>10 minutes</item>
        databaseManager.insertIdee(31, "", "10 minutes", "Faire la vaisselle", 3);
        databaseManager.insertIdee(32, "Si vous avez aucune idée - http://www.arkazia.fr/steven-universe.php", "10 minutes", "Regarder un épisode de série de 10 minutes", 3);
        databaseManager.insertIdee(33, "https://www.youtube.com/results?search_query=m%C3%A9ditation+10+minutes", "10 minutes", "Méditer", 3);
        databaseManager.insertIdee(34, "", "10 minutes", "Regarder votre prochaine destination pour vos vacances", 3);
        databaseManager.insertIdee(35, "", "10 minutes", "Trier vos papiers administratif", 3);
        databaseManager.insertIdee(36, "Musique, cours, ...", "10 minutes", "Réviser une notion acquise récemment", 3);
        databaseManager.insertIdee(37, "", "10 minutes", "Trier vos mails", 3);
        databaseManager.insertIdee(38, "", "10 minutes", "Appeler un ami pour prendre de ses nouvelles", 3);
        databaseManager.insertIdee(39, "On dirait pas mais tenir un journal, ça fait du bien", "10 minutes", "Ecrire le récit de votre journée", 3);
        databaseManager.insertIdee(40, "Attention : on les ramène au pharmacien !", "10 minutes", "Faire le tri des médicaments périmés", 3);



        //<item>20 minutes</item>
        databaseManager.insertIdee(41, "", "20 minutes", "Jouer de votre instrument de musique", 3);
        databaseManager.insertIdee(42, "Si vous avez aucune idée - http://simpson-en-streaming.com/", "20 minutes", "Regarder un épisode de série de 20 minutes", 3);
        databaseManager.insertIdee(43, "", "20 minutes", "Faire les courses alimentaires", 3);
        databaseManager.insertIdee(44, "", "20 minutes", "Commencer un challenge long-terme", 3);
        databaseManager.insertIdee(45, "Et pourquoi pas donner les anciens ?", "20 minutes", "Trier vos vêtements", 3);
        databaseManager.insertIdee(46, "Si vous n'avez pas de programme de sport : https://gravityladies.net/portfolio/workout-fitness-routine-15-20minutes-maison/", "20 minutes", "Faire un entrainement de sport", 3);
        databaseManager.insertIdee(47, "", "20 minutes", "Faire un tour dehors, sans prendre son téléphone", 3);
        databaseManager.insertIdee(48, "", "20 minutes", "Faire de la pâte à crêpe", 3);
        databaseManager.insertIdee(49, "https://www.youtube.com/playlist?list=PLx58Sm1jzcB5KTTLovalMvt8T1-Ji_bS8", "20 minutes", "Regarder 'Crossed'", 3);
        databaseManager.insertIdee(50, "Richard Sandeau : https://www.youtube.com/channel/UCeyasiFBP5VUhRaMoapQHVA", "20 minutes", "Regarder les vidéos d'un vidéaste peu connu ", 3);



        //<item>30 minutes</item>
        databaseManager.insertIdee(51, "Ma technique perso : une casserole d'eau chaude dans le freezer, et laisser pendant 20 minutes avant d'enlever la glace", "30 minutes", "Dégivrer votre freezer", 3);
        databaseManager.insertIdee(52, "https://www.youtube.com/user/epenser1/videos", "30 minutes", "Cutlivez vous sur un domaine spécifique", 3);
        databaseManager.insertIdee(53, "https://www.youtube.com/user/cestpassorcierftv/playlists", "30 minutes", "Regarder un 'C'est Pas Sorcier'", 3);
        databaseManager.insertIdee(54, "Et pourquoi pas un classique ? L'ile Mystérieuse, de Jules Verne", "30 minutes", "Lire un livre", 3);
        databaseManager.insertIdee(55, "", "30 minutes", "Aller courir dehors", 3);
        databaseManager.insertIdee(56, "", "30 minutes", "Commencer ou poursuivre un projet perso de programmation", 3);
        databaseManager.insertIdee(57, "", "30 minutes", "Numériser certaines de vos vieilles photos", 3);
        databaseManager.insertIdee(58, "", "30 minutes", "Ecire une jolie lettre pour quelqu'un", 3);
        databaseManager.insertIdee(59, "Perdez vous le plus possible", "30 minutes", "Trainer sur Youtube", 3);
        databaseManager.insertIdee(60, "https://wallstreetenglish.fr/fiches-anglais/grammaire/", "30 minutes", "Réviser vos règles de grammaire anglaise", 3);


        //<item>45 minutes</item>
        databaseManager.insertIdee(61, "Si vous avez pas d'idée - https://regarder-mr-robot-streaming.com/robot-streaming-gratuit-saison-1", "45 minutes", "Regarder un épisode de série de 45 minutes ", 3);
        databaseManager.insertIdee(62, "Si vous n'avez pas d'idée - https://www.youtube.com/watch?v=xaGwEe1VqwQ&list=PLfGibfZATlGo2PVTfOcTCwd3B9LjEQ9QP", "45 minutes", "Ecouter un album entier de musique", 3);
        databaseManager.insertIdee(63, "", "45 minutes", "Faire un grand nettoyage dans toute la maison", 3);
        databaseManager.insertIdee(64, "", "45 minutes", "Faire du vélo", 3);
        databaseManager.insertIdee(65, "", "45 minutes", "Lire une bande-dessinée", 3);
        databaseManager.insertIdee(66, "", "45 minutes", "Jouer à un jeu vidéo", 3);
        databaseManager.insertIdee(67, "", "45 minutes", "Tracer votre arbre généalogique", 3);
        databaseManager.insertIdee(68, "https://cuisine.journaldesfemmes.fr/recette-gateau", "45 minutes", "Faire un gâteau", 3);
        databaseManager.insertIdee(69, "https://www.youtube.com/results?search_query=m%C3%A9diter+45+minutes", "45 minutes", "Méditer", 3);
        databaseManager.insertIdee(70, "https://www.darty.com/darty-et-vous/high-tech/audio/comment-numeriser-vos-cd", "45 minutes", "Numériser quelques un CD\"", 3);


        //<item>1h</item>
        databaseManager.insertIdee(71, "Si vous n'avez pas d'idée - https://www.bbc.co.uk/iplayer/episodes/b006mywy/planet-earth", "1h", "Regarder un documentaire animalier", 3);
        databaseManager.insertIdee(72, "", "1h", "Aller voir une exposition dans un musée", 3);
        databaseManager.insertIdee(73, "Pour plus d'idées : https://www.unjourunerecette.fr/que-manger-ce-soir", "1h", "Préparer le repas de ce soir", 3);
        databaseManager.insertIdee(74, "", "1h", "Trier votre bibliothèque", 3);
        databaseManager.insertIdee(75, "", "1h", "Jouer à un jeu de société avec vos proches", 3);
        databaseManager.insertIdee(76, "Si vous n'avez pas d'idées : https://www.youtube.com/watch?v=u41ujNodvnM", "1h", "Regarder un one-man show", 3);
        databaseManager.insertIdee(77, "", "1h", "Avancer sur votre travail pour les prochains jours", 3);
        databaseManager.insertIdee(78, "", "1h", "Ecrire une histoire", 3);
        databaseManager.insertIdee(79, "Badminton ? Ping-pong ? Foot ? Basket ?", "1h", "Aller faire du sport avec quelqu'un", 3);
        databaseManager.insertIdee(80, "https://www.marmiton.org/recettes/recette_ciabatta_25639.aspx", "1h", "Faites votre propre pain", 3);


        //<item>1h30</item>
        databaseManager.insertIdee(81, "Pourquoi pas un classique, comme La Grande Vadrouille ?", "1h30", "Regarder un film", 3);
        databaseManager.insertIdee(82, "", "1h30", "Débarasser votre cave ou grenier", 3);
        databaseManager.insertIdee(83, "", "1h30", "Planter ou aller acheter un arbre", 3);
        databaseManager.insertIdee(84, "", "1h30", "Rattraper un cours non suivi de Christophe Jouve", 3);
        databaseManager.insertIdee(85, "", "1h30", "Essayer de réparer quelque chose", 3);
        databaseManager.insertIdee(86, "", "1h30", "Bricoler pour améliorer votre habitat", 3);
        databaseManager.insertIdee(87, "", "1h30", "Sortir seul dans un bar pour rencontrer des gens", 3);
        databaseManager.insertIdee(88, "", "1h30", "Lire une phrase de Marcel Proust", 3);
        databaseManager.insertIdee(89, "", "1h30", "Regarder le temps qui passe, puis le temps perdu", 3);
        databaseManager.insertIdee(90, "Lien github : https://github.com/thomasNah/Timator_3000.git", "1h30", "Contribuer au développement de cette application", 3);





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

    //NavigationView
    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        if (id == R.id.nav_meteo) {
            Intent WeatherActivityInt = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(WeatherActivityInt);
        }
        else if (id == R.id.nav_actu) {
            Intent ActuActivity = new Intent(MainActivity.this, ActualiteActivity.class);
            startActivity(ActuActivity);
        }

        switch (id){
            case R.id.nav_meteo:
                break;
            case R.id.nav_actu:
                break;
            default:
                break;
        }

        return true;

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
                List<IdeeData> idees = databaseManager.lireTableTemps(inputTempsDispo);
                if (idees.size() > 0) {
                    Intent ideeActivity = new Intent(MainActivity.this, IdeeActivity.class);
                    ideeActivity.putExtra("inputTempsDispo", inputTempsDispo);
                    startActivity(ideeActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "il n'y a pas d'idée de cette durée pour l'instant", Toast.LENGTH_SHORT).show();
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
        /*navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WeatherActivityInt = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(WeatherActivityInt);
            }
        });
        navigationView.getHeaderView(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActuActivity = new Intent(MainActivity.this, ActualiteActivity.class);
                startActivity(ActuActivity);
            }
        });*/

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
