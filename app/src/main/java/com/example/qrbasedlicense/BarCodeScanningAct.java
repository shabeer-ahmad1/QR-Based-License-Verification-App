package com.example.qrbasedlicense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrbasedlicense.MainActivity;
import com.example.qrbasedlicense.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BarCodeScanningAct extends AppCompatActivity {
    ImageView imageView;
    Button button;
    Button btnScan,btnAddChalan;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 350 ;
    Bitmap bitmap ;

    TextView tv_qr_readTxt,tvDrFirstName,tvDrLastame,tvDrLicenceNumber,tvIssueDate,tvExpDate;
    String id;
    private DatabaseReference mDatabase;
    SimpleDateFormat DMY_TIME_SLASH_FORMAT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanning);
         DMY_TIME_SLASH_FORMAT = new SimpleDateFormat("d/M/yyyy", Locale.US);


        imageView = (ImageView)findViewById(R.id.imageView);
        btnScan = (Button)findViewById(R.id.btnScan);
        tv_qr_readTxt = (TextView) findViewById(R.id.tv_qr_readTxt);
        tvDrFirstName = (TextView) findViewById(R.id.tvDriverFirstname);
        tvDrLastame = (TextView) findViewById(R.id.tvDriverLastname);
        tvDrLicenceNumber = (TextView) findViewById(R.id.tvDriverLicenceNumber);
        tvIssueDate = (TextView) findViewById(R.id.tvissueDate);
        tvExpDate = (TextView) findViewById(R.id.tvExprDate);
        btnAddChalan=findViewById(R.id.btnAddChalan);






        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");





        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(BarCodeScanningAct.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });
    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

                tv_qr_readTxt.setText("Checking If Driver Exists...");
                id=result.getContents().toString();

                ViewArtist();

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void ViewArtist() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");

        mDatabase.orderByChild("artistId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            Artist artist = postSnapshot.getValue(Artist.class);
                            tv_qr_readTxt.setText(artist.getArtistName());
                            tvDrFirstName.setText(artist.getArtistName());
                            tvDrLastame.setText(artist.getArtistGenre());
                            tvDrLicenceNumber.setText(artist.getArtistId());
                            String beginDate=formatTimestamp(artist.getBeginValidity());
                            String expDate=formatTimestamp(artist.getEndValidity());



                            tvIssueDate.setText(String.valueOf(beginDate));
                                tvExpDate.setText(String.valueOf(expDate));


                                String en=artist.getImgpath();
                                byte[] decodedString = Base64.decode(en, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                imageView.setImageBitmap(decodedByte);

                                btnAddChalan.setVisibility(View.VISIBLE);
                                btnAddChalan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(BarCodeScanningAct.this,AddChalanActivity.class);
                                        intent.putExtra("LicenceNumber",artist.getArtistId());
                                        startActivity(intent);
                                    }
                                });

                        }}
                        else {
                            tv_qr_readTxt.setText("Wrong Qr.");
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
    public SimpleDateFormat getDefaultDateFormat() {
        return DMY_TIME_SLASH_FORMAT;
    }
    public String formatTimestamp(long timestamp) {
        return getDefaultDateFormat().format(new Date(timestamp));
    }
    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(BarCodeScanningAct.this,LoginActivity.class);
        startActivity(intent);
    }
}