package com.example.qrbasedlicense;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddChalanActivity extends AppCompatActivity {
    private EditText etChalanID,etChalanTitle,etChalanAmount,etChalanIssueDate;
    private Button btnAddChalan;
    private long dateIssue;
    private DatabaseReference mDatabase,postRef;
    String licenceNo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chalan);
        Intent intent=getIntent();
         licenceNo=intent.getStringExtra("LicenceNumber");
        etChalanID=findViewById(R.id.chalanID);
        etChalanTitle=findViewById(R.id.chalanTitle);
        etChalanAmount=findViewById(R.id.chalanAmount);
        etChalanIssueDate=findViewById(R.id.chalanIssueDate);
        btnAddChalan=findViewById(R.id.btnChalan);

        etChalanIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddChalanActivity.this, (view1, year, month, dayOfMonth) -> {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, year);
                            calendar1.set(Calendar.MONTH, month);
                            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            etChalanIssueDate.setText(formatDayMonthYear(calendar1.getTime()));
                            dateIssue= calendar1.getTimeInMillis();
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Chalan").child(licenceNo);
        btnAddChalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput())
                    addChalan();
            }
        });
    }
    public static String formatDayMonthYear(Date date) {
        SimpleDateFormat FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        return FORMAT_DAY_MONTH_YEAR.format(date);
    }

    private boolean validateInput() {




        if (etChalanID.getText().toString().isEmpty()) {
            etChalanID.setError("Please Fill in ID");
            return false;
        }
        if (etChalanAmount.getText().toString().isEmpty()) {
            etChalanAmount.setError("Please Fill in Amount");
            return false;
        }
        if (etChalanTitle.getText().toString().isEmpty()) {
            etChalanTitle.setError("Please Fill in Chalan Title");
            return false;
        }
        if (etChalanIssueDate.getText().toString().isEmpty()) {
            etChalanIssueDate.setError("Please Choose a date");
            return false;
        }




        return true;
    }

    private void addChalan() {
        String chalanId = etChalanID.getText().toString();
        String chalanTitle = etChalanTitle.getText().toString();
        String chalanAmount = etChalanAmount.getText().toString().trim();



        postRef = FirebaseDatabase.getInstance().getReference().child("Chalan").child(licenceNo);

        postRef.orderByChild("chalanID").equalTo(chalanId)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            Toast.makeText(AddChalanActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                        } else {

                            Chalan chalan = new Chalan(chalanId, chalanTitle, chalanAmount,dateIssue);

                            //Saving the Artist
                            mDatabase.child(chalanId).setValue(chalan);

                            Toast.makeText(AddChalanActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            etChalanID.setText("");
                            etChalanAmount.setText("");
                            etChalanTitle.setText("");
                            etChalanIssueDate.setText("");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
     /*   String name = etFn.getText().toString();
        String genre = etLn.getText().toString();
        String id = etLicNum.getText().toString();
        Artist artist = new Artist(id, name, genre);

        //Saving the Artist
        mDatabase.child(id).setValue(artist);*/
    }


}