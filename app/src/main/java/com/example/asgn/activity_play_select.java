package com.example.asgn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class activity_play_select extends AppCompatActivity {

    private AdView mAdView;
    private RadioButton select_X;
    private RadioButton select_O;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_play_select);
        select_O = findViewById(R.id.select_O);
        select_X = findViewById(R.id.select_X);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void next(View view) {

        Intent i = new Intent(this, activity_play.class);
        if (select_O.isChecked()) {
            //send player role the activity_play
            i.putExtra("player_role", "O");
            i.putExtra("cpu_role", "X");
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        } else if (select_X.isChecked()) {
            //send player role the activity_play
            i.putExtra("player_role", "X");
            i.putExtra("cpu_role", "O");
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        } else {
            //must select
            Toast.makeText(this, getString(R.string.selpls), Toast.LENGTH_SHORT).show();

        }
    }
}