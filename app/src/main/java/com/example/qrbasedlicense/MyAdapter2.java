package com.example.qrbasedlicense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private List<MyList>myLists;
    private Context context;
    private RecyclerViewClickListener mrecyclerViewClickListener;


    public MyAdapter2(List<MyList> myLists, Context context, RecyclerViewClickListener recyclerViewClickListener) {
        this.myLists = myLists;
        this.context = context;
        this.mrecyclerViewClickListener=recyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newfil1,parent,false);
        return new ViewHolder(view,mrecyclerViewClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyList myList=myLists.get(position);
        holder.textView.setText(myList.getDesc());
        holder.img.setImageDrawable(context.getResources().getDrawable(myList.getImage()));

    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView textView;
         RecyclerViewClickListener recyclerViewClickListener;
        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.image);
            textView=(TextView)itemView.findViewById(R.id.desc);
            this.recyclerViewClickListener=recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
recyclerViewClickListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener {

        void onNoteClick(int position);
    }
}
