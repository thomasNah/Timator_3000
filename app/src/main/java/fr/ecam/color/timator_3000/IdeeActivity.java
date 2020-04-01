package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class IdeeActivity extends AppCompatActivity {

    TextView ideeText;
    Button autreIdeeButton;
    Button likeButton;
    Button noLikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idee);

        ideeText = findViewById(R.id.ideeText);
        autreIdeeButton = findViewById(R.id.autreIdeeButton);
        likeButton = findViewById(R.id.likeButton);
        noLikeButton = findViewById(R.id.noLikeButton);


    }
}
