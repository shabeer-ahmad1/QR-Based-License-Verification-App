package com.example.qrbasedlicense;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class chalanadapter extends FirebaseRecyclerAdapter<Chalan, chalanadapter.myviewholder>
{    FirebaseRecyclerOptions<Chalan> options;
    public chalanadapter(@NonNull FirebaseRecyclerOptions<Chalan> options) {
        super(options);
        this.options=options;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, final int position, @NonNull final Chalan artist)
    {
       // String en=artist.getImgpath();
       // byte[] decodedString = Base64.decode(en, Base64.DEFAULT);
      //  Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        holder.name.setText(artist.getChalanTitle());
       holder.course.setText(artist.getChalanAmount());
       holder.email.setText(formatTimestamp(artist.getChalanDate()));
      // Glide.with(holder.img.getContext()).load(decodedByte).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), PayingChalan.class);
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
    public SimpleDateFormat getDefaultDateFormat() {
        SimpleDateFormat DMY_TIME_SLASH_FORMAT = new SimpleDateFormat("d/M/yyyy", Locale.US);

        return DMY_TIME_SLASH_FORMAT;
    }
    public String formatTimestamp(long timestamp) {
        return getDefaultDateFormat().format(new Date(timestamp));
    }
}
