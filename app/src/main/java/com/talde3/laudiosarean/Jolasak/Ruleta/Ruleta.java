package com.talde3.laudiosarean.Jolasak.Ruleta;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.talde3.laudiosarean.R;

import java.util.Random;

public class Ruleta extends AppCompatActivity {

    private ImageView ruleta;
    private Button btnGirar;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

        ruleta = findViewById(R.id.btnRuleta);
        btnGirar = findViewById(R.id.btnGirar);

        handler = new Handler();

        btnGirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la rotación con una velocidad aleatoria
                Random rand = new Random();
                int velocidadInicial = rand.nextInt(70) + 120;
                girarConDesaceleracion(velocidadInicial);
            }
        });
    }

    private void girarConDesaceleracion(final int velocidadActual) {
        final int retraso = 100; // Ajusta según sea necesario

        // Realiza la rotación actual
        int rotacion = (int) ruleta.getRotation();
        ruleta.setRotation(rotacion + velocidadActual);

        // Verifica si debemos continuar la desaceleración
        if (velocidadActual > 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    girarConDesaceleracion(velocidadActual - 1);
                }
            }, retraso);
        } else {
            float angleFinal = ruleta.getRotation() / 360;
            Toast.makeText(Ruleta.this, "Desaceleración completa " + angleFinal, Toast.LENGTH_SHORT).show();
            kalkulatuZenbakia(angleFinal);

        }
    }

    private void kalkulatuZenbakia(float angleFinal) {
        // Aquí puedes implementar la lógica para determinar el número en el que se detiene la ruleta
        // Utiliza la rotación para calcular el número
        String angleFinalString =  String.valueOf(angleFinal);
        String emaitza = new String();
        if (angleFinal >= 0 && angleFinal <= 51) {
            erakutsiMezua("Lamuza jauregia");
            emaitza = "Lamuza jauregia";
        }
        if (angleFinal >= 52 && angleFinal <= 102) {
            erakutsiMezua("Lezeagako sorgina");
            emaitza = "Lezeagako sorgina";
        }
        if (angleFinal >= 103 && angleFinal <= 154) {
            erakutsiMezua("Yermoko Andre Mariren Santutegia");
            emaitza = "Yermoko Andre Mariren Santutegia";
        }
        if (angleFinal >= 155 && angleFinal <= 205) {
            erakutsiMezua("Burdin Hesia");
            emaitza = "Burdin hesia";
        }
        if (angleFinal >= 206 && angleFinal <= 257) {
            erakutsiMezua("Santa Aguedako ermita");
            emaitza = "Santa Aguedako ermita";
        }
        if (angleFinal >= 206 && angleFinal <= 258) {
            erakutsiMezua("Santa Aguedako ermita");
            emaitza = "Santa Aguedako ermita";
        }
        if (angleFinal >= 259 && angleFinal <= 308) {
            erakutsiMezua("Katuxako jauregia");
            emaitza = "Katuxako jauregia";
        }
        if (angleFinal >= 309 && angleFinal <= 360) {
            erakutsiMezua("Lamuzako San Pedro eliza");
            emaitza = "Lamuzako San Pedro eliza";
        }

        if (emaitza.equals("")) {
            emaitza = "vacio";
        }
    }

    private void erakutsiMezua(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
}
