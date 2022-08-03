package com.example.asgn;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView txtItem;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txtItem=itemView.findViewById(R.id.txtItem);
    }

}
