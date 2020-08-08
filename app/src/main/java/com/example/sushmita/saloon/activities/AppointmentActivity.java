package com.example.sushmita.saloon;
/**
 * Sushmita Shrestha -2019445
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sushmita.saloon.adapter.SalonAdapter;
import com.example.sushmita.saloon.adapter.ServiceAdapter;
import com.example.sushmita.saloon.model.Service;
import com.example.sushmita.saloon.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AppointmentActivity extends AppCompatActivity {

    private static final String TAG = "SalonParlour1";
    int time, date;

    TextView datepicker;
    int year_x, month_x, day_x;
    static final int DILOG_ID = 0;

    int temp;
    Button confirmAppointmentBtn;

    TextView timepicker, salonNameTv;
    ImageView salonIv;

    public static ArrayList<Service> serviceArrayList = new ArrayList<>();
    ServiceAdapter adapter;
    RecyclerView rv;
    String salonId = "", salonName = "", salonImage = "";
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mDatabase = FirebaseDatabase.getInstance().getReference("servies");

        salonId = getIntent().getStringExtra("salonId");
        salonName = getIntent().getStringExtra("salonName");
        salonImage = getIntent().getStringExtra("salonImage");

        init();

        salonNameTv.setText(salonName);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(this)
                .load(salonImage)
                .apply(options)
                .into(salonIv);

//        arrayList = new ArrayList<>();
//        arrayList.add(new Service("abc123", "Face Massage", false, 50));
//        arrayList.add(new Service("abc124", "Whitening Facial ", false, 150));
//        arrayList.add(new Service("abc125", "Mud Mask Facial", false, 500));
//        arrayList.add(new Service("abc126", "Black Heads Removal", false, 250));
//

        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

//        timepicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(AppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//
////                        if (selectedHour <= 9 || selectedHour >= 21) {
////                            Toast.makeText(AppointmentActivity.this, "Please select valid time", Toast.LENGTH_SHORT).show();
////                        } else {
////
//                        timepicker.setText(selectedHour + " : " + selectedMinute);
//                        time = 1;
////                        }
//                    }
//                }, hour, minute, false);
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//            }
//        });
    }

    public void init() {
        salonNameTv = findViewById(R.id.salonNameTv);
        salonIv = findViewById(R.id.salonIv);
        timepicker = findViewById(R.id.timePicker);
        confirmAppointmentBtn = findViewById(R.id.pay);
        datepicker = findViewById(R.id.datepicker);
        rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String price = postSnapshot.getValue().toString();
                    String sName = postSnapshot.getKey();

                    double p = Double.parseDouble(price);

                    serviceArrayList.add(new Service(sName, false, p, true));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ServiceAdapter(AppointmentActivity.this, serviceArrayList);
        rv.setAdapter(adapter);

    }

    public static int total = 0;

    public void pay(View view) {
        if (datepicker.getText().toString().equalsIgnoreCase("Select your date")) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (timepicker.getText().toString().equalsIgnoreCase("Select your time")) {
            Toast.makeText(this, "Please select time", Toast.LENGTH_SHORT).show();
            return;
        }


        appointmentConfirmation();
    }

    public void selectDate(View view) {
        getDate();
    }

    public void selectTime(View view) {
        getTime();
    }

    public void appointmentConfirmation() {

        Map<String, Double> serviceMap = new HashMap<>();

        total = 0;

        for (Service service : serviceArrayList) {
            if (service.isServiceChecked()) {
                total += service.getServiceCharges();
                serviceMap.put(service.getServiceName(), service.getServiceCharges());
            }
        }
        if (total < 1) {
            Toast.makeText(this, "You cannot make appointment without service", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> a = new HashMap<>();
        a.put("userId", userId);
        a.put("salonId", "" + salonId);
        a.put("salonName", "" + salonName);
        a.put("services", serviceMap);
        a.put("time", "" + timepicker.getText().toString());
        a.put("date", "" + datepicker.getText().toString());

        ref.child("appointment").push().setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AppointmentActivity.this, "Your appointment has been confirmed", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AppointmentActivity.this, FeePaymentActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(AppointmentActivity.this, "Not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int mYear, mMonth, mDay, mHour, mMinute;

    public void getTime() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timepicker.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void getDate() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datepicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
}

