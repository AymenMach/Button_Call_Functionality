package com.example.button_call_functionality;



import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    // Handler for managing delayed tasks
    private Handler handler;
    // Runnable task to make a call after a delay
    private Runnable callRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Initialize the Handler
        handler = new Handler();
        callRunnable = this::makeCall;

        // Set an onClickListener for the button to trigger the call after a delay
        findViewById(R.id.triggerButton).setOnClickListener(v -> triggerCall());
        checkPermissions();
    }

    // Method to check and request CALL_PHONE permission if needed
    private void checkPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    // Method to initiate the call after a 15-second delay
    private void triggerCall()
    {
        handler.postDelayed(callRunnable, 15000); // Delay call by 15 seconds
    }

    // Method to make a call to the specified number
    private void makeCall() {
        // Check if the app has CALL_PHONE permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:4384557589"));
                startActivity(callIntent);
            } else {
                // Show a Toast message if the permission is not granted
                Toast.makeText(this, "Call Phone permission is not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            // For devices with OS versions lower than M, directly initiate the call
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:4384557589")); // Replace with your number
            startActivity(callIntent);
        }
    }
}

