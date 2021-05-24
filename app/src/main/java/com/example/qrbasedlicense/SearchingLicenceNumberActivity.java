package com.example.qrbasedlicense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchingLicenceNumberActivity extends AppCompatActivity {
    private TextView tvRet;
    private EditText etSearch;
    private DatabaseReference mDatabase;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_licence_number);
        tvRet=findViewById(R.id.tvRetreive);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");
        etSearch=findViewById(R.id.etSearch);
        btnSearch=findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewArtist();

            }
        });
    }
    private void ViewArtist() {
       String id=etSearch.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceHolders");

        mDatabase.orderByChild("artistId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            Artist artist = postSnapshot.getValue(Artist.class);
                            tvRet.setText(artist.getArtistName());
                        }}
                        else {

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