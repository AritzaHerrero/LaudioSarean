package com.talde3.laudiosarean.Jolasak.Laberintoa;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.talde3.laudiosarean.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Laberintoa extends AppCompatActivity {

    private int jugadorXAnterior = 1;
    private int jugadorYAnterior = 1;
    private int jugadorX = 1;
    private int jugadorY = 1;
    private int salidaX = 23;
    private int salidaY = 23;

    private Random random = new Random();
    private int alturaLaberinto = 25;
    private int anchoLaberinto = 25;
    private int[][] laberinto = new int[anchoLaberinto][alturaLaberinto];
    private GridLayout gridLayout;
    private ImageButton[][] botones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laberintoa);

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

        Button buttonDerecha = findViewById(R.id.btnDerecha);
        Button buttonIzquierda = findViewById(R.id.btnIzquierda);
        Button buttonArriba = findViewById(R.id.btnArriba);
        Button buttonAbajo = findViewById(R.id.btnAbajo);
        Button restart = findViewById(R.id.laberintoRestar);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarLaberinto();
            }
        });

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
                        mezuaErakutzi("Irabazi duzu!");
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

    private void mezuaErakutzi(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void reiniciarLaberinto() {
        // Jokalariaren irudia kendu
        botones[jugadorX][jugadorY].setImageResource(0);

        // Hasierako posiziora eraman jokalaria
        jugadorXAnterior = 1;
        jugadorYAnterior = 1;
        jugadorX = 1;
        jugadorY = 1;

        // Laberinto berria egin
        laberintoaSortu();

        // Laberintoa erakutsi
        interfazeaEguneratu();

        // Koloreak eguneratu
        botoienKoloreakEguneratu();
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

}
