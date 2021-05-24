package com.example.qrbasedlicense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsfroLicenceAuthority extends AppCompatActivity {
    private Button btnAddDriver,btnViewDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionsfro_licence_authority);
        btnAddDriver=findViewById(R.id.btnAddDriver);
        btnViewDriver=findViewById(R.id.btnViewDriver);
        btnAddDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsfroLicenceAuthority.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnViewDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsfroLicenceAuthority.this,ShowingLicenceHolders.class);
                startActivity(intent);
            }
        });
    }
}