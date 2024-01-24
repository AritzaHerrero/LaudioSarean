package com.talde3.laudiosarean;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.talde3.laudiosarean.Room.Entities.Ranking;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout headerContainer;
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
        headerContainer = view.findViewById(R.id.headerContainer);

        Spinner spin = view.findViewById(R.id.spin);

        // Obtén la lista de cadenas desde tu parámetro
        List<Integer> guneak = Arrays.asList(1, 2, 3, 4, 5);

        // Crea un ArrayAdapter utilizando la lista de cadenas
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, guneak);

        // Especifica el diseño para mostrar los elementos en el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el ArrayAdapter al Spinner
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(this);

        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Integer gunea = (Integer) parent.getItemAtPosition(pos);

        List<Ranking> topi = LoginActivity.db.puntuazioaDao().topGune(gunea);

        LinearLayout linearLayoutRanking = requireView().findViewById(R.id.linearLayoutRanking);
        linearLayoutRanking.removeAllViews();

        for (int x = 0; x < topi.size(); x++){
            // Crea un nuevo TextView
            TextView textView = new TextView(getActivity());

            // Configura los márgenes programáticamente
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            textView.setTextSize(18);
            textView.setText((x + 1) + ". " + topi.get(x).getIzena() + ": " + topi.get(x).getPuntuazioa());

            layoutParams.setMargins(60, 0, 60, 0); // Aquí estableces los márgenes
            textView.setLayoutParams(layoutParams);

            // Agrega el TextView al LinearLayout
            linearLayoutRanking.addView(textView);
        }
        //Toast.makeText(requireContext(), "Elemento seleccionado: " + gunea, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}