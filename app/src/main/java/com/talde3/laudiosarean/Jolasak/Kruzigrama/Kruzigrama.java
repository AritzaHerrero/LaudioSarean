package com.talde3.laudiosarean.Jolasak.Kruzigrama;

import static android.view.View.generateViewId;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.talde3.laudiosarean.Jolasak.JokoDatuakFragment;
import com.talde3.laudiosarean.Jolasak.JolasakMetodoak;
import com.talde3.laudiosarean.Jolasak.Puzlea.PuzzleActivity;
import com.talde3.laudiosarean.R;

import java.util.HashMap;

public class Kruzigrama extends AppCompatActivity {


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
        setContentView(R.layout.activity_kruzigrama);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new JokoDatuakFragment())
                    .commit();
        }

        HitzPosizioa desfile = new HitzPosizioa(3, 3, 9, 3);
        HitzPosizioa sutzar = new HitzPosizioa(1, 5, 1, 10);
        HitzPosizioa mozorro = new HitzPosizioa(4, 1, 10, 1);
        HitzPosizioa otsaila = new HitzPosizioa(5, 1, 5, 7);
        HitzPosizioa sorgin = new HitzPosizioa(1, 5, 6, 5);
        HitzPosizioa lezeaga = new HitzPosizioa(8,3, 8, 9);

        HashMap<String, HitzPosizioa> hitzakPosizioaMap = new HashMap<>();

        hitzakPosizioaMap.put("DESFILE", desfile);
        hitzakPosizioaMap.put("SUTZAR", sutzar);
        hitzakPosizioaMap.put("MOZORRO", mozorro);
        hitzakPosizioaMap.put("OTSAILA", otsaila);
        hitzakPosizioaMap.put("SORGIN", sorgin);
        hitzakPosizioaMap.put("LEZEAGA", lezeaga);

        String[] hitzak = {"DESFILE", "SUTZAR", "MOZORRO", "OTSAILA", "SORGIN", "LEZEAGA"};
        char[][] letters = {
                {'X', 'X', 'X', 'X', 'X', '5', 'X', 'X', 'X', 'X', 'X',},
                {'X', 'X', 'X', 'X', '4', ' ', ' ', ' ', ' ', ' ', ' ',},
                {'X', 'X', 'X', '3', 'X', ' ', 'X', 'X', 'X', 'X', 'X',},
                {'X', '1', 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X',},
                {'X', ' ', 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X',},
                {'2', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X',},
                {'X', ' ', 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X',},
                {'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', 'X',},
                {'X', ' ', '6', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X',},
                {'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', 'X',},
                {'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',},
        };

        GridLayout gridLayout = findViewById(R.id.gridLayoutKruzigrama);
        Button btnKonprobatu = findViewById(R.id.btnKonprobatu);

        int rows = letters.length;
        int cols = letters[0].length;

        gridLayout.setColumnCount(cols);
        gridLayout.setRowCount(rows);

        EditText[][] editTextArray = new EditText[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (letters[i][j] != 'X') {
                    EditText editText = new EditText(this);
                    // Eliminar espacios en blanco al establecer el texto inicial
                    editText.setText(String.valueOf(letters[i][j]).trim());
                    editText.setTextSize(16);
                    editText.setGravity(Gravity.CENTER);
                    editText.setPadding(5, 5, 5, 5);
                    editText.setTextColor(Color.BLACK);
                    int id = generateViewId();
                    editText.setId(id);
                    editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                    editText.setBackgroundResource(R.drawable.cell_background);

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            String textWithoutSpaces = s.toString().replaceAll("\\s+", "");
                            String uppercaseText = textWithoutSpaces.toUpperCase(); // Convertir a mayúsculas

                            if (!s.toString().equals(uppercaseText)) {
                                editText.removeTextChangedListener(this); // Remover el listener temporalmente para evitar un bucle infinito
                                editText.setText(uppercaseText);
                                editText.setSelection(uppercaseText.length());
                                editText.addTextChangedListener(this); // Volver a agregar el listener

                                // Obtener el índice del EditText actual en el array
                                int currentRow = -1;
                                int currentCol = -1;
                                outerLoop:
                                for (int i = 0; i < editTextArray.length; i++) {
                                    for (int j = 0; j < editTextArray[i].length; j++) {
                                        if (editTextArray[i][j] == editText) {
                                            currentRow = i;
                                            currentCol = j;
                                            break outerLoop;
                                        }
                                    }
                                }

                                // Mover el foco al siguiente EditText
                                if (currentRow != -1 && currentCol != -1) {
                                    int nextRow = currentCol == editTextArray[currentRow].length - 1 ? currentRow + 1 : currentRow;
                                    int nextCol = currentCol == editTextArray[currentRow].length - 1 ? 0 : currentCol + 1;

                                    EditText nextEditText = editTextArray[nextRow][nextCol];
                                    if (nextEditText != null) {
                                        nextEditText.requestFocus();
                                    }
                                }
                            }
                        }
                    });


                    GridLayout.Spec rowSpec = GridLayout.spec(i);
                    GridLayout.Spec colSpec = GridLayout.spec(j, 1f);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                    params.width = 0;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.setMargins(1, 1, 1, 1);

                    editText.setLayoutParams(params);
                    gridLayout.addView(editText);

                    editTextArray[i][j] = editText;

                    if (Character.isDigit(letters[i][j])) {
                        editText.setBackgroundColor(Color.CYAN);
                        editText.setEnabled(false);
                    }


                } else {
                    TextView textView = new TextView(this);
                    textView.setText(String.valueOf(letters[i][j]));
                    textView.setTextSize(16);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(5, 5, 5, 5);
                    textView.setTextColor(Color.BLACK);
                    int id = generateViewId();
                    textView.setId(id);
                    textView.setBackgroundColor(Color.BLACK);

                    GridLayout.Spec rowSpec = GridLayout.spec(i);
                    GridLayout.Spec colSpec = GridLayout.spec(j, 1f);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                    params.width = 0;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.setMargins(1, 1, 1, 1);

                    textView.setLayoutParams(params);
                    gridLayout.addView(textView);
                }
            }
        }

        btnKonprobatu.setOnClickListener(view -> {
            for (String hitza : hitzak) {
                for (int i = 0; i < letters.length; i++) {
                    for (int j = 0; j < letters[i].length - hitza.length() + 1; j++) {
                        StringBuilder horizontal = new StringBuilder();
                        for (int k = 0; k < hitza.length(); k++) {
                            if (letters[i][j + k] != 'X') {
                                horizontal.append(editTextArray[i][j + k].getText().toString());
                            }
                        }
                        if (horizontal.toString().trim().equalsIgnoreCase(hitza)) {
                            // Verificar la posición
                            HitzPosizioa hitzPosizioa = hitzakPosizioaMap.get(hitza);
                            if (hitzPosizioa != null && hitzPosizioa.isMatch(i, j)) {
                                // La palabra está en la posición correcta
                                for (int m = 0; m < hitza.length(); m++) {
                                    editTextArray[i][j + m].setBackgroundColor(Color.GREEN);
                                    editTextArray[i][j + m].setEnabled(false);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void erakutsiMezua(TextView puntuaizoa) {
        View view = LayoutInflater.from(Kruzigrama.this).inflate(R.layout.zorionak_dialog, null);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(Kruzigrama.this);
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
                hasierakoDenbora = System.currentTimeMillis();
                koronoHandler.postDelayed(kronoRunnable, 0);
                Toast.makeText(Kruzigrama.this, "KAAAAAAAAAA", Toast.LENGTH_SHORT).show();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }
}