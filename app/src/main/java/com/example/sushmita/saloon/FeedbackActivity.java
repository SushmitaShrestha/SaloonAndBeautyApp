package com.example.sushmita.saloon;

//Sushmita Shrestha - 2019445
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                sendFeedback();
                Toast.makeText(FeedbackActivity.this, "Your feedback is through email app", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // this adds items to the action bar if it is active.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //new activity to send email

    private void sendFeedback()
    {
        final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
        _Intent.setType("text/html");
        _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email) });

        _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
        _Intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));

        startActivity(Intent.createChooser(_Intent, getString(R.string.title_send_feedback)));
    }
}