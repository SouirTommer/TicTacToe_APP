package com.example.asgn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_result extends AppCompatActivity {

    DatabaseHelper GameDB;
    public static ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView recordlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_result);
        GameDB = new DatabaseHelper(this);
        recordlist = findViewById(R.id.result_list);
        listItem = new ArrayList<>();
        viewData();
    }
    public void show (View view){

        startActivity(new Intent(this, chart.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void viewData(){
        //getdata method in gameDB class
        Cursor res = GameDB.getAllData();

        //return when no data
        if (res.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            return;
        }
        //move cursor, add to arraylist
        while(res.moveToNext()){
            listItem.add(res.getString(1 )+", "+res.getString(2 )+", "+res.getString(4 )+", "+res.getString(3 )+" sec");
        }
        //show
        adapter = new ArrayAdapter<>(this, R.layout.record_list, listItem);
        recordlist.setAdapter(adapter);
    }
}