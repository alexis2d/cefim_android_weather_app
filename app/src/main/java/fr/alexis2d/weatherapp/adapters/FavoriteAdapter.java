package fr.alexis2d.weatherapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.alexis2d.weatherapp.models.City;
import fr.alexis2d.weatherapp.R;
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
        TextView mTextViewCity;
        ImageView mImageViewWeatherIcon;
        TextView mTextViewTemperature;
        TextView mTextViewDescription;

        public ViewHolder(View view) {
            super(view);
            view.setOnLongClickListener(mOnLongClickListener);
            view.setTag(this);
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
        holder.mTextViewCity.setText(cityApi.getName());
        holder.mTextViewDescription.setText(cityApi.getWeather().get(0).getDescription());
        holder.mTextViewTemperature.setText(Double.toString(cityApi.getMain().getTemp()));
        int actualId = cityApi.getWeather().get(0).getId();
        long sunrise = cityApi.getSys().getSunrise();
        long sunset = cityApi.getSys().getSunset();
        holder.mImageViewWeatherIcon.setImageResource(Util.setWeatherIcon(actualId));
    }

    @Override
    public int getItemCount() {
        return mCitiesApi.size();
    }
}
