package com.example.sushmita.saloon.activities;
/**
 * Sushmita Shrestha -2019445
 */

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sushmita.saloon.R;
import com.example.sushmita.saloon.adapter.HistoryAdapter;
import com.example.sushmita.saloon.model.History;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";

    ArrayList<History> historyArrayList = new ArrayList<>();
    HistoryAdapter adapter;
    RecyclerView rv;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mDatabase = FirebaseDatabase.getInstance().getReference("appointment");

        init();
    }

    public void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Appointments");
        toolbar.setSubtitle("History");
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
    }

    HashMap<String, String> stringMap = new HashMap<String, String>();

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String userId = postSnapshot.child("userId").getValue().toString();
                    String date = postSnapshot.child("date").getValue().toString();
                    String time = postSnapshot.child("time").getValue().toString();
                    String salonName = postSnapshot.child("salonName").getValue().toString();
                    String salonId = postSnapshot.child("salonId").getValue().toString();
                    DataSnapshot ds = postSnapshot.child("services");

                    for (DataSnapshot d : ds.getChildren())
                        stringMap.put(d.getKey(), d.getValue().toString());

                    History h = new History(
                            "" + userId,
                            "" + salonId,
                            "" + salonName,
                            stringMap,
                            "" + time,
                            "" + date);

                    historyArrayList.add(h);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new HistoryAdapter(HistoryActivity.this, historyArrayList);
        rv.setAdapter(adapter);

    }
}