package com.example.qrbasedlicense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ViewPoliceProfile extends AppCompatActivity {
    private EditText etCNIC,etName,etCntact,etEmail,etDesignation;
    private String CNIC,Name,Cntact,Email,Designation;
    private Button btnUpdate;
    private PoliceProfile policeProfile1;


    private FirebaseDatabase myData;
    private DatabaseReference myRef;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver_profile);
        etCNIC=findViewById(R.id.etCNIC5);
        etCntact=findViewById(R.id.etContact11);
        etEmail=findViewById(R.id.etEmail5);
        etDesignation=findViewById(R.id.etDes);
        etName=findViewById(R.id.etName5);
        btnUpdate=findViewById(R.id.btnUpdateProf);
        policeProfile1=new PoliceProfile();



        myData=FirebaseDatabase.getInstance();
        myRef=myData.getReference();
        myAuth=FirebaseAuth.getInstance();
        String key=myAuth.getCurrentUser().getUid();

        myRef.child("PoliceOfficers").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PoliceProfile policeProfile=dataSnapshot.getValue(PoliceProfile.class);

                etCNIC.setText(policeProfile.getCnic());
                etCntact.setText(policeProfile.getContactno());
                etDesignation.setText(policeProfile.getDesignation());
                etEmail.setText(policeProfile.getEmail());
                etName.setText(policeProfile.getName());
                etEmail.setKeyListener(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                policeProfile1.setCnic(etCNIC.getText().toString());
                policeProfile1.setContactno(etCntact.getText().toString());
                policeProfile1.setDesignation(etDesignation.getText().toString());
                policeProfile1.setName(etName.getText().toString());
                policeProfile1.setCnic(etEmail.getText().toString());
                myRef.child("PoliceOfficers").child(key).setValue(policeProfile1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ViewPoliceProfile.this, "Ipdated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });





    }
}