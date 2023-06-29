package fr.alexis2d.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private TextView mTextViewNoConnexion;
    private Button mButtonFavorite;
    private View mLayoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutContent = findViewById(R.id.layout_content);
        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewNoConnexion = findViewById(R.id.text_no_connexion);
        mButtonFavorite = findViewById(R.id.button_favorite);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mTextViewNoConnexion.setVisibility(View.GONE);
        } else {
            mLayoutContent.setVisibility(View.GONE);
            mButtonFavorite.setVisibility(View.GONE);
            mTextViewNoConnexion.setVisibility(View.VISIBLE);
        }
    }

    public void onClickButtonFavorite(View v) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

}