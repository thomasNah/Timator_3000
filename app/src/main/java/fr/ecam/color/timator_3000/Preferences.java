package fr.ecam.color.timator_3000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

public class Preferences extends AppCompatActivity {

    private Switch modeSombre;
    private DatabaseManager databaseManager;
    private TextView ideeView;
    private EditText ville;
    private ImageButton validerVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ville = findViewById(R.id.ville);
        validerVille = findViewById(R.id.validerVille);
        validerVille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherActivity.Weather weather  = new WeatherActivity.Weather();
                String content1;
                String laVille = ville.getText().toString();
                if (laVille.equals("Vienot")) {
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    Toast.makeText(getApplicationContext(), "La coloration numérique était pour nous uniquement centré sur la programmation. Par conséquent, le temps passé sur l'électronique avancée, c'est pas fameux fameux. Avec une bonne note, on pourrait peut être se passer de rattrapage ? A votre bon coeur <3", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        content1 = weather.execute("https://api.weatherbit.io/v2.0/forecast/hourly?city=" + laVille + "&lang=fr&key=d31be973eb0149218e716d52a361d0da&hours=10").get();
                        if (content1.equals("")) {
                            Toast.makeText(getApplicationContext(), "La ville spécifiée n'est pas valide", Toast.LENGTH_SHORT).show();
                        } else {
                            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            Toast.makeText(getApplicationContext(), "La ville est définie sur " + laVille, Toast.LENGTH_SHORT).show();
                            ville.setText(null);
                            SharedPreferences preferences = getSharedPreferences("fr.ecam.color.timator_3000", MODE_PRIVATE);
                            preferences.edit().putString("ville", laVille).apply();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        modeSombre = findViewById(R.id.modeSombre);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            modeSombre.setChecked(true);
        }
        modeSombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences("fr.ecam.color.timator_3000",MODE_PRIVATE);
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    preferences.edit().putBoolean("Dark_Theme",true).apply();
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    preferences.edit().putBoolean("Dark_Theme",false).apply();
                    recreate();
                }
            }
        });
        //ADD BACK BUTTON POUR RETOURNER SUR MAIN ACTIVITY
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent mainActivity = new Intent(Preferences.this, MainActivity.class);
            startActivity(mainActivity);
        }
        return super.onOptionsItemSelected(item);
    }

}