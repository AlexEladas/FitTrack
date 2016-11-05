package com.example.alexeladas.assignment4;

import java.util.Random;

/**
 * Created by Alex Eladas on 11/4/2016.
 */
public class Run {

    private double mDistance;
    private double mPace;
    private long mDuration;
    private double mCaloriesBurn;
    //  private String weight;

    Run(double distance, long time) {



        mDistance = distance/1000;

        mDuration =time/60000;
        mPace = mDistance/mDuration ;

    }

    //Getters


    public double getDistance() {
        return mDistance;
    }

    public double getPace() {
        return mPace;
    }

    public long getDuration() {
        return mDuration;
    }

    public double getCaloriesBurn() {
        return mCaloriesBurn;
    }

    //Setters


    public void setDistance(double b) {
        mDistance = b;
    }

    public void setPace(double c) {
        mPace = c;
    }

    public void setDuration(long d) {
        mDuration = d;
    }

    public void setCaloriesBurn(double e) {
        mCaloriesBurn = e;
    }

}