package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;


public class EditerIdeeActivity extends AppCompatActivity {
        private TextView idee;
        private IdeeData ideeEnCours;
        private DatabaseManager databaseManager;
        private Spinner spinnerTempsDispo;
        private Spinner spinnerNote;
        private Button boutonEdit;
        private EditText editIdee;
        private Button deleteBouton;
        private TextView stringIdee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editer_idee);
        idee = findViewById(R.id.textView2);
        databaseManager = new DatabaseManager(this);
        spinnerTempsDispo = findViewById(R.id.spinnerTempsDispo);
        spinnerNote = findViewById(R.id.spinnerNote);
        boutonEdit = findViewById(R.id.button);
        editIdee = findViewById(R.id.editText);
        editIdee.setHint("Description Idee");
        deleteBouton = findViewById(R.id.button2);
        stringIdee = findViewById(R.id.textView6);

        Intent intent = getIntent();
         String nomIdee = "";
        if (intent != null){
            if (intent.hasExtra("nomIdee")){
                nomIdee = intent.getStringExtra("nomIdee");
            }
        }

        final String nomIdeeFinal = nomIdee; // je sais pas trop pourquoi mais sinon il veut pas

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayTempsDispoEditer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTempsDispo.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.arrayNote, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNote.setAdapter(adapter2);
        final List<IdeeData> idees = databaseManager.lireTable();
        final IdeeData[] ideeData = new IdeeData[1];
        for  (int i=0;i<idees.size();i++){
            if (idees.get(i).getNom().equals(nomIdeeFinal)){
                ideeEnCours = idees.get(i);
            }
        }
        stringIdee.setText(ideeEnCours.toString1());
        boutonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //c'est ici qu'on update l'idee dans la base de données
                        int idIdee = ideeEnCours.getIdIdee();
                        String nouvelleDesc = "";
                        if (String.valueOf(editIdee.getText()).equals("")){
                            nouvelleDesc = ideeEnCours.getContenu();
                        }
                        else{
                            nouvelleDesc = String.valueOf(editIdee.getText());
                        }
                        String nouvelleDuree = "";
                        if (String.valueOf(spinnerTempsDispo.getSelectedItem()).equals("selectionner")){
                            nouvelleDuree = ideeEnCours.getDuree();
                        }
                        else{
                            nouvelleDuree = String.valueOf(spinnerTempsDispo.getSelectedItem());
                        }
                        int nouvelleNote = 0;
                        if (String.valueOf(spinnerNote.getSelectedItem()).equals("selectionner")){
                            nouvelleNote = ideeEnCours.getNote();
                        }
                        else{
                        nouvelleNote = Integer.valueOf(String.valueOf(spinnerNote.getSelectedItem()));
                        }
                        String str = "update IDEE set contenu = '"+nouvelleDesc+"' , duree = '"+nouvelleDuree+"', note =" +nouvelleNote+" where idIdee = "+idIdee;
                        databaseManager.getWritableDatabase().execSQL(str);
                        List<IdeeData> idees1  = databaseManager.lireTable();
                        Intent RetourGererPerso = new Intent(EditerIdeeActivity.this,GererPersoActivity.class);
                        startActivity(RetourGererPerso);
            }
        });
        deleteBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for  (int i=0;i<idees.size();i++){
                    if (idees.get(i).getNom().equals(nomIdeeFinal)){
                        int idIdee = idees.get(i).getIdIdee();
                        String str = "delete from IDEE where idIdee ="+idIdee;
                        databaseManager.getWritableDatabase().execSQL(str);
                        Intent RetourGererPerso = new Intent(EditerIdeeActivity.this,GererPersoActivity.class);
                        startActivity(RetourGererPerso);
                    }
                }
            }

        });
        idee.setText(nomIdee);

    }
}
