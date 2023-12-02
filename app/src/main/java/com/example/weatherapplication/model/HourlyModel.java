package com.example.weatherapplication.model;

import java.util.List;

public class HourlyModel {
    private long dt;
    private double temp;
    private List<WeatherModel> weather;

    public HourlyModel() {
    }

    public HourlyModel(long dt, double temp, List<WeatherModel> weather) {
        this.dt = dt;
        this.temp = temp;
        this.weather = weather;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }
}
