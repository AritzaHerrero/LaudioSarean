package com.talde3.laudiosarean.Jolasak.HutsuneakBete;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.talde3.laudiosarean.Jolasak.Kruzigrama.Kruzigrama;
import com.talde3.laudiosarean.LoginActivity;
import com.talde3.laudiosarean.R;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

import org.w3c.dom.Text;

public class HutsuneakBete extends AppCompatActivity {
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private TextView txtPuntuazioa;
    private Handler koronoHandler = new Handler();

    private EditText etErantzuna1;
    private EditText etErantzuna2;
    private EditText etErantzuna3;
    private EditText etErantzuna4;
    private EditText etErantzuna5;

    private TextView tvErantzuna1;
    private TextView tvErantzuna2;
    private TextView tvErantzuna3;
    private TextView tvErantzuna4;
    private TextView tvErantzuna5;
    private Button btnZuzendu;
    private FirebaseAuth mAuth;
    public static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hutsuneak_bete);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);
        txtPuntuazioa = findViewById(R.id.txtPuntuazioa);

        etErantzuna1 = findViewById(R.id.etErantzuna1);
        etErantzuna2 = findViewById(R.id.etErantzuna2);
        etErantzuna3 = findViewById(R.id.etErantzuna3);
        etErantzuna4 = findViewById(R.id.etErantzuna4);
        etErantzuna5 = findViewById(R.id.etErantzuna5);

        tvErantzuna1 = findViewById(R.id.tvErantzuna1);
        tvErantzuna2 = findViewById(R.id.tvErantzuna2);
        tvErantzuna3 = findViewById(R.id.tvErantzuna3);
        tvErantzuna4 = findViewById(R.id.tvErantzuna4);
        tvErantzuna5 = findViewById(R.id.tvErantzuna5);

        btnZuzendu =  findViewById(R.id.btnZuzendu);

        btnZuzendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean zuzena1 = false, zuzena2 = false, zuzena3 = false, zuzena4 = false, zuzena5 = false;
                zuzena1 = zuzenduErantzuna(etErantzuna1, tvErantzuna3);
                zuzena2 = zuzenduErantzuna(etErantzuna2, tvErantzuna4);
                zuzena3 = zuzenduErantzuna(etErantzuna3, tvErantzuna2);
                zuzena4 = zuzenduErantzuna(etErantzuna4, tvErantzuna1);
                zuzena5 = zuzenduErantzuna(etErantzuna5, tvErantzuna5);

                if (zuzena1 && zuzena2 && zuzena3 && zuzena4 && zuzena5) {
                    erakutsiMezua(txtPuntuazioa);
                }
            }
        });

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

    private boolean zuzenduErantzuna(EditText etErantzuna, TextView tvErantzuna) {
        boolean zuzena = false;
        String erantzuna1 = String.valueOf(etErantzuna.getText()).toUpperCase().trim();
        String emaitza1 = String.valueOf(tvErantzuna.getText()).toUpperCase();

        if (erantzuna1.equals(emaitza1)) {
            tvErantzuna.setBackgroundResource(R.drawable.blackborder_greenbackground);
            etErantzuna.setEnabled(false);
            zuzena = true;
        }
        return zuzena;
    }

    private void erakutsiMezua(TextView puntuaizoa) {
        // Authentification
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        detenerCronometro();
        View view = LayoutInflater.from(HutsuneakBete.this).inflate(R.layout.zorionak_dialog, null);
        Button successDone = view.findViewById(R.id.successDone);
        Button berriroJolastu = view.findViewById(R.id.berriroJolastu);

        // Obtener la referencia correcta de successDesc desde la vista inflada 'view'
        TextView successDesc = view.findViewById(R.id.successDesc);
        TextView successTitle= view.findViewById(R.id.successTitle);

        if (successDesc != null) {
            String puntuaizoText = puntuaizoa.getText().toString();
            successDesc.setText("Hau izan da zure puntuazioa " + puntuaizoText + "!!");

            Ikaslea ikaslea = LoginActivity.db.ikasleaDao().getIkasleaByEmail(currentUser.getEmail());

            int puntukant = LoginActivity.db.puntuazioaDao().lastPuntuazioa();
            puntukant ++;
            String puntukantString = String.valueOf(puntukant);
            Puntuazioa puntuazioa = new Puntuazioa();
            puntuazioa.setId_puntuazioa(puntukant);
            puntuazioa.setId_gunea(5);
            puntuazioa.setId_ikaslea(ikaslea.getId_ikaslea());
            puntuazioa.setPuntuazioa(Integer.parseInt(puntuaizoText));
            LoginActivity.db.puntuazioaDao().insert(puntuazioa);
            Log.i(TAG, String.valueOf(puntuazioa.getPuntuazioa()));
            firestore.collection("Puntuazioak").document(puntukantString).set(puntuazioa);

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

        AlertDialog.Builder builder = new AlertDialog.Builder(HutsuneakBete.this);
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
               // reiniciarKruzigrama();

                etErantzuna1.setText("");
                etErantzuna2.setText("");
                etErantzuna3.setText("");
                etErantzuna4.setText("");
                etErantzuna5.setText("");

                etErantzuna1.setEnabled(true);
                etErantzuna2.setEnabled(true);
                etErantzuna3.setEnabled(true);
                etErantzuna4.setEnabled(true);
                etErantzuna5.setEnabled(true);

                tvErantzuna1.setBackgroundResource(R.drawable.black_border);
                tvErantzuna2.setBackgroundResource(R.drawable.black_border);
                tvErantzuna3.setBackgroundResource(R.drawable.black_border);
                tvErantzuna4.setBackgroundResource(R.drawable.black_border);
                tvErantzuna5.setBackgroundResource(R.drawable.black_border);
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }

}