package com.example.weatherapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.R;
import com.example.weatherapplication.adapters.DailyAdapter;
import com.example.weatherapplication.domains.Daily;
import com.example.weatherapplication.model.DailyModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FutureActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterDaily;

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        Intent intent = getIntent();

        String json = intent.getStringExtra("data");

        TypeToken<List<DailyModel>> typeToken = new TypeToken<List<DailyModel>>() {};
        List<DailyModel> receivedModels = new Gson().fromJson(json, typeToken.getType());

        setTomorrowView(receivedModels.get(1));

        initRecyclerView(receivedModels);
        setVariable();
    }

    @SuppressLint("SetTextI18n")
    private void setTomorrowView(DailyModel dailyModel) {
        TextView summary = findViewById(R.id.tomoSummary);
        summary.setText(dailyModel.getWeather().get(0).getMain());

        LocalDateTime dateTime = Instant.ofEpochSecond(dailyModel.getDt())
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                .toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Ngày mai,' dd/MM");
        String formattedTime = dateTime.format(formatter);

        TextView tomoDate = findViewById(R.id.tomoDate);
        tomoDate.setText(formattedTime);

        ImageView summaryIcon = findViewById(R.id.tomoIcon);
        int drawableResourceId = getResources().getIdentifier(
                "icon_" + dailyModel.getWeather().get(0).getIcon(),
                "mipmap",
                getPackageName()
        );

        Glide.with(FutureActivity.this)
                .load(drawableResourceId)
                .into(summaryIcon);

        TextView pop = findViewById(R.id.tomoPop);
        pop.setText(dailyModel.getPop() * 100 + "%");

        TextView wind = findViewById(R.id.tomoWind);
        wind.setText(dailyModel.getWindSpeed() + "km/h");

        TextView humidity = findViewById(R.id.tomoHumidity);
        humidity.setText(dailyModel.getHumidity() + "%");
    }

    private void setVariable() {
        ConstraintLayout backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FutureActivity.this, MainActivity.class));
            }
        });
    }

    private void initRecyclerView(List<DailyModel> dailyModels) {
        List<Daily> dailies = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, 'ngày' dd/MM");

        for(int i=2;i<dailyModels.size();i++) {
            LocalDateTime dateTime = Instant.ofEpochSecond(dailyModels.get(i).getDt())
                    .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                    .toLocalDateTime();
            dailies.add(new Daily(
                    dateTime.format(formatter),
                    "icon_" + dailyModels.get(i).getWeather().get(0).getIcon(),
                    dailyModels.get(i).getWeather().get(0).getMain(),
                    getC(dailyModels.get(i).getTemp().getMax()) + "\u2103",
                    getC(dailyModels.get(i).getTemp().getMin())+ "\u2103"
            ));
        }

        recyclerView = findViewById(R.id.viewWeek);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        adapterDaily = new DailyAdapter(dailies);
        recyclerView.setAdapter(adapterDaily);

    }

    private int getC(double k) {
        return (int) Math.round(k - 273.15);
    }
}