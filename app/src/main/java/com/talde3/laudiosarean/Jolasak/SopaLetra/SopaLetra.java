package com.talde3.laudiosarean.Jolasak.SopaLetra;

import static android.view.View.generateViewId;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import com.talde3.laudiosarean.Jolasak.JokoDatuakFragment;
import com.talde3.laudiosarean.Jolasak.Kruzigrama.Kruzigrama;
import com.talde3.laudiosarean.R;

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
    private JokoDatuakFragment jokoDatuakFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopa_letra);

        jokoDatuakFragment = (JokoDatuakFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        if (savedInstanceState == null) {
            jokoDatuakFragment = new JokoDatuakFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new JokoDatuakFragment())
                    .commit();
        }

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
                                            txtAbiazioa.setBackgroundColor(Color.GREEN);
                                            break;
                                        case "TUNELAK":
                                            txtTunelak.setBackgroundColor(Color.GREEN);
                                            break;
                                        case "ARTILLERIA":
                                            txtArtilleria.setBackgroundColor(Color.GREEN);
                                            break;
                                        case "APORT":
                                            txtTropa.setBackgroundColor(Color.GREEN);
                                            break;
                                        case "LUBAKI":
                                            txtLubaki.setBackgroundColor(Color.GREEN);
                                            break;
                                        case "BUNKERRAK":
                                            txtBunkerrak.setBackgroundColor(Color.GREEN);
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
                                    Toast.makeText(getApplicationContext(), "Hitz hori ez da zuzena.", Toast.LENGTH_SHORT).show();
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
        jokoDatuakFragment.detenerCronometro();
        View view = LayoutInflater.from(SopaLetra.this).inflate(R.layout.zorionak_dialog, null);
        Button successDone = view.findViewById(R.id.successDone);
        Button berriroJolastu = view.findViewById(R.id.berriroJolastu);

        // Obtener la referencia correcta de successDesc desde la vista inflada 'view'
        TextView successDesc = view.findViewById(R.id.successDesc);
        TextView successTitle= view.findViewById(R.id.successTitle);

        if (successDesc != null) {
            String puntuaizoText = puntuaizoa.getText().toString();
            successDesc.setText("Hau izan da zure puntuazioa " + puntuaizoText + "!!");
            int puntuaizoInt = Integer.parseInt(puntuaizoText);
            if(puntuaizoInt>8000) {
                successTitle.setText("Hobeezina!!!");
            } else if (puntuaizoInt>6000) {
                successTitle.setText("Oso ondo!!");
            } else if (puntuaizoInt>3500) {
                successTitle.setText("Ondo!");
            } else {
                successTitle.setText("Hurrengoan hobeto egingo duzu!");
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
                jokoDatuakFragment = (JokoDatuakFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                jokoDatuakFragment.reiniciarJokoDatuakFragment();
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


}