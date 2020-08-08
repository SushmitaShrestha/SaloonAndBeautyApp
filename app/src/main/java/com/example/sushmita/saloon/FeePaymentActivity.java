package com.example.sushmita.saloon;

//Sushmita Shrestha - 2019445

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import static com.example.sushmita.saloon.AppointmentActivity.total;

//Implement on click listener to class
public class FeePaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAmount;


    //Amount paid
    private String paymentAmount;


    //paypal intent req code
    //to track onActivityresult method
    public static final int PAYPAL_REQUEST_CODE = 123;


    //Paypal Config Object
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with test environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or realtime/live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //views
        Button buttonPay = findViewById(R.id.buttonPay);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextAmount.setEnabled(false);
        editTextAmount.setText("" + total);

        buttonPay.setOnClickListener(this);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void getPayment() {
        //user input amount
        paymentAmount = editTextAmount.getText().toString();


        //declaring a paypal payment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "EUR", "Pay Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal config to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Getting payment of paypal into the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // activity starts for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //If result from paypal
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //confirmation is not null
                if (confirm != null) {
                    try {
                        //payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConformationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unfortunate failure occurred: ", e);
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled transaction.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPal Configuration was submitted. Please see the docs.");
            }
        }
    }


    @Override
    public void onClick(View v) {
        getPayment();
    }
}
//60