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

import fr.alexis2d.weatherapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private TextView mTextViewNoConnexion;
    private Button mButtonFavorite;
    private View mLayoutContent;

    private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG:LifeCycle", "MainActivity: onCreate()");

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