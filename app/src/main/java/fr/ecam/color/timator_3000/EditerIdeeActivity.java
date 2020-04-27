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
        private DatabaseManager databaseManager;
        private Spinner spinnerTempsDispo;
        private Spinner spinnerNote;
        private Button boutonEdit;
        private EditText editIdee;
        private TextView ideeEditee;

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
        ideeEditee = findViewById(R.id.textView3);

        Intent intent = getIntent();
         String nomIdee = "";
        if (intent != null){
            if (intent.hasExtra("nomIdee")){
                nomIdee = intent.getStringExtra("nomIdee");
            }
        }
        final String nomIdeeFinal = nomIdee; // je sais pas trop pourquoi mais sinon il veut pas
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayTempsDispo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTempsDispo.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.arrayNote, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNote.setAdapter(adapter2);
        final List<IdeeData> idees = databaseManager.lireTable();
        final IdeeData[] ideeData = new IdeeData[1];
        boutonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for  (int i=0;i<idees.size();i++){
                    if (idees.get(i).getNom().equals(nomIdeeFinal)){
                        //c'est ici qu'on update l'idee dans la base de données
                        int idIdee = idees.get(i).getIdIdee();
                        String nouvellDesc = String.valueOf(editIdee.getText());
                        String nouvelleDuree = String.valueOf(spinnerTempsDispo.getSelectedItem());
                        int nouvelleNote = Integer.valueOf(String.valueOf(spinnerNote.getSelectedItem()));
                        String str = "update IDEE set contenu = '"+nouvellDesc+"' , duree = '"+nouvelleDuree+"', note =" +nouvelleNote+" where idIdee = "+idIdee;
                        //String strSql = "update IDEE set contenu = '"+ nouvellDesc +"', duree ='"+ nouvelleDuree+"', note = "+nouvelleNote +"where nom = '"+nomIdeeFinal+"'";
                        databaseManager.getWritableDatabase().execSQL(str);
                        List<IdeeData> idees1  = databaseManager.lireTable();
                        ideeEditee.setText(idees1.get(i).toString());

                    }
                }
            }
        });
        //ideeEditee.setText(ideeData[0].toString());
        idee.setText(nomIdee);

    }
}
