package com.example.qrbasedlicense;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase,postRef;
    private Button btnAdd,save;
    private EditText etFn,etLn,etLicNum,etIssueDate,etExpireDate,etphoneNum;
     private ImageView qrImage;
  private   Bitmap bitmap;
    private   String savePath;
    private long dateIssue;
    private long dateExpire;
    Bitmap bmp ;
    ByteArrayOutputStream bos;
    byte[] bt ;
    String encodeString;
    private ImageView DriverImage;
    private Uri filePath;


    String inputValue,phoneNumber;

    private QRGEncoder qrgEncoder;
    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QRCode/";
        qrImage = (ImageView) findViewById(R.id.qr_image1);
        btnAdd=findViewById(R.id.btnAdd);
        save = (Button) findViewById(R.id.save_barcode1);



        DriverImage=findViewById(R.id.imgViewDriver);
        etFn=findViewById(R.id.FirstName);
        etLn=findViewById(R.id.LastName);
        etLicNum=findViewById(R.id.LicenceNumber);
        etIssueDate=findViewById(R.id.etDateBegin);
        etExpireDate=findViewById(R.id.etDateEnd);
        etphoneNum=findViewById(R.id.phoneNumber);

        DriverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        etIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(MainActivity.this, (view1, year, month, dayOfMonth) -> {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, year);
                            calendar1.set(Calendar.MONTH, month);
                            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                           etIssueDate.setText(formatDayMonthYear(calendar1.getTime()));
                           dateIssue= calendar1.getTimeInMillis();
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        etExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(MainActivity.this, (view1, year, month, dayOfMonth) -> {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, year);
                            calendar1.set(Calendar.MONTH, month);
                            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            etExpireDate.setText(formatDayMonthYear(calendar1.getTime()));
                            dateExpire= calendar1.getTimeInMillis();
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();


            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean save;
                String result;
                try {
                    save = QRGSaver.save(savePath, etLicNum.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                    result = save ? "Image Saved" : "Image Not Saved";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput())
                addArtist();
            }
        });
    }

    private void addArtist() {
        String name = etFn.getText().toString();
        String genre = etLn.getText().toString();
        String id = etLicNum.getText().toString();
        inputValue = etLicNum.getText().toString().trim();
        phoneNumber = etphoneNum.getText().toString().trim();


        postRef = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");

        postRef.orderByChild("artistId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            Toast.makeText(MainActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                        } else {

                            Artist artist = new Artist(id, name, genre,dateIssue,dateExpire,encodeString,phoneNumber);

                            //Saving the Artist
                            mDatabase.child(id).setValue(artist);
                            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                            Display display = manager.getDefaultDisplay();
                            Point point = new Point();
                            display.getSize(point);
                            int width = point.x;
                            int height = point.y;
                            int smallerDimension = width < height ? width : height;
                            smallerDimension = smallerDimension * 3 / 4;

                            qrgEncoder = new QRGEncoder(
                                    inputValue, null,
                                    QRGContents.Type.TEXT,
                                    smallerDimension);
                            try {
                                bitmap = qrgEncoder.encodeAsBitmap();
                                qrImage.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                //Log.v(TAG, e.toString());

                            }
                            save.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
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
    public static String formatDayMonthYear(Date date) {
       SimpleDateFormat FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        return FORMAT_DAY_MONTH_YEAR.format(date);
    }


    private boolean validateInput() {


        if (DriverImage.getDrawable()==null){
            Toast.makeText(this, "Please Choose an image", Toast.LENGTH_LONG).show();
            return false;
        }

        if (etFn.getText().toString().isEmpty()) {
etFn.setError("Please Fill in Your First Name");
        return false;
        }
        if (etLn.getText().toString().isEmpty()) {
            etLn.setError("Please Fill in Your Last Name");
            return false;
        }
        if (etLicNum.getText().toString().isEmpty()) {
            etLicNum.setError("Please Fill in Your Licence Number");
            return false;
        }
        if (etIssueDate.getText().toString().isEmpty()) {
            etIssueDate.setError("Please Choose a date");
            return false;
        }
        if (etExpireDate.getText().toString().isEmpty()) {
        etExpireDate.setError("Please Choose a date ");
        return false;
        }
        if (etphoneNum.getText().toString().isEmpty()) {
            etLn.setError("Please Fill in Your Phone Number");
            return false;
        }



        return true;
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
                 DriverImage.setImageBitmap(bmp);
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




}