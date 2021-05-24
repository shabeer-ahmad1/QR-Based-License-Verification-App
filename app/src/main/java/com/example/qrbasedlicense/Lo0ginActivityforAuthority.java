package com.example.qrbasedlicense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lo0ginActivityforAuthority extends AppCompatActivity {
    EditText etUsernameAuth,etUserPassAuth;
    Button btnSigninAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lo0gin_activityfor_authority);

        etUsernameAuth=findViewById(R.id.etUserNameAuth);
        etUserPassAuth=findViewById(R.id.etPasswordAuth);
        btnSigninAuth=findViewById(R.id.btnLoginToMainAuth);
        btnSigninAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=etUsernameAuth.getText().toString();
                String userPass=etUserPassAuth.getText().toString();

                if ((userName.equals("authPunjab@gov.pk")&& userPass.equals("112233"))||(userName.equals("authSindh@gov.pk")&& userPass.equals("112233"))||(userName.equals("authNWFP@gov.pk")&& userPass.equals("112233"))||(userName.equals("authBalochistan@gov.pk")&& userPass.equals("112233"))){
                    Intent intent=new Intent(Lo0ginActivityforAuthority.this,OptinonsLicenceImproved.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Lo0ginActivityforAuthority.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}