package com.example.perpustakaan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnlg, btnAd ;
    EditText username, password;
    TextView btnrg;
    String email, pass;
    String URL ="http://tkjbpnup.com/kelompok_12/perpustakaan/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlg = findViewById(R.id.login);
        btnrg = findViewById(R.id.register);
        btnAd = findViewById(R.id.Admin);
        username = findViewById(R.id.user);
        password = findViewById(R.id.pass);


        btnlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = username.getText().toString().trim();
                pass = password.getText().toString().trim();

                if(!email.equals("")&& !pass.equals("")){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("bisa")) {
                                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            } else if (response.equals("gagal")) {
                                Toast.makeText(MainActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Cek jaringan", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String,String> getParams() throws AuthFailureError{
                            Map<String, String> data = new HashMap<>();
                            data.put("username",email);
                            data.put("password", pass);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(MainActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        btnAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login_Admin.class);
                startActivity(intent);
                finish();
            }
        });





    }



}
