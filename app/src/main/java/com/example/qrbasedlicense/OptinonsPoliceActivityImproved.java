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

public class OptinonsPoliceActivityImproved extends AppCompatActivity implements MyAdapter2.RecyclerViewClickListener {
    private List<MyList> myLists;
    MyAdapter2 adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_police_improved);
        recyclerView=(RecyclerView)findViewById(R.id.rec4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myLists=new ArrayList<>();

        getdata();

    }

    private void getdata() {

        myLists.add(new MyList(R.drawable.pol,"Motorway Police"));
        myLists.add(new MyList(R.drawable.office,"Licence Office"));

        myLists.add(new MyList(R.drawable.driver,"Driver"));

        adapter=new MyAdapter2(myLists,getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick( int position) {

        if (position==0)
        {

            Intent intent=new Intent(OptinonsPoliceActivityImproved.this,LoginActivity.class);
            startActivity(intent);

        }else if (position==1){
            Intent intent=new Intent(OptinonsPoliceActivityImproved.this,OptinonsLicenceImproved.class);
            startActivity(intent);

        }
         else if (position==2)
        {
            Intent intent=new Intent(OptinonsPoliceActivityImproved.this, UsingLicenceNumbertogetOTP.class);
            startActivity(intent);
        }


    }
}
