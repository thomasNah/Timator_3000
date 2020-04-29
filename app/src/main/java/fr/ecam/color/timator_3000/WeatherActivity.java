package fr.ecam.color.timator_3000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
        texte = findViewById(R.id.textView5);
        // key: d31be973eb0149218e716d52a361d0da
        String content;
        Weather weather  = new Weather();
        try {
            content = weather.execute("https://api.weatherbit.io/v2.0/current?city=Lyon&lang=fr&key=d31be973eb0149218e716d52a361d0da").get();
            //verifier si les données sont récupérées
            Log.i("contentData", content);
            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("data");
            //JSONArray array = new JSONArray(weatherData);
            texte.setText(weatherData);
            /*String main;
            String description;
            for (int i=0;i<array.length();i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");

            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
