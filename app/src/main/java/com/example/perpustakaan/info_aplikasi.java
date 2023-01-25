package com.example.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class info_aplikasi extends AppCompatActivity {
    private Button kembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_aplikasi);
        kembali =(Button) findViewById(R.id.kembaliD);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(info_aplikasi.this, Dashboard.class);
                startActivity(i);
            }
        });
    }

}