package com.example.alexeladas.FitTrack;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alexeladas.FitTrack.R;

public class MainActivity extends AppCompatActivity {
    protected Button runButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }




    protected void setupUI()
    {

        runButton = (Button) findViewById(R.id.runButton);



    }

    void goToMapsActivity() {
        startActivity(new Intent(getApplicationContext(), com.example.alexeladas.FitTrack.MapsActivity.class));

    }
    void goToProfilePage() {
        startActivity(new Intent(getApplicationContext(), com.example.alexeladas.FitTrack.Profile.class));

    }
    public void run(View v){goToMapsActivity();}
    public void profileClick(View v){goToProfilePage();}
}
