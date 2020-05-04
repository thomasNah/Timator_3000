package fr.ecam.color.timator_3000.pack_actu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import fr.ecam.color.timator_3000.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualiteActivity extends AppCompatActivity {

    private TextView title1;
    private TextView title2;
    private TextView title3;
    private TextView title4;
    private TextView description1;
    private TextView description2;
    private TextView description3;
    private TextView description4;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private TextView date1;
    private TextView date2;
    private TextView date3;
    private TextView date4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actu);

        GetActuService ws = RetrofitBuilder.getSimpleClient();
        ws.getArticle().enqueue(new Callback<Actu>() {
            @Override
            public void onResponse(Call<Actu> call, Response<Actu> response) {
                if (response.code()==200) {
                    //Récupération du 1er article
                    Article article1 = response.body().getArticles().get(0);
                    title1 = findViewById(R.id.titre1);
                    title1.setText(article1.getTitle());
                    description1 = findViewById(R.id.description1);
                    description1.setText(article1.getDescription().toString());
                    image1 = findViewById(R.id.image1);
                    if(article1.getUrlToImage() == null) {
                        image1.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image1).execute(article1.getUrlToImage().toString());
                    }
                    date1 = findViewById(R.id.date1);
                    date1.setText(article1.getPublishedAt());
                    //Récupération du 2eme article
                    Article article2 = response.body().getArticles().get(1);
                    title2 = findViewById(R.id.titre2);
                    title2.setText(article2.getTitle());
                    description2 = findViewById(R.id.description2);
                    description2.setText(article2.getDescription().toString());
                    image2 = findViewById(R.id.image2);
                    if(article2.getUrlToImage() == null) {
                        image2.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image2).execute(article2.getUrlToImage().toString());
                    }
                    date2 = findViewById(R.id.date2);
                    date2.setText(article2.getPublishedAt());
                    //Récupération du 3eme article
                    Article article3 = response.body().getArticles().get(2);
                    title3 = findViewById(R.id.titre3);
                    title3.setText(article3.getTitle());
                    description3 = findViewById(R.id.description3);
                    description3.setText(article3.getDescription().toString());
                    image3 = findViewById(R.id.image3);
                    if(article3.getUrlToImage() == null) {
                        image3.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image3).execute(article3.getUrlToImage().toString());
                    }
                    date3 = findViewById(R.id.date3);
                    date3.setText(article3.getPublishedAt());
                    //Récupération du 4eme article
                    Article article4 = response.body().getArticles().get(3);
                    title4 = findViewById(R.id.titre4);
                    title4.setText(article4.getTitle());
                    description4 = findViewById(R.id.description4);
                    description4.setText(article4.getDescription().toString());
                    image4 = findViewById(R.id.image4);
                    if(article4.getUrlToImage() == null) {
                        image4.setImageDrawable(getResources().getDrawable(R.drawable.image_pbactu));
                    } else {
                        new DownloadImageTask(image4).execute(article4.getUrlToImage().toString());
                    }
                    date4 = findViewById(R.id.date4);
                    date4.setText(article4.getPublishedAt());

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

        //ADD BACK BUTTON POUR RETOURNER SUR MAIN ACTIVITY
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}