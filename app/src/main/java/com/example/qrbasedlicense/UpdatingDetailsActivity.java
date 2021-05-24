package com.example.qrbasedlicense;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdatingDetailsActivity extends AppCompatActivity {
    EditText etNumber,etFN,etLN,etDateBegin,etDateEnd,etPhone;
    private DatabaseReference mDatabase,postRef;
    private Button btnAdd;

    ImageView imageView;
    private String savePath;
    private long dateIssue;
    private long dateExpire;
    Bitmap bmp ;
    ByteArrayOutputStream bos;
    byte[] bt ;
    String encodeString,phoneNumber;
    private Uri filePath;
    String inputValue;
    private final int PICK_IMAGE_REQUEST = 71;
    SimpleDateFormat DMY_TIME_SLASH_FORMAT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating_details);
        btnAdd=findViewById(R.id.btnAdd);



        DMY_TIME_SLASH_FORMAT = new SimpleDateFormat("d/M/yyyy", Locale.US);


        etNumber=findViewById(R.id.LicenceNumber);
        etFN=findViewById(R.id.FirstName);
        etLN=findViewById(R.id.LastName);

        etDateEnd=findViewById(R.id.etDateEnd);
        imageView=findViewById(R.id.imgViewDriver);
        etDateBegin=findViewById(R.id.etDateBegin);
        etPhone=findViewById(R.id.PhoneNum1);

        Intent intent=getIntent();
       Artist nm= (Artist) intent.getSerializableExtra("model");
        etNumber.setText(nm.getArtistId());
        etNumber.setEnabled(false);

        etFN.setText(nm.getArtistName());
        etLN.setText(nm.getArtistGenre());
        etPhone.setText(nm.getPhoneNumber());
        String beginDate=formatTimestamp(nm.getBeginValidity());
        String expDate=formatTimestamp(nm.getEndValidity());
        etDateBegin.setText(beginDate);
        etDateEnd.setText(expDate);
        String en=nm.getImgpath();
        dateIssue=nm.getBeginValidity();
        dateExpire=nm.getEndValidity();
        byte[] decodedString = Base64.decode(en, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        etDateBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(UpdatingDetailsActivity.this, (view1, year, month, dayOfMonth) -> {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, year);
                            calendar1.set(Calendar.MONTH, month);
                            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            etDateBegin.setText(formatDayMonthYear(calendar1.getTime()));
                            dateIssue= calendar1.getTimeInMillis();
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        etDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(UpdatingDetailsActivity.this, (view1, year, month, dayOfMonth) -> {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, year);
                            calendar1.set(Calendar.MONTH, month);
                            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            etDateEnd.setText(formatDayMonthYear(calendar1.getTime()));
                            dateExpire= calendar1.getTimeInMillis();
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();


            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if (validateInput())
                    addArtist();
            }
        });


    }

    public static String formatDayMonthYear(Date date) {
        SimpleDateFormat FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        return FORMAT_DAY_MONTH_YEAR.format(date);
    }





    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bmp);
                bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bt = bos.toByteArray();
                encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public SimpleDateFormat getDefaultDateFormat() {
        return DMY_TIME_SLASH_FORMAT;
    }
    public String formatTimestamp(long timestamp) {
        return getDefaultDateFormat().format(new Date(timestamp));
    }



    private void addArtist() {
        String name = etFN.getText().toString();
        String genre = etLN.getText().toString();
        String id = etNumber.getText().toString();
        inputValue = etNumber.getText().toString().trim();
        String phoneNum=etPhone.getText().toString().trim();
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bt = bos.toByteArray();
        encodeString = Base64.encodeToString(bt, Base64.DEFAULT);





        postRef = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");

        postRef.orderByChild("artistId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            Artist artist = new Artist(id, name, genre,dateIssue,dateExpire,encodeString,phoneNum);

                            //Saving the Artist
                            mDatabase.child(id).setValue(artist);

                            Toast.makeText(UpdatingDetailsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
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