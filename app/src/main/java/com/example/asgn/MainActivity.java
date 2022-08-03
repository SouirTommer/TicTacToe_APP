package com.example.asgn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void close(View view){
        exit();
    }
    public void play(View view){

        //go to next page
        startActivity(new Intent(MainActivity.this, activity_play_select.class));
        //animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
    public void rank(View view){
        //go to next page
        startActivity(new Intent(MainActivity.this, activity_rank.class));
        //animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void result(View view){
        //go to next page
        startActivity(new Intent(MainActivity.this, activity_result.class));
        //animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void exit() {

        //new AlertDialog to ask user exit
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.sure));
        builder.setTitle(getString(R.string.CLOSE));
        builder.setNegativeButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.synt, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setPositiveButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void htp (View view){
        //call browser to show how to play
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Tic-tac-toe"));
        startActivity(browserIntent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}