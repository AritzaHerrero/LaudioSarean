package com.talde3.laudiosarean;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.talde3.laudiosarean.Room.Dao.IkasleaDao;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth mAuth;
    private EditText etIzena;
    private ImageButton ibEditIzena;
    private EditText etAbizenak;
    private ImageButton ibEditAbizena;

    public ProfilaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilaFragment newInstance(String param1, String param2) {
        ProfilaFragment fragment = new ProfilaFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profila, container, false);

        EditText etEposta = view.findViewById(R.id.etEposta);
        etIzena = view.findViewById(R.id.etIzena);
        ibEditIzena = view.findViewById(R.id.ibEditIzenaProfila);
        etAbizenak = view.findViewById(R.id.etAbizenak);
        ibEditAbizena = view.findViewById(R.id.ibEditAbizenaProfila);
        EditText etKurtsoa = view.findViewById(R.id.etKurtsoaProfila);
        Button btnAldatuPasahitza = view.findViewById(R.id.btnAldatuPasahitza);
        Button btnItxiSaioa = view.findViewById(R.id.btnItxiSaioa);
        Button btnEzabatuKontua = view.findViewById(R.id.btnEzabatuKontua);

        mAuth = FirebaseAuth.getInstance();

        // Erabiltzailearen informazioa datu basetik hartzen da
        IkasleaDao ikaselaDao = LoginActivity.db.ikasleaDao();
        Ikaslea ikaslea = ikaselaDao.getIkasleaByEmail(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());

        etEposta.setText(ikaslea.getEmail());
        etIzena.setText(ikaslea.getIzena());
        etAbizenak.setText(ikaslea.getAbizenak());
        etKurtsoa.setText(ikaslea.getKurtsoa());

        ibEditIzena.setOnClickListener(view1 -> {
            boolean isEnabled = etIzena.isEnabled();

            if (!isEnabled) {
                etIzena.setEnabled(true);
                ibEditIzena.setImageResource(R.drawable.save_24);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage(getString(R.string.aldaketakGorde))
                        .setPositiveButton(getString(R.string.bai), (dialog, id) -> {
                            ikaslea.setIzena(etIzena.getText().toString());
                            LoginActivity.db.ikasleaDao().update(ikaslea);
                            etIzena.setText(ikaslea.getIzena());
                            etIzena.setEnabled(false);
                            ibEditIzena.setImageResource(R.drawable.edit_pencil_24);
                        })
                        .setNegativeButton(getString(R.string.ez), (dialog, id) -> {
                            etIzena.setText(ikaslea.getIzena());
                            etIzena.setEnabled(false);
                            dialog.dismiss();
                            ibEditIzena.setImageResource(R.drawable.edit_pencil_24);
                        })
                        .setOnDismissListener(dialogInterface -> etIzena.setText(ikaslea.getIzena()));
                builder.create().show();
            }
        });

        ibEditAbizena.setOnClickListener(view2 -> {
            boolean isEnabled = etAbizenak.isEnabled();

            if (!isEnabled) {
                etAbizenak.setEnabled(true);
                ibEditAbizena.setImageResource(R.drawable.save_24);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage(getString(R.string.aldaketakGorde))
                        .setPositiveButton(getString(R.string.bai), (dialog, id) -> {
                            ikaslea.setAbizenak(etAbizenak.getText().toString());
                            LoginActivity.db.ikasleaDao().update(ikaslea);
                            etAbizenak.setText(ikaslea.getAbizenak());
                            etAbizenak.setEnabled(false);
                            ibEditAbizena.setImageResource(R.drawable.edit_pencil_24);
                        })
                        .setNegativeButton(getString(R.string.ez), (dialog, id) -> {
                            etAbizenak.setText(ikaslea.getAbizenak());
                            etAbizenak.setEnabled(false);
                            dialog.dismiss();
                            ibEditAbizena.setImageResource(R.drawable.edit_pencil_24);
                        })
                        .setOnDismissListener(dialogInterface -> etAbizenak.setText(ikaslea.getAbizenak()));
                builder.create().show();
            }
        });

        btnAldatuPasahitza.setOnClickListener(view3 -> {
            // Edit text pasahitz berria sartzeko
            final EditText etPasahitzBerriaInput = new EditText(getActivity());

            etPasahitzBerriaInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setMessage(getString(R.string.pasahitzBerria)).setView(etPasahitzBerriaInput)
                    .setPositiveButton(getString(R.string.aldatu), (dialogInterface, i) -> {
                        // Sartutako pasahitz berria eskuratu
                        String newPassword = etPasahitzBerriaInput.getText().toString();
                        // Pasahitz berria beteta dagoela egiaztazen du
                        if (!TextUtils.isEmpty(newPassword)) {
                            // Momentuko erabiltzailea eskuratu
                            FirebaseUser erabiltzailea = mAuth.getCurrentUser();
                            if (erabiltzailea != null) {
                                // Pasahitza eguneratu
                                LoginActivity.db.ikasleaDao().update(ikaslea);

                                erabiltzailea.updatePassword(newPassword).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Pasahitza ondo aldatu da
                                        Toast.makeText(getContext(), getString(R.string.pasahitzaOndoAldatuta), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Exception exception = task.getException();
                                        if (exception instanceof FirebaseNetworkException) {
                                            // Ezin izan da pasahitza aldatu, konexio gabe dagoelako
                                            Toast.makeText(getContext(), getString(R.string.internetKonexioEz), Toast.LENGTH_SHORT).show();
                                        } else if (exception instanceof FirebaseAuthWeakPasswordException) {
                                            // Ezin izan da pasahitza aldatu, pasahitzak ez duelako betetzen gutxieneko baldintzak
                                            Toast.makeText(getContext(), getString(R.string.pasahitzFormaEz), Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Ezin izan da pasahitza aldatu
                                            Toast.makeText(getContext(), getString(R.string.pasahitzaGaizkiAldatuta), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            // Pasahitza berria hutsik egonda
                            Toast.makeText(getContext(), getString(R.string.pasahitzaHutsik), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(getString(R.string.ezeztatu), (dialogInterface, i) -> {
                        // Pasahitz aldaketa ezeztatu da.
                        dialogInterface.dismiss();
                    });
            builder.create().show();
        });

        btnItxiSaioa.setOnClickListener(view4 -> {
            mAuth.signOut();
            requireActivity().finish();
        });

        btnEzabatuKontua.setOnClickListener(view5 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setMessage(getString(R.string.kontuaEzabatuGaldera))
                    .setPositiveButton(getString(R.string.bai), (dialogInterface, i) -> {
                        // Kontua ezabatu
                        FirebaseUser erabiltzailea = FirebaseAuth.getInstance().getCurrentUser();
                        if (erabiltzailea != null) {
                            LoginActivity.db.ikasleaDao().delete(ikaslea);
                            erabiltzailea.delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Kontua ondo ezabatu da
                                    Toast.makeText(getContext(), getString(R.string.kontuaEzabatuta), Toast.LENGTH_SHORT).show();
                                    requireActivity().finish();
                                } else {
                                    // Error al eliminar la cuenta
                                    Exception exception = task.getException();
                                    if (exception instanceof FirebaseNetworkException) {
                                        // Ezin izan da pasahitza aldatu, konexio gabe dagoelako
                                        Toast.makeText(getContext(), getString(R.string.internetKonexioEz), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), getString(R.string.kontuaEzEzabatuta), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton(getString(R.string.ez), (dialogInterface, i) -> {
                        // Ez ezabatzeko aukeratzen bada.
                        dialogInterface.dismiss();
                    });
            builder.create().show();
        });



        return view;
    }
}