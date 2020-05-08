package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActiviteLongTermeChoisieActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";

    private TextView nomActivite;
    private IdeeDataList sousIdees = new IdeeDataList();

    private TextView echeances;
    private Button buttonDone;
    private Button buttonFinish;

    private EditText etapeRentree;
    private int etapeActuelle;

    private ArrayList<String> savedDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_long_terme_choisie);

        nomActivite = findViewById(R.id.nomActivite);

        echeances = findViewById(R.id.echeances);
        buttonDone = findViewById(R.id.buttonDone);
        buttonFinish = findViewById(R.id.buttonFinish);
        etapeRentree = findViewById(R.id.etapeActuelle);

        Bundle b = getIntent().getExtras();
        IdeeDataList sousIdees = b.getParcelable("sousIdees");
        String activiteChoisie = b.getString("activiteChoisie");

        for(int i = 0;i<sousIdees.size();i++){
            int etapeSousIdees = i + 1;
            echeances.append("Etape " + etapeSousIdees + " : ");
            echeances.append(sousIdees.get(i).getNom() + " (durée : " + sousIdees.get(i).getDuree() + " )");
            echeances.append(System.getProperty ("line.separator"));
        }





    }

    @Override
    protected void onResume() {
        super.onResume();



        savedDataList = loadList("savedDataList");
        readList(savedDataList);


        Bundle b = getIntent().getExtras();
        final IdeeDataList sousIdees = b.getParcelable("sousIdees");
        final String activiteChoisie = b.getString("activiteChoisie");

        int iFoundSavedDataList = 0;
        boolean flag = false;

        while(flag == false && iFoundSavedDataList<savedDataList.size()) {
            System.out.println(iFoundSavedDataList);
            System.out.println(savedDataList.get(iFoundSavedDataList));
            System.out.println(activiteChoisie);

            if (savedDataList.get(iFoundSavedDataList).equals(activiteChoisie)) {
                etapeActuelle = Integer.parseInt(savedDataList.get(iFoundSavedDataList+1));
                System.out.println(activiteChoisie);
                System.out.println(etapeActuelle);
                flag = true;

                if(etapeActuelle == sousIdees.size()){
                    buttonFinish.setEnabled(true);
                }
                else{
                    buttonFinish.setEnabled(false);
                }

            }


            iFoundSavedDataList++;
        }


        nomActivite.setText(activiteChoisie);


        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etapeRentree.getText().toString().equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "Veuillez rentrer un nombre valide";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    etapeActuelle = Integer.parseInt(etapeRentree.getText().toString());
                    if(etapeActuelle == sousIdees.size()){
                        buttonFinish.setEnabled(true);
                    }
                    else{
                        buttonFinish.setEnabled(false);
                    }
                }
                if (etapeActuelle > sousIdees.size() || etapeActuelle <= 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Veuillez rentrer un nombre valide";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    etapeRentree.getText().clear();
                    etapeActuelle = 0;
                }

                System.out.println("etapeActuelle : " + etapeActuelle);
                etapeRentree.getText().clear();


                if(savedDataList.contains(activiteChoisie)){
                    int index = savedDataList.indexOf(activiteChoisie);
                    System.out.println("index add :" + index);
                    savedDataList.set(index+1,Integer.toString(etapeActuelle));
                }
                else{
                    savedDataList.add(activiteChoisie);
                    savedDataList.add(Integer.toString(etapeActuelle));
                }
                saveList(savedDataList,"savedDataList");
                System.out.println("savedDataList 2:");
                readList(savedDataList);





            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                CharSequence text = "Activité '" + activiteChoisie + "' finie ! Bravo !";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                int jFoundSavedDataList = 0;
                boolean flag = false;

                while(flag == false && jFoundSavedDataList<savedDataList.size()) {

                    if (savedDataList.get(jFoundSavedDataList).equals(activiteChoisie)) {
                        etapeActuelle = 666;
                        int index = savedDataList.indexOf(activiteChoisie);
                        savedDataList.set(index+1,Integer.toString(etapeActuelle));
                        //System.out.println(activiteChoisie);
                        //System.out.println(etapeActuelle);
                        flag = true;
                        saveList(savedDataList,"savedDataList");
                        readList(savedDataList);

                    }

                    jFoundSavedDataList++;
                }

            }
        });

    }


    public boolean saveList(ArrayList<String> list, String listName) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(listName +"_size", list.size());
        for(int i=0;i<list.size();i++)
            editor.putString(listName + "_" + i, list.get(i));
        return editor.commit();
    }

    public ArrayList<String> loadList(String listName) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int size = prefs.getInt(listName + "_size", 0);
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<size;i++){
            list.add(prefs.getString(listName + "_" + i, null));
        }

        return list;
    }

    public void readList(ArrayList<String> list){
        for(int i = 0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }

    public ArrayList<String> getSavedDataList() {
        return savedDataList;
    }
}
