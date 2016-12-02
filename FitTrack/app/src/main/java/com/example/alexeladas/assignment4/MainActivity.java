package com.example.alexeladas.assignment4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    protected Button runButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    protected void onStart() {


        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name",null);

        if(name == null)
            goToProfileActivity();

    }
    void goToProfileActivity() {
        startActivity(new Intent(getApplicationContext(), Profile.class));

    }

    protected void setupUI()
    {

        runButton = (Button) findViewById(R.id.runButton);



    }

    void goToMapsActivity() {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));

    }
    void goToProfilePage() {
        startActivity(new Intent(getApplicationContext(), Profile.class));

    }
    void goToDataActivity() {
        startActivity(new Intent(getApplicationContext(), Data.class));

    }
    public void run(View v){goToMapsActivity();}
    public void profileClick(View v){goToProfilePage();}
    public void past(View v){goToDataActivity();}
}
