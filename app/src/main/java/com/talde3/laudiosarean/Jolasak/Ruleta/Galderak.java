package com.talde3.laudiosarean.Jolasak.Ruleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.talde3.laudiosarean.R;

public class Galderak extends AppCompatActivity {

    TextView galderak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galderak2);

        Intent intent = getIntent();
        String testua = intent.getStringExtra("Gunea");
        galderak = findViewById(R.id.tvGalderak);

        galderak.setText(testua);
    }
}