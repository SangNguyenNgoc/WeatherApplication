package com.example.weatherapplication.model;

import com.example.weatherapplication.domains.Daily;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    private double lat;
    private double lon;
    private String timezone;
    @SerializedName("timezone_offset")
    private String timezoneOffset;
    private CurrentModel current;
    private List<HourlyModel> hourly;

    private List<DailyModel> daily;

    public WeatherResponse() {
    }

    public WeatherResponse(double lat, double lon, String timezone, String timezoneOffset, CurrentModel current, List<HourlyModel> hourly, List<DailyModel> daily) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.current = current;
        this.hourly = hourly;
        this.daily = daily;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public CurrentModel getCurrent() {
        return current;
    }

    public void setCurrent(CurrentModel current) {
        this.current = current;
    }

    public List<HourlyModel> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyModel> hourly) {
        this.hourly = hourly;
    }

    public List<DailyModel> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyModel> daily) {
        this.daily = daily;
    }
}
