package com.example.asgn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class activity_rank extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Arrayadpter adapter;
    private String[] dataArray;
    private JSON_Thread JSONThread;
    private Spinner sort_spin;
    private String url="http://113.252.161.163/ranking_api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_rank);
        sort_spin = findViewById(R.id.sort_spinner);

        //add data to recyclerView
        newThread();
        while (JSONThread.parsingComplete) {
            dataArray = JSONThread.getListItems();
        }
        recyclerView = findViewById(R.id.rV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setAder();

    }
    public void sort(View v){
        int id = sort_spin.getSelectedItemPosition();

        newThread();

        if (id == 0){

            //sort sec
            while (JSONThread.parsingComplete) {
                dataArray = JSONThread.getListItems();
            }
            setAder();

        } else{
            //sort name

            newThread();

            while (JSONThread.parsingComplete) {
                dataArray = JSONThread.getSortItems();
            }
            setAder();
        }
    }
    public void newThread(){
        JSONThread = new JSON_Thread(url);
        JSONThread.fetchJSON();

    }
    public void setAder(){
        adapter=new Arrayadpter(this,dataArray);
        recyclerView.setAdapter(adapter);
    }
}