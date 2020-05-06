package fr.ecam.color.timator_3000.pack_actu;

import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import fr.ecam.color.timator_3000.MainActivity;
import fr.ecam.color.timator_3000.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualiteActivity extends AppCompatActivity {

    private TextView title1;
    private TextView title2;
    private TextView title3;
    private TextView title4;
    private TextView title5;
    private TextView description1;
    private TextView description2;
    private TextView description3;
    private TextView description4;
    private TextView description5;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private TextView url1;
    private TextView url2;
    private TextView url3;
    private TextView url4;
    private TextView url5;
    private TextView date1;
    private TextView date2;
    private TextView date3;
    private TextView date4;
    private TextView date5;

    private ImageButton refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actu);

        //ADD BACK BUTTON POUR RETOURNER SUR MAIN ACTIVITY
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetActuService ws = RetrofitBuilder.getSimpleClient();
        ws.getArticle().enqueue(new Callback<Actu>() {
            @Override
            public void onResponse(Call<Actu> call, Response<Actu> response) {
                if (response.code()==200) {
                    int tot = response.body().getArticles().size();
                    //Récupération du 1er article
                    Article article1 = response.body().getArticles().get(0);
                    TextView title1 = findViewById(R.id.titre1);
                    title1.setText(article1.getTitle());
                    description1 = findViewById(R.id.description1);
                    if (article1.getDescription() == null || article1.getDescription().toString().equals("") ) {
                        description1.setText("Pas de description");
                    } else {
                        description1.setText(article1.getDescription().toString());
                    }
                    image1 = findViewById(R.id.image1);
                    if(article1.getUrlToImage() == null) {
                        image1.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image1).execute(article1.getUrlToImage().toString());
                    }
                    url1 = findViewById(R.id.url1);
                    url1.setText("Lien vers l'article complet : " + article1.getUrl());
                    date1 = findViewById(R.id.date1);
                    date1.setText(article1.getPublishedAt());
                    //Récupération du 2eme article
                    Article article2 = response.body().getArticles().get(1);
                    title2 = findViewById(R.id.titre2);
                    title2.setText(article2.getTitle());
                    description2 = findViewById(R.id.description2);
                    if (article2.getDescription() == null || article2.getDescription().toString().equals("") ) {
                        description2.setText("Pas de description");
                    } else {
                        description2.setText(article2.getDescription().toString());
                    }
                    image2 = findViewById(R.id.image2);
                    if(article2.getUrlToImage() == null) {
                        image2.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image2).execute(article2.getUrlToImage().toString());
                    }
                    url2 = findViewById(R.id.url2);
                    url2.setText("Lien vers l'article complet : " + article2.getUrl());
                    date2 = findViewById(R.id.date2);
                    date2.setText(article2.getPublishedAt());
                    //Récupération du 3eme article
                    Article article3 = response.body().getArticles().get(2);
                    title3 = findViewById(R.id.titre3);
                    title3.setText(article3.getTitle());
                    description3 = findViewById(R.id.description3);
                    if (article3.getDescription() == null || article3.getDescription().toString().equals("") ) {
                        description3.setText("Pas de description");
                    } else {
                        description3.setText(article3.getDescription().toString());
                    }
                    image3 = findViewById(R.id.image3);
                    if(article3.getUrlToImage() == null) {
                        image3.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image3).execute(article3.getUrlToImage().toString());
                    }
                    url3 = findViewById(R.id.url3);
                    url3.setText("Lien vers l'article complet : " + article3.getUrl());
                    date3 = findViewById(R.id.date3);
                    date3.setText(article3.getPublishedAt());
                    //Récupération du 4eme article
                    Article article4 = response.body().getArticles().get(3);
                    title4 = findViewById(R.id.titre4);
                    title4.setText(article4.getTitle());
                    description4 = findViewById(R.id.description4);
                    if (article4.getDescription() == null || article4.getDescription().toString().equals("") ) {
                        description4.setText("Pas de description");
                    } else {
                        description4.setText(article4.getDescription().toString());
                    }
                    image4 = findViewById(R.id.image4);
                    if(article4.getUrlToImage() == null) {
                        image4.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image4).execute(article4.getUrlToImage().toString());
                    }
                    url4 = findViewById(R.id.url4);
                    url4.setText("Lien vers l'article complet : " + article4.getUrl());
                    date4 = findViewById(R.id.date4);
                    date4.setText(article4.getPublishedAt());
                    //Récupération du 5eme et dernier article
                    Article article5 = response.body().getArticles().get(4);
                    title5 = findViewById(R.id.titre5);
                    title5.setText(article5.getTitle());
                    description5 = findViewById(R.id.description5);
                    if (article5.getDescription() == null || article5.getDescription().toString().equals("") ) {
                        description5.setText("Pas de description");
                    } else {
                        description5.setText(article5.getDescription().toString());
                    }
                    image5 = findViewById(R.id.image5);
                    if(article5.getUrlToImage() == null) {
                        image5.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image5).execute(article5.getUrlToImage().toString());
                    }
                    url5 = findViewById(R.id.url5);
                    url5.setText("Lien vers l'article complet : " + article5.getUrl());
                    date5 = findViewById(R.id.date5);
                    date5.setText(article5.getPublishedAt());


                } else {
                    title1 = findViewById(R.id.titre1);
                    title1.setText("Une erreur est survenue " + response.code());
                }

            }
            @Override
            public void onFailure(Call<Actu> call, Throwable t) {
                title1 = findViewById(R.id.titre1);
                title1.setText("onFailure " + t.getMessage());
            }

        });

    }
    protected void onResume() {
        super.onResume();
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActuActivity_page2 = new Intent(ActualiteActivity.this, ActualiteActivity2.class);
                startActivity(ActuActivity_page2);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent mainActivity = new Intent(ActualiteActivity.this, MainActivity.class);
            startActivity(mainActivity);
        }
        return super.onOptionsItemSelected(item);
    }
}