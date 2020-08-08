package com.example.sushmita.saloon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sushmita.saloon.AppointmentActivity;
import com.example.sushmita.saloon.R;
import com.example.sushmita.saloon.model.Service;

import java.util.ArrayList;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private static final String TAG = "ServiceAdapter";

    Context context;
    ArrayList<Service> arrayList;
    LayoutInflater inflater;

    public ServiceAdapter(Context context, ArrayList<Service> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = inflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder h, final int position) {
        final Service s = arrayList.get(position);

        h.serviceName.setText(s.getServiceName());
        h.serviceCharges.setText("€" + s.getServiceCharges());

        if (s.isHideCheckBox()) {
            h.checkBox.setVisibility(View.VISIBLE);
        } else {
            h.checkBox.setVisibility(View.INVISIBLE);
        }

        if (s.isServiceChecked())
            h.checkBox.setChecked(true);
        else
            h.checkBox.setChecked(false);

        h.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppointmentActivity.serviceArrayList.add(position, new Service(s.getServiceName(), true, s.getServiceCharges(), true));
                } else {
                    AppointmentActivity.serviceArrayList.add(position, new Service(s.getServiceName(), false, s.getServiceCharges(), true));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName, serviceCharges;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            serviceCharges = itemView.findViewById(R.id.serviceCharges);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
