package fr.ecam.color.timator_3000;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Preferences extends AppCompatActivity {

    private Switch modeSombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        modeSombre = findViewById(R.id.modeSombre);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            modeSombre.setChecked(true);
        }
        modeSombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            }
        });
        //ADD BACK BUTTON POUR RETOURNER SUR MAIN ACTIVITY
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent mainActivity = new Intent(Preferences.this, MainActivity.class);
            startActivity(mainActivity);
        }
        return super.onOptionsItemSelected(item);
    }
}