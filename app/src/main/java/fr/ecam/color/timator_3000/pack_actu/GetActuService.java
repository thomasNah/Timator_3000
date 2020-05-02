package fr.ecam.color.timator_3000.pack_actu;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetActuService {
    @GET("top-headlines?country=fr&apiKey=e5b6ccfeee244e3fa804e4fc2809ff58")
    Call<Actu> getArticle();
}