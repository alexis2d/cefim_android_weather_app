package fr.alexis2d.weatherapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fr.alexis2d.weatherapp.R;
import fr.alexis2d.weatherapp.adapters.FavoriteAdapter;
import fr.alexis2d.weatherapp.clients.ClientSingletonWeather;
import fr.alexis2d.weatherapp.databinding.ActivityFavoriteBinding;
import fr.alexis2d.weatherapp.models.CityApi;
import fr.alexis2d.weatherapp.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private ArrayList<CityApi> mCitiesApi;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG:LifeCycle", "FavoriteActivity: onCreate()");

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        mContext = this;

        FloatingActionButton searchButton = binding.searchButton;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
                builder.setView(v);

                final EditText editTextCity = v.findViewById(R.id.edit_text_dialog_city);
                builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
                    if (editTextCity.getText().toString().length() > 0) {
                        updateWeatherDataCityName(editTextCity.getText().toString());
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                });

                builder.create().show();
            }
        });

        mRecyclerView = binding.includeMyRecyclerView.myRecyclerView;

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCitiesApi = Util.initFavoriteCities(mContext);

        mAdapter = new FavoriteAdapter(mContext, mCitiesApi);

        mRecyclerView.setAdapter(mAdapter);

    }

    public void updateWeatherDataCityName(final String cityName) {
        if (Util.isActiveNetwork(mContext)) {

            Call<CityApi> call = ClientSingletonWeather.getClient().getCityApi(cityName, ClientSingletonWeather.API_KEY);

            call.enqueue(new Callback<CityApi>() {
                @Override
                public void onResponse(Call<CityApi> call, Response<CityApi> response) {
                    if (response.isSuccessful()) {
                        CityApi cityApi = response.body();
                        mCitiesApi.add(cityApi);
                        mAdapter.notifyItemInserted(mCitiesApi.size() - 1);
                        Util.saveFavouriteCities(mContext,mCitiesApi);
                        Log.d("GetCity", "Ajout à la liste");
                    } else {
                        // Traitement des erreurs
                    }
                }

                @Override
                public void onFailure(Call<CityApi> call, Throwable t) {
                    Log.d("GetCity", "Error");
                }
            });
        } else {
            Log.d("GetCity", "No connection");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG:LifeCycle", "FavoriteActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG:LifeCycle", "FavoriteActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG:LifeCycle", "FavoriteActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG:LifeCycle", "FavoriteActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG:LifeCycle", "FavoriteActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG:LifeCycle", "FavoriteActivity: onDestroy()");
    }

}