package com.talde3.laudiosarean.Jolasak.SopaLetra;

import static android.view.View.generateViewId;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.talde3.laudiosarean.Jolasak.JokoDatuakFragment;
import com.talde3.laudiosarean.Jolasak.Kruzigrama.Kruzigrama;
import com.talde3.laudiosarean.LoginActivity;
import com.talde3.laudiosarean.R;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

import java.util.ArrayList;

public class SopaLetra extends AppCompatActivity {
    private  GridLayout gridLayout;
    private  String[] hitzak;
    private TextView txtTunelak;
    private TextView txtArtilleria;
    private TextView txtAbiazioa;
    private TextView txtTropa;
    private TextView txtLubaki;
    private TextView txtBunkerrak;
    private TextView lastClickedTextView;
    private ArrayList<String> hitzAurkituak = new ArrayList<>();
    private ArrayList <Integer> idTextView = new ArrayList<>();
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private TextView txtPuntuazioa;
    private Handler koronoHandler = new Handler();
    private FirebaseAuth mAuth;
    public static FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopa_letra);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);
        txtPuntuazioa = findViewById(R.id.txtPuntuazioa);

        gridLayout = findViewById(R.id.gridLayoutSopaLetras);
        txtAbiazioa = findViewById(R.id.txtAbiazioa);
        txtTunelak = findViewById(R.id.txtTunnelak);
        txtArtilleria = findViewById(R.id.txtArtilleria);
        txtTropa = findViewById(R.id.txtTropa);
        txtLubaki = findViewById(R.id.txtLubaki);
        txtBunkerrak = findViewById(R.id.txtBunkerrak);

        char[][] letters = {
                {'A', 'B', 'B', 'D', 'T', 'U', 'N', 'E', 'L', 'A', 'K', 'E', 'E', 'A'},
                {'F', 'G', 'O', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'L', 'R'},
                {'R', 'S', 'T', 'N', 'U', 'X', 'Z', 'A', 'B', 'D', 'E', 'F', 'Ñ', 'T'},
                {'G', 'H', 'I', 'J', 'B', 'K', 'B', 'L', 'M', 'N', 'O', 'P', 'Z', 'I'},
                {'R', 'A', 'S', 'T', 'U', 'A', 'B', 'I', 'A', 'Z', 'I', 'O', 'A', 'L'},
                {'X', 'P', 'Z', 'A', 'B', 'B', 'R', 'E', 'D', 'F', 'G', 'H', 'F', 'L'},
                {'I', 'O', 'J', 'E', 'L', 'K', 'M', 'D', 'N', 'O', 'P', 'R', 'P', 'E'},
                {'S', 'R', 'S', 'U', 'B', 'A', 'Z', 'D', 'A', 'E', 'F', 'G', 'L', 'R'},
                {'T', 'T', 'H', 'I', 'J', 'L', 'U', 'B', 'A', 'K', 'I', 'R', 'K', 'I'},
                {'U', 'A', 'B', 'C', 'K', 'L', 'M', 'N', 'O', 'P', 'E', 'S', 'D', 'A'},
                {'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'Z', 'T', 'T', 'T'},
                {'N', 'B', 'U', 'N', 'K', 'E', 'R', 'R', 'A', 'K', 'X', 'U', 'A', 'A'}
        };

        hitzak = new String[]{"TUNELAK", "ARTILLERIA", "ABIAZIOA", "APORT", "LUBAKI", "BUNKERRAK"};


        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters[i].length; j++) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(letters[i][j]));
                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(5, 5, 5, 5);
                textView.setTextColor(Color.BLACK);
                int id = generateViewId();
                textView.setId(id);
                textView.setBackgroundResource(R.drawable.cell_background);
                GridLayout.Spec rowSpec = GridLayout.spec(i);
                GridLayout.Spec colSpec = GridLayout.spec(j, 1f); // Añadir peso relativo

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                params.width = 0;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.setMargins(5, 5, 5, 5);

                textView.setLayoutParams(params);
                gridLayout.addView(textView);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView currentTextView = (TextView) v;

                        if (lastClickedTextView == null) {
                            lastClickedTextView = currentTextView;
                            lastClickedTextView.setBackgroundColor(Color.GREEN);
                        } else {
                            int lastRow = gridLayout.indexOfChild(lastClickedTextView) / letters[0].length;
                            int lastCol = gridLayout.indexOfChild(lastClickedTextView) % letters[0].length;

                            int currentRow = gridLayout.indexOfChild(currentTextView) / letters[0].length;
                            int currentCol = gridLayout.indexOfChild(currentTextView) % letters[0].length;

                            if (lastRow == currentRow || lastCol == currentCol) {
                                int minRow = Math.min(lastRow, currentRow);
                                int maxRow = Math.max(lastRow, currentRow);
                                int minCol = Math.min(lastCol, currentCol);
                                int maxCol = Math.max(lastCol, currentCol);

                                StringBuilder word = new StringBuilder();
                                for (int r = minRow; r <= maxRow; r++) {
                                    for (int c = minCol; c <= maxCol; c++) {
                                        int index = r * letters[0].length + c;
                                        TextView textViewToHighlight = (TextView) gridLayout.getChildAt(index);
                                        textViewToHighlight.setBackgroundColor(Color.GREEN);

                                        word.append(letters[r][c]);
                                    }
                                }
                                String aukeratutakoHitza = word.toString();

                                boolean aurkituta = false;

                                for (String hitza : hitzak) {
                                    if (aukeratutakoHitza.equals(hitza)) {
                                        aurkituta = true;
                                        break;
                                    }
                                }

                                if (aurkituta) {
                                    for (int r = minRow; r <= maxRow; r++) {
                                        for (int c = minCol; c <= maxCol; c++) {
                                            int index = r * letters[0].length + c;
                                            TextView textViewToHighlight = (TextView) gridLayout.getChildAt(index);
                                            textViewToHighlight.setBackgroundColor(Color.CYAN);
                                            if (!idTextView.contains(textViewToHighlight.getId())) {
                                                idTextView.add(textViewToHighlight.getId());
                                            }
                                        }
                                    }
                                    hitzAurkituak.add(aukeratutakoHitza);

                                    if(comprobarSopaAcabada()==true){
                                        TextView txtPuntuazioa = findViewById(R.id.txtPuntuazioa);
                                        erakutsiMezua(txtPuntuazioa);
                                    }

                                    switch (aukeratutakoHitza) {
                                        case "ABIAZIOA":
                                            txtAbiazioa.setBackgroundResource(R.drawable.blackborder_greenbackground);
                                            break;
                                        case "TUNELAK":
                                            txtTunelak.setBackgroundResource(R.drawable.blackborder_greenbackground);
                                            break;
                                        case "ARTILLERIA":
                                            txtArtilleria.setBackgroundResource(R.drawable.blackborder_greenbackground);
                                            break;
                                        case "APORT":
                                            txtTropa.setBackgroundResource(R.drawable.blackborder_greenbackground);
                                            break;
                                        case "LUBAKI":
                                            txtLubaki.setBackgroundResource(R.drawable.blackborder_greenbackground);
                                            break;
                                        case "BUNKERRAK":
                                            txtBunkerrak.setBackgroundResource(R.drawable.blackborder_greenbackground);
                                            break;
                                    }
                                } else {
                                    boolean idEncontrado = false;
                                    for (int r = minRow; r <= maxRow; r++) {
                                        for (int c = minCol; c <= maxCol; c++) {
                                            int index = r * letters[0].length + c;
                                            TextView textViewToChange = (TextView) gridLayout.getChildAt(index);
                                            if (idTextView.contains(textViewToChange.getId())) {
                                                textViewToChange.setBackgroundColor(Color.CYAN);
                                                idEncontrado = true;
                                            } else {
                                                textViewToChange.setBackgroundResource(R.drawable.cell_background);
                                            }
                                        }
                                    }
                                    if (!idEncontrado) {
                                        Toast.makeText(getApplicationContext(), "La palabra no está en el array.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                int minRow = Math.min(lastRow, currentRow);
                                int maxRow = Math.max(lastRow, currentRow);
                                int minCol = Math.min(lastCol, currentCol);
                                int maxCol = Math.max(lastCol, currentCol);

                                boolean idEncontrado = false;
                                for (int r = minRow; r <= maxRow; r++) {
                                    for (int c = minCol; c <= maxCol; c++) {
                                        int index = r * letters[0].length + c;
                                        TextView textViewToChange = (TextView) gridLayout.getChildAt(index);
                                        if (idTextView.contains(textViewToChange.getId())) {
                                            textViewToChange.setBackgroundColor(Color.CYAN);
                                            idEncontrado = true;
                                        } else {
                                            textViewToChange.setBackgroundResource(R.drawable.cell_background);
                                        }
                                    }
                                }
                                if (!idEncontrado) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.hitzOkerra), Toast.LENGTH_SHORT).show();
                                }
                            }

                            lastClickedTextView = null;
                        }
                    }
                });
            }
        }
    }
    private void erakutsiMezua(TextView puntuaizoa) {
        // Authentification
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        detenerCronometro();
        View view = LayoutInflater.from(SopaLetra.this).inflate(R.layout.zorionak_dialog, null);
        Button successDone = view.findViewById(R.id.successDone);
        Button berriroJolastu = view.findViewById(R.id.berriroJolastu);

        // Obtener la referencia correcta de successDesc desde la vista inflada 'view'
        TextView successDesc = view.findViewById(R.id.successDesc);
        TextView successTitle= view.findViewById(R.id.successTitle);

        if (successDesc != null) {
            String puntuaizoText = puntuaizoa.getText().toString();
            successDesc.setText(getString(R.string.zurePuntuazioa) + puntuaizoText);

            Ikaslea ikaslea = LoginActivity.db.ikasleaDao().getIkasleaByEmail(currentUser.getEmail());

            int puntukant = LoginActivity.db.puntuazioaDao().lastPuntuazioa();
            puntukant ++;
            String puntukantString = String.valueOf(puntukant);
            Puntuazioa puntuazioa = new Puntuazioa();
            puntuazioa.setId_puntuazioa(puntukant);
            puntuazioa.setId_gunea(2);
            puntuazioa.setId_ikaslea(ikaslea.getId_ikaslea());
            puntuazioa.setPuntuazioa(Integer.parseInt(puntuaizoText));
            LoginActivity.db.puntuazioaDao().insert(puntuazioa);
            Log.i(TAG, String.valueOf(puntuazioa.getPuntuazioa()));
            firestore.collection("Puntuazioak").document(puntukantString).set(puntuazioa);

            int puntuaizoInt = Integer.parseInt(puntuaizoText);
            if(puntuaizoInt>8000) {
                successTitle.setText(getString(R.string.hobezina));
            } else if (puntuaizoInt>6000) {
                successTitle.setText(getString(R.string.osoOndo));
            } else if (puntuaizoInt>3500) {
                successTitle.setText(getString(R.string.ondo));
            } else {
                successTitle.setText(getString(R.string.hobetoEgin));
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(SopaLetra.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


        successDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });

        berriroJolastu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                reiniciarJokoDatuakFragment();
                reiniciarSopaLetra();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private boolean comprobarSopaAcabada() {
        boolean sopaAcabada = true;

        for (String palabra : hitzak) {
            if (!hitzAurkituak.contains(palabra)) {
                sopaAcabada = false;
                break;
            }
        }
        return sopaAcabada;
    }

    private void reiniciarSopaLetra() {
        // Restablecer colores de fondo de los TextViews en el GridLayout
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView textView = (TextView) gridLayout.getChildAt(i);
            textView.setBackgroundResource(R.drawable.cell_background);
        }

        // Limpiar listas de palabras encontradas y IDs de TextViews
        hitzAurkituak.clear();
        idTextView.clear();

        // También puedes reiniciar los colores de fondo de los TextViews de las palabras debajo de la pantalla
        txtAbiazioa.setBackgroundColor(Color.TRANSPARENT);
        txtTunelak.setBackgroundColor(Color.TRANSPARENT);
        txtArtilleria.setBackgroundColor(Color.TRANSPARENT);
        txtTropa.setBackgroundColor(Color.TRANSPARENT);
        txtLubaki.setBackgroundColor(Color.TRANSPARENT);
        txtBunkerrak.setBackgroundColor(Color.TRANSPARENT);
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

    private Runnable kronoRunnable = new Runnable() {
        @Override
        public void run() {
            long milisegundoak = System.currentTimeMillis() - hasierakoDenbora;
            int segundoak = (int) (milisegundoak / 1000);
            int minutuak = segundoak / 60;
            segundoak = segundoak % 60;

            TextView txtKronometroa = findViewById(R.id.txtKronometroa);

            String time = String.format("%02d:%02d", minutuak, segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo transcurrido
            unekoPuntuazioa = puntazioaKalkulatu(milisegundoak);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));

            koronoHandler.postDelayed(this, 10);
        }
    };
}