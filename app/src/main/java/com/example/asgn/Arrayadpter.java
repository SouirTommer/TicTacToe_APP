package com.example.asgn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Arrayadpter extends RecyclerView.Adapter<com.example.asgn.MyViewHolder>{
    @NonNull
    Context context;
    String [] mData;


    public Arrayadpter(Context context, String[] mData){
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list,parent, false);
        com.example.asgn.MyViewHolder myViewHolder = new com.example.asgn.MyViewHolder(view);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtItem.setText(mData[position]);

    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}
