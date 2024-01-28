package com.talde3.laudiosarean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.talde3.laudiosarean.Jolasak.Anagrama.Anagrama;
import com.talde3.laudiosarean.Jolasak.HutsuneakBete.HutsuneakBete;
import com.talde3.laudiosarean.Jolasak.Kruzigrama.Kruzigrama;
import com.talde3.laudiosarean.Jolasak.Laberintoa.Laberintoa;
import com.talde3.laudiosarean.Jolasak.Arauak;
import com.talde3.laudiosarean.Jolasak.Puzlea.PuzzleActivity;
import com.talde3.laudiosarean.Jolasak.SopaLetra.SopaLetra;
import com.talde3.laudiosarean.Jolasak.TestGune4.Galderak;
import com.talde3.laudiosarean.Room.Entities.Gunea;

import java.io.IOException;
import java.util.Objects;

public class GuneInformazioa extends Activity {
    private TextView txtTiempoActual;
    private final Handler handler = new Handler();
    private ImageButton imgBtnHasi;
    private ImageButton imgBtnGelditu;
    private ViewPager viewPager;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarAudio;
    private boolean isPlaying = false;
    private int audioResource = 0;


    @SuppressLint({"MissingInflatedId", "DiscouragedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gune_informazioa);

        txtTiempoActual = findViewById(R.id.txtTiempoActual);
        TextView txtTiempoTotal = findViewById(R.id.txtTiempoTotal);


        int botoia = getIntent().getIntExtra("aukeratutakoGunea", 0);
        ImageView imgGunea = findViewById(R.id.imgGunea);
        Button btnPlay = findViewById(R.id.btnPlay);
        TextView tituloa = findViewById(R.id.txtGunea);
        TextView informazioa = findViewById(R.id.txtHistoriaGunea);
        viewPager = findViewById(R.id.imageSlider);

        btnPlay.setOnClickListener(v -> {

        });

        switch (botoia) {
            case 1:
                Gunea gunea = LoginActivity.db.guneaDao().getGuneaById(1);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] yermo = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName()),  getResources().getIdentifier(gunea.getIrudiak().split(",")[4], "drawable", getPackageName())};
                ImageSliderAdapter adapterYermo = new ImageSliderAdapter(this, yermo);
                viewPager.setAdapter(adapterYermo);
                btnPlay.setOnClickListener(v -> {
                    PuzzleActivity puzzleActivity = new PuzzleActivity();
                    erakutsiMezua(puzzleActivity);
                });
                break;
            case 2:
                gunea = LoginActivity.db.guneaDao().getGuneaById(2);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] burdinHesia = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[4], "drawable", getPackageName())};
                ImageSliderAdapter burdinHesiaAdapter = new ImageSliderAdapter(this, burdinHesia);
                viewPager.setAdapter(burdinHesiaAdapter);
                btnPlay.setOnClickListener(v -> {
                    SopaLetra sopaLetra = new SopaLetra();
                    erakutsiMezua(sopaLetra);
                });
                break;
            case 3:
                gunea = LoginActivity.db.guneaDao().getGuneaById(3);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] santaAgueda = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName())};
                ImageSliderAdapter santaAguedaAdapter = new ImageSliderAdapter(this, santaAgueda);
                viewPager.setAdapter(santaAguedaAdapter);
                btnPlay.setOnClickListener(v -> {
                    Laberintoa laberintoa = new Laberintoa();
                    erakutsiMezua(laberintoa);
                });
                break;
            case 4:
                gunea = LoginActivity.db.guneaDao().getGuneaById(4);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] katuxakoJaureguia = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[4], "drawable", getPackageName())};
                ImageSliderAdapter katuxakoJaureguiaAdapter = new ImageSliderAdapter(this, katuxakoJaureguia);
                viewPager.setAdapter(katuxakoJaureguiaAdapter);
                btnPlay.setOnClickListener(v -> {
                    Galderak galderak = new Galderak();
                    erakutsiMezua(galderak);
                });
                break;
            case 5:
                gunea = LoginActivity.db.guneaDao().getGuneaById(5);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] lamuzakosanpedroeliza = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[4], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[5], "drawable", getPackageName())};
                ImageSliderAdapter lamuzakosanpedroelizaAdapter = new ImageSliderAdapter(this, lamuzakosanpedroeliza);
                viewPager.setAdapter(lamuzakosanpedroelizaAdapter);
                btnPlay.setOnClickListener(v -> {
                    HutsuneakBete galderak = new HutsuneakBete();
                    erakutsiMezua(galderak);
                });
                break;
            case 6:
                gunea = LoginActivity.db.guneaDao().getGuneaById(6);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] lamuzajauregia = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName())};
                ImageSliderAdapter lamuzajauregiaAdapter = new ImageSliderAdapter(this, lamuzajauregia);
                viewPager.setAdapter(lamuzajauregiaAdapter);
                btnPlay.setOnClickListener(v -> {
                    Anagrama anagrama = new Anagrama();
                    erakutsiMezua(anagrama);
                });
                break;
            case 7:
                gunea = LoginActivity.db.guneaDao().getGuneaById(7);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] lezeagakosorgina = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName())};
                ImageSliderAdapter lezeagakosorginaAdapter = new ImageSliderAdapter(this, lezeagakosorgina);
                viewPager.setAdapter(lezeagakosorginaAdapter);
                btnPlay.setOnClickListener(v -> {
                    Kruzigrama kruzigrama = new Kruzigrama();
                    erakutsiMezua(kruzigrama);
                });
                break;
        }
        // MediaPlayer klasea abiaraziko dugu Audio fitxategia elkartuz
        mediaPlayer = MediaPlayer.create(this, audioResource);

        int tiempoTotal = mediaPlayer.getDuration();

        // Configurar el tiempo total en el TextView
        String tiempoTotalStr = obtenerFormatoTiempo(tiempoTotal);
        txtTiempoTotal.setText(tiempoTotalStr);

        ImageView imgPrevious = findViewById(R.id.imgPrevious);
        ImageView imgNext = findViewById(R.id.imgNext);
        imgPrevious.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current > 0) {
                viewPager.setCurrentItem(current - 1);
            }
        });
        imgNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            int total = Objects.requireNonNull(viewPager.getAdapter()).getCount();
            if (current < total - 1) {
                viewPager.setCurrentItem(current + 1);
            }
        });

        // Interfazearen hiru botoiak lortuko ditugu
        imgBtnHasi = findViewById(R.id.imgBtnHasi);
        imgBtnGelditu = findViewById(R.id.imgBtnGelditu);
        ImageButton imgBtnBerrebiarazi = findViewById(R.id.imgBtnBerrebiarazi);
        seekBarAudio= findViewById(R.id.seekBarAudio);

        imgBtnHasi.setVisibility(View.VISIBLE);
        imgBtnGelditu.setVisibility(View.GONE);



        //seekBar max jarri
        seekBarAudio.setMax(mediaPlayer.getDuration());

        // Actualizar la posición del SeekBar durante la reproducción del audio
        Runnable runnable = new Runnable() {
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
                    mediaPlayer.seekTo(progress);
                }

                // Actualizar el tiempo actual en el TextView
                String tiempoActualStr = obtenerFormatoTiempo(progress);
                txtTiempoActual.setText(tiempoActualStr);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Konfiguratu botoiaren klik erreprodukzioa hasteko edo eteteko
        imgBtnHasi.setOnClickListener(v -> {
            if (!isPlaying) {
                startAudio();
            }
        });

        // Konfiguratu botoiaren klik erreprodukzioa gelditzeko
        imgBtnGelditu.setOnClickListener(v -> stopAudio());

        // Konfiguratu botoiaren klik, erreprodukzioa hasieratik berriz hasteko
        imgBtnBerrebiarazi.setOnClickListener(v -> restartAudio());
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

    private void erakutsiMezua(Activity x) {
        View view = LayoutInflater.from(GuneInformazioa.this).inflate(R.layout.informazioa_dialog, null);
        Button prestBai = view.findViewById(R.id.prestBai);
        Button prestEz = view.findViewById(R.id.prestEz);

        AlertDialog.Builder builder = new AlertDialog.Builder(GuneInformazioa.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        prestEz.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(GuneInformazioa.this, Arauak.class);
            startActivity(intent);
        });

        prestBai.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(GuneInformazioa.this, x.getClass());
            startActivity(intent);
            finish();
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }

    @SuppressLint("DefaultLocale")
    private String obtenerFormatoTiempo(int milisegundos) {
        int segundos = (milisegundos / 1000) % 60;
        int minutos = (milisegundos / (1000 * 60)) % 60;

        return String.format("%02d:%02d", minutos, segundos);
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