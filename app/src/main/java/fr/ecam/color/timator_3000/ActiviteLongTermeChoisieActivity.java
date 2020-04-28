package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class ActiviteLongTermeChoisieActivity extends AppCompatActivity {

    private TextView nomActivite;
    private String s1[],s2[];

    private RecyclerView RecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_long_terme_choisie);

        nomActivite = findViewById(R.id.nomActivite);
        s1 = getResources().getStringArray(R.array.echeancesActivite);
        s2 = getResources().getStringArray(R.array.descriptionEcheances);




    }
}
