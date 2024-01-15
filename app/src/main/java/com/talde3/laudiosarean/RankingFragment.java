package com.talde3.laudiosarean;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItxiSaioaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
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
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        // Obtén la referencia al LinearLayout que contendrá los TextView
        LinearLayout linearLayoutRanking = view.findViewById(R.id.linearLayoutRanking);

        // Supongamos que tienes un array bidimensional de puntuaciones y usuarios
        String[][] datos = {
                {"725", "Usuario1"},
                {"975", "Usuario2"},
                {"1050", "Unax"},
                {"700", "Usuario4"},
                {"600", "Usuario5"},
                {"500", "Usuario6"},
                {"625", "Usuario7"},
                {"775", "Usuario8"},
                {"1000", "Aritza "},
                {"85", "Usuario10"},
                {"92", "Usuario11"},
                {"78", "Usuario12"},
                {"95", "Aingeru"},
                {"88", "Usuario14"},
                {"90", "Usuario15"},
                {"87", "Usuario16"}
        };


        // Convertir las puntuaciones a int y ordenar
        Arrays.sort(datos, (a, b) -> Integer.compare(Integer.parseInt(b[0]), Integer.parseInt(a[0])));
        int topN = Math.min(10, datos.length); // Muestra hasta las 10 mejores puntuaciones

        // Muestra las mejores puntuaciones en los TextView
        for (int i = 0; i < topN; i++) {
            // Crea un nuevo TextView
            TextView textView = new TextView(getActivity());

            // Configura los márgenes programáticamente
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            textView.setTextSize(18);
            textView.setText((i + 1) + ". " + datos[i][1] + ": " + datos[i][0]);

            layoutParams.setMargins(60, 60, 60, 0); // Aquí estableces los márgenes
            textView.setLayoutParams(layoutParams);

            // Agrega el TextView al LinearLayout
            linearLayoutRanking.addView(textView);
        }

        return view;
    }
}