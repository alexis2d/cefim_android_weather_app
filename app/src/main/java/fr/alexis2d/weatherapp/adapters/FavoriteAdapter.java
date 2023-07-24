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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<City> mCities;

    // Constructor
    public FavoriteAdapter(Context mContext, ArrayList<City> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
    }

    // Classe holder qui contient la vue d’un item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public City mCity;
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
            final City city = holder.mCity;
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(R.string.del_city);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mCities.remove(city);
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
        City city = mCities.get(position);
        holder.mCity = city;
        holder.mTextViewCity.setText(city.mName);
        holder.mTextViewTemperature.setText(city.mTemperature);
        holder.mTextViewDescription.setText(city.mDescription);
        holder.mImageViewWeatherIcon.setImageResource(city.mWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
