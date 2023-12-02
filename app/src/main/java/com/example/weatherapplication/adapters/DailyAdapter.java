package com.example.weatherapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.R;
import com.example.weatherapplication.domains.Daily;
import com.example.weatherapplication.domains.Hourly;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.viewHolder> {

    List<Daily> items;

    Context context;

    public DailyAdapter(List<Daily> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DailyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_daily, parent, false);
        context = parent.getContext();
        return new viewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.viewHolder holder, int position) {

        holder.dayTxt.setText(items.get(position).getDay());
        holder.statusTxt.setText(items.get(position).getStatus());
        holder.lowTxt.setText(items.get(position).getLowTemp() + "");
        holder.highTxt.setText(items.get(position).getHighTemp() + "");


        int drawableResourceId = holder.itemView.getResources().getIdentifier(
                items.get(position).getPicPath(),
                "mipmap",
                holder.itemView.getContext().getPackageName()
        );

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView dayTxt, statusTxt, lowTxt, highTxt;
        ImageView pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            dayTxt = itemView.findViewById(R.id.dayTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            lowTxt = itemView.findViewById(R.id.lowTxt);
            highTxt = itemView.findViewById(R.id.highTxt);
            pic = itemView.findViewById(R.id.picFuture);
        }
    }
}
