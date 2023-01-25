package com.example.perpustakaan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CLV_Project extends ArrayAdapter<String> {
    //Declarasi variable
    private final Activity context;
    private ArrayList<String> vJudul_buku;
    private ArrayList<String> vDeskripsi;
    private ArrayList<String> vfoto;

    public CLV_Project(Activity context, ArrayList<String> Judul_buku, ArrayList<String> Deskripsi,ArrayList<String> foto)
    {
        super(context, R.layout.list_project,Judul_buku);
        this.context    = context;
        this.vJudul_buku     = Judul_buku;
        this.vDeskripsi      = Deskripsi;
        this.vfoto           = foto;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_project, null, true);

        //Declarasi komponen
        TextView judul_buku       = rowView.findViewById(R.id.judul_buku);
        TextView deskripsi        =  rowView.findViewById(R.id.deskripsi);
        ImageView foto     = rowView.findViewById(R.id.idIVGambar);

        //Set Parameter Value sesuai widget textview
        judul_buku.setText(vJudul_buku.get(position));
        deskripsi.setText(vDeskripsi.get(position));


        if (vfoto.get(position).equals(""))
        {
            Picasso.get().load("http://tkjbpnup.com/kelompok_12/perpustakaan/image/noimage.png").into(foto);
        }
        else
        {
            Picasso.get().load("http://tkjbpnup.com/kelompok_12/perpustakaan/image/"+vfoto.get(position)).into(foto);
        }

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }


}