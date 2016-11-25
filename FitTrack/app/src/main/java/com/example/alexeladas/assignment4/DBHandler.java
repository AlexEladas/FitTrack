package com.example.alexeladas.assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Eladas on 10/14/2016.
 */
public class DBHandler extends SQLiteOpenHelper
{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "RUNS";
    protected static String TAG = "Report Activity";

    // Contacts table name
    private static final String TABLE_RUNS = "Runs";
    // Shops Table Columns names
    private static final String KEY_ID = "Id";
    private static final String KEY_DISTANCE= "Distance";
    private static final String KEY_SPEED = "Speed";
    private static final String KEY_DURATION = "Duration";
    private static final String KEY_CALORIE_BURN = "CaloriesBurn";

    public DBHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RUNS_TABLE = "CREATE TABLE " + TABLE_RUNS +"("+
                KEY_ID+ " TEXT UNIQUE," +
                KEY_DISTANCE+" REAL NOT NULL,"+
                KEY_SPEED+" REAL NOT NULL,"+
                KEY_DURATION+" INT NOT NULL,"+
                KEY_CALORIE_BURN+" REAL NOT NULL"+");";
        ;
        db.execSQL(CREATE_RUNS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addRun(Run run)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, run.getDate());
        values.put(KEY_DISTANCE, run.getDistance());
        values.put(KEY_SPEED, run.getPace());
        values.put(KEY_DURATION, run.getDuration());
        values.put(KEY_CALORIE_BURN, run.getCaloriesBurn());
// Inserting Row
        db.insert(TABLE_RUNS, null, values);        Log.d(TAG, "!!!!!!!! "+ "suuuuuuuuuuuuuuuuuuuup3");

        db.close(); // Closing database connection
    }


    public List<Run> getRuns() {
        List<Run> runList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RUNS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Run run = new Run();
                run.setDate((cursor.getString(0)));

                run.setDistance(Double.parseDouble(cursor.getString(1)));
                run.setPace(Double.parseDouble(cursor.getString(2)));
                run.setDuration(cursor.getString(3));
                run.setCaloriesBurn(Double.parseDouble(cursor.getString(4)));

                runList.add(run);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return runList;
    }
    public double retrieveDistance(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        double distance = 0.0;
        try {

            cursor = db.rawQuery("SELECT " + KEY_DISTANCE + " FROM " + TABLE_RUNS + " WHERE " + KEY_ID + "=?", new String[]{id + ""});

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                distance = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_DISTANCE)));
            }

            return distance;
        }finally {
            cursor.close();
            // db.close();
        }


    }

    public String retrieveDuration(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String duration= "" ;

        try{
            cursor = db.rawQuery("SELECT " + KEY_DURATION +" FROM " + TABLE_RUNS +" WHERE " + KEY_ID +"=?", new String[] {id + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                duration = (cursor.getString(cursor.getColumnIndex(KEY_DURATION)));
            }

            return duration;
        }finally {
            cursor.close();
            //db.close();
        }

    }
    public double retrieveSpeed(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        double speed = 0.0;

        try{
            cursor = db.rawQuery("SELECT " + KEY_SPEED +" FROM " + TABLE_RUNS +" WHERE " + KEY_ID +"=?", new String[] {id + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                speed = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_SPEED)));
            }

            return speed;
        }finally {
            cursor.close();
            // db.close();
        }

    }

    public double retrieveCalories(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        double calories = 0.0;

        try{
            cursor = db.rawQuery("SELECT " + KEY_CALORIE_BURN +" FROM " + TABLE_RUNS +" WHERE " + KEY_ID +"=?", new String[] {id + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                calories = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_CALORIE_BURN)));
            }
            return calories;
        }finally {
            cursor.close();
            //db.close();
        }




    }
}