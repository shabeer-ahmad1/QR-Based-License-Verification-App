package com.example.qrbasedlicense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsforPoliceActivity extends AppCompatActivity {
    private Button btnScanQr,btnScanMonogram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionsfor_police);
        btnScanMonogram=findViewById(R.id.btnforMonogram);
        btnScanQr=findViewById(R.id.btnforScanning);
        btnScanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsforPoliceActivity.this,BarCodeScanningAct.class);
                startActivity(intent);
            }

        });
        btnScanMonogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsforPoliceActivity.this,MonogramRecognizerActivity.class);
                startActivity(intent);
            }
        });
    }
}