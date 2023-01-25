package com.example.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Dasboard_Admin extends AppCompatActivity {
    CardView cvMenu, cvLogout, cvlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_admin);
        cvMenu = (CardView) findViewById(R.id.cvMenu);
        cvLogout = (CardView) findViewById(R.id.cvLogout);
        cvlist = (CardView) findViewById(R.id.cvlist);





        cvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dasboard_Admin.this, Input_buku_admin.class);
                startActivity(i);

            }
        });
        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dasboard_Admin.this, MainActivity.class);
                startActivity(i);
            }
        });
        cvlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dasboard_Admin.this, list_buku_admin.class);
                startActivity(i);
            }
        });


    }
}