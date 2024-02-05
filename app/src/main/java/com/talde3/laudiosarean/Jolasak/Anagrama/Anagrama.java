package com.talde3.laudiosarean.Jolasak.Anagrama;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.talde3.laudiosarean.Jolasak.Kruzigrama.Kruzigrama;
import com.talde3.laudiosarean.LoginActivity;
import com.talde3.laudiosarean.R;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Anagrama extends AppCompatActivity {

    private LinearLayout containerPalabras;
    private Button btnVerificar;
    private List<String> palabrasOriginales;
    private List<TextView> textViews;
    private List<EditText> editTexts;
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private TextView txtPuntuazioa;
    private Handler koronoHandler = new Handler();
    private FirebaseAuth mAuth;
    public static FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagrama);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);
        txtPuntuazioa = findViewById(R.id.txtPuntuazioa);

        containerPalabras = findViewById(R.id.containerPalabras);
        btnVerificar = findViewById(R.id.btnVerificar);

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuestas();
            }
        });

        iniciarJuego();
    }

    /**
     * Jokoa hasieratzen du
     */
    private void iniciarJuego() {
        palabrasOriginales = obtenerPalabrasAleatorias();
        textViews = new ArrayList<>();
        editTexts = new ArrayList<>();

        // LineatLayout bertikal bat sortzen du hitz bakoitzarentzak
        for (int i = 0; i < palabrasOriginales.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            // Hitza
            String palabraOriginal = palabrasOriginales.get(i);
            String palabraMezclada = mezclarLetras(palabraOriginal);
            crearTextViewEditText(linearLayout, palabraMezclada);

            // LinearLayout-a gehitzen du edukiontzi nagusira.
            containerPalabras.addView(linearLayout);
        }
    }

    /**
     * TextView-a eta EditText-a sortzen ditu hitz bakoitzarentzat.
     * @param linearLayout Hitza-ren linearLayouta.
     * @param palabraMezclada Hitza nahastuta
     */
    private void crearTextViewEditText(LinearLayout linearLayout, String palabraMezclada) {
        // TextView-a sortzen du nahastutako hitzarentzat.
        TextView textView = new TextView(this);
        textView.setText(palabraMezclada);
        textView.setTextSize(24);
        // Horizontalki erdiratzen du.
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textViews.add(textView);

        // Erantzunarentzat EditTexta sortzen da
        EditText editText = new EditText(this);
        editText.setHint(getString(R.string.hitzaordenatu));
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        InputFilter inputFilter = new InputFilter.AllCaps();
        editText.setFilters(new InputFilter[]{inputFilter});
        editTexts.add(editText);

        // TextView-a eta EditText-a gehitzen ditu LinearLayout-era.
        linearLayout.addView(textView);
        linearLayout.addView(editText);
    }


    /**
     * Hitzen ordena ausaz desordenatzen ditu.
     * @return
     */
    private List<String> obtenerPalabrasAleatorias() {
        String[] palabras = {"ARABA", "MARKESA", "FINKA", "XVIII", "LAMUZA", "BARROKO", "JAUREGIA", "URKIXO"};
        List<String> listaPalabras = new ArrayList<>();
        Collections.addAll(listaPalabras, palabras);
        Collections.shuffle(listaPalabras);
        return listaPalabras;
    }

    /**
     * Hitz bakoitza ausaz desordenatzen du.
     * @param palabra Hitza ordenatuta
     * @return Hitza desordenatuta
     */
    private String mezclarLetras(String palabra) {
        List<Character> letras = new ArrayList<>();
        for (char letra : palabra.toCharArray()) {
            letras.add(letra);
        }
        Collections.shuffle(letras);

        StringBuilder palabraMezclada = new StringBuilder();
        for (char letra : letras) {
            palabraMezclada.append(letra);
        }

        return palabraMezclada.toString();
    }

    /**
     * Erantzunak konprobatzen ditu
     */
    private void verificarRespuestas() {
        boolean todasCorrectas = true;

        for (int i = 0; i < editTexts.size(); i++) {
            String respuestaUsuario = editTexts.get(i).getText().toString().toUpperCase();
            String palabraOriginal = palabrasOriginales.get(i);

            if (!respuestaUsuario.equals(palabraOriginal)) {
                todasCorrectas = false;
            } else {
                // Deshabilitar la edición para el EditText correspondiente a una respuesta correcta
                editTexts.get(i).setEnabled(false);
            }
        }

        if (todasCorrectas) {
            erakutsiMezua(txtPuntuazioa);
        } else {
            Toast.makeText(this, getString(R.string.erroreaAnigrama), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Jokoa berrabiarazteko metodoa
     */
    private void reiniciarJuego() {
        // Reiniciar variables y elementos necesarios
        palabrasOriginales = obtenerPalabrasAleatorias();

        // Limpiar las respuestas anteriores y habilitar la edición de los EditText
        for (int i = 0; i < editTexts.size(); i++) {
            editTexts.get(i).setText("");
            editTexts.get(i).setEnabled(true);
        }

        // Actualizar las palabras mezcladas en los TextView
        for (int i = 0; i < textViews.size(); i++) {
            String palabraMezclada = mezclarLetras(palabrasOriginales.get(i));
            textViews.get(i).setText(palabraMezclada);
        }
    }

    /**
     * Kronometroa gelditzen du
     */
    public void detenerCronometro() {
        koronoHandler.removeCallbacks(kronoRunnable);
    }

    /**
     * Fragmenta berrabiarazten du
     */
    public void reiniciarJokoDatuakFragment() {
        // Reiniciar variables
        hasierakoDenbora = System.currentTimeMillis();

        // Reiniciar el cronómetro
        koronoHandler.postDelayed(kronoRunnable, 0);
    }

    /**
     * Puntuazioa kalkulatzeko metodoa
     * @param totalTimeInMillis Momentuan zenbat denbora doan jokalaria.
     * @return Jokalariaren puntuazioa
     */
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

    /**
     * Jokoa bukatzean erakuzten den mezua. Jokalariaren emaitza erakusten da.
     * @param puntuaizoa Azken puntuazioa
     */
    private void erakutsiMezua(TextView puntuaizoa) {
        // Authentification
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        detenerCronometro();
        View view = LayoutInflater.from(Anagrama.this).inflate(R.layout.zorionak_dialog, null);
        Button successDone = view.findViewById(R.id.successDone);
        Button berriroJolastu = view.findViewById(R.id.berriroJolastu);

        // Obtener la referencia correcta de successDesc desde la vista inflada 'view'
        TextView successDesc = view.findViewById(R.id.successDesc);
        TextView successTitle= view.findViewById(R.id.successTitle);

        if (successDesc != null) {
            String puntuaizoText = puntuaizoa.getText().toString();
            successDesc.setText(getString(R.string.zurePuntuazioa) + " " + puntuaizoText);

            Ikaslea ikaslea = LoginActivity.db.ikasleaDao().getIkasleaByEmail(currentUser.getEmail());

            int puntukant = LoginActivity.db.puntuazioaDao().lastPuntuazioa();
            puntukant ++;
            String puntukantString = String.valueOf(puntukant);
            Puntuazioa puntuazioa = new Puntuazioa();
            puntuazioa.setId_puntuazioa(puntukant);
            puntuazioa.setId_gunea(6);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(Anagrama.this);
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
                reiniciarJuego();
                reiniciarJokoDatuakFragment();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }

}