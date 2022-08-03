package com.example.asgn;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSON_Thread {
    private String urlString ;
    String[] listItems;
    String[] TempItems;


    public volatile boolean parsingComplete = true;
    String data = "";
    public String[] getListItems(){
        return listItems;
    }
    public String[] getSortItems(){ return TempItems;}

    public JSON_Thread(String url){
        this.urlString = url;
    }



    public void readAndParseJSON(String data) {
        try {
            JSONArray codearray = new JSONArray(data);
            listItems = new String[codearray.length()];
            TempItems = new String[codearray.length()];

            for (int i=0; i<codearray.length(); i++){
                //get data from json
                TempItems[i] = codearray.getJSONObject(i).getString("Name")+ ", "+ codearray.getJSONObject(i).getInt("Duration") + " sec";
            }
            List<String> strings = Arrays.asList(TempItems);
            // sort by second (only check number)
            Collections.sort(strings, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return extractInt(o1) - extractInt(o2);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
            //add rank number into sorted array

            for (int i=0; i<codearray.length(); i++){
                listItems[i] = "Rank " +(i+1) + ", "+TempItems[i];
            }

            //make Tempitems sort by name
            Arrays.sort(TempItems);

            parsingComplete = false;

//          Bubble sort (It work but removed)
//            for (int i=0; i<codearray.length(); i++){
//                name[i] = codearray.getJSONObject(i).getString("Name")+", ";
//                dur[i] = codearray.getJSONObject(i).getInt("Duration");
//            }
//
//            for (int i=0; i<codearray.length(); i++){
//                for (int j=i+1; j<codearray.length(); j++){
//                    int temp=0;
//                    String nametemp="";
//                    if (dur[i] > dur[j]){
//
//                        temp = dur[i];
//                        dur[i] = dur[j];
//                        dur[j] = temp;
//
//                        nametemp = name[i];
//                        name[i] = name[j];
//                        name[j] = nametemp;
//                    }
//                }
//                listItems[i] = name[i] + dur[i] + ", sec";
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(stream);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                    readAndParseJSON(data);
                    stream.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
