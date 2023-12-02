package com.example.weatherapplication.model;

public class TempModel {
    private double max;
    private double min;
    private double day;

    public TempModel() {
    }

    public TempModel(double max, double min, double day) {
        this.max = max;
        this.min = min;
        this.day = day;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }
}
