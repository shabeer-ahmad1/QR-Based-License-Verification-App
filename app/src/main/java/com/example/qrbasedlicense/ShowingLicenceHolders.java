package com.example.qrbasedlicense;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowingLicenceHolders extends AppCompatActivity
{
   RecyclerView recview;
   myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_licence_holders);

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        FirebaseRecyclerOptions<Artist> options =
                new FirebaseRecyclerOptions.Builder<Artist>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("LicenceHolders"), Artist.class)
                        .build();


        adapter=new myadapter(options);
        recview.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}