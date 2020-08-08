package com.example.sushmita.saloon.activities;


//Sushmita Shrestha - 2019445

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.sushmita.saloon.FeedbackActivity;
import com.example.sushmita.saloon.R;
import com.example.sushmita.saloon.RateUsActivity;
import com.example.sushmita.saloon.adapter.SalonAdapter;
import com.example.sushmita.saloon.adapter.ServiceAdapter;
import com.example.sushmita.saloon.login.LoginActivity;
import com.example.sushmita.saloon.model.Salon;
import com.example.sushmita.saloon.model.Service;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    ArrayList<Salon> salonArrayList = new ArrayList<>();
    ArrayList<Service> serviceArrayList;
    SalonAdapter adapter;
    RecyclerView rv;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference("salons");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setSubtitle("Salons");
        setSupportActionBar(toolbar);


        rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        salonArrayList.clear();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Salon salon = dataSnapshot.getValue(Salon.class);
                salon.setSalonId(dataSnapshot.getKey());
                salonArrayList.add(salon);
                adapter = new SalonAdapter(HomeActivity.this, salonArrayList);
                rv.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is true.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handel action bar and control up/down btn
        int id = item.getItemId();

        //no inspection Simplifiable If-Statement

        if (id == R.id.action_settings) {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0892309620"));
            startActivity(i);
        }
        if (id == R.id.action_settings2) {
            Intent i = new Intent(HomeActivity.this, FeedbackActivity.class);
            startActivity(i);
        }
        if (id == R.id.action_settings3) {
            Intent i = new Intent(HomeActivity.this, RateUsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // navigation view handel b click
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            //  camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();

            startActivity(new Intent(this, LoginActivity.class));

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?package/testing.id \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    private void salonLoggedIn() {
//        serviceArrayList = new ArrayList<>();
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("services");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //clearing the previous artist list
//                if (serviceArrayList != null || serviceArrayList.size() > 0)
//                    serviceArrayList.clear();
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Salon artist = postSnapshot.getValue(Salon.class);
//                    arrayList.add(artist);
//                }
//
//                adapter = new ServiceAdapter(HomeActivity.this, arrayList);
//                rv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void customerLoggedIn() {
//        salonArrayList = new ArrayList<>();
//        salonArrayList.add(new Salon("", "Mahrose Beauty Salon", "https://i.ytimg.com/vi/VuAtp44FCsw/maxresdefault.jpg"));
//        salonArrayList.add(new Salon("", "Mahrose Beauty Salon", "https://onlybusinessideas.com/wp-content/uploads/2019/09/beauty-parlour-1024x576.png"));
//        salonArrayList.add(new Salon("", "Mahrose Beauty Salon", "https://sketchup.cgtips.org/wp-content/uploads/2019/04/1927-Spa-Hair-Salon-Sketchup-Model-Free-Download.jpg"));
//        salonArrayList.add(new Salon("", "Mahrose Beauty Salon", "https://i.ytimg.com/vi/VuAtp44FCsw/maxresdefault.jpg"));
//        salonArrayList.add(new Salon("", "Mahrose Beauty Salon", "https://onlybusinessideas.com/wp-content/uploads/2019/09/beauty-parlour-1024x576.png"));
//        salonArrayList.add(new Salon("", "Mahrose Beauty Salon", "https://sketchup.cgtips.org/wp-content/uploads/2019/04/1927-Spa-Hair-Salon-Sketchup-Model-Free-Download.jpg"));
//
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("salons");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //clearing the previous artist list
//                if (arrayList != null || arrayList.size() > 0)
//                    arrayList.clear();
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Salon artist = postSnapshot.getValue(Salon.class);
//                    arrayList.add(artist);
//                }
//
//                adapter = new SalonAdapter(HomeActivity.this, arrayList);
//                rv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}

