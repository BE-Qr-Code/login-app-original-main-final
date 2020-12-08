package com.example.loginapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class display_qr_data extends AppCompatActivity {

    private TextView courseName, sessionName, instructorName, password;
    private Button scanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr_data);

        courseName = findViewById(R.id.course_name);
        sessionName = findViewById(R.id.session_name);
        instructorName = findViewById(R.id.instructor_name);
        password = findViewById(R.id.password_name);
        scanQR = findViewById(R.id.scan_button);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            courseName.setText(extra.getString("courseName"));
            sessionName.setText(extra.getString("sessionName"));
            instructorName.setText(extra.getString("instructorName"));
            password.setText(extra.getString("password"));
        }
        else {
            Toast.makeText(display_qr_data.this, "Qr does not contain any data", Toast.LENGTH_LONG).show();
        }

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(display_qr_data.this, qrScan.class);
                startActivity(intent);
            }
        });
    }
}