package com.talde3.laudiosarean;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private EditText etEposta;
    private EditText etIzena;
    private ImageButton ibEditIzena;
    private EditText etAbizena;
    private ImageButton ibEditAbizena;
    private EditText etKurtsoa;
    private Button btnAldatuPasahitza;
    private Button btnItxiSaioa;
    private Button btnEzabatuKontua;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profila, container, false);

        etEposta = view.findViewById(R.id.etEposta);
        etIzena = view.findViewById(R.id.etIzena);
        ibEditIzena = view.findViewById(R.id.ibEditIzenaProfila);
        etAbizena = view.findViewById(R.id.etAbizena);
        ibEditAbizena = view.findViewById(R.id.ibEditAbizenaProfila);
        etKurtsoa = view.findViewById(R.id.etKurtsoaProfila);
        btnAldatuPasahitza = view.findViewById(R.id.btnAldatuPasahitza);
        btnItxiSaioa = view.findViewById(R.id.btnItxiSaioa);
        btnEzabatuKontua = view.findViewById(R.id.btnEzabatuKontua);

        mAuth = FirebaseAuth.getInstance();

        ibEditIzena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEnabled = etIzena.isEnabled();

                if (!isEnabled) {
                    etIzena.setEnabled(true);
                    ibEditIzena.setImageResource(R.drawable.save_24);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.aldaketakGorde))
                            .setPositiveButton(getString(R.string.bai), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    etIzena.setEnabled(false);
                                    ibEditIzena.setImageResource(R.drawable.edit_pencil_24);
                                }
                            })
                            .setNegativeButton(getString(R.string.ez), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    etIzena.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    etIzena.setText("");
                                }
                            });
                    builder.create().show();
                }
            }
        });

        ibEditAbizena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEnabled = etAbizena.isEnabled();

                if (!isEnabled) {
                    etAbizena.setEnabled(true);
                    ibEditAbizena.setImageResource(R.drawable.save_24);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.aldaketakGorde))
                            .setPositiveButton(getString(R.string.bai), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    etAbizena.setEnabled(false);
                                    ibEditAbizena.setImageResource(R.drawable.edit_pencil_24);
                                }
                            })
                            .setNegativeButton(getString(R.string.ez), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    etAbizena.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    etAbizena.setText("");
                                }
                            });
                    builder.create().show();
                }
            }
        });

        btnAldatuPasahitza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Edit text pasahitz berria sartzeko
                final EditText etPasahitzBerriaInput = new EditText(getActivity());

                etPasahitzBerriaInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getString(R.string.pasahitzBerria)).setView(etPasahitzBerriaInput)
                        .setPositiveButton(getString(R.string.aldatu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Sartutako pasahitz berria eskuratu
                                String newPassword = etPasahitzBerriaInput.getText().toString();
                                // Pasahitz berria beteta dagoela egiaztazen du
                                if (!TextUtils.isEmpty(newPassword)) {
                                    // Momentuko erabiltzailea eskuratu
                                    FirebaseUser erabiltzailea = mAuth.getCurrentUser();
                                    if (erabiltzailea != null) {
                                        // Pasahitza eguneratu
                                        erabiltzailea.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
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
                                                    }
                                                });
                                    }
                                } else {
                                    // La nueva contraseña está vacía
                                    Toast.makeText(getContext(), getString(R.string.pasahitzaHutsik), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.ezeztatu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Pasahitz aldaketa ezeztatu da.
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        btnItxiSaioa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                getActivity().finish();
            }
        });

        btnEzabatuKontua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getString(R.string.kontuaEzabatuGaldera))
                        .setPositiveButton(getString(R.string.bai), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Kontua ezabatu
                                FirebaseUser erabiltzailea = FirebaseAuth.getInstance().getCurrentUser();
                                if (erabiltzailea != null) {
                                    erabiltzailea.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Kontua ondo ezabatu da
                                                Toast.makeText(getContext(), getString(R.string.kontuaEzabatuta), Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
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
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.ez), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Ez ezabatzeko aukeratzen bada.
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });



        return view;
    }
}