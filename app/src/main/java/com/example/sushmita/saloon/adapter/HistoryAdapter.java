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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sushmita.saloon.R;
import com.example.sushmita.saloon.model.History;
import com.example.sushmita.saloon.model.Service;

import java.util.ArrayList;
import java.util.Set;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private static final String TAG = "HistoryAdapter";

    Context context;
    ArrayList<History> arrayList;
    LayoutInflater inflater;

    public HistoryAdapter(Context context, ArrayList<History> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = inflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_history_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder h, int position) {
        History s = arrayList.get(position);

        h.saloonNameTv.setText(s.getSalonName());
        h.timeTv.setText(s.getTime());
        h.dateTv.setText(s.getDate());

        ArrayList<Service> serviceArrayList = new ArrayList<>();

        for (String key : s.getMap().keySet()) {
            Service service = new Service("" + key, Double.parseDouble(s.getMap().get(key)), false);
            serviceArrayList.add(service);
        }

        ServiceAdapter adapter = new ServiceAdapter(context, serviceArrayList);

        h.rv.setLayoutManager(new LinearLayoutManager(context));
        h.rv.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView saloonNameTv, timeTv, dateTv;
        RecyclerView rv;

        public MyViewHolder(View itemView) {
            super(itemView);

            saloonNameTv = itemView.findViewById(R.id.salonNameTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            rv = itemView.findViewById(R.id.list);
        }
    }
}
