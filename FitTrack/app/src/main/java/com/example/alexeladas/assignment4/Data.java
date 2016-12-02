package com.example.alexeladas.assignment4;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class Data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Data","hello");
        setContentView(R.layout.activity_data2);

        Log.d("Data","hello");
        addId();
    }



    public void addId()
    {

        DBHandler db = new DBHandler(this);

        List<Run> testList = db.getRuns();

        Collections.reverse(testList);

        ScrollView scroll = new ScrollView(this);
        scroll.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.FILL_PARENT));


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));


        scroll.addView(linearLayout,
                new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(scroll);
        String temp;
        int i = 0;
        for (Run run : testList)
        {
            if (i>=30){
                break;
            }
           // TextView calorie = new TextView(this);

           // ((ViewGroup)linearLayout.getParent()).removeView(linearLayout);
            TextView Date = new TextView(this);
            TextView Distance = new TextView(this);
            TextView Duration = new TextView(this);
            TextView Pace = new TextView(this);
            TextView Calories = new TextView(this);
            TextView empty = new TextView(this);
            temp = run.getDate();
            Log.d("Data",temp);
            String distance = "Distance:"+db.retrieveDistance(temp);
            String time = "Duration:"+db.retrieveDuration(temp);
            String pace = "Pace:"+db.retrieveSpeed(temp);
            String calories = "Calories Lost:"+db.retrieveCalories(temp);
            String e ="";
           // String v = temp+"/n Distance:"+db.retrieveDistance(temp)+"/n Duration:"+db.retrieveDuration(temp)+"/n Pace:"+db.retrieveSpeed(temp);
            Date.setText(temp);
            Distance.setText(distance);
            Duration.setText(time);
            Pace.setText(pace);
            Calories.setText(calories);
            empty.setText(e);
            Log.d("Data",time);
            linearLayout.addView(Date);
            linearLayout.addView(Distance);
            linearLayout.addView(Duration);
            linearLayout.addView(Pace);
            linearLayout.addView(Calories);
            linearLayout.addView(empty);

            i++;


        }
            //setContentView(sv);
    }
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
