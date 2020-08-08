package com.example.sushmita.saloon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sushmita.saloon.AppointmentActivity;
import com.example.sushmita.saloon.R;
import com.example.sushmita.saloon.model.Salon;

import java.util.ArrayList;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.MyViewHolder> {

    Context context;
    ArrayList<Salon> arrayList;
    LayoutInflater inflater;

    public SalonAdapter(Context context, ArrayList<Salon> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = inflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_salon_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder h, int position) {
        final Salon s = arrayList.get(position);

        h.salonNameTv.setText(s.getSalon());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context)
                .load(s.getImage())
                .apply(options)
                .into(h.salonIv);


        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, AppointmentActivity.class);
                in.putExtra("salonId", s.getSalonId());
                in.putExtra("salonName", s.getSalon());
                in.putExtra("salonImage", s.getImage());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView salonNameTv;
        ImageView salonIv;

        public MyViewHolder(View itemView) {
            super(itemView);

            salonNameTv = itemView.findViewById(R.id.salonNameTv);
            salonIv = itemView.findViewById(R.id.salonIv);

        }
    }
}
