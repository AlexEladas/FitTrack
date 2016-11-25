package com.example.alexeladas.assignment4;

import java.util.Random;

/**
 * Created by Alex Eladas on 11/4/2016.
 */
public class Run {

    private double mDistance;
    private double mPace;
    private String mDuration;
    private double mCaloriesBurn;
    private String mDate;
    //  private String weight;
    Run(){}

    Run(String date, double distance, double time, double weight) {


        mDate = date;
        mDistance = (double)Math.round(distance*100d)/100d;

        mDuration =formatTime(time);
        mPace = (double)Math.round((mDistance/(time/60000))*100d)/100d ;
        mCaloriesBurn= weight*0.75*mDistance;

    }

    public String formatTime(double time){

        int h   = (int)(time /3600000);
        int m = (int)(time - h*3600000)/60000;
        int s= (int)(time - h*3600000- m*60000)/1000 ;
        String hh = h < 10 ? "0"+h: h+"";
        String mm = m < 10 ? "0"+m: m+"";
        String ss = s < 10 ? "0"+s: s+"";
        return (hh+":"+mm+":"+ss) ;
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

    public String getDuration() {
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

    public void setDuration(String d) {
        mDuration = d;
    }

    public void setCaloriesBurn(double e) {
        mCaloriesBurn = e;
    }

}