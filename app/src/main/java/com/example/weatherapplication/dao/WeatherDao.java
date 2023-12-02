package com.example.weatherapplication.dao;

import android.util.Log;

import com.example.weatherapplication.activity.MainActivity;
import com.example.weatherapplication.api.ApiService;
import com.example.weatherapplication.api.RetrofitClient;
import com.example.weatherapplication.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDao {

    private static volatile WeatherDao weatherDao;

    private WeatherDao() {

    }

    public static WeatherDao getInstance() {
        if(weatherDao == null) {
            synchronized (WeatherDao.class) {
                if (weatherDao == null) {
                    weatherDao = new WeatherDao();
                }
            }
        }
        return weatherDao;
    }

    public void getCurrentWeather(MainActivity mainActivity) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<WeatherResponse> call = apiService.getWeather(mainActivity.getLat(), mainActivity.getLon(), RetrofitClient.API_KEY);

        Log.d("API", "API URL: " + call.request().url().toString());

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if(weatherResponse!= null) {
                        mainActivity.initCurrentWeather(weatherResponse.getCurrent(), weatherResponse.getDaily().get(0));
                        mainActivity.initRecyclerView(weatherResponse);
                        mainActivity.setVariable(weatherResponse.getDaily());
                        Log.d("API", "Time zone: " + weatherResponse.getTimezone());
                    }
                } else {
                    Log.i("api", "onFailure");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("api", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
