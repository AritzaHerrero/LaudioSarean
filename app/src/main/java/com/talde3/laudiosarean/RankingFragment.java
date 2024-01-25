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

import com.talde3.laudiosarean.Room.Entities.ItemSpinner;
import com.talde3.laudiosarean.Room.Entities.Ranking;

import java.util.ArrayList;
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
    private LinearLayout top1;
    private LinearLayout top2;
    private LinearLayout top3;
    private LinearLayout top4;
    private LinearLayout top5;
    private LinearLayout top6;
    private LinearLayout top7;
    private LinearLayout top8;
    private LinearLayout top9;
    private LinearLayout top10;
    private TextView izena1;
    private TextView izena2;
    private TextView izena3;
    private TextView izena4;
    private TextView izena5;
    private TextView izena6;
    private TextView izena7;
    private TextView izena8;
    private TextView izena9;
    private TextView izena10;
    private TextView pnt1;
    private TextView pnt2;
    private TextView pnt3;
    private TextView pnt4;
    private TextView pnt5;
    private TextView pnt6;
    private TextView pnt7;
    private TextView pnt8;
    private TextView pnt9;
    private TextView pnt10;
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

        izena1 = view.findViewById(R.id.izena1);
        izena2 = view.findViewById(R.id.izena2);
        izena3 = view.findViewById(R.id.izena3);
        izena4 = view.findViewById(R.id.izena4);
        izena5 = view.findViewById(R.id.izena5);
        izena6 = view.findViewById(R.id.izena6);
        izena7 = view.findViewById(R.id.izena7);
        izena8 = view.findViewById(R.id.izena8);
        izena9 = view.findViewById(R.id.izena9);
        izena10 = view.findViewById(R.id.izena10);

        pnt1 = view.findViewById(R.id.pnt1);
        pnt2 = view.findViewById(R.id.pnt2);
        pnt3 = view.findViewById(R.id.pnt3);
        pnt4 = view.findViewById(R.id.pnt4);
        pnt5 = view.findViewById(R.id.pnt5);
        pnt6 = view.findViewById(R.id.pnt6);
        pnt7 = view.findViewById(R.id.pnt7);
        pnt8 = view.findViewById(R.id.pnt8);
        pnt9 = view.findViewById(R.id.pnt9);
        pnt10 = view.findViewById(R.id.pnt10);

        top1 = view.findViewById(R.id.top1);
        top2 = view.findViewById(R.id.top2);
        top3 = view.findViewById(R.id.top3);
        top4 = view.findViewById(R.id.top4);
        top5 = view.findViewById(R.id.top5);
        top6 = view.findViewById(R.id.top6);
        top7 = view.findViewById(R.id.top7);
        top8 = view.findViewById(R.id.top8);
        top9 = view.findViewById(R.id.top9);
        top10 = view.findViewById(R.id.top10);

        // Obtén la lista de cadenas desde tu parámetro
        List<ItemSpinner> guneak = new ArrayList<>();
        guneak.add(new ItemSpinner(1, "1. YERMOKO MARIA ANDREAREN SANTUTEGIA"));
        guneak.add(new ItemSpinner(2, "2. BURDIN HESIA"));
        guneak.add(new ItemSpinner(3, "3. SANTA AGUEDA ERMITA"));
        guneak.add(new ItemSpinner(3, "4. KATUXAKO JAUREGIA"));
        guneak.add(new ItemSpinner(3, "5. LAMUZAKO SAN PEDRO ELIZA"));
        guneak.add(new ItemSpinner(3, "6. LAMUZAKO JAUREGIA"));
        guneak.add(new ItemSpinner(3, "7. LEZEAGAKO SORGINA"));

        // Crea un ArrayAdapter utilizando la lista de cadenas
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, guneak);

        // Especifica el diseño para mostrar los elementos en el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el ArrayAdapter al Spinner
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(this);

        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        ItemSpinner gunea = (ItemSpinner) parent.getItemAtPosition(pos);

        List<Ranking> topi = LoginActivity.db.puntuazioaDao().topGune(gunea.getId());

//        LinearLayout linearLayoutRanking = requireView().findViewById(R.id.linearLayoutRanking);
//        linearLayoutRanking.removeAllViews();
        ezabatuRanking();

        for (int x = 0; x < topi.size(); x++){
            textViewEguneratu(x, topi);
            eskutatuTop();
            erakutsiTop();
            // Crea un nuevo TextView
//            TextView textView = new TextView(getActivity());
//
//            // Configura los márgenes programáticamente
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//
//            textView.setTextSize(18);
//            textView.setText((x + 1) + ". " + topi.get(x).getIzena() + " " + topi.get(x).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase() + ". : " + topi.get(x).getPuntuazioa());
//
//            layoutParams.setMargins(80, 80, 80, 0); // Aquí estableces los márgenes
//            textView.setLayoutParams(layoutParams);
//
//            // Agrega el TextView al LinearLayout
//            linearLayoutRanking.addView(textView);
        }
        //Toast.makeText(requireContext(), "Elemento seleccionado: " + gunea, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void textViewEguneratu(int x, List<Ranking> topi) {
        int zbk = x - 1;
        if (x == 1) {
            izena1.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt1.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 2) {
            izena2.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt2.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 3) {
            izena3.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt3.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 4) {
            izena4.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt4.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 5) {
            izena5.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt5.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 6) {
            izena6.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt6.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 7) {
            izena7.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt7.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 8) {
            izena8.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt8.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 9) {
            izena9.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt9.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
        if (x == 10) {
            izena10.setText(topi.get(zbk).getIzena() + " " + topi.get(zbk).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase());
            pnt10.setText(String.valueOf(topi.get(zbk).getPuntuazioa()));
        }
    }

    private void ezabatuRanking() {
        izena1.setText("");
        pnt1.setText("");
        izena2.setText("");
        pnt2.setText("");
        izena3.setText("");
        pnt3.setText("");
        izena4.setText("");
        pnt4.setText("");
        izena5.setText("");
        pnt5.setText("");
        izena6.setText("");
        pnt6.setText("");
        izena7.setText("");
        pnt7.setText("");
        izena8.setText("");
        pnt8.setText("");
        izena9.setText("");
        pnt9.setText("");
        izena10.setText("");
        pnt10.setText("");
    }

    private void eskutatuTop() {
        if (izena1.getText().equals("")) {
            top1.setVisibility(View.GONE);
        }

        if (izena2.getText().equals("")) {
            top2.setVisibility(View.GONE);
        }

        if (izena3.getText().equals("")) {
            top3.setVisibility(View.GONE);
        }

        if (izena4.getText().equals("")) {
            top4.setVisibility(View.GONE);
        }

        if (izena5.getText().equals("")) {
            top5.setVisibility(View.GONE);
        }

        if (izena6.getText().equals("")) {
            top6.setVisibility(View.GONE);
        }

        if (izena7.getText().equals("")) {
            top7.setVisibility(View.GONE);
        }

        if (izena8.getText().equals("")) {
            top8.setVisibility(View.GONE);
        }

        if (izena9.getText().equals("")) {
            top9.setVisibility(View.GONE);
        }

        if (izena10.getText().equals("")) {
            top10.setVisibility(View.GONE);
        }
    }

    private void erakutsiTop() {
        if (!izena1.getText().equals("")) {
            top1.setVisibility(View.VISIBLE);
        }

        if (!izena2.getText().equals("")) {
            top2.setVisibility(View.VISIBLE);
        }

        if (!izena3.getText().equals("")) {
            top3.setVisibility(View.VISIBLE);
        }

        if (!izena4.getText().equals("")) {
            top4.setVisibility(View.VISIBLE);
        }

        if (!izena5.getText().equals("")) {
            top5.setVisibility(View.VISIBLE);
        }

        if (!izena6.getText().equals("")) {
            top6.setVisibility(View.VISIBLE);
        }

        if (!izena7.getText().equals("")) {
            top7.setVisibility(View.VISIBLE);
        }

        if (!izena8.getText().equals("")) {
            top8.setVisibility(View.VISIBLE);
        }

        if (!izena9.getText().equals("")) {
            top9.setVisibility(View.VISIBLE);
        }

        if (!izena10.getText().equals("")) {
            top10.setVisibility(View.VISIBLE);
        }
    }
}