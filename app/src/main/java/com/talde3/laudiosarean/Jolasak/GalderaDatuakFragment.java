package com.talde3.laudiosarean.Jolasak;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.talde3.laudiosarean.R;

public class GalderaDatuakFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 30 * 1000; // 30 segundos en milisegundos
    private TextView txtPuntuazioa;
    private TextView txtKronometroa;

    // Constructor por defecto
    public GalderaDatuakFragment() {
        // Required empty public constructor
    }

    public int puntazioaKalkulatu(long totalTimeInMillis) {
        int segundoak = (int) totalTimeInMillis/1000;
        int puntuazioa;
        puntuazioa = segundoak*100 ;

        if (puntuazioa < 0) {
            puntuazioa = 0;
        }

        return puntuazioa;
    }


    public void detenerCronometro() {
        // Detener el temporizador
        countDownTimer.cancel();
    }

    public void reiniciarJokoDatuakFragment() {
        // Reiniciar variables
        hasierakoDenbora = 30 * 1000;

        // Reiniciar el temporizador
        countDownTimer.start();
    }

    // Método de fábrica para crear una nueva instancia del fragmento
    public static GalderaDatuakFragment newInstance(String param1, String param2) {
        GalderaDatuakFragment fragment = new GalderaDatuakFragment();
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
        txtKronometroa = view.findViewById(R.id.txtKronometroa);

        // Configurar el temporizador
        countDownTimer.start();

        return view;
    }

    // Temporizador de cuenta atrás de 30 segundos
    private CountDownTimer countDownTimer = new CountDownTimer(hasierakoDenbora, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            hasierakoDenbora = millisUntilFinished;

            int segundoak = (int) (millisUntilFinished / 1000) % 60;

            String time = String.format("%02d", segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo restante
            unekoPuntuazioa = puntazioaKalkulatu(millisUntilFinished);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));
        }


        @Override
        public void onFinish() {
            Toast.makeText(getActivity(), "¡Tiempo agotado!", Toast.LENGTH_SHORT).show();
        }
    };
}
