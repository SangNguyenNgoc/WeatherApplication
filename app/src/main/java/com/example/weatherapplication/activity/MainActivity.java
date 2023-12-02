package com.example.weatherapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.Manifest;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.R;
import com.example.weatherapplication.adapters.HourlyAdapter;
import com.example.weatherapplication.dao.WeatherDao;
import com.example.weatherapplication.domains.Daily;
import com.example.weatherapplication.domains.Hourly;
import com.example.weatherapplication.model.CurrentModel;
import com.example.weatherapplication.model.DailyModel;
import com.example.weatherapplication.model.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    private FusedLocationProviderClient fusedLocationClient;

    private final WeatherDao weatherDao = WeatherDao.getInstance();

    private double lon, lat;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

    }

    public void setVariable(List<DailyModel> dailyModels) {
        TextView nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FutureActivity.class);

                String json = new Gson().toJson(dailyModels);

                intent.putExtra("data", json);

                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void initCurrentWeather(CurrentModel currentModel, DailyModel dailyModel) {
        TextView summary = findViewById(R.id.summary);
        summary.setText(currentModel.getWeather().get(0).getMain());

        ImageView summaryIcon = findViewById(R.id.summaryIcon);
        int drawableResourceId = getResources().getIdentifier(
                "icon_" + currentModel.getWeather().get(0).getIcon(),
                "mipmap",
                getPackageName()
        );

        Glide.with(MainActivity.this)
                .load(drawableResourceId)
                .into(summaryIcon);

        LocalDateTime dateTime = Instant.ofEpochSecond(currentModel.getDt())
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                .toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, 'ngày' dd/MM HH:mm");
        String formattedTime = dateTime.format(formatter);

        TextView currentDate = findViewById(R.id.currentDate);
        currentDate.setText(formattedTime);

        TextView tempTxt = findViewById(R.id.currentTemp);
        tempTxt.setText(getC(currentModel.getTemp()) + "\u2103");

        TextView tempMinMax = findViewById(R.id.currentMinMax);
        tempMinMax.setText("H: " + getC(dailyModel.getTemp().getMax()) + " L: " + getC(dailyModel.getTemp().getMin()));

        TextView pop = findViewById(R.id.pop);
        pop.setText(dailyModel.getPop() * 100 + "%");

        TextView wind = findViewById(R.id.windSpeed);
        wind.setText(dailyModel.getWindSpeed() + "km/h");

        TextView humidity = findViewById(R.id.humidity);
        humidity.setText(dailyModel.getHumidity() + "%");

    }

    public void initRecyclerView(WeatherResponse data) {
        List<Hourly> hourlies = new ArrayList<>();

        data.getHourly().forEach(hourlyModel -> {

            LocalDateTime dateTime = Instant.ofEpochSecond(hourlyModel.getDt())
                    .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                    .toLocalDateTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = LocalTime.of(dateTime.getHour(), 0).format(formatter);


            hourlies.add(new Hourly(
                    formattedTime,
                    (int) Math.round(hourlyModel.getTemp() - 273.15),
                    "icon_" + hourlyModel.getWeather().get(0).getIcon()
                    )
            );
        });

        recyclerView = findViewById(R.id.viewHourly);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        );

        adapterHourly = new HourlyAdapter(hourlies);
        recyclerView.setAdapter(adapterHourly);
    }

    private void getCurrentLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        setLat(location.getLatitude());
                        setLon(location.getLongitude());
                        weatherDao.getCurrentWeather(MainActivity.this);
                    }
                }
            });
        } else {
            askForPermission();
        }
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                getCurrentLocation();
            } else {
                Toast.makeText(MainActivity.this,"Permission required", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private int getC(double k) {
        return (int) Math.round(k - 273.15);
    }
}