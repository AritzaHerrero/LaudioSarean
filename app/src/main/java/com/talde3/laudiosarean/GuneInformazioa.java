package com.talde3.laudiosarean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import java.io.IOException;

public class GuneInformazioa extends Activity {
    private Handler handler = new Handler();
    private Runnable runnable;
    private ImageView imgGunea;
    private ImageButton imgBtnHasi;
    private ImageButton imgBtnGelditu;
    private ImageButton imgBtnBerrebiarazi;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarAudio;
    private boolean isPlaying = false;
    private int audioarenPosizioa = 0;
    private int botoia= 0;
    private int audioResource = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gune_informazioa);

        botoia = getIntent().getIntExtra("aukeratutakoGunea", 0);
        imgGunea = findViewById(R.id.imgGunea);

        switch (botoia) {
            case 1:
                audioResource = R.raw.yermokoandremariarensantutegia;
                imgGunea.setImageResource(R.drawable.yermoko_andre_mariaren_santutegia);
                break;
            case 2:
                audioResource = R.raw.burdinhesia;
                imgGunea.setImageResource(R.drawable.burdin_hesia);
                break;
            case 3:
                audioResource = R.raw.santaaguedakoermita;
                imgGunea.setImageResource(R.drawable.santa_aguedako_ermita);
                break;
            case 4:
                audioResource = R.raw.katuxakojauregia;
                imgGunea.setImageResource(R.drawable.katuxako_jauregia);
                break;
            case 5:
                audioResource = R.raw.lamuzakosanpedroeliza;
                imgGunea.setImageResource(R.drawable.lamuzako_san_pedro_eliza);
                break;
            case 6:
                audioResource = R.raw.lamuzajauregia;
                imgGunea.setImageResource(R.drawable.lamuza_jauregia);
                break;
            case 7:
                audioResource = R.raw.lezeagakosorgina;
                imgGunea.setImageResource(R.drawable.lezeagako_sorgina);
                break;
        }
        // MediaPlayer klasea abiaraziko dugu Audio fitxategia elkartuz
        mediaPlayer = MediaPlayer.create(this, audioResource);

        ViewPager viewPager = findViewById(R.id.imageSlider);
        int[] images = {R.drawable.katuxako_jauregia, R.drawable.lamuza_jauregia, R.drawable.lezeagako_sorgina, R.drawable.lamuzako_san_pedro_eliza};
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(adapter);

        ImageView imgPrevious = findViewById(R.id.imgPrevious);
        ImageView imgNext = findViewById(R.id.imgNext);
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem();
                if (current > 0) {
                    viewPager.setCurrentItem(current - 1);
                }
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem();
                int total = viewPager.getAdapter().getCount();
                if (current < total - 1) {
                    viewPager.setCurrentItem(current + 1);
                }
            }
        });

        // Interfazearen hiru botoiak lortuko ditugu
        imgBtnHasi = findViewById(R.id.imgBtnHasi);
        imgBtnGelditu = findViewById(R.id.imgBtnGelditu);
        imgBtnBerrebiarazi = findViewById(R.id.imgBtnBerrebiarazi);
        seekBarAudio= findViewById(R.id.seekBarAudio);

        imgBtnHasi.setVisibility(View.VISIBLE);
        imgBtnGelditu.setVisibility(View.GONE);



        //seekBar max jarri
        seekBarAudio.setMax(mediaPlayer.getDuration());

        // Actualizar la posición del SeekBar durante la reproducción del audio
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                    seekBarAudio.setProgress(mCurrentPosition);
                }
                handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable, 100);

        seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress); // Aldatu audioaren posizioa SeekBar-en aurrerapenera
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Konfiguratu botoiaren klik erreprodukzioa hasteko edo eteteko
        imgBtnHasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    startAudio();
                }
            }
        });

        // Konfiguratu botoiaren klik erreprodukzioa gelditzeko
        imgBtnGelditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        // Konfiguratu botoiaren klik, erreprodukzioa hasieratik berriz hasteko
        imgBtnBerrebiarazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartAudio();
            }
        });
    }

    // Audioa erreproduzitzen hasteko edo berriz hasteko metodoa
    private void startAudio() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(seekBarAudio.getProgress());
            mediaPlayer.start();
            isPlaying = true;
            imgBtnHasi.setVisibility(View.GONE);
            imgBtnGelditu.setVisibility(View.VISIBLE);
        } else {
            isPlaying = false;
            mediaPlayer.pause();
            imgBtnHasi.setVisibility(View.VISIBLE);
            imgBtnGelditu.setVisibility(View.GONE);
        }
    }

    // Audio-erreprodukzioa gelditzeko metodoa
    private void stopAudio() {
        if (mediaPlayer.isPlaying()) {
            audioarenPosizioa = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
            isPlaying = false;
            imgBtnHasi.setVisibility(View.VISIBLE);
            imgBtnGelditu.setVisibility(View.GONE);
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
        imgBtnHasi.setVisibility(View.GONE);
        imgBtnGelditu.setVisibility(View.VISIBLE);
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