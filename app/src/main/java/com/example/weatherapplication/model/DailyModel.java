package com.example.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyModel {

    private long dt;

    private double pop;

    private double humidity;

    @SerializedName("wind_speed")
    private double windSpeed;

    private TempModel temp;

    private String summary;

    private List<WeatherModel> weather;

    public DailyModel() {
    }

    public DailyModel(long dt, double pop, double humidity, double windSpeed, TempModel temp, String summary, List<WeatherModel> weather) {
        this.dt = dt;
        this.pop = pop;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.temp = temp;
        this.summary = summary;
        this.weather = weather;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
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

    public TempModel getTemp() {
        return temp;
    }

    public void setTemp(TempModel temp) {
        this.temp = temp;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }
}
