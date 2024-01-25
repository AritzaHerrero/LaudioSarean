package com.talde3.laudiosarean.Jolasak;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.talde3.laudiosarean.R;

public class JokoDatuakFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private TextView txtPuntuazioa;
    private Handler koronoHandler = new Handler();

    // Constructor por defecto
    public JokoDatuakFragment() {
        // Required empty public constructor
    }
    public void detenerCronometro() {
        koronoHandler.removeCallbacks(kronoRunnable);
    }



    public void reiniciarJokoDatuakFragment() {
        // Reiniciar variables
        hasierakoDenbora = System.currentTimeMillis();

        // Reiniciar el cronómetro
        koronoHandler.postDelayed(kronoRunnable, 0);
    }


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

    // Método de fábrica para crear una nueva instancia del fragmento
    public static JokoDatuakFragment newInstance(String param1, String param2) {
        JokoDatuakFragment fragment = new JokoDatuakFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joko_datuak, container, false);
        txtPuntuazioa = view.findViewById(R.id.txtPuntuazioa);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);
        return view;
    }

    // Método para iniciar el cronómetro y actualizar la puntuación
    private Runnable kronoRunnable = new Runnable() {
        @Override
        public void run() {
            long milisegundoak = System.currentTimeMillis() - hasierakoDenbora;
            int segundoak = (int) (milisegundoak / 1000);
            int minutuak = segundoak / 60;
            segundoak = segundoak % 60;

            TextView txtKronometroa = getView().findViewById(R.id.txtKronometroa);

            String time = String.format("%02d:%02d", minutuak, segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo transcurrido
            unekoPuntuazioa = puntazioaKalkulatu(milisegundoak);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));

            koronoHandler.postDelayed(this, 10);
        }
    };
}