package com.example.qrbasedlicense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UsingLicenceNumbertogetOTP extends AppCompatActivity {
    private EditText editTextMobile;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_licence_numbertoget_o_t_p);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");


        editTextMobile = findViewById(R.id.editTextMobile);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 1){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                mDatabase.orderByChild("artistId").equalTo(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Intent intent = new Intent(UsingLicenceNumbertogetOTP.this, VerifyPhoneActivity.class);
                            intent.putExtra("mobile", mobile);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UsingLicenceNumbertogetOTP.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Name",mobile);
                            editor.apply();
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(UsingLicenceNumbertogetOTP.this, "Driver With This Licence Number Doesnot Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
    }
}