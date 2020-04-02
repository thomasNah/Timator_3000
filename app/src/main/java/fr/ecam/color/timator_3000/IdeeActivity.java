package fr.ecam.color.timator_3000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IdeeActivity extends AppCompatActivity {

    TextView affichageTempsIdee;
    TextView ideeText;
    Button autreIdeeButton;
    Button likeButton;
    Button noLikeButton;

    String inputTempsDispo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idee);

        affichageTempsIdee = findViewById(R.id.affichageTempsIdee);
        ideeText = findViewById(R.id.ideeText);
        autreIdeeButton = findViewById(R.id.autreIdeeButton);
        likeButton = findViewById(R.id.likeButton);
        noLikeButton = findViewById(R.id.noLikeButton);

        //RECUPERATION DE LA VARIABLE inputTempsDispo
        Intent intent = getIntent();
        inputTempsDispo = "";
        if(intent != null){
            if(intent.hasExtra("inputTempsDispo")){
                inputTempsDispo = intent.getStringExtra("inputTempsDispo");
            }
        }

        affichageTempsIdee.setText("Temps de votre idée : " + inputTempsDispo);

        //APPUI BOUTON autreIdeeButton
        autreIdeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ideeActivity = new Intent(IdeeActivity.this, IdeeActivity.class);
                ideeActivity.putExtra("inputTempsDispo",inputTempsDispo);
                startActivity(ideeActivity);

            }
        });

        //ADD BACK BUTTON POUR RETOURNER SUR MAIN ACTIVITY
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
