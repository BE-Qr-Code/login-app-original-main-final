package com.example.loginapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class display_qr_data extends AppCompatActivity {

    private TextView sessionId, instructorName, subjectName, status;
    private Button scanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr_data);

        sessionId = findViewById(R.id.session_Id);
        subjectName = findViewById(R.id.subject_name);
        instructorName = findViewById(R.id.instructor_name);
        status = findViewById(R.id.status_name);
        scanQR = findViewById(R.id.scan_button);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            sessionId.setText(extra.getString("sessionId"));
            //subjectName.setText(extra.getString("subjectName"));
            //instructorName.setText(extra.getString("instructorName"));
            //status.setText(extra.getString("status"));
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