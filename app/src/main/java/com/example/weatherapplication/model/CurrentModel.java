package com.example.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentModel {
    private long dt;
    private double temp;
    private double humidity;
    @SerializedName("wind_speed")
    private double windSpeed;

    private List<WeatherModel> weather;

    public CurrentModel() {
    }

    public CurrentModel(long dt, double temp, double humidity, double windSpeed, List<WeatherModel> weather) {
        this.dt = dt;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
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

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }
}
