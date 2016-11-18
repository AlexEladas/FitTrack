package com.example.alexeladas.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class Data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Data","hello");
        setContentView(R.layout.activity_data2);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Log.d("Data","hello");
        addId();
    }



    public void addId()
    {
        DBHandler db = new DBHandler(this);
        Log.d("Data","hello");
        List<Run> testList = db.getRuns();
        Log.d("Data","hello");
        Collections.reverse(testList);
        Log.d("Data","hello");
        String temp;

        for (Run run : testList)
        {
            Log.d("Data","hello");
            TextView textView = new TextView(this);
            temp = run.getDate();
           textView.setText(temp+"/n Distance:"+db.retrieveDistance(temp)+"/n Duration:"+db.retrieveDuration(temp)+"/n Pace:"+db.retrieveSpeed(temp));





        }


    }
}
