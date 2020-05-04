package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.icu.lang.UCharacter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {

    private TextView texte;
    private ImageView image;
    private GridLayout lay;


    class Weather extends AsyncTask<String, Void, String>{ // 1er string c'est la forme que prend l'url et le 2eme string c'est la forme que prend le retour


        @Override
        protected String doInBackground(String... address) {
            // String... agit commme un array
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // fait la connection avec address
                connection.connect();
                // recupere les données depuis l'url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                // recupere les données et les met sous forme de string
                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();

                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                    e.printStackTrace();
            }
            return null;
        }
    }
// https://www.youtube.com/watch?v=zP41A8VSjB4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //texte = findViewById(R.id.textView5);
        //image = findViewById(R.id.imageView);
        lay = findViewById(R.id.lay);
        // key: d31be973eb0149218e716d52a361d0da
        String content;
        String content1;
        Weather weather  = new Weather();
        try {
            //content = weather.execute("https://api.weatherbit.io/v2.0/current?city=Lyon&lang=fr&key=d31be973eb0149218e716d52a361d0da").get();

<<<<<<< HEAD
            content1 = weather.execute("https://api.weatherbit.io/v2.0/forecast/hourly?city=Lyon&lang=fr&key=d31be973eb0149218e716d52a361d0da&hours=12").get();
=======
            content1 = weather.execute("https://api.weatherbit.io/v2.0/forecast/hourly?city=Lyon&lang=fr&key=d31be973eb0149218e716d52a361d0da&hours=7").get();
>>>>>>> 2b9c062454d5823aaf39d805dc3c8f364e002494

            //verifier si les données sont récupérées
            Log.i("contentData", content1);
            JSONObject jsonObject = new JSONObject(content1);
            String data = jsonObject.getString("data");
            JSONArray array = new JSONArray((data));
            String time = "";
            String temps = "";
            String fin = "";
            String iconCode;
            /*for (int i = 0;i<array.length();i++){
                time = array.getJSONObject(i).getString("timestamp_utc");
                String[] times = time.split("T");
                temps = array.getJSONObject(i).getString("weather");
                iconCode = temps.split(":")[1].substring(1,5);
                fin = fin + times[0]+" " +times[1].substring(0,times[1].length()-3)+ " "+ temps.split(":")[3].substring(1,temps.split(":")[3].length()-2) + " "+iconCode+"\n\n";

            }

            texte.setText(fin);*/


            for (int i = 0;i<array.length();i++){
                time = array.getJSONObject(i).getString("timestamp_utc");
                String[] times = time.split("T");
                temps = array.getJSONObject(i).getString("weather");
                iconCode = temps.split(":")[1].substring(1,5);
                fin = times[0]+" " +times[1].substring(0,times[1].length()-3)+ " "+ temps.split(":")[3].substring(1,temps.split(":")[3].length()-2);
                TextView affichHeure = new TextView(WeatherActivity.this);
                affichHeure.setText(fin);
                lay.setColumnCount(2);
                //lay.setRowCount(5);

                //affichHeure.setLayoutParams(new GridLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT,GridLayout.LayoutParams.WRAP_CONTENT));
                lay.addView(affichHeure);
                affichHeure.setTextSize(12);
                int imageResource = getResources().getIdentifier( "drawable/"+iconCode, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                ImageView image = new ImageView(WeatherActivity.this);
                image.setImageDrawable(res);
                LinearLayout.LayoutParams param= new LinearLayout.LayoutParams(50,50);
                param.gravity= Gravity.RIGHT;
                image.setLayoutParams(param);
                lay.addView(image);

            }
            /*int imageResource = getResources().getIdentifier( "drawable/f01d", null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            image.setImageDrawable(res);*/
            //JSON
            /*JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("data");
            JSONArray array = new JSONArray(weatherData);
            String main =  "";
            for (int i = 0;i<array.length();i++){
                main = array.getJSONObject(i).getString("weather");
            }

            texte.setText(main.split(":")[3].substring(1,main.split(":")[3].length()-2));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
<TextView
            android:id="@+id/textView8"
            android:layout_width="wrap_content"
            android:layout_height="38dp"
            android:layout_gravity="center_vertical"
            android:gravity="center_vertical"

            android:text="ce texte sera quand meme assez long vyuhbhjjjjfkkf" />

        <ImageView
            android:id="@+id/imageView1"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_gravity="right"

            app:srcCompat="@drawable/a01d" />

        <TextView
            android:id="@+id/textView7"
            android:layout_width="wrap_content"
            android:layout_height="38dp"
            android:layout_gravity="center_vertical"
            android:gravity="center_vertical"

            android:text="ce texte sera quand meme assez longbvfdnj" />

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_gravity="right"

            app:srcCompat="@drawable/a01d" />
 */