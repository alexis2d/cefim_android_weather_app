package fr.alexis2d.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import fr.alexis2d.weatherapp.R;
import fr.alexis2d.weatherapp.clients.ClientSingletonWeather;
import fr.alexis2d.weatherapp.databinding.ActivityFavoriteBinding;
import fr.alexis2d.weatherapp.databinding.ActivityMainBinding;
import fr.alexis2d.weatherapp.models.CityApi;
import fr.alexis2d.weatherapp.utils.Util;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Context mContext;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Récupération des données pour les coordonnées
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG:LifeCycle", "MainActivity: onCreate()");

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        mContext = this;

        setContentView(binding.getRoot());

        initApp();

    }

    private void initApp() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mLocation = getLocation();
            binding.textNoConnexion.setVisibility(View.GONE);
            if (mLocation != null) {
                getCityByLocation(String.valueOf(mLocation.getLatitude()), String.valueOf(mLocation.getLongitude()));
                binding.textViewNoLocation.setVisibility(View.GONE);
            }
        } else {
            binding.layoutContent.setVisibility(View.GONE);
            binding.buttonFavorite.setVisibility(View.GONE);
            binding.textNoConnexion.setVisibility(View.VISIBLE);
        }
    }

    private void getCityByLocation(String lat, String lon) {
        Call<CityApi> call = ClientSingletonWeather.getClient().getCityApiFromLatLon(lat, lon, ClientSingletonWeather.API_KEY);
        call.enqueue(new Callback<CityApi>() {
            @Override
            public void onResponse(Call<CityApi> call, Response<CityApi> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        updateUi(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<CityApi> call, Throwable t) {

            }
        });
    }

    public void onClickButtonMap(View v) {
        Intent intent = new Intent(mContext, MapsActivity.class);
        intent.putExtra("location",mLocation);
        startActivity(intent);
    }

    public void onClickButtonFavorite(View v) {
        Intent intent = new Intent(mContext, FavoriteActivity.class);
        startActivity(intent);
    }

    public void onClickButtonGetLocation(View v) {
        mLocation = getLocation();
        if (mLocation != null) {
            getCityByLocation(String.valueOf(mLocation.getLatitude()), String.valueOf(mLocation.getLongitude()));
            binding.textViewNoLocation.setVisibility(View.GONE);
        }
    }

    public Location getLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, permissions, Util.REQUEST_CODE);
            return null;
        } else {
            binding.buttonGetLocation.setVisibility(View.GONE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            return mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    public void updateUi(CityApi cityApi) {
        binding.textViewCityName.setText(cityApi.getName());
        binding.textViewCityDescription.setText(cityApi.getDescription());
        binding.textViewCityTemperature.setText(cityApi.getTemp());
        binding.imageViewCityWeatherIcon.setImageResource(Util.setWeatherIcon(cityApi.getActualId(),cityApi.getSunrise(),cityApi.getSunset()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Util.REQUEST_CODE :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "Ca passe ici", Toast.LENGTH_SHORT).show();
                    binding.buttonGetLocation.setVisibility(View.GONE);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG:LifeCycle", "MainActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG:LifeCycle", "MainActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG:LifeCycle", "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG:LifeCycle", "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG:LifeCycle", "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG:LifeCycle", "MainActivity: onDestroy()");
    }

}