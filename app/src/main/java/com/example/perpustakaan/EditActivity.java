package com.example.perpustakaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    com.rengwuxian.materialedittext.MaterialEditText ETNim,ETNama, ETtanggal_peminjaman, ETtanggal_pengembalian, ETjudul_buku;
    String nim,nama,tanggal_peminjaman,tanggal_pengembalian,judul_buku;
    Button BTNUpdate;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Intent, get data from main
        nim     = getIntent().getStringExtra("nim");
        nama = getIntent().getStringExtra("nama");
        tanggal_peminjaman = getIntent().getStringExtra("tanggal_peminjaman");
        tanggal_pengembalian = getIntent().getStringExtra("tanggal_pengembalian");
        judul_buku = getIntent().getStringExtra("judul_buku");


        //Toast.makeText(edit_mahasiswa.this,nim,Toast.LENGTH_LONG).show();


        ETNim       = findViewById(R.id.ETNim);
        ETNama      = findViewById(R.id.ETnama);
        ETtanggal_peminjaman   = findViewById(R.id.ETAtanggal_peminjaman);
        ETtanggal_pengembalian     = findViewById(R.id.ETHtanggal_pengembalian);
        ETjudul_buku     = findViewById(R.id.ETJudulBuku);
        BTNUpdate  = findViewById(R.id.BTNupdate);
        //Toast.makeText(edit_mahasiswa.this,photo,Toast.LENGTH_LONG).show();
        // BTNImage.setImageURI(Uri.parse("http://tkjbpnup.com/kelompok_12/image/"+photo));
        //Picasso.get().load("http://tkjbpnup.com/kelompok_12/image/"+photo).into(BTNImage);

        ETNim.setText(nim);
        ETNama.setText(nama);
        ETtanggal_peminjaman.setText(tanggal_peminjaman);
        ETtanggal_pengembalian.setText(tanggal_pengembalian);
        ETjudul_buku.setText(judul_buku);

        progressDialog = new ProgressDialog(this);


        BTNUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Updating Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                nim     = ETNim.getText().toString();
                nama    = ETNama.getText().toString();
                judul_buku = ETjudul_buku.getText().toString();
                tanggal_peminjaman   = ETtanggal_peminjaman.getText().toString();
                tanggal_pengembalian   = ETtanggal_pengembalian.getText().toString();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validatingData();
                    }
                },1000);
            }
        });

    }

    void validatingData(){
        if(nim.equals("") || nama.equals("") || tanggal_peminjaman.equals("") || tanggal_pengembalian.equals("") || judul_buku.equals("")){
            progressDialog.dismiss();
            Toast.makeText(EditActivity.this, "Check your input!", Toast.LENGTH_SHORT).show();
        }else {
            updateData();
        }
    }

    // taking image
    // get encode image to minimize image
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void updateData() {
        AndroidNetworking.post("http://tkjbpnup.com/kelompok_12/perpustakaan/updatepeminjaman.php")
                .addBodyParameter("nama", "" + nama)
                .addBodyParameter("nim", "" + nim)
                .addBodyParameter("tanggal_peminjaman", "" + tanggal_peminjaman)
                .addBodyParameter("tanggal_pengembalian", "" + tanggal_pengembalian)
                .addBodyParameter("judul_buku", "" + judul_buku)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekUpdate", "" + response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(EditActivity.this, "" + pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status", "" + status);
                            if (status) {
                                new AlertDialog.Builder(EditActivity.this)
                                        .setMessage("Data berhasil di update !")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Intent i = getIntent();
                                                //setResult(RESULT_OK,i);
                                                //add_mahasiswa.this.finish();
                                            }
                                        })
                                        .show();
                            } else {
                                new AlertDialog.Builder(EditActivity.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Intent i = getIntent();
                                                //setResult(RESULT_CANCELED,i);
                                                //add_mahasiswa.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Tidak dapat memperbarui data Anda", "" + anError.getErrorBody());
                    }
                });
    }

    }

