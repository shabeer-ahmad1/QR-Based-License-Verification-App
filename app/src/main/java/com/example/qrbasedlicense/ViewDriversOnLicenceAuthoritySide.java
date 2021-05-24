package com.example.qrbasedlicense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewDriversOnLicenceAuthoritySide extends AppCompatActivity {
    RecyclerView recview;
    policeadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drivers_on_licence_authority_side);
        recview=(RecyclerView)findViewById(R.id.recviewDrivers);
        recview.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<PoliceProfile> options =
                new FirebaseRecyclerOptions.Builder<PoliceProfile>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("PoliceOfficers"), PoliceProfile.class)
                        .build();


        adapter=new policeadapter(options);
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