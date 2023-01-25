package com.example.perpustakaan;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder {

    ImageView img;
    TextView text1, text2;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        img = (ImageView) itemView.findViewById(R.id.img1);
        text1 = (TextView) itemView.findViewById(R.id.Text1);
        text2 = (TextView) itemView.findViewById(R.id.Text2);
    }
}
