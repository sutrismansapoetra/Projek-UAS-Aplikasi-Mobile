package com.example.perpustakaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class Register extends AppCompatActivity {

    EditText ETuser, ETpass, ETjurusan, ETemail, ETnim;
    Button BTNsave;
    String user,pass,jurusan,email,nim;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ETuser = findViewById(R.id.user);
        ETpass = findViewById(R.id.pass);
        ETjurusan = findViewById(R.id.jurusan);
        ETemail = findViewById(R.id.email);
        ETnim = findViewById(R.id.nim);
        BTNsave = findViewById(R.id.regis);
        progressDialog = new ProgressDialog(this);

        BTNsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                user      = ETuser.getText().toString();
                pass      = ETpass.getText().toString();
                jurusan   = ETjurusan.getText().toString();
                email     = ETemail.getText().toString();
                nim       = ETnim.getText().toString();




                validasiData();
            }
        });

    }

    void validasiData(){
        if(user.equals("") && pass.equals("")){
            progressDialog.dismiss();
            Toast.makeText(Register.this, "Periksa Kembali Data Yang Anda Masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("http://tkjbpnup.com/kelompok_12/perpustakaan/register.php")
                .addBodyParameter("username",""+user)
                .addBodyParameter("password",""+pass)
                .addBodyParameter("jurusan",""+jurusan)
                .addBodyParameter("email",""+email)
                .addBodyParameter("nim",""+nim)
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
                            String pesan = response.getString("result");
                            Toast.makeText(Register.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(Register.this)
                                        .setMessage("Berhasil Menambahkan Data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent page;
                                                page = new Intent(Register.this,MainActivity.class);
                                                startActivity(page);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(Register.this)
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