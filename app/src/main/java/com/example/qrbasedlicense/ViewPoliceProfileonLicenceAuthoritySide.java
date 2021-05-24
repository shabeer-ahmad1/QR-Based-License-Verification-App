package com.example.qrbasedlicense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewPoliceProfileonLicenceAuthoritySide extends AppCompatActivity {
    private EditText etCNIC,etName,etCntact,etEmail,etDesignation;
    private String CNIC,Name,Cntact,Email,Designation;
    private PoliceProfile policeProfile1;


    private FirebaseDatabase myData;
    private DatabaseReference myRef;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_police_profile_auth_side);
        etCNIC=findViewById(R.id.etCNIC6);
        etCntact=findViewById(R.id.etContact6);
        etEmail=findViewById(R.id.etEmail6);
        etDesignation=findViewById(R.id.etDes2);
        etName=findViewById(R.id.etName6);

        Intent intent=getIntent();
        policeProfile1= (PoliceProfile) intent.getSerializableExtra("model");
        etCNIC.setText(policeProfile1.getCnic());
        etCntact.setText(policeProfile1.getContactno());
        etDesignation.setText(policeProfile1.getDesignation());
        etEmail.setText(policeProfile1.getEmail());
        etName.setText(policeProfile1.getName());
        etEmail.setKeyListener(null);



       /* myData=FirebaseDatabase.getInstance();
        myRef=myData.getReference();
        myAuth=FirebaseAuth.getInstance();
        String key=myAuth.getCurrentUser().getUid();*/






    }
}