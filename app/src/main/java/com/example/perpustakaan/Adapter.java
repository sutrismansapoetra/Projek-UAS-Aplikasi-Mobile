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

public class Adapter extends ArrayAdapter<String> {

    //Declarasi variable
    private final Activity context;
    private ArrayList<String> nama;
    private ArrayList<String> nim;
    private ArrayList<String> tanggal_peminjaman;
    private ArrayList<String> tanggal_pengembalian;
    private ArrayList<String> judul_buku;

    public Adapter(Activity context, ArrayList<String> Nama, ArrayList<String> Nim, ArrayList<String> Tanggal_Peminjaman,ArrayList<String> Tanggal_Pengembalian,ArrayList<String> Judul_Buku)
    {
        super(context, R.layout.list_peminjaman_buku,Nama);
        this.context    = context;
        this.nama      = Nama;
        this.nim       = Nim;
        this.tanggal_peminjaman   = Tanggal_Peminjaman;
        this.tanggal_pengembalian = Tanggal_Pengembalian;
        this.judul_buku = Judul_Buku;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_peminjaman_buku, null, true);

        //Declarasi komponen
        TextView vnama                   = rowView.findViewById(R.id.nama);
        TextView vtanggal_pengembalian   =  rowView.findViewById(R.id.tanggal_pengembalian);

        //Set Parameter Value sesuai widget textview
        vnama.setText(nama.get(position));
        vtanggal_pengembalian.setText(tanggal_pengembalian.get(position));


        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }

}
