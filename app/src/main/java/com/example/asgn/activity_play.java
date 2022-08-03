package com.example.asgn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class activity_play extends AppCompatActivity implements View.OnClickListener{


    private Button[][] buttons = new Button[3][3];
    private TextView textViewPlayer;
    Context context = this;
    private Button reset;
    private MediaPlayer mediaPlayer;

    private int roundCount = 0;
    boolean status = true;
    private long playTime=0;
    DatabaseHelper GameDB;
    String winningStatus = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_play);
        reset = findViewById(R.id.reset);
        //new DB
        GameDB = new DatabaseHelper(this);

        //hide reset button
        reset.setVisibility(View.INVISIBLE);

        textViewPlayer = findViewById(R.id.text_view_player);

        //loop to findviewbyid button array

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });
    }


    @Override
    public void onClick(View v) {

        //do nothing if not null
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        //call method to player
        playerRound(v);

        //check win and check is player round
        if (checkForWin()&& (roundCount % 2 != 0) ) {
            playerWins();
            // draw when 9 round
        } else if (roundCount == 9){
            draw();
        } else{
            //if player not win, call CPU
            callCPU();
            if (checkForWin()) {
                CPU_Wins();
            }
        }
        //start playtime when player click the first one
        if (playTime==0){
            playTime = System.currentTimeMillis();
            Intent intent = getIntent();
            textViewPlayer.setText(getString(R.string.count)+intent.getStringExtra("player_role"));
        }

    }

    public void playerRound(View v){
        //count the step
        ++roundCount;

        Intent intent = getIntent();
        //get player role
        ((Button) v).setText(intent.getStringExtra("player_role"));
        soundEffect();
        //set color
        ((Button) v).setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.player_select));
    }

    private void callCPU(){
                Intent intent = getIntent();
                ++roundCount;
                Random rand = new Random();
                int upperbound=3;

                int random_r;
                int random_c;
                // keep loop when find the location
                while (true){
                    random_r= rand.nextInt(upperbound);
                    random_c= rand.nextInt(upperbound);
                    //random array

                    if(buttons[random_c][random_r].getText().toString().equals("")){
                        buttons[random_c][random_r].setText(intent.getStringExtra("cpu_role"));
                        buttons[random_c][random_r].setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.cpu_select));
                        //disable_btn(status);
                        return;
                    }
                }
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }
        for (int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void playerWins(){
        Toast.makeText(this, getString(R.string.play_win)+"!", Toast.LENGTH_SHORT).show();
        textViewPlayer.setText(getString(R.string.play_win)+"!" + " "+getString(R.string.dur) +" "+checkplaytime(playTime) + " "+getString(R.string.sec)+"!");
        //show the reset button
        reset.setVisibility(View.VISIBLE);
        //disable all button
        disable_btn(!status);
        winningStatus = "Win";
        //add win record
        addRecord(winningStatus);


    }
    private void CPU_Wins(){
        Toast.makeText(this, getString(R.string.play_lose)+"!", Toast.LENGTH_SHORT).show();
        textViewPlayer.setText(getString(R.string.play_lose)+"!" + " "+getString(R.string.dur) +" "+checkplaytime(playTime) + " "+getString(R.string.sec)+"!");
        //show the reset button
        reset.setVisibility(View.VISIBLE);
        //disable all button
        disable_btn(!status);
        winningStatus = "Lose";
        //add lose record
        addRecord(winningStatus);

    }
    private void draw(){
        Toast.makeText(this, getString(R.string.draw)+"!", Toast.LENGTH_SHORT).show();
        textViewPlayer.setText(getString(R.string.draw)+"!" + " "+getString(R.string.dur) +" "+checkplaytime(playTime) + " "+getString(R.string.sec)+"!");
        //show the reset button
        reset.setVisibility(View.VISIBLE);
        //disable all button
        disable_btn(!status);
        winningStatus = "Draw";
        //add draw record
        addRecord(winningStatus);

    }
    private int checkplaytime(long playTime){
        // count the playtime
        long finishTime = System.currentTimeMillis();
        int elapsedTime = (int)(finishTime-playTime)/1000;
        return elapsedTime;
    }

    private void disable_btn(boolean status){
        //loop buttons the enabled and disabled
        for (int i = 0; i< 3; i++){
            for (int j = 0 ; j<3; j++){
                buttons[i][j].setEnabled(status);
            }
        }
    }

    public void addRecord(String winningStatus){
        //check today
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = (new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(new Date())).toLowerCase(Locale.ROOT);

        boolean isInserted=GameDB.insertData(currentDate, currentTime, String.valueOf(checkplaytime(playTime)), winningStatus);
        if(isInserted){
            Toast.makeText(this, getString(R.string.create_record), Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Create record failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void Reset(){

        //reset game
        for (int i = 0; i< 3; i++){
            for (int j = 0 ; j<3; j++){
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.button));
            }
        }
        roundCount = 0;
        playTime = 0;
        textViewPlayer.setText(R.string.csts);
        reset.setVisibility(View.INVISIBLE);
    }

    public void soundEffect(){

        Uri uri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.card_effect);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
    }
}