package com.example.asgn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class chart extends AppCompatActivity {

    DatabaseHelper GameDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        GameDB = new DatabaseHelper(this);
        getSupportActionBar().hide();
        setContentView(new Panel(this));
    }
    class Panel extends View {
        public Panel(Context context){
            super (context);
        }

        String title = getString(R.string.win_stat);
        String items[] = {getString(R.string.win),getString(R.string.lose),getString(R.string.draw)};

        //getdata from gameDB
        int data[] = {GameDB.getwinstatus(), GameDB.getlosestatus(), GameDB.getDrawstatus()};

        int rcolor[] = {0xffff0000,0xffffff00,0xff32cd32};
        float cDegree= 0;


        public void onDraw(Canvas c){
            super.onDraw(c);
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#faf8ef"));
            c.drawPaint(paint);

            paint.setStyle(Paint.Style.FILL);

            //since sum data must be ==100, So I do this function to made sum to 100
            float[] status = new float[3];
            float sum = data[0] + data[1] + data[2];
            status[0]  = (100/sum)*data[0];
            status[1]  = (100/sum)*data[1];
            status[2]  = (100/sum)*data[2];

            for(int i=0;i<data.length;i++){
                float drawDegree = status[i]*360/100;

                paint.setColor(rcolor[i]);

                RectF rec=new RectF(100,350,950,1200);

                c.drawArc(rec,cDegree,drawDegree,true,paint);
                cDegree+=drawDegree;
            }

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(70);
            c.drawText(title, 20, 100, paint);

            int vSpace=getHeight()-200;
            paint.setTextSize(55);
            for (int i = items.length -1;i>=0;i--){
                paint.setColor(rcolor[i]);
                c.drawRect(getWidth()-260,vSpace, getWidth()-220, vSpace+40, paint);

                paint.setColor(Color.BLACK);

                c.drawText(items[i], getWidth()-190,vSpace+40,paint);
                vSpace-=70;
            }

        }

    }
}