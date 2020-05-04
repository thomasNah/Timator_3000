package fr.ecam.color.timator_3000.pack_actu;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static GetActuService getSimpleClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetActuService webServer=retrofit.create(GetActuService.class);
        return webServer; }
}
