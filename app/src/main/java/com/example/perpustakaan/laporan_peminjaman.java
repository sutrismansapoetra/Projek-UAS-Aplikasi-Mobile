package com.example.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class laporan_peminjaman extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_nama,array_nim,array_tanggal_peminjaman,array_tanggal_pengembalian,array_judul_buku;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_peminjaman);

        listProses = findViewById(R.id.LV);
        srl_main    = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });

        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        scrollRefresh();
    }

    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Data.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {getData(); }
        },2000);
    }

    void initializeArray() {
        array_nama = new ArrayList<String>();
        array_nim = new ArrayList<String>();
        array_tanggal_peminjaman = new ArrayList<String>();
        array_tanggal_pengembalian = new ArrayList<String>();
        array_judul_buku = new ArrayList<String>();

        // clear ini untuk menginilisasi array
        array_nama.clear();
        array_nim.clear();
        array_tanggal_peminjaman.clear();
        array_tanggal_pengembalian.clear();
        array_judul_buku.clear();
    }


    public void getData(){
        initializeArray();
        AndroidNetworking.get("http://tkjbpnup.com/kelompok_12/perpustakaan/getdata_peminjaman.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try{
                            Boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_nama.add(jo.getString("nama"));
                                    array_nim.add(jo.getString("nim"));
                                    array_tanggal_peminjaman.add(jo.getString("tanggal_peminjaman"));
                                    array_tanggal_pengembalian.add(jo.getString("tanggal_pengembalian"));
                                    array_judul_buku.add(jo.getString("judul_buku"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final Adapter adapter = new Adapter(laporan_peminjaman.this,array_nama,array_nim,array_tanggal_peminjaman,array_tanggal_pengembalian,array_judul_buku);
                                //Set adapter to list
                                listProses.setAdapter(adapter);
                                //edit and delete
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik",""+array_nama.get(position));
                                        Toast.makeText(laporan_peminjaman.this, array_nama.get(position), Toast.LENGTH_SHORT).show();

//                                        Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
//                                        DataActivity.class dan membawa/mengirim data-data hasil query dari server.
                                        Intent i = new Intent(laporan_peminjaman.this, EditActivity.class);
                                        i.putExtra("nama",array_nama.get(position));
                                        i.putExtra("nim",array_nim.get(position));
                                        i.putExtra("tanggal_peminjaman",array_tanggal_peminjaman.get(position));
                                        i.putExtra("tanggal_pengembalian",array_tanggal_pengembalian.get(position));
                                        i.putExtra("judul_buku",array_judul_buku.get(position));

                                        startActivity(i);
                                    }
                                });


                            }else{
                                Toast.makeText(laporan_peminjaman.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}