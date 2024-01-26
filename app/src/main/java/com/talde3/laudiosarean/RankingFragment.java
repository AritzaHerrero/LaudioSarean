
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
    private Switch switchIkasle;
    private Spinner spin;
    private ArrayAdapter<ItemSpinner> adapter;
    private List<ItemSpinner> item;
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

        spin = view.findViewById(R.id.spin);

        // Obtén la lista de cadenas desde tu parámetro
        switchIkasle = view.findViewById(R.id.switchIkasle);

        item = new ArrayList<>();
        item.add(new ItemSpinner(1, "1. YERMOKO ANDRE MARIAREN SANTUTEGIA"));
        item.add(new ItemSpinner(2, "2. BURDIN HESIA"));
        item.add(new ItemSpinner(3, "3. SANTA AGUEDA ERMITA"));
        item.add(new ItemSpinner(4, "4. KATUXAKO JAUREGIA"));
        item.add(new ItemSpinner(5, "5. LAMUZAKO SAN PEDRO ELIZA"));
        item.add(new ItemSpinner(6, "6. LAMUZAKO JAUREGIA"));
        item.add(new ItemSpinner(7, "7. LEZEAGAKO SORGINA"));
        // Crea un ArrayAdapter utilizando la lista de cadenas
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, item);

        // Especifica el diseño para mostrar los elementos en el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el ArrayAdapter al Spinner
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(this);

        // Configura el Listener del Switch
        switchIkasle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSpinnerData(isChecked);
            }
        });

        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        ItemSpinner gunea = (ItemSpinner) parent.getItemAtPosition(pos);

        List<Ranking> topi = new ArrayList<Ranking>();

        // Verificar si el Switch está activado
        boolean switchState = switchIkasle.isChecked();

        if (switchState) {
            // El Switch está activado
            topi = LoginActivity.db.puntuazioaDao().topIkasle(gunea.getId());
        } else {
            // El Switch está desactivado
            topi = LoginActivity.db.puntuazioaDao().topGune(gunea.getId());
        }

        LinearLayout linearLayoutRanking = requireView().findViewById(R.id.linearLayoutRanking);
        linearLayoutRanking.removeAllViews();

        for (int x = 0; x < topi.size(); x++){
            // Crea un nuevo LinearLayout para contener los TextViews
            LinearLayout itemLayout = new LinearLayout(requireContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 20, 0, 0);

            // Aplicar los parámetros de diseño al LinearLayout
            itemLayout.setLayoutParams(layoutParams);

            itemLayout.setOrientation(LinearLayout.HORIZONTAL); // Orientación horizontal para que los TextViews estén uno al lado del otro
            itemLayout.setPadding(30, 30, 30, 30);
            // Crea un nuevo TextView para el nombre
            TextView nameTextView = new TextView(getActivity());
            LinearLayout.LayoutParams nameLayoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            // Establece los márgenes izquierdo y derecho
            nameTextView.setLayoutParams(nameLayoutParams);
            nameTextView.setTextSize(18);
            nameTextView.setText((x + 1) + ". " + topi.get(x).getIzena() + " " + topi.get(x).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase() + ".");
            nameTextView.setTextAppearance(R.style.GameRankingTextStyle);

            // Crea un nuevo TextView para la puntuación
            TextView scoreTextView = new TextView(getActivity());
            LinearLayout.LayoutParams scoreLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            // Establece los márgenes izquierdo y derecho
            scoreLayoutParams.setMargins(20, 0, 20, 0);
            scoreTextView.setLayoutParams(scoreLayoutParams);
            scoreTextView.setTextSize(18);
            scoreTextView.setText(String.valueOf(topi.get(x).getPuntuazioa()));
            scoreTextView.setTextAppearance(R.style.GameRankingTextStyle);

            // Agrega los TextViews al LinearLayout
            itemLayout.addView(nameTextView);
            itemLayout.addView(scoreTextView);

            if(x == 0) {
                itemLayout.setBackgroundResource(R.drawable.background_top1);
                nameTextView.setTextSize(30);
                scoreTextView.setTextSize(30);
            } else if(x == 1) {
                itemLayout.setBackgroundResource(R.drawable.background_top2);
                nameTextView.setTextSize(26);
                scoreTextView.setTextSize(26);
            } else if(x == 2) {
                itemLayout.setBackgroundResource(R.drawable.background_top3);
                nameTextView.setTextSize(22);
                scoreTextView.setTextSize(22);
            }else{
                itemLayout.setBackgroundResource(R.drawable.background_top4_top10);
            }

            // Agrega el LinearLayout contenedor al LinearLayout principal
            linearLayoutRanking.addView(itemLayout);
        }
        //Toast.makeText(requireContext(), "Elemento seleccionado: " + gunea, Toast.LENGTH_SHORT).show();
    }

    // Método para actualizar los datos del Spinner según el estado del Switch
    private void updateSpinnerData(boolean switchState) {
        if (switchState) {
            // El Switch está activado, cargar datos de una fuente
            item.clear();
            item.addAll(LoginActivity.db.ikasleaDao().getIkasleak());
        } else {
            // El Switch está desactivado, cargar datos de otra fuente
            item.clear();
            item.add(new ItemSpinner(1, "1. YERMOKO MARIA ANDREAREN SANTUTEGIA"));
            item.add(new ItemSpinner(2, "2. BURDIN HESIA"));
            item.add(new ItemSpinner(3, "3. SANTA AGUEDA ERMITA"));
            item.add(new ItemSpinner(4, "4. KATUXAKO JAUREGIA"));
            item.add(new ItemSpinner(5, "5. LAMUZAKO SAN PEDRO ELIZA"));
            item.add(new ItemSpinner(6, "6. LAMUZAKO JAUREGIA"));
            item.add(new ItemSpinner(7, "7. LEZEAGAKO SORGINA"));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

