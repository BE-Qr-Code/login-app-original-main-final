package com.example.loginapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.gson.JsonObject;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class qrScan extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private String qrCode_data;
    private ProgressBar progressBar;
    private Double latitude, longitude;
    private String sessionId, qrcode, departId, subjectId, currentTime, currentDate;
    private String moodleId;

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* To remove the TOP BAR from the app */
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        /*  */
        setContentView(R.layout.activity_qr_scan);

        progressBar = this.findViewById(R.id.progress_circular);

        requestCamera();
    }

    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(qrScan.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        //todo
        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        //mCodeScanner.setScanMode(ScanMode.CONTINUOUS);  //to scan qr codes continuously

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrCode_data = result.getText(); //get the whole JSON string from the qr code
                        try {
                            //to compare with database
                            ScanDetails scanDetails = new ScanDetails();
                            JSONObject obj = new JSONObject(qrCode_data);
                            Log.d("qrScan_qrdet", qrCode_data);

                            //current time
                            currentTime= new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                            //current date
                            currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            //current location: lati and longi
                            locationModel locationModel = new locationModel();
                            latitude = locationModel.getLatitude();
                            longitude = locationModel.getLongitude();

                            //set these values into ScanDetails class using getters and setters
                            scanDetails.setSessionId(obj.getString("sessionId"));
                            scanDetails.setQrcode(obj.getString("qrCode"));
                            scanDetails.setCurrentDate(currentDate);
                            scanDetails.setCurrentTime(currentTime);
                            scanDetails.setDepartId(obj.getString("deptId"));
                            //scanDetails.setStatus(obj.getString("status"));
                            scanDetails.setLatitude(latitude);
                            scanDetails.setLongitude(longitude);
                            scanDetails.setSubjectId(obj.getString("subjectId"));

                            //retrieve those values
                            sessionId = scanDetails.getSessionId();
                            qrcode = scanDetails.getQrcode();
                            departId = scanDetails.getDepartId();
                            subjectId = scanDetails.getSubjectId();
                            currentDate = scanDetails.getCurrentDate();
                            currentTime = scanDetails.getCurrentTime();

                            //get moodle ID from sharedPreferences
                            moodleId = SharedPref.getInstance(qrScan.this).LoggedInUser();
                            Log.d("qrScan moodleid: ", moodleId);

                            Log.d("qrScan date and time", currentTime + " " + currentDate);
                            Log.d("qrScan_latiandloti", "onLocationResult: Latitude: " + locationModel.getLatitude() + "Longitude: " + locationModel.getLongitude());
                            Log.d("qrScan_scanDetails", sessionId + " " + qrcode + " " + departId + " " + subjectId);

                            //send these values to api
                            Api api = ApiClient.getClient().create(Api.class);
                            Call<JsonObject> call = api.scanDetails(sessionId, qrcode, currentDate, currentTime, departId,
                                    latitude, longitude, subjectId, moodleId);

                            //progress bar start
                            progressBar.setVisibility(ProgressBar.VISIBLE);

                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if(response.isSuccessful()){    //checks whether we get a response(json object) from api or not
                                        //progress bar end
                                        progressBar.setVisibility(ProgressBar.INVISIBLE);

                                        //convert response to json
                                        String jsonresponse = response.body().toString();
                                        try {
                                            JSONObject apiResponse = new JSONObject(jsonresponse);
                                            Log.d("qrScan json: ", apiResponse.toString());

                                            //toast the message received
                                            Toast.makeText(qrScan.this, apiResponse.getString("message"), Toast.LENGTH_LONG).show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        Toast.makeText(qrScan.this, response.message(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    //progress bar end
                                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                                    Toast.makeText(qrScan.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    Log.d("onFailure", "onFailure: " + t.getMessage());
                                }
                            });

                            //To start next activity/screen
                            Intent intent = new Intent(qrScan.this, HomeScreen.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(qrScan.this, qrCode_data, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}