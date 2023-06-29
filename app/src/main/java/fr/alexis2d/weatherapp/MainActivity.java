package fr.alexis2d.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private TextView mTextViewNoConnexion;
    private View mButtonFavorite;
    private View mLayoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutContent = findViewById(R.id.layout_content);

        mTextViewCityName = findViewById(R.id.text_view_city_name);
        Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        mTextViewNoConnexion = findViewById(R.id.text_no_connexion);
        mButtonFavorite = findViewById(R.id.button_favorite);

        if (networkInfo != null && networkInfo.isConnected()) {
            mTextViewNoConnexion.setVisibility(View.GONE);
        } else {
            mLayoutContent.setVisibility(View.GONE);
            mButtonFavorite.setVisibility(View.GONE);
            mTextViewNoConnexion.setVisibility(View.VISIBLE);
        }
    }

}