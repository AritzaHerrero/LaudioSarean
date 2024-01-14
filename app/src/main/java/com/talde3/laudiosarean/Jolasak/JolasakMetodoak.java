package com.talde3.laudiosarean.Jolasak;


import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.talde3.laudiosarean.R;


public class JolasakMetodoak {

    public static int puntazioaKalkulatu(long totalTimeInMillis) {
        int maxPuntuazioa = 10000;
        int milisegundoak = (int) totalTimeInMillis;
        int puntuazioa;

        if (milisegundoak <= 10000) {
            puntuazioa = 10000;
        } else if (milisegundoak <= 20000) {
            puntuazioa = maxPuntuazioa - ((milisegundoak - 10000) * 128) / 1000;
        } else if (milisegundoak <= 30000) {
            puntuazioa = maxPuntuazioa - 1280 - ((milisegundoak - 20000) * 64) / 1000;
        } else {
            puntuazioa = maxPuntuazioa - 1920 - ((milisegundoak - 30000) * 32) / 1000;
        }
        if (puntuazioa < 0) {
            puntuazioa = 0;
        }
        return puntuazioa;
    }

        public static void eguneratuKronometro(TextView txtKronometroa, TextView txtPuntuazioa, long hasierakoDenbora, Handler kronoHandler) {
            Runnable kronoRunnable = new Runnable() {
                @Override
                public void run() {
                    long milisegundoak = System.currentTimeMillis() - hasierakoDenbora;
                    int segundoak = (int) (milisegundoak / 1000);
                    int minutuak = segundoak / 60;
                    segundoak = segundoak % 60;

                    String time = String.format("%02d:%02d", minutuak, segundoak);
                    txtKronometroa.setText(time);

                    // Actualizar puntuación según el tiempo transcurrido
                    int unekoPuntuazioa = puntazioaKalkulatu(milisegundoak);
                    txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));

                    kronoHandler.postDelayed(this, 10);
                }
            };

            kronoHandler.post(kronoRunnable);
        }


    public void erakutsiMezua(TextView puntuaizoa, Activity x, long hasierakoDenbora, Handler koronoHandler) {
        View view = LayoutInflater.from(x).inflate(R.layout.zorionak_dialog, null);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(x);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        successDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                x.finish();
            }
        });



        berriroJolastu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                koronoHandler.postDelayed((Runnable) koronoHandler, 0);
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }
}