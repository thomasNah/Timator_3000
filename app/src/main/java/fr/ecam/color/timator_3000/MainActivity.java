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

                //PASSAGE A L'ACTIVITE "IDEE"
                Intent gererPersoActivity = new Intent(MainActivity.this, GererPersoActivity.class);
                startActivity(gererPersoActivity);


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
