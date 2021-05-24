package com.example.qrbasedlicense;
/* Intent intent=new Intent(Main2Activity.this,CaloriesRecommendationActivity.class);
                startActivity(intent);

                */


import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main2Activity extends AppCompatActivity implements MyAdapter2.RecyclerViewClickListener {
    private List<MyList> myLists;
    MyAdapter2 adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2main);
        recyclerView=(RecyclerView)findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myLists=new ArrayList<>();

        getdata();

    }

    private void getdata() {

        myLists.add(new MyList(R.drawable.scqr,"Scan Licence QR"));
        myLists.add(new MyList(R.drawable.monog,"Scan Monogram"));

        myLists.add(new MyList(R.drawable.pr,"Profile"));
        myLists.add(new MyList(R.drawable.so,"Logout"));

        adapter=new MyAdapter2(myLists,getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick( int position) {
        if (position==0)
        {

            Intent intent=new Intent(Main2Activity.this,BarCodeScanningAct.class);
            startActivity(intent);

        }else if (position==1){
            Intent intent=new Intent(Main2Activity.this,MonogramRecognizerActivity.class);
            startActivity(intent);

        }
         else if (position==2)
        {
            Intent intent=new Intent(Main2Activity.this, ViewPoliceProfile.class);
            startActivity(intent);
        }

    else if (position==3){
            FirebaseAuth.getInstance().signOut();


            Intent intent1=new Intent(Main2Activity.this,OptinonsImprovedInterface.class);
            startActivity(intent1);
            finish();
        }
    }
}
