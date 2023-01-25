package com.example.perpustakaan;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Peminjaman_buku extends AppCompatActivity {

    com.rengwuxian.materialedittext.MaterialEditText ETNim,ETnama, ETAtanggal_peminjaman,ETHtanggal_pengembalian,ETjudul_buku;
    String nim,nama,tanggal_peminjaman,tanggal_pengembalian,judul_buku;
    Button BTNSubmit;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman_buku);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.logo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ETNim       = findViewById(R.id.ETNim);
        ETnama      = findViewById(R.id.ETnama);
        ETAtanggal_peminjaman  = findViewById(R.id.ETAtanggal_peminjaman);
        ETHtanggal_pengembalian   = findViewById(R.id.ETHtanggal_pengembalian);
        ETjudul_buku   = findViewById(R.id.ETJudulBuku);
        BTNSubmit   = findViewById(R.id.BTNSubmit);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        //Tanggal
        ETAtanggal_peminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogPinjam();
            }
        });

        ETHtanggal_pengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogKembali();
            }
        });

//Batas Tanggal

        progressDialog = new ProgressDialog(this);

        BTNSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();


                nim     = ETNim.getText().toString();
                nama    = ETnama.getText().toString();
                tanggal_peminjaman = ETAtanggal_peminjaman.getText().toString();
                tanggal_pengembalian   = ETHtanggal_pengembalian.getText().toString();
                judul_buku   = ETjudul_buku.getText().toString();


                kirimData();
                }
        });

    }
//Jangan Di Ubah
    private void showDateDialogPinjam() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, dayOfMonth);
            ETAtanggal_peminjaman.setText(dateFormatter.format(newDate.getTime()));

            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDateDialogKembali() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                ETHtanggal_pengembalian.setText(dateFormatter.format(newDate.getTime()));

            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
//Sampai sini

    void validasiData(){
        if(nim.equals("") ||nama.equals("") || tanggal_peminjaman.equals("") || tanggal_pengembalian.equals("") || judul_buku.equals("")){
            progressDialog.dismiss();
            Toast.makeText(Peminjaman_buku.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }
    void kirimData(){
        AndroidNetworking.post("http://tkjbpnup.com/kelompok_12/perpustakaan/peminjaman.php")
                .addBodyParameter("nim",""+nim)
                .addBodyParameter("nama",""+nama)
                .addBodyParameter("tanggal_peminjaman",""+tanggal_peminjaman)
                .addBodyParameter("tanggal_pengembalian",""+tanggal_pengembalian)
                .addBodyParameter("judul_buku",""+judul_buku)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(Peminjaman_buku.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(Peminjaman_buku.this)
                                        .setMessage("Berhasil Menambahkan Data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Peminjaman_buku.this, laporan_peminjaman.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(Peminjaman_buku.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Peminjaman_buku.this, laporan_peminjaman.class);
                                                startActivity(i);

                                                Log.e("gagal",""+response);
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }



}