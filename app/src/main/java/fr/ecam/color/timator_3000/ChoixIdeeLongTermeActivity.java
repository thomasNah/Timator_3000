package fr.ecam.color.timator_3000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChoixIdeeLongTermeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String SHARED_PREFS = "sharedPrefs";

    private Button voirEcheances;
    private Spinner spinnerIdeeLongTerme;
    private TextView descriptionIdee;
    private TextView listeEcheance;
    private TextView dureeChallenge;
    private TextView dureeRestanteChallenge;
    private TextView prochaineEcheance;
    private TextView pourcentageChiffre;
    private ArrayList<String> savedDataList = new ArrayList<>();
    ActiviteLongTermeChoisieActivity longTerme = new ActiviteLongTermeChoisieActivity();


    private DatabaseManager databaseManager;

    private String activiteChoisie;
    //private List<IdeeData> sousIdees = new ArrayList<>();
    private IdeeDataList sousIdees = new IdeeDataList();

    private int pourcentageRestant;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_idee_long_terme);

        voirEcheances = findViewById(R.id.lancerLongTermeButton);
        spinnerIdeeLongTerme = findViewById(R.id.spinnerIdeeLongTerme);
        descriptionIdee = findViewById(R.id.descriptionIdee);
        listeEcheance = findViewById(R.id.prochaineEcheance);
        dureeChallenge = findViewById(R.id.dureeChallenge);
        dureeRestanteChallenge = findViewById(R.id.dureeRestanteChallenge);
        prochaineEcheance = findViewById(R.id.prochaineEcheance);
        pourcentageChiffre = findViewById(R.id.pourcentageChiffre);
        progressBar = findViewById(R.id.progressBar);

        databaseManager = new DatabaseManager(this);

        progressBar.setMax(100);

        //ETRE AU LANCEMENT A L'ETAPE 1
        /*
        final List<IdeeData> listeChallenge = databaseManager.lireTableChallenge();
        activiteChoisie = listeChallenge.get(0).getNom();
        savedDataList.add(activiteChoisie);
        savedDataList.add(Integer.toString(1));
        saveList(savedDataList,"savedDataList");

         */



    }

    protected void onResume() {
        super.onResume();


        savedDataList = loadList("savedDataList");
        System.out.println("savedDataList 1:");
        readList(savedDataList);



        int max = databaseManager.getIdMaxChallenge();
        System.out.println("max :" + max);
        final ArrayList<String> spinnerItems= new ArrayList<String>();
        for (int i = 0; i<max; i = i + 10){
            spinnerItems.add(databaseManager.lireIdSpecifique(i).getNom());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdeeLongTerme.setAdapter(adapter);
        spinnerIdeeLongTerme.setOnItemSelectedListener(this);

        voirEcheances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent echeancesActivite = new Intent(ChoixIdeeLongTermeActivity.this, ActiviteLongTermeChoisieActivity.class);
                echeancesActivite.putExtra("sousIdees", (Parcelable) sousIdees);
                echeancesActivite.putExtra("activiteChoisie",activiteChoisie);
                startActivity(echeancesActivite);

            }
        });


    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        final List<IdeeData> listeChallenge = databaseManager.lireTableChallenge();
        final List<Integer> listeIdChallenge = new ArrayList<>();
        for(int i = 0; i<listeChallenge.size();i++){
            listeIdChallenge.add(listeChallenge.get(i).getIdIdee());
        }
        sousIdees.clear();

        activiteChoisie = parent.getItemAtPosition(position).toString();


        //System.out.println("activiteChoisie :" + activiteChoisie);

        for(int i=0;i< listeIdChallenge.size();i++){

            String str = databaseManager.lireIdSpecifique(listeIdChallenge.get(i)).getNom();
            //System.out.println("str : " + str);

            String contenu = databaseManager.lireIdSpecifique(listeIdChallenge.get(i)).getContenu();
            String duree = databaseManager.lireIdSpecifique(listeIdChallenge.get(i)).getDuree();





            if(activiteChoisie.equalsIgnoreCase(str)){
                descriptionIdee.setText(contenu);
                dureeChallenge.setText(duree);




                System.out.println("listeIdChallenge max : " + Collections.max(listeIdChallenge));

                ArrayList<Integer> trouverMaxId = new ArrayList<>();
                for(int iTrouverIdMax=0;iTrouverIdMax<10;iTrouverIdMax++){
                    if(databaseManager.lireIdSpecifique(iTrouverIdMax+listeIdChallenge.get(i)).getNote() != 666){
                        trouverMaxId.add(databaseManager.lireIdSpecifique(iTrouverIdMax+listeIdChallenge.get(i)).getIdIdee());
                    }
                }
                int maxId = Collections.max(trouverMaxId);

                System.out.println("maxId de trouverMaxId (ChoixIdee) : " + maxId);





                int j = listeIdChallenge.get(i)+1;

                    if(listeIdChallenge.get(i) != 0) {
                        while (j <= maxId) {

                            sousIdees.add(databaseManager.lireIdSpecifique(j));
                            System.out.println("sousIdees  %10: " + sousIdees.get(0).getNom());
                            j++;

                        }
                    }

                    if(listeIdChallenge.get(i) == 0) {
                        while (j <= maxId) {
                            sousIdees.add(databaseManager.lireIdSpecifique(j));
                            //System.out.println("sousIdees : " + sousIdees.get(0).getNom());
                            j++;
                        }

                    }

                    //LECTURE DES SOUS IDEES
                    for(int z = 0;z<sousIdees.size();z++){
                        System.out.println(sousIdees.get(z));
                    }

                String etape = "";


                        boolean flag = false;
                        int iFoundSavedDataList = 0;
                        while (flag == false && iFoundSavedDataList < savedDataList.size()) {
                            System.out.println("ZOB");
                            System.out.println(iFoundSavedDataList);
                            System.out.println(savedDataList.get(iFoundSavedDataList));
                            System.out.println(str);

                            if (savedDataList.get(iFoundSavedDataList).equals(str)) {
                                System.out.println("INNN");
                                etape = savedDataList.get(iFoundSavedDataList + 1);

                                if(Integer.parseInt(etape) == 666){
                                    listeEcheance.setText("Activité finie ! Bravo !");
                                    dureeRestanteChallenge.setText("0 minutes - Finie");
                                    pourcentageChiffre.setText("100 %");
                                    progressBar.setProgress(100);
                                    flag = true;
                                }
                                else {

                                    int etapeInt = Integer.parseInt(etape);
                                    int etapeIntMoins1 = etapeInt - 1;
                                    System.out.println(etapeIntMoins1);

                                    if (etapeIntMoins1 >= 0) {
                                        listeEcheance.setText("Etape " + etape + " : " + sousIdees.get(etapeIntMoins1).getNom() + " (durée : " + sousIdees.get(etapeIntMoins1).getDuree() + " )" + System.getProperty ("line.separator") + "Description : " + sousIdees.get(etapeIntMoins1).getContenu());
                                    }

                                    flag = true;

                                    //Choper la durée totale faite
                                    int dureeDone = 0;
                                    for (int iDuree = 0; iDuree < etapeInt - 1; iDuree++) {
                                        //System.out.println("dureee ta maman : " +  getIntegers(sousIdees.get(iDuree).getDuree()));
                                        dureeDone = dureeDone + getIntegers(sousIdees.get(iDuree).getDuree());
                                    }
                                    System.out.println("dureeDone :" + dureeDone);

                                    int dureeRestantes = getIntegers(duree) - dureeDone;
                                    System.out.println("DUREE RESTANTE : " + dureeRestantes);
                                    dureeRestanteChallenge.setText(Integer.toString(dureeRestantes) + " minutes");

                                    pourcentageRestant = (dureeDone*100)/getIntegers(duree);
                                    pourcentageChiffre.setText(pourcentageRestant + " %");


                                    progressBar.setProgress(pourcentageRestant);




                                }




                                }

                            iFoundSavedDataList++;


                        }











            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void readList(ArrayList<String> list){
        if (list == null){
            System.out.println("Liste vide");
        }
        else{
            for(int i = 0;i<list.size();i++){
                System.out.println(list.get(i));
            }

        }

    }

    public ArrayList<String> loadList(String listName) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int size = prefs.getInt(listName + "_size", 0);
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<size;i++)
            list.add(prefs.getString(listName + "_" + i, null));
        return list;
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


    public boolean saveList(ArrayList<String> list, String listName) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(listName +"_size", list.size());
        for(int i=0;i<list.size();i++)
            editor.putString(listName + "_" + i, list.get(i));
        return editor.commit();
    }





}
