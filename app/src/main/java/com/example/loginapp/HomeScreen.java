package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    private TextView moodleId;
    private Button scanQR_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        moodleId = findViewById(R.id.moodleId);
        scanQR_btn = findViewById(R.id.scanQR_button);

        moodleId.setText(SharedPref.getInstance(HomeScreen.this).LoggedInUser());

        scanQR_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, qrScan.class);
                startActivity(intent);
            }
        });
    }
}