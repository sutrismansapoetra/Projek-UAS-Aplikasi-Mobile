package com.example.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
CardView cvMenu, cvLogout, cvInfo, cvlaporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
cvMenu = (CardView) findViewById(R.id.cvMenu);
cvLogout = (CardView) findViewById(R.id.cvLogout);
cvInfo = (CardView) findViewById(R.id.cvInfo);
cvlaporan = (CardView) findViewById(R.id.cvLaporan);




cvMenu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Dashboard.this, list_buku.class);
        startActivity(i);

    }
});
cvLogout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Dashboard.this, MainActivity.class);
        startActivity(i);
    }
});
cvInfo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Dashboard.this, info_aplikasi.class);
        startActivity(i);
    }
});
        cvlaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, laporan_peminjaman.class);
                startActivity(i);
            }
        });

    }
}