package fr.ecam.color.timator_3000;

import fr.ecam.color.timator_3000.model.Actu;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetActuService {
    @GET("top-headlines")
    Call<Actu> getArticle(@Query("country") String country, @Query("key") String key);
}

