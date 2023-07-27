package fr.alexis2d.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import fr.alexis2d.weatherapp.R;
import fr.alexis2d.weatherapp.models.CityApi;

public class Util {
    public static final int REQUEST_CODE = 111;
    private static final String PREFS_NAME = "file_prefs";
    private static final String PREFS_FAVORITE_CITIES = "file_prefs_cities";

    public static void saveFavouriteCities(Context context, ArrayList<CityApi> citiesApi) {
        JSONArray jsonArrayCities = new JSONArray();
        Gson gson = new Gson();
        for (int i = 0; i < citiesApi.size(); i++) {
            jsonArrayCities.put(gson.toJson(citiesApi));
        }
        SharedPreferences preferences = context.getSharedPreferences(Util.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Util.PREFS_FAVORITE_CITIES, jsonArrayCities.toString());
        editor.apply();
        Log.d("Favorites", String.valueOf(jsonArrayCities));
    }

    public static ArrayList<CityApi> initFavoriteCities(Context context){
        ArrayList<CityApi> cities = new ArrayList<>();
        SharedPreferences preferences = context.getSharedPreferences(Util.PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        try {
            JSONArray jsonArray = new JSONArray(preferences.getString(Util.PREFS_FAVORITE_CITIES,""));
            Log.d("Favorites", String.valueOf(jsonArray));
            cities.addAll(Arrays.asList(gson.fromJson(jsonArray.getString(0),CityApi[].class)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Favorites", cities.toString());
        return cities;
    }

    /*
    * Méthode qui initialise l'icon blanc présent dans la MainActivity
    * */
    public static int setWeatherIcon(int actualId, long sunrise, long sunset) {

        int id = actualId / 100;
        int icon = R.drawable.weather_sunny_white;

        if (actualId == 800) {
            long currentTime = new Date().getTime();

            if (currentTime >= sunrise && currentTime < sunset) {
                icon = R.drawable.weather_sunny_white;
            } else {
                icon = R.drawable.weather_clear_night_white;
            }
        } else {
            switch (id) {
                case 2:
                    icon = R.drawable.weather_thunder_white;
                    break;
                case 3:
                    icon = R.drawable.weather_drizzle_white;
                    break;
                case 7:
                    icon = R.drawable.weather_foggy_white;
                    break;
                case 8:
                    icon = R.drawable.weather_cloudy_white;
                    break;
                case 6:
                    icon = R.drawable.weather_snowy_white;
                    break;
                case 5:
                    icon = R.drawable.weather_rainy_white;
                    break;
            }
        }

        return icon;
    }

    /*
    * Méthode qui initialise l'icon gris présent dans le forecast et dans la liste des favoris.
    * */
    public static int setWeatherIcon(int actualId) {

        int id = actualId / 100;
        int icon = R.drawable.weather_sunny_grey;

        if (actualId != 800) {
            switch (id) {
                case 2:
                    icon = R.drawable.weather_thunder_grey;
                    break;
                case 3:
                    icon = R.drawable.weather_drizzle_grey;
                    break;
                case 7:
                    icon = R.drawable.weather_foggy_grey;
                    break;
                case 8:
                    icon = R.drawable.weather_cloudy_grey;
                    break;
                case 6:
                    icon = R.drawable.weather_snowy_grey;
                    break;
                case 5:
                    icon = R.drawable.weather_rainy_grey;
                    break;
            }
        }

        return icon;
    }

    public static boolean isActiveNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static String capitalize(String s) {
        if (s == null) return null;
        if (s.length() == 1) {
            return s.toUpperCase();
        }
        if (s.length() > 1) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return "";
    }
}
