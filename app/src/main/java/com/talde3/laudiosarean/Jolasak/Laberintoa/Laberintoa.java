package com.talde3.laudiosarean.Jolasak.Laberintoa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.talde3.laudiosarean.GuneInformazioa;
import com.talde3.laudiosarean.GuneakFragment;
import com.talde3.laudiosarean.MainActivity;
import com.talde3.laudiosarean.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Laberintoa extends AppCompatActivity {

    private int hasieraPuntuaX = 1;
    private int hasieraPuntuaY = 1;
    private int jugadorXAnterior = 1;
    private int jugadorYAnterior = 1;
    private int jugadorX;
    private int jugadorY;
    private int salidaX = 23;
    private int salidaY = 23;
    private Button buttonDerecha;
    private Button buttonIzquierda;
    private Button buttonArriba;
    private Button buttonAbajo;
    private TextView txtKronometroa ;
    private TextView txtPuntuazioa ;
    private Random random = new Random();
    private int alturaLaberinto = 25;
    private int anchoLaberinto = 25;
    private int[][] laberinto = new int[anchoLaberinto][alturaLaberinto];
    private GridLayout gridLayout;
    private ImageButton[][] botones;
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private Handler koronoHandler = new Handler();

    //Metodo honek segunduro egiten da, denbora kalkulatzeko eta puntuazizoa unean ikusteko balio du
    private Runnable kronoRunnable = new Runnable() {
        @Override
        public void run() {
            long milisegundoak = System.currentTimeMillis() - hasierakoDenbora;
            int segundoak = (int) (milisegundoak / 1000);
            int minutuak = segundoak / 60;
            segundoak = segundoak % 60;

             txtKronometroa = findViewById(R.id.txtKronometroa);
             txtPuntuazioa = findViewById(R.id.txtPuntuazioa);

            String time = String.format("%02d:%02d", minutuak, segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo transcurrido
            unekoPuntuazioa = puntazioaKalkulatu(milisegundoak);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));

            koronoHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laberintoa);

        jugadorX = hasieraPuntuaX;
        jugadorY = hasieraPuntuaY;

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);
        buttonDerecha = findViewById(R.id.btnDerecha);
        buttonIzquierda = findViewById(R.id.btnIzquierda);
        buttonArriba = findViewById(R.id.btnArriba);
        buttonAbajo = findViewById(R.id.btnAbajo);

        laberintoaSortu();
      //  imprimirLaberinto();



        //Ajustar tamaño de botones segun resolucion de pantalla
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float scaleFactor = 0.040f;
        int buttonSize = (int) (Math.min(screenWidth, screenHeight) * scaleFactor);

        gridLayout = findViewById(R.id.gridLayout);
        botones = new ImageButton[laberinto.length][laberinto[0].length];

        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                ImageButton button = new ImageButton(this);
                button.setLayoutParams(new GridLayout.LayoutParams(new ViewGroup.LayoutParams(buttonSize, buttonSize)));
                button.setPadding(0, 0, 0, 0);

                if (i == jugadorX && j == jugadorY) {
                    // Jokalariaren posizioa
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
                    button.setImageResource(R.drawable.pertsona);
                    button.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else if (laberinto[i][j] == 1) {
                    // Pareta
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
                } else if (i == salidaX && j == salidaY) {
                    // Meta
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                } else {
                    // Bideak
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
                }

                botones[i][j] = button;
                gridLayout.addView(button);
                gridLayout.getLayoutParams().width = laberinto[0].length * buttonSize;
                gridLayout.getLayoutParams().height = laberinto.length * buttonSize;
            }
        }
         buttonAbajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover(0, 1, buttonDerecha, buttonIzquierda, buttonArriba, buttonAbajo);
            }
        });

        buttonArriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover(0, -1, buttonDerecha, buttonIzquierda, buttonArriba, buttonAbajo);
            }
        });

        buttonIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover(-1, 0, buttonDerecha, buttonIzquierda, buttonArriba, buttonAbajo);
            }
        });

        buttonDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover(1, 0, buttonDerecha, buttonIzquierda, buttonArriba, buttonAbajo);
            }
        });
    }

    private void laberintoaSortu() {
        for (int i = 0; i < anchoLaberinto; i++) {
            for (int j = 0; j < alturaLaberinto; j++) {
                laberinto[i][j] = 1;
            }
        }

        recursiveBacktracking(1, 1);
    }

    private void recursiveBacktracking(int fila, int columna) {
        ArrayList<Direccion> vecinos = getVecinos(fila, columna);
        Collections.shuffle(vecinos, random);

        for (Direccion direccion : vecinos) {
            int filaNueva = fila;
            int columnaNueva = columna;

            switch (direccion) {
                case ARRIBA:
                    filaNueva -= 2;
                    break;
                case ABAJO:
                    filaNueva += 2;
                    break;
                case IZQUIERDA:
                    columnaNueva -= 2;
                    break;
                case DERECHA:
                    columnaNueva += 2;
                    break;
            }

            if (laberinto[filaNueva][columnaNueva] == 1) {
                laberinto[(fila + filaNueva) / 2][(columna + columnaNueva) / 2] = 0;
                laberinto[filaNueva][columnaNueva] = 0;
                recursiveBacktracking(filaNueva, columnaNueva);
            }
        }
    }

    private ArrayList<Direccion> getVecinos(int fila, int columna) {
        ArrayList<Direccion> vecinos = new ArrayList<>();

        if (fila > 1 && laberinto[fila - 2][columna] == 1) {
            vecinos.add(Direccion.ARRIBA);
        }
        if (columna > 1 && laberinto[fila][columna - 2] == 1) {
            vecinos.add(Direccion.IZQUIERDA);
        }
        if (fila < anchoLaberinto - 2 && laberinto[fila + 2][columna] == 1) {
            vecinos.add(Direccion.ABAJO);
        }
        if (columna < alturaLaberinto - 2 && laberinto[fila][columna + 2] == 1) {
            vecinos.add(Direccion.DERECHA);
        }
        Collections.shuffle(vecinos, random);
        return vecinos;
    }

    enum Direccion {
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA
    }

    private void mover(int deltaY, int deltaX, Button buttonDerecha, Button buttonIzquierda, Button buttonArriba, Button buttonAbajo) {
        jugadorXAnterior = jugadorX;
        jugadorYAnterior = jugadorY;

        int nuevaPosX = jugadorX + deltaX;
        int nuevaPosY = jugadorY + deltaY;

        if (nuevaPosX >= 0 && nuevaPosX < laberinto.length &&
                nuevaPosY >= 0 && nuevaPosY < laberinto[0].length &&
                laberinto[nuevaPosX][nuevaPosY] == 0) {

            jugadorX = nuevaPosX;
            jugadorY = nuevaPosY;

            if (jugadorX == salidaX && jugadorY == salidaY) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        erakutsiMezua(txtPuntuazioa);
                        koronoHandler.removeCallbacks(kronoRunnable);
//                        mezuaErakutzi("Irabazi duzu!");
                        interfazeaEguneratu();
                        buttonArriba.setEnabled(false);
                        buttonIzquierda.setEnabled(false);
                        buttonDerecha.setEnabled(false);
                        buttonAbajo.setEnabled(false);
                    }
                }, 0);
            } else {
                // Jokalariaren posizio berriarekin interfaze grafikoa eguneratzea
                interfazeaEguneratu();
            }
        } else {
            //mezuaErakutzi("¡Pareta!");
        }
    }

    private void interfazeaEguneratu() {
        // Garbitu jokalariaren aurreko posizioa
        botones[jugadorXAnterior][jugadorYAnterior].setImageResource(0);

        // Jokalariaren posizio berria ezartzea
        ImageButton button = botones[jugadorX][jugadorY];
        button.setImageResource(R.drawable.pertsona);
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // Irteerako posizioa garbitzea (jokalariaren posizioa ez bada)
        if (!(jugadorX == salidaX && jugadorY == salidaY)) {
            botones[salidaX][salidaY].setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }
    }

    private void reiniciarLaberinto() {
        //Denbora eta puntuazioa berrezarri
        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);
        // Jokalariaren irudia kendu
        botones[jugadorX][jugadorY].setImageResource(0);

        // Hasierako posiziora eraman jokalaria
        jugadorXAnterior = hasieraPuntuaX;
        jugadorYAnterior = hasieraPuntuaY;
        jugadorX = hasieraPuntuaX;
        jugadorY = hasieraPuntuaY;

        // Laberinto berria egin
        laberintoaSortu();

        // Laberintoa erakutsi
        interfazeaEguneratu();

        // Koloreak eguneratu
        botoienKoloreakEguneratu();

        buttonArriba.setEnabled(true);
        buttonIzquierda.setEnabled(true);
        buttonDerecha.setEnabled(true);
        buttonAbajo.setEnabled(true);
    }


    private void botoienKoloreakEguneratu() {
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                ImageButton button = botones[i][j];

                if (i == jugadorX && j == jugadorY) {
                    // Jokalariaren posizioa
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
                } else if (laberinto[i][j] == 1) {
                    // Pareta
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
                } else if (i == salidaX && j == salidaY) {
                    // Meta
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                } else {
                    // Bideak
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
                }
            }
        }
    }

    private int puntazioaKalkulatu (long totalTimeInMillis) {
        int maxPuntuazioa = 10000;
        int milisegundoak = (int) totalTimeInMillis;
        int puntuazioa;

        if (milisegundoak <= 10000){
            puntuazioa=10000;
        } else if(milisegundoak<=20000){
            puntuazioa = maxPuntuazioa - ((milisegundoak-10000)*128)/1000;
        } else if(milisegundoak<=30000){
            puntuazioa = maxPuntuazioa - 1280 - ((milisegundoak-20000)*64)/1000;
        } else{
            puntuazioa = maxPuntuazioa -  1920 - ((milisegundoak-30000)*32)/1000;
        }
        if (puntuazioa < 0) {
            puntuazioa = 0;
        }
        return puntuazioa;
    }

    private void erakutsiMezua(TextView puntuaizoa) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(Laberintoa.this).inflate(R.layout.zorionak_dialog, null);
        Button successDone = view.findViewById(R.id.successDone);
        Button berriroJolastu = view.findViewById(R.id.berriroJolastu);
        TextView successDesc = view.findViewById(R.id.successDesc);
        TextView successTitle= view.findViewById(R.id.successTitle);

        if (successDesc != null) {
            String puntuaizoText = puntuaizoa.getText().toString();
            successDesc.setText("Hau izan da zure puntuazioa " + puntuaizoText + "!!");
            int puntuaizoInt = Integer.parseInt(puntuaizoText);
            if(puntuaizoInt>8000) {
                successTitle.setText("Hobeezina!!!");
            } else if (puntuaizoInt>6000) {
                successTitle.setText("Oso ondo!!");
            } else if (puntuaizoInt>3500) {
                successTitle.setText("Ondo!");
            } else {
                successTitle.setText("Hurrengoan hobeto egingo duzu!");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Laberintoa.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        successDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });



        berriroJolastu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                hasierakoDenbora = System.currentTimeMillis();
                koronoHandler.postDelayed(kronoRunnable, 0);
                reiniciarLaberinto();
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
        koronoHandler.removeCallbacks(kronoRunnable);
    }
}
