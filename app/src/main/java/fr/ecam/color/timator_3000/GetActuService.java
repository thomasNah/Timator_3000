package fr.ecam.color.timator_3000;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetActuService {
    @GET("top-headlines?country=fr&apiKey=e5b6ccfeee244e3fa804e4fc2809ff58")
    Call<ActuResult> getArticle();
}

