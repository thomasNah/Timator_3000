package fr.ecam.color.timator_3000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class IdeeActivity extends AppCompatActivity {

    TextView affichageTempsIdee;
    TextView ideeText;
    Button autreIdeeButton;
    Button likeButton;
    Button noLikeButton;
    private DatabaseManager databaseManager;
    String inputTempsDispo;
    int compteur =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idee);
        databaseManager = new DatabaseManager(this);
        //LIEN VARIABLE AVEC LAYOUT
        affichageTempsIdee = findViewById(R.id.affichageTempsIdee);
        ideeText = findViewById(R.id.ideeText);
        autreIdeeButton = findViewById(R.id.autreIdeeButton);
        likeButton = findViewById(R.id.likeButton);
        noLikeButton = findViewById(R.id.noLikeButton);

        //ADD BACK BUTTON POUR RETOURNER SUR MAIN ACTIVITY
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<IdeeData> idees = databaseManager.lireTable();

    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<IdeeData> idees = databaseManager.lireTable();
        ideeText.setText(idees.get(compteur).getContenu());
        //RECUPERATION DE LA VARIABLE inputTempsDispo
        /*Intent intent = getIntent();
        inputTempsDispo = "";idees.get(compteur).getContenu());
        if(intent != null){
            if(intent.hasExtra("inputTempsDispo")){
                inputTempsDispo = intent.getStringExtra("inputTempsDispo");
            }
        }*/

        affichageTempsIdee.setText("Temps de votre idée : " + inputTempsDispo);

        //APPUI BOUTON autreIdeeButton
        autreIdeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            compteur+=1;
            if (compteur>=idees.size()){
                compteur = 0;
            }
            ideeText.setText(idees.get(compteur).getContenu());


            }
        });
    }

    //BACK BUTTON MANAGE TO GO TO MAIN ACTIVITY
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent mainActivity = new Intent(IdeeActivity.this, MainActivity.class);
            startActivity(mainActivity);

            //LE FINISH DEVRAIT NORMALEMENT DETRUIRE POUR QU'ON NE PUISSE PAS REVENIR A CELLE CI
            //MAIS EN PRATIQUE IL NE MARCHE PAS
            //IdeeActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
/*
List<IdeeData> idees = databaseManager.lireTable();
        for (int i = 0;i<idees.size();i++){
            ideesView.append(idees.get(i).toString()+"\n");
        }
 */