package com.example.qrbasedlicense;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowingChalans extends AppCompatActivity
{
   RecyclerView recview;
   chalanadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_chalans);

        recview=(RecyclerView)findViewById(R.id.recview2);
        recview.setLayoutManager(new LinearLayoutManager(this));
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent intent=getIntent();
        String LicenceNumber=intent.getStringExtra("mobile");


        FirebaseRecyclerOptions<Chalan> options =
                new FirebaseRecyclerOptions.Builder<Chalan>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Chalan").child(LicenceNumber), Chalan.class)
                        .build();


        adapter=new chalanadapter(options);
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