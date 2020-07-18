package com.example.quakereport;

public class Report {
    private double eMagnitude;
    private String ePlace;
    private String eDate;
    private String eUrl;

    public Report(double magnitude,String place,String date,String url){
        eMagnitude = magnitude;
        ePlace = place;
        eDate = date;
        eUrl = url;
    }
    public double getMagnitude(){
        return eMagnitude;
    }
    public String getPlace(){
        return ePlace;
    }
    public String getDate(){
        return eDate;
    }
    public String getUrl() {return eUrl;}
}
