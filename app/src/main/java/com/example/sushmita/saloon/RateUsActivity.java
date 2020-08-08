package com.example.sushmita.saloon;
/**
 * Sushmita Shrestha -2019445
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RateUsActivity extends AppCompatActivity
{

    RatingBar ratingBar;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_us);

        ratingBar = findViewById(R.id.rate_bar);
        ratingBar.setNumStars(5);

        button=(Button)findViewById(R.id.btn);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser)
            {
                Toast.makeText(RateUsActivity.this, "Stars:"+ v, Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
                Toast.makeText(RateUsActivity.this, "Stars:"+ ratingBar.getRating()+"/"+ 5, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
