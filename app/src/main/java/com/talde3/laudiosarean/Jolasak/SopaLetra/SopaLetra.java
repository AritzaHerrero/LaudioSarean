package com.talde3.laudiosarean.Jolasak.SopaLetra;

import static android.view.View.generateViewId;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.talde3.laudiosarean.Jolasak.JokoDatuakFragment;
import com.talde3.laudiosarean.Jolasak.JolasakMetodoak;
import com.talde3.laudiosarean.R;

import java.util.ArrayList;

public class SopaLetra extends AppCompatActivity {

    private TextView lastClickedTextView;
    private ArrayList<String> hitzAurkituak = new ArrayList<>();
    private ArrayList <Integer> idTextView = new ArrayList<>();

    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private JolasakMetodoak jolasakMetodoak;
    private TextView txtPuntuazioa;
    private Handler koronoHandler = new Handler();

    //Metodo honek segunduro egiten da, denbora kalkulatzeko eta puntuazizoa unean ikusteko balio du
    private Runnable kronoRunnable = new Runnable() {
        @Override
        public void run() {
            long milisegundoak = System.currentTimeMillis() - hasierakoDenbora;
            int segundoak = (int) (milisegundoak / 1000);
            int minutuak = segundoak / 60;
            segundoak = segundoak % 60;

            TextView txtKronometroa = findViewById(R.id.txtKronometroa);
            txtPuntuazioa = findViewById(R.id.txtPuntuazioa);

            String time = String.format("%02d:%02d", minutuak, segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo transcurrido
            unekoPuntuazioa= jolasakMetodoak.puntazioaKalkulatu(milisegundoak);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));

            koronoHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopa_letra);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new JokoDatuakFragment())
                    .commit();
        }

        GridLayout gridLayout = findViewById(R.id.gridLayoutSopaLetras);

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

        String[] palabras = {"TUNELAK", "BONBARDAKETA", "ARTILLERIA", "ABIAZIOA", "BABESTU", "APORT", "LUBAKI", "BUNKERRAK"};


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

                                boolean encontrado = false;

                                for (String palabra : palabras) {
                                    if (aukeratutakoHitza.equals(palabra)) {
                                        encontrado = true;
                                        break;
                                    }
                                }

                                if (encontrado) {
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
//                                    mostrarIdTextView();
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
                                    Toast.makeText(getApplicationContext(), "La palabra no está en el array.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            lastClickedTextView = null;
                        }
                    }
                });
            }
        }
    }

//    private void mostrarIdTextView() {
//        StringBuilder textoIds = new StringBuilder();
//        for (Integer id : idTextView) {
//            textoIds.append(id).append(" ,");
//        }
//        Toast.makeText(getApplicationContext(), textoIds.toString(), Toast.LENGTH_LONG).show();
//    }

}