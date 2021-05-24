package com.example.qrbasedlicense;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName,inputCNIC,inputDesignation,inputContactNo;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private FirebaseAuth auth;
    String DriverCNIC,DriverName,DriverEmail,DriverDesignation,DriverContact;
    PoliceProfile profile;
    private DatabaseReference myRef;
    private FirebaseDatabase myData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profile=new PoliceProfile();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        myData = FirebaseDatabase.getInstance();
        myRef = myData.getReference();

        btnSignUp = (Button) findViewById(R.id.btnSignup1);
        inputEmail = (EditText) findViewById(R.id.etUserEmail1);
        inputPassword = (EditText) findViewById(R.id.etPasswordSignup);
        inputCNIC=findViewById(R.id.etCNIC1);
        inputName=findViewById(R.id.etUserNameSignup1);
        inputDesignation=findViewById(R.id.etDesignation);
        inputContactNo=findViewById(R.id.etContact3);

     /*   btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });*/

      /*  btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverCNIC=inputCNIC.getText().toString();
                DriverName=inputName.getText().toString();
                DriverDesignation=inputDesignation.getText().toString();
                DriverContact=inputContactNo.getText().toString();

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                               // Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {



                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    String key=auth.getCurrentUser().getUid();

                                    profile.setCnic(DriverCNIC);
                                    profile.setEmail(email);
                                    profile.setName(DriverName);
                                    profile.setDesignation(DriverDesignation);
                                    profile.setContactno(DriverContact);

                                    myRef.child("PoliceOfficers").child(key).setValue(profile);

                                    FirebaseAuth.getInstance().signOut();



                                    startActivity(new Intent(SignupActivity.this, OptinonsLicenceImproved.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}