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

public class Login_Admin extends AppCompatActivity {

    Button btnlg;
    EditText username, password;
    String email, pass;
    String URL ="http://tkjbpnup.com/kelompok_12/perpustakaan/loginAdmin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        btnlg = findViewById(R.id.login);
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
                                Intent intent = new Intent(Login_Admin.this, Dasboard_Admin.class);
                                startActivity(intent);
                                finish();
                            } else if (response.equals("gagal")) {
                                Toast.makeText(Login_Admin.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login_Admin.this, "Cek jaringan", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Login_Admin.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }



}
