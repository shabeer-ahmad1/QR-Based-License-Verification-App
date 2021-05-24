package com.example.qrbasedlicense;
/* Intent intent=new Intent(Main2Activity.this,CaloriesRecommendationActivity.class);
                startActivity(intent);

                */


import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OptinonsLicenceImproved extends AppCompatActivity implements MyAdapter2.RecyclerViewClickListener {
    private List<MyList> myLists;
    MyAdapter2 adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_licence_improved);
        recyclerView=(RecyclerView)findViewById(R.id.rec3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myLists=new ArrayList<>();

        getdata();

    }

    private void getdata() {

        myLists.add(new MyList(R.drawable.driver,"Add Driver"));
        myLists.add(new MyList(R.drawable.viewandeditdrivers,"View/Edit Drivers"));
        myLists.add(new MyList(R.drawable.pol,"Add Police"));
        myLists.add(new MyList(R.drawable.addpol,"View Motorway Officers"));





        adapter=new MyAdapter2(myLists,getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick( int position) {

        if (position==0)
        {

            Intent intent=new Intent(OptinonsLicenceImproved.this,MainActivity.class);
            startActivity(intent);

        }else if (position==1){
            Intent intent=new Intent(OptinonsLicenceImproved.this,ShowingLicenceHolders.class);
            startActivity(intent);

        }
         else if (position==2)
        {
            Intent intent=new Intent(OptinonsLicenceImproved.this, SignupActivity.class);
            startActivity(intent);
        }
        else if (position==3)
        {
            Intent intent=new Intent(OptinonsLicenceImproved.this, ViewDriversOnLicenceAuthoritySide.class);
            startActivity(intent);
        }


    }
}
