package com.example.qrbasedlicense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class policeadapter extends FirebaseRecyclerAdapter<PoliceProfile, policeadapter.myviewholder>
{    FirebaseRecyclerOptions<PoliceProfile> options;
    public policeadapter(@NonNull FirebaseRecyclerOptions<PoliceProfile> options) {
        super(options);
        this.options=options;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, final int position, @NonNull final PoliceProfile artist)
    {
       // String en=artist.getImgpath();
       // byte[] decodedString = Base64.decode(en, Base64.DEFAULT);
       // Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        holder.name.setText(artist.getName());
       holder.course.setText(artist.getDesignation());
       holder.email.setText(artist.getContactno());
     //  Glide.with(holder.img.getContext()).load(decodedByte).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewPoliceProfileonLicenceAuthoritySide.class);
                intent.putExtra("model", (Serializable) artist);
                //intent.putExtra("artname", artist.getArtistName());

                v.getContext().startActivity(intent);
            }
        });






    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);

       return new myviewholder(view);
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,course,email;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            course=(TextView)itemView.findViewById(R.id.coursetext);
            email=(TextView)itemView.findViewById(R.id.emailtext);






        }
    }
}
