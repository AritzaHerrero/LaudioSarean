package com.talde3.laudiosarean;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.talde3.laudiosarean.Jolasak.Ruleta.Ruleta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuneakFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuneakFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public GuneakFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuneakFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuneakFragment newInstance(String param1, String param2) {
        GuneakFragment fragment = new GuneakFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guneak, container, false);

        ImageButton ibSantutegia = view.findViewById(R.id.ibSantutegia);
        ImageButton ibBurdinHesia = view.findViewById(R.id.ibBurdinHesia);
        ImageButton ibErmita = view.findViewById(R.id.ibErmita);
        ImageButton ibKatutxaJauregia = view.findViewById(R.id.ibKatutxaJauregia);
        ImageButton ibEliza = view.findViewById(R.id.ibEliza);
        ImageButton ibLamuzaJauregia = view.findViewById(R.id.ibLamuzaJauregia);
        ImageButton ibLezeagakoSorgina = view.findViewById(R.id.ibLezeagakoSorgina);
        ImageButton ibAzkenEbaluazioa = view.findViewById(R.id.ibAzkenEbaluazioa);

        ibSantutegia.setOnClickListener(v -> {
            Intent YermokoSantutegiaIntent = new Intent(getActivity(), GuneInformazioa.class);
            YermokoSantutegiaIntent.putExtra("aukeratutakoGunea", 1);
            startActivity(YermokoSantutegiaIntent);
        });

        ibBurdinHesia.setOnClickListener(v -> {
            Intent BurdinHesiaIntent = new Intent(getActivity(), GuneInformazioa.class);
            BurdinHesiaIntent.putExtra("aukeratutakoGunea", 2);
            startActivity(BurdinHesiaIntent);
        });

        ibErmita.setOnClickListener(v -> {
            Intent SantaAguedakoErmitaIntent = new Intent(getActivity(), GuneInformazioa.class);
            SantaAguedakoErmitaIntent.putExtra("aukeratutakoGunea", 3);
            startActivity(SantaAguedakoErmitaIntent);
        });

        ibKatutxaJauregia.setOnClickListener(v -> {
            Intent KatuxakoJauregiaIntent = new Intent(getActivity(), GuneInformazioa.class);
            KatuxakoJauregiaIntent.putExtra("aukeratutakoGunea", 4);
            startActivity(KatuxakoJauregiaIntent);
        });

        ibEliza.setOnClickListener(v -> {
            Intent LamuzakoSanPedroElizaIntent = new Intent(getActivity(), GuneInformazioa.class);
            LamuzakoSanPedroElizaIntent.putExtra("aukeratutakoGunea", 5);
            startActivity(LamuzakoSanPedroElizaIntent);
        });

        ibLamuzaJauregia.setOnClickListener(v -> {
            Intent LamuzaJauregiaIntent = new Intent(getActivity(), GuneInformazioa.class);
            LamuzaJauregiaIntent.putExtra("aukeratutakoGunea", 6);
            startActivity(LamuzaJauregiaIntent);
        });

        ibLezeagakoSorgina.setOnClickListener(v -> {
            Intent LezeagakoSorginaIntent = new Intent(getActivity(), GuneInformazioa.class);
            LezeagakoSorginaIntent.putExtra("aukeratutakoGunea", 7);
            startActivity(LezeagakoSorginaIntent);
        });

        ibAzkenEbaluazioa.setOnClickListener(v -> {
            Intent AzkenEbaluazioaIntent = new Intent(getActivity(), Ruleta.class);
            startActivity(AzkenEbaluazioaIntent);
        });

        return view;
    }
}