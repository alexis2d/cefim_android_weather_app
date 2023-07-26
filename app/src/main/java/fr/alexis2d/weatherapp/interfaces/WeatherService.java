package fr.alexis2d.weatherapp.interfaces;

import fr.alexis2d.weatherapp.models.CityApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather?units=metric&lang=fr")
    Call<CityApi> getCityApi(@Query("q") String cityName, @Query("appid") String apiKey);
}
