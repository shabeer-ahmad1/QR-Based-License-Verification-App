package com.example.qrbasedlicense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {
    private Button btnPolice,btnLicenceAuthority,btnDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        btnPolice=findViewById(R.id.btnforMotorwayOption);
        btnLicenceAuthority=findViewById(R.id.btnforLicenceOffice);
        btnDriver=findViewById(R.id.btnDriver);
        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnLicenceAuthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this,OptionsfroLicenceAuthority.class);
                startActivity(intent);
            }
        });

        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this,UsingLicenceNumbertogetOTP.class);
                startActivity(intent);
            }
        });
    }

}