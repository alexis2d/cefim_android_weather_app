package fr.alexis2d.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import fr.alexis2d.weatherapp.R;
import fr.alexis2d.weatherapp.databinding.ActivityFavoriteBinding;
import fr.alexis2d.weatherapp.databinding.ActivityMainBinding;
import fr.alexis2d.weatherapp.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG:LifeCycle", "MainActivity: onCreate()");

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            binding.textNoConnexion.setVisibility(View.GONE);
            Log.d("test", (String) binding.textNoConnexion.getText());
            mOkHttpClient = new OkHttpClient();

            Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat=47.390026&lon=0.688891&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("error",e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String stringJson = response.body().string();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    updateUi(stringJson);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                }
            });
        } else {
            binding.layoutContent.setVisibility(View.GONE);
            binding.buttonFavorite.setVisibility(View.GONE);
            binding.textNoConnexion.setVisibility(View.VISIBLE);
        }

    }

    public void onClickButtonFavorite(View v) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void updateUi(String stringJson) throws JSONException {
        City city = new City(stringJson);
        binding.textViewCityName.setText(city.mName);
        binding.textViewCityDescription.setText(city.mDescription);
        binding.textViewCityTemperature.setText(city.mTemperature);
        binding.imageViewCityWeatherIcon.setImageResource(city.mWeatherIcon);
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