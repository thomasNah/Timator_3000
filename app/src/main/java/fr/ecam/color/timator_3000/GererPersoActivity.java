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

public class GererPersoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerIdeeDejaExistantes;
    private Button editerIdee;
    private Button creerNouvelleIdeePerso;
    private EditText inputNomIdee;
    private Spinner spinnerChoixTempsActivity;
    private EditText inputDescription;
    private EditText inputNoteIdee;

    private boolean userPutSomethingInNomIdee;
    private boolean userPutSomethingInDescription;
    private boolean creerNouvelleIdeePersoState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_perso);

        spinnerIdeeDejaExistantes = findViewById(R.id.spinnerIdeeDejaExistantes);
        editerIdee = findViewById(R.id.editerIdee);
        creerNouvelleIdeePerso = findViewById(R.id.editerIdee);
        inputNomIdee = findViewById(R.id.inputNomIdee);
        spinnerChoixTempsActivity = findViewById(R.id.spinnerChoixTempsActivity);
        inputDescription = findViewById(R.id.inputDescription);
        inputNoteIdee = findViewById(R.id.inputNoteIdee);

        //GERER ACTIVATION BOUTON creerNouvelleIdeePerso
        userPutSomethingInNomIdee = false;
        userPutSomethingInDescription = false;
        creerNouvelleIdeePersoState = false;


        //SPINNER spinnerIdeeDejaExistantes
        ArrayAdapter<CharSequence> adapterSpinnerIdeeDejaExistantes = ArrayAdapter.createFromResource(this,
                R.array.arrayActiviteExistantes, android.R.layout.simple_spinner_item);
        adapterSpinnerIdeeDejaExistantes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdeeDejaExistantes.setAdapter(adapterSpinnerIdeeDejaExistantes);
        spinnerIdeeDejaExistantes.setOnItemSelectedListener(this);


        //SPINNER spinnerChoixTempsActivity
        ArrayAdapter<CharSequence> adapterSpinnerChoixTempsActivity = ArrayAdapter.createFromResource(this,
                R.array.arrayTempsDispo, android.R.layout.simple_spinner_item);
        adapterSpinnerChoixTempsActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoixTempsActivity.setAdapter(adapterSpinnerChoixTempsActivity);
        spinnerChoixTempsActivity.setOnItemSelectedListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

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

                if(userPutSomethingInNomIdee && userPutSomethingInPassword){
                    creerNouvelleIdeePerso.setEnabled(true);
                    creerNouvelleIdeePersoState = true;
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

            //RECUPERER LES INFORMATIONS DE L'IDEE !

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
