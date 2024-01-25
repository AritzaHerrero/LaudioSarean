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
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
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

public class GuneInformazioa extends Activity {
    private Handler handler = new Handler();
    private Runnable runnable;
    private ImageView imgGunea;
    private ImageButton imgBtnHasi;
    private ImageButton imgBtnGelditu;
    private ImageButton imgBtnBerrebiarazi;
    private Button btnPlay;
    private TextView tituloa;
    private TextView informazioa;
    private ViewPager viewPager;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarAudio;
    private boolean isPlaying = false;
    private int audioarenPosizioa = 0;
    private int botoia= 0;
    private int audioResource = 0;
    private Gunea gunea;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gune_informazioa);


        botoia = getIntent().getIntExtra("aukeratutakoGunea", 0);
        imgGunea = findViewById(R.id.imgGunea);
        btnPlay = findViewById(R.id.btnPlay);
        tituloa= findViewById(R.id.txtGunea);
        informazioa= findViewById(R.id.txtHistoriaGunea);
        viewPager = findViewById(R.id.imageSlider);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        switch (botoia) {
            case 1:
                gunea = LoginActivity.db.guneaDao().getGuneaById(1);
                audioResource = getResources().getIdentifier(gunea.getAudioa(), "raw", getPackageName());
                imgGunea.setImageResource(getResources().getIdentifier(gunea.getIrudiak().split(",")[0], "drawable", getPackageName()));
                tituloa.setText(gunea.getIzena());
                informazioa.setText(gunea.getDeskribapena());
                int[] yermo = {getResources().getIdentifier(gunea.getIrudiak().split(",")[1], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[2], "drawable", getPackageName()), getResources().getIdentifier(gunea.getIrudiak().split(",")[3], "drawable", getPackageName()),  getResources().getIdentifier(gunea.getIrudiak().split(",")[4], "drawable", getPackageName())};
                ImageSliderAdapter adapterYermo = new ImageSliderAdapter(this, yermo);
                viewPager.setAdapter(adapterYermo);
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PuzzleActivity puzzleActivity = new PuzzleActivity();
                        erakutsiMezua(puzzleActivity);
                    }
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
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SopaLetra sopaLetra = new SopaLetra();
                        erakutsiMezua(sopaLetra);
                    }
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
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Laberintoa laberintoa = new Laberintoa();
                        erakutsiMezua(laberintoa);
                    }
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
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Galderak galderak = new Galderak();
                        erakutsiMezua(galderak);
                    }
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
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HutsuneakBete galderak = new HutsuneakBete();
                        erakutsiMezua(galderak);
                    }
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
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Anagrama anagrama = new Anagrama();
                        erakutsiMezua(anagrama);
                    }
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
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Kruzigrama kruzigrama = new Kruzigrama();
                        erakutsiMezua(kruzigrama);
                    }
                });
                break;
        }
        // MediaPlayer klasea abiaraziko dugu Audio fitxategia elkartuz
        mediaPlayer = MediaPlayer.create(this, audioResource);

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

    private void erakutsiMezua(Activity x) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(GuneInformazioa.this).inflate(R.layout.informazioa_dialog, null);
        Button prestBai = view.findViewById(R.id.prestBai);
        Button prestEz = view.findViewById(R.id.prestEz);

        AlertDialog.Builder builder = new AlertDialog.Builder(GuneInformazioa.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        prestEz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(GuneInformazioa.this, Arauak.class);
                startActivity(intent);
            }
        });

        prestBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(GuneInformazioa.this, x.getClass());
                startActivity(intent);
                finish();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
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