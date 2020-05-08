package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {

    private TextView texte0;
    private ImageView image0;
    private TextView texte1;
    private ImageView image1;
    private TextView texte2;
    private ImageView image2;
    private TextView texte3;
    private ImageView image3;
    private TextView texte4;
    private ImageView image4;
    private TextView texte5;
    private ImageView image5;
    private TextView texte6;
    private ImageView image6;
    private TextView texte7;
    private ImageView image7;
    private TextView texte8;
    private ImageView image8;
    private TextView texte9;
    private ImageView image9;
    private String ville;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        texte0 = findViewById(R.id.Texte0);
        image0 = findViewById(R.id.Image0);
        texte1 = findViewById(R.id.Texte1);
        image1 = findViewById(R.id.Image1);
        texte2 = findViewById(R.id.Texte2);
        image2 = findViewById(R.id.Image2);
        texte3 = findViewById(R.id.Texte3);
        image3 = findViewById(R.id.Image3);
        texte4 = findViewById(R.id.Texte4);
        image4 = findViewById(R.id.Image4);
        texte5 = findViewById(R.id.Texte5);
        image5 = findViewById(R.id.Image5);
        texte6 = findViewById(R.id.Texte6);
        image6 = findViewById(R.id.Image6);
        texte7 = findViewById(R.id.Texte7);
        image7 = findViewById(R.id.Image7);
        texte8 = findViewById(R.id.Texte8);
        image8 = findViewById(R.id.Image8);
        texte9 = findViewById(R.id.Texte9);
        image9 = findViewById(R.id.Image9);

        ImageView[] images = {image0,image1,image2,image3,image4,image5,image6,image7,image8,image9};
        TextView[] textes = {texte0,texte1,texte2,texte3,texte4,texte5,texte6,texte7,texte8,texte9};
        // key: d31be973eb0149218e716d52a361d0da c'est la clé qu'on obtient en créant un compte gratuit, le nombre d'appel par heures est limité mais suffisant pour notre cas d'utilisation
        String content1;
        Weather weather  = new Weather();
        try {

            content1 = weather.execute("https://api.weatherbit.io/v2.0/forecast/hourly?city="+ville+"&lang=fr&key=d31be973eb0149218e716d52a361d0da&hours=10").get();
            if (content1.equals("")){
                Toast.makeText(getApplicationContext(), "La ville spécifiée n'est pas valide", Toast.LENGTH_SHORT).show();
            }
            else {
                JSONObject jsonObject = new JSONObject(content1); // l'url renvoi un objet JSON qui contient une array JSON
                String data = jsonObject.getString("data");
                JSONArray array = new JSONArray((data));
                List<String> codes = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    String time = array.getJSONObject(i).getString("timestamp_utc");
                    String[] times = time.split("T");
                    String temps = array.getJSONObject(i).getString("weather");
                    String temp = array.getJSONObject(i).getString("temp");
                    String iconCode = temps.split(":")[1].substring(1, 5);
                    if (iconCode.substring(3).equals("n")) { //il y'a aussi des icones pour la nuit mais on en a pas besoin, elles se ressemblent beaucoup
                        iconCode = iconCode.substring(0, 3) + "d";
                    }
                    codes.add(iconCode);
                    System.out.println(iconCode);
                    String textFin = times[1].substring(0, times[1].length() - 3) + " " + temps.split(":")[3].substring(1, temps.split(":")[3].length() - 2) + " (" + temp + "°) ";
                    int imageResource = getResources().getIdentifier("drawable/" + iconCode, null, getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    if (res.equals(null)){
                        res = getResources().getDrawable(getResources().getIdentifier("drawable/erreur",null,getPackageName()));
                    }
                    images[i].setImageDrawable(res);
                    textes[i].setText(textFin);
                }
                System.out.println(codes.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}