
package com.talde3.laudiosarean;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.talde3.laudiosarean.Room.Entities.ItemSpinner;
import com.talde3.laudiosarean.Room.Entities.Ranking;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Switch switchIkasle;
    private ArrayAdapter<ItemSpinner> adapter;
    private List<ItemSpinner> item;
    private TextView postop;
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        Spinner spin = view.findViewById(R.id.spin);
        switchIkasle = view.findViewById(R.id.switchIkasle);
        postop = view.findViewById(R.id.pos);

        // Spinner-a defektuz izango duen lista
        item = new ArrayList<>();
        item.add(new ItemSpinner(1, "1. YERMOKO ANDRE MARIAREN SANTUTEGIA"));
        item.add(new ItemSpinner(2, "2. BURDIN HESIA"));
        item.add(new ItemSpinner(3, "3. SANTA AGUEDA ERMITA"));
        item.add(new ItemSpinner(4, "4. KATUXAKO JAUREGIA"));
        item.add(new ItemSpinner(5, "5. LAMUZAKO SAN PEDRO ELIZA"));
        item.add(new ItemSpinner(6, "6. LAMUZAKO JAUREGIA"));
        item.add(new ItemSpinner(7, "7. LEZEAGAKO SORGINA"));

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        // Switch-aren listener-ra
        switchIkasle.setOnCheckedChangeListener((buttonView, isChecked) -> updateSpinnerData(isChecked));
        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        ItemSpinner gunea = (ItemSpinner) parent.getItemAtPosition(pos);

        postop.setText("Pos.");

        List<Ranking> topi;

        // Switch-a aktibatuta edo ez dagoen konprobatu
        boolean switchState = switchIkasle.isChecked();

        if (switchState) {
            // Switch-a aktibatuta
            topi = LoginActivity.db.puntuazioaDao().topIkasle(gunea.getId());
        } else {
            // Switch-a desaktibatuta
            topi = LoginActivity.db.puntuazioaDao().topGune(gunea.getId());
        }

        LinearLayout linearLayoutRanking = requireView().findViewById(R.id.linearLayoutRanking);
        linearLayoutRanking.removeAllViews();

        for (int x = 0; x < topi.size(); x++){
            // LinearLayout berria sortu puntuazioa eta izenaren textview-ak batzeko
            LinearLayout itemLayout = new LinearLayout(requireContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 10, 0, 10);
            itemLayout.setLayoutParams(layoutParams);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL); // Textview.ak bata bestearen alboan egon dadina
            itemLayout.setPadding(30, 30, 30, 30);

            // Izenaren textview-a sortu
            TextView nameTextView = new TextView(getActivity());
            LinearLayout.LayoutParams nameLayoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            // Izenaren eskuin eta ezker marginak
            nameTextView.setLayoutParams(nameLayoutParams);
            nameTextView.setTextSize(18);
            if(!switchState){
                nameTextView.setText((x + 1) + ". " + topi.get(x).getIzena() + " " + topi.get(x).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase() + ".");
            }else{
                nameTextView.setText(topi.get(x).getGunea() + " " + topi.get(x).getIzena() + " " + topi.get(x).getAbizenak().split("\\s+")[0].substring(0, 1).toUpperCase() + ".");
            }
            nameTextView.setTextAppearance(R.style.GameRankingTextStyle);

            // Puntuazioaren textview-a sortu
            TextView scoreTextView = new TextView(getActivity());
            LinearLayout.LayoutParams scoreLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            // Puntuazioaren eskuin eta ezker marginak
            scoreLayoutParams.setMargins(20, 0, 20, 0);
            scoreTextView.setLayoutParams(scoreLayoutParams);
            scoreTextView.setTextSize(18);
            scoreTextView.setText(String.valueOf(topi.get(x).getPuntuazioa()));
            scoreTextView.setTextAppearance(R.style.GameRankingTextStyle);

            // TextView-ak linearLayout-ean gehitzen dira
            itemLayout.addView(nameTextView);
            itemLayout.addView(scoreTextView);

            if(!switchState){
                postop.setText("Pos.");
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
            }else{
                postop.setText("Gunea");
                itemLayout.setBackgroundResource(R.drawable.background_top4_top10);
            }

            // Agrega el LinearLayout contenedor al LinearLayout principal
            linearLayoutRanking.addView(itemLayout);
        }
    }

    // Spinner-en datuak eguneratu switch-aren arabera
    private void updateSpinnerData(boolean switchState) {
        if (switchState) {
            // Switch aktibatuta, ikaleen lista kargatu
            item.clear();
            item.addAll(LoginActivity.db.ikasleaDao().getIkasleakSpinner());
        } else {
            // Switch desaktibatuta, guneen lista kargatu
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

