package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class GererPersoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerIdeeDejaExistantes;
    private Button editerIdee;
    private Button creerNouvelleIdeePerso;
    private EditText inputNomIdee;
    private Spinner spinnerChoixTempsActivity;
    private EditText inputDescription;
    private EditText inputNoteIdee;
    private DatabaseManager databaseManager;
    private boolean userPutSomethingInNomIdee;

    private boolean creerNouvelleIdeePersoState;
    private boolean supprIdeePersoState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_perso);
        databaseManager = new DatabaseManager(this); // je sais pas pourquoi il faut mettre ca mais sinon ca marche pas (je chercherai plus tard )
        spinnerIdeeDejaExistantes = findViewById(R.id.spinnerIdeeDejaExistantes);
        editerIdee = findViewById(R.id.editerIdee);
        creerNouvelleIdeePerso = findViewById(R.id.creerNouvelleIdeePerso);
        inputNomIdee = findViewById(R.id.inputNomIdee);
        spinnerChoixTempsActivity = findViewById(R.id.spinnerChoixTempsActivity);
        inputDescription = findViewById(R.id.inputDescription);
        inputNoteIdee = findViewById(R.id.inputNoteIdee);

        //GERER ACTIVATION BOUTON creerNouvelleIdeePerso
        userPutSomethingInNomIdee = false;

        creerNouvelleIdeePersoState = false;
        supprIdeePersoState = false;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayTempsDispo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoixTempsActivity.setAdapter(adapter);




    }


    @Override
    protected void onResume() {
        super.onResume();

        //SPINNER spinnerIdeeDejaExistantes
        final List<IdeeData> idees = databaseManager.lireTable();
        ArrayList<String> spinnerItems= new ArrayList<String>();
        for (int i = 0; i<idees.size(); i++){
            spinnerItems.add(idees.get(i).getNom());
        }

        spinnerIdeeDejaExistantes.setAdapter(new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, spinnerItems));

        creerNouvelleIdeePerso.setEnabled(creerNouvelleIdeePersoState);

        inputNomIdee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() != 0){
                    userPutSomethingInNomIdee = true;
                }

                if (s.toString().length() == 0){
                    userPutSomethingInNomIdee = false;
                }

                if(userPutSomethingInNomIdee){
                    creerNouvelleIdeePerso.setEnabled(true);
                    creerNouvelleIdeePersoState = true;
                    supprIdeePersoState = true;
                }

                else{
                    creerNouvelleIdeePerso.setEnabled(false);
                    creerNouvelleIdeePersoState = false;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        editerIdee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActivityConnect = new Intent(GererPersoActivity.this,EditerIdeeActivity.class);
                ActivityConnect.putExtra("nomIdee",String.valueOf(spinnerIdeeDejaExistantes.getSelectedItem()));
                startActivity(ActivityConnect);
            //RECUPERER LES INFORMATIONS DE L'IDEE !

            }
        });
        creerNouvelleIdeePerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = String.valueOf(inputNomIdee.getText());
                String duree = spinnerChoixTempsActivity.getSelectedItem().toString();
                String description = String.valueOf(inputDescription.getText());
                if (description==null){
                    description = "";
                }
                int note = Integer.valueOf(String.valueOf(inputNoteIdee.getText())); //cette ligne fait planter le setText je sais pas pk
                int id = databaseManager.getIdMax()+ 1;
                databaseManager.insertIdee(id,description,duree,nom,note);
                inputNomIdee.setText("");
                spinnerChoixTempsActivity.setSelection(0);
                inputDescription.setText("");
                inputNoteIdee.setText("");
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onBackPressed(){
        Intent retourMain = new Intent(GererPersoActivity.this,MainActivity.class);
        startActivity(retourMain);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
