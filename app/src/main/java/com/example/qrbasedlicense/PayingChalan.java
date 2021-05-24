package com.example.qrbasedlicense;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PayingChalan extends AppCompatActivity {
    private EditText etCNIC,etCntact,etEmail,etDesignation;
    private String CNIC,Name,Cntact,Email,Designation;
    private Chalan chalan;
    private Button btnpayChalan;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying_chalan);
        etCNIC=findViewById(R.id.etChalanid);
        etCntact=findViewById(R.id.etChalantitle);
        etEmail=findViewById(R.id.etChalanamount);
        etDesignation=findViewById(R.id.etChalandate);
        btnpayChalan=findViewById(R.id.btnpayChalan);

        Intent intent=getIntent();
        chalan= (Chalan) intent.getSerializableExtra("model");
        etCNIC.setText(chalan.getChalanID());
        etCntact.setText(chalan.getChalanTitle());
        etDesignation.setText(chalan.getChalanAmount());
        etEmail.setText(String.valueOf(chalan.getChalanDate()));
        etEmail.setKeyListener(null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String LiceNumber = preferences.getString("Name", "");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Chalan").child(LiceNumber).child(chalan.getChalanID());

        btnpayChalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PayingChalan.this);
                builder.setTitle("Enter Your Credit Card Number");

// Set up the input
                final EditText input = new EditText(PayingChalan.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txt=input.getText().toString();
                        if (txt.isEmpty()){
                            dialog.cancel();
                            Toast.makeText(PayingChalan.this, "Please Enter valid Number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(PayingChalan.this, "Chalan Paid Successfully", Toast.LENGTH_SHORT).show();
                            mDatabase.removeValue();
                            Intent intent1=new Intent(PayingChalan.this,Main2Activity.class);
                            startActivity(intent1);

                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



       /* myData=FirebaseDatabase.getInstance();
        myRef=myData.getReference();
        myAuth=FirebaseAuth.getInstance();
        String key=myAuth.getCurrentUser().getUid();*/






    }
}