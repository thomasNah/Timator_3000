package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ChoixIdeeLongTermeActivity extends AppCompatActivity {

    private Button lancerLongTermeButton;
    private Spinner spinnerIdeeLongTerme;
    private TextView descriptionIdee;
    private TextView listeEcheance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_idee_long_terme);

        lancerLongTermeButton = findViewById(R.id.lancerLongTermeButton);
        spinnerIdeeLongTerme = findViewById(R.id.spinnerIdeeLongTerme);
        descriptionIdee = findViewById(R.id.descriptionIdee);
        listeEcheance = findViewById(R.id.listeEcheance);


    }
}
