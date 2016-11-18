package com.example.alexeladas.assignment4;

import java.util.Random;

/**
 * Created by Alex Eladas on 11/4/2016.
 */
public class Run {

    private double mDistance;
    private double mPace;
    private double mDuration;
    private double mCaloriesBurn;
    private String mDate;
    //  private String weight;
    Run(){}

    Run(String date, double distance, double time) {


        mDate = date;
        mDistance = distance/1000;

        mDuration =time/60000;
        mPace = mDistance/mDuration ;

    }

    //Getters

    public String getDate() {
        return mDate;
    }

    public double getDistance() {
        return mDistance;
    }

    public double getPace() {
        return mPace;
    }

    public double getDuration() {
        return mDuration;
    }

    public double getCaloriesBurn() {
        return mCaloriesBurn;
    }

    //Setters

    public void setDate(String b) {
        mDate = b;
    }

    public void setDistance(double b) {
        mDistance = b;
    }

    public void setPace(double c) {
        mPace = c;
    }

    public void setDuration(double d) {
        mDuration = d;
    }

    public void setCaloriesBurn(double e) {
        mCaloriesBurn = e;
    }

}