package fr.alexis2d.weatherapp.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.alexis2d.weatherapp.R;
import fr.alexis2d.weatherapp.activities.MapsActivity;
import fr.alexis2d.weatherapp.databinding.ActivityFavoriteBinding;
import fr.alexis2d.weatherapp.models.CityApi;
import fr.alexis2d.weatherapp.utils.Util;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CityApi> mCitiesApi;

    // Constructor
    public FavoriteAdapter(Context mContext, ArrayList<CityApi> mCitiesApi) {
        this.mContext = mContext;
        this.mCitiesApi = mCitiesApi;
    }

    // Classe holder qui contient la vue d’un item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public CityApi mCityApi;
        LinearLayout mLayoutFavoriteCity;
        TextView mTextViewCity;
        ImageView mImageViewWeatherIcon;
        TextView mTextViewTemperature;
        TextView mTextViewDescription;

        public ViewHolder(View view) {
            super(view);
            //view.setOnLongClickListener(mOnLongClickListener);
            view.setOnClickListener(mOnClickListener);
            view.setTag(this);
            mLayoutFavoriteCity = view.findViewById(R.id.layout_favorite_city);
            mTextViewCity = view.findViewById(R.id.text_view_item_city_name);
            mImageViewWeatherIcon = view.findViewById(R.id.image_view_item_picture);
            mTextViewTemperature = view.findViewById(R.id.text_view_item_temperature);
            mTextViewDescription = view.findViewById(R.id.text_view_item_description);
        }
    }

    private final View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            final CityApi city = holder.mCityApi;
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(R.string.del_city);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mCitiesApi.remove(city);
                    Util.saveFavouriteCities(mContext, mCitiesApi);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            builder.create().show();
            return false;
        }
    };

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            final CityApi cityApi = holder.mCityApi;
            Intent intent = new Intent(mContext, MapsActivity.class);
            Location location = new Location("");
            location.setLatitude(cityApi.getCoord().getLat());
            location.setLongitude(cityApi.getCoord().getLon());
            intent.putExtra("location",location);
            startActivity(mContext,intent,null);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityApi cityApi = mCitiesApi.get(position);
        holder.mCityApi = cityApi;
        holder.mLayoutFavoriteCity.setTag(String.valueOf(holder.mLayoutFavoriteCity.getId()).concat(String.valueOf(cityApi.getId())));
        holder.mTextViewCity.setText(cityApi.getName());
        holder.mTextViewDescription.setText(cityApi.getDescription());
        holder.mTextViewTemperature.setText(cityApi.getTemp());
        holder.mImageViewWeatherIcon.setImageResource(Util.setWeatherIcon(cityApi.getActualId()));
    }

    @Override
    public int getItemCount() {
        return mCitiesApi.size();
    }
}
