package com.example.alexeladas.assignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    private EditText Name, Age, Height, Weight;
    private TextView BMI, BMR;
    private Button button;
    private boolean a;
    private String name, age, name1, name2;
    protected static String TAG = "Profile Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupUI();
        SharedPreferences sharedPreferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String firstname = sharedPreferences.getString("Name",null);
        if (TextUtils.isEmpty(firstname)){}//If there is no name go to edit
        else{
            bmibmr();
            MakeVisible();
            MakeNonEditable();
            button.setVisibility(View.INVISIBLE);}
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Name.setText(sharedPreferences.getString("Name", ""));
        Log.d(TAG,"oncreate");
    }

    protected void setupUI()
    {
        Name = (EditText) findViewById(R.id.Name);
        Age = (EditText) findViewById(R.id.Age);
        Height = (EditText) findViewById(R.id.Height);
        Weight = (EditText) findViewById(R.id.Weight);
        BMI = (TextView) findViewById(R.id.BMI);
        BMR = (TextView) findViewById(R.id.BMR);
        button = (Button) findViewById(R.id.button);

    }

    protected void setText(){// Function to set the text written in the edittexts views
        SharedPreferences sharedPreferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name",null);

        String age = sharedPreferences.getString("Age",null);
        String weight = sharedPreferences.getString("Weight",null);
        String height = sharedPreferences.getString("Height",null);
        Name.setText(name,TextView.BufferType.EDITABLE);
        Age.setText(age,TextView.BufferType.EDITABLE);
        Weight.setText(weight,TextView.BufferType.EDITABLE);
        Height.setText(height,TextView.BufferType.EDITABLE);
    }
    protected void onStart() {
        setText();
        MakeVisible(); //Make the BMI and BMR visible


        super.onStart();


    }

    protected void onStop() {
        Log.d(TAG,"stopped");
        super.onStop();
    }

    public void onclick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name",Name.getText().toString());
        editor.commit();
        name = sharedPreferences.getString("Name",null);

        a= false;
        if ( TextUtils.isEmpty(name)){//Checks if name is empty
            Toast.makeText(this, "Enter your name ", Toast.LENGTH_SHORT).show();
        } else{
            String i = Age.getText().toString();//age
            Log.d(TAG,i);



            Log.d(TAG, "here");

            if ( TextUtils.isEmpty(i)){
                Toast.makeText(this, "Enter your age ", Toast.LENGTH_SHORT).show();
            }else if(isInteger(i)) {
                Toast.makeText(this, "Enter possible Age", Toast.LENGTH_SHORT).show();
                a = true;}
            else if (i == null || Integer.valueOf(i) < 0 || Integer.valueOf(i) > 130) {//Checks if the number entered makes sense
                Toast.makeText(this, "Enter possible Age", Toast.LENGTH_SHORT).show();
                a = true;
            }

            else {// if everything is good commit the values

                editor.putString("Age",Age.getText().toString());
                editor.commit();
                age = sharedPreferences.getString("Age",null);
                String y = Weight.getText().toString();



                if(TextUtils.isEmpty(y)){
                    Toast.makeText(this, "Enter your Weight ", Toast.LENGTH_SHORT).show();
                    a= true;
                }

                if(isInteger(y)){
                    Toast.makeText(this, "Enter possible Weight", Toast.LENGTH_SHORT).show();

                }
                else if (y == null || Integer.valueOf(y) < 0 || Integer.valueOf(y) > 200 ) {
                    Toast.makeText(this, "Enter possible Weight", Toast.LENGTH_SHORT).show();
                    a = true;
                } else {//if everything is good commit the values
                    editor.putString("Weight", Weight.getText().toString());
                    editor.commit();
                    name1 = sharedPreferences.getString("Weight", null);//weight

                    String u = Height.getText().toString();



                    if (TextUtils.isEmpty(u)) {
                        Toast.makeText(this, "Enter your Height ", Toast.LENGTH_SHORT).show();
                        a = true;
                    }   else if (isInteger(u)) {
                        Toast.makeText(this, "Enter possible Height", Toast.LENGTH_SHORT).show();
                    }

                    else   if (u == null || Integer.valueOf(u) < 0 || Integer.valueOf(u) > 210) {
                        Toast.makeText(this, "Enter possible Height", Toast.LENGTH_SHORT).show();
                        a = true;
                    }   else//If everything is good commit the values
                    {
                        editor.putString("Height", Height.getText().toString());
                        editor.commit();
                        name2 = sharedPreferences.getString("Height", null);//height


                        if (a) {
                            Toast.makeText(this, "Missing or Incorrect information", Toast.LENGTH_SHORT).show();
                        }   else//If al the conditions are met everything is saved and the button disappears
                        {
                            bmibmr();
                            MakeVisible();
                            MakeNonEditable();
                            button.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu) {//USe for the overflow menu
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    public void MakeVisible(){// Mkae the BMI and BMR visible

        //  button.setVisibility(View.VISIBLE);
        BMI.setVisibility(View.VISIBLE);
        BMR.setVisibility(View.VISIBLE);

    }

    public void MakeInvisible(){ //Make the BMI and BMR invisible
        // button.setVisibility(View.VISIBLE);
        BMI.setVisibility(View.INVISIBLE);
        BMR.setVisibility(View.INVISIBLE);
    }
    public void MakeEditable(){//Make the whole layout edtiable
        Name.setEnabled(true);
        Age.setEnabled(true);
        Weight.setEnabled(true);
        Height.setEnabled(true);
    }
    public void MakeNonEditable(){//Make the whole layout uneditable
        Name.setEnabled(false);
        Age.setEnabled(false);
        Weight.setEnabled(false);
        Height.setEnabled(false);
    }
    public void bmibmr(){//Calculates and sets the BMI and BMr appropriately
        SharedPreferences sharedPreferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);


        String age = sharedPreferences.getString("Age",null);
        String weight = sharedPreferences.getString("Weight",null);
        String height = sharedPreferences.getString("Height",null);
        int w = Integer.valueOf(age);
        int y = Integer.valueOf(weight);
        float x = Float.valueOf(height)/100;
        Log.d(TAG,String.valueOf(y));
        Log.d(TAG,String.valueOf(x));
        Log.d(TAG,"sup");
        Float z = y/(x*x);
        Double u = 66.5 + (13.75*y)+(5.003*x*100)-(6.755*w);

        BMI.setText(String.valueOf(y/(x*x)));
        BMR.setText(String.valueOf(u));
        MakeVisible();
        Log.d(TAG,String.valueOf(z));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){//Listens for the action bar menu item selected
        int id = item.getItemId();

        if (id == android.R.id.home && button.getVisibility() == View.VISIBLE){ //If the up button is selected while the save button is visble. Deny the user
            Toast.makeText(this, "Save your stuff", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        if(id==R.id.action_edit){// If the edit button is selected
            MakeInvisible();
            button.setVisibility(View.VISIBLE);
            MakeEditable();

        }
        return super.onOptionsItemSelected(item);


    }

    public static boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return false;
        }
        catch (NumberFormatException nfe){
            Log.d(TAG,"hello");

            return true;
        }
    }
}