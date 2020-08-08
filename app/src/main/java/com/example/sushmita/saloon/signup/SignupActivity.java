package com.example.sushmita.saloon.signup;
/**
 * Sushmita Shrestha -2019445
 */

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sushmita.saloon.R;
import com.example.sushmita.saloon.ResetPasswordActivity;
import com.example.sushmita.saloon.Users;
import com.example.sushmita.saloon.activities.HomeActivity;
import com.example.sushmita.saloon.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {

    //variables
    private RadioButton customerRb, salonRb;
    private EditText inputName, inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Fire-base auth instance
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        //find button and text
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputName = (EditText) findViewById(R.id.nameTv);
        inputEmail = (EditText) findViewById(R.id.emailTv);
        inputPassword = (EditText) findViewById(R.id.passTv);
//        customerRb = (RadioButton) findViewById(R.id.customerRb);
//        salonRb = (RadioButton) findViewById(R.id.salonRb);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        // btn on-click listener
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    String id = databaseUsers.push().getKey();

                    Users users = new Users(id, email, password);

                    databaseUsers.child(id).setValue(users);

                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                addUsers();
            }

            private void addUsers() {
                progressBar.setVisibility(View.VISIBLE);
                //user created
                auth.createUserWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    String id = FirebaseAuth.getInstance().getUid();
                                    String customerOrOwner = "";

                                    if (customerRb.isChecked()) {
                                        customerOrOwner = "customer";
                                        salonRb.setChecked(false);
                                    }

                                    if (salonRb.isChecked()) {
                                        customerOrOwner = "owner";
                                        customerRb.setChecked(false);
                                    }

                                    User user = new User(
                                            id,
                                            inputName.getText().toString(),
                                            inputEmail.getText().toString(),
                                            inputPassword.getText().toString(),
                                            customerOrOwner
                                    );

                                    databaseUsers.child(id).setValue(user);

                                    startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                    Toast.makeText(SignupActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
