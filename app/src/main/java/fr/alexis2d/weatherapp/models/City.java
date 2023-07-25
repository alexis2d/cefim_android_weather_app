package fr.alexis2d.weatherapp.models;

import static fr.alexis2d.weatherapp.utils.Util.setWeatherIcon;

import org.json.JSONException;
import org.json.JSONObject;

public class City {

    public int mIdCity;
    public String mName;
    public String mDescription;
    public String mTemperature;
    public int mWeatherIcon;
    public double mLongitude;
    public double mLatitude;

    public City(String mName, String mDescription, String mTemperature, int mWeatherIcon) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mTemperature = mTemperature;
        this.mWeatherIcon = mWeatherIcon;
    }

    public City(String stringJson) throws JSONException {
        JSONObject json = new JSONObject(stringJson);
        this.mName = json.getString("name");
        this.mDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
        this.mTemperature = json.getJSONObject("main").getString("temp");
        int actualId = json.getJSONArray("weather").getJSONObject(0).getInt("id");
        long sunrise = json.getJSONObject("sys").getLong("sunrise");
        long sunset = json.getJSONObject("sys").getLong("sunset");
        this.mWeatherIcon = setWeatherIcon(actualId,sunrise,sunset);
    }

}
