package com.talde3.laudiosarean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class GuneInformazioa extends Activity {
    private Button btnHasi;
    private Button btnGelditu;
    private Button btnBerezarri;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gune_informazioa);


        // MediaPlayer klasea abiaraziko dugu Audio fitxategia elkartuz
        mediaPlayer = MediaPlayer.create(this, R.raw.audioa4);

        // Interfazearen hiru botoiak lortuko ditugu
        btnHasi = findViewById(R.id.btnHasi);
        btnGelditu = findViewById(R.id.btnGelditu);
        btnBerezarri = findViewById(R.id.btnBerrabiarazi);

        // Konfiguratu botoiaren klik erreprodukzioa hasteko edo eteteko
        btnHasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    startAudio();
                } else {
                    pauseAudio();
                }
            }
        });

        // Konfiguratu botoiaren klik erreprodukzioa gelditzeko
        btnGelditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        // Konfiguratu botoiaren klik, erreprodukzioa hasieratik berriz hasteko
        btnBerezarri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartAudio();
            }
        });
    }

    // Audioa erreproduzitzen hasteko edo berriz hasteko metodoa
    private void startAudio() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            btnHasi.setText("Gelditu");
        }
    }

    // Audio-erreprodukzioa eteteko metodoa
    private void pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            btnHasi.setText("Jarraitu");
        }
    }

    // Audio-erreprodukzioa gelditzeko metodoa
    private void stopAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            isPlaying = false;
            btnHasi.setText("Erreproduzitu");
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Erreprodukzioa hasieratik berriz hasteko metodoa
    private void restartAudio() {
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        isPlaying = true;
        btnHasi.setText("Gelditu");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}