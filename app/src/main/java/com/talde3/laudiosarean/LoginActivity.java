package com.talde3.laudiosarean;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.talde3.laudiosarean.Room.Dao.IkasleaDao;
import com.talde3.laudiosarean.Room.Datubase;
import com.talde3.laudiosarean.Room.Entities.Gunea;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Irakaslea;
import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static Datubase db;
    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private EditText etEposta;
    private EditText etPasahitza;
    private Button btnSaioahasi;
    private CheckBox cbPasahitzaGorde;
    private TextView tvErregistratuEmen;
    private ImageButton ibPasahitza;
    private ProgressBar pbKarga;
    private List<String> lehentasunakInfo;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Datu baseko instantziak
        firestore = FirebaseFirestore.getInstance(); // Firestore
        db = Datubase.getInstance(getApplicationContext()); // Room

        // Datuak kargatu
        if (isFirstRun()) {
            dbKarga();
            markFirstRun();
        }

        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Ikaslea ikaslea = (Ikaslea) bundle.getSerializable("ikaslea");

                // Ikaslea null den ikusten du
                IkasleaDao ikasleaDao = db.ikasleaDao();
                if (ikasleaDao != null) {
                    // Ikaslea null den ikusten du
                    if (ikaslea != null) {
                        try {
                            // Inserta egiten du Room-en
                            ikasleaDao.insert(ikaslea);

                            // Firestorera bidaltzen du
                            firestore.collection("Ikasleak").document(ikaslea.getEmail()).set(ikaslea);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        etEposta = findViewById(R.id.etEposta);
        etPasahitza = findViewById(R.id.etPasahitza);
        btnSaioahasi = findViewById(R.id.btnSaioahasi);
        tvErregistratuEmen = findViewById(R.id.tvErregistratuEmen);
        ibPasahitza = findViewById(R.id.ibPasahitza);
        ibPasahitza = findViewById(R.id.ibPasahitza);
        cbPasahitzaGorde = findViewById(R.id.cbPasahitzaGorde);
        pbKarga = findViewById(R.id.pbKarga);

        mAuth = FirebaseAuth.getInstance();
        lehentasunakInfo = lehentasunakKargatu();

        aktibatuUI();
        btnSaioahasi.setOnClickListener(v -> {
            String posta = etEposta.getText().toString().trim();
            String pasahitza = etPasahitza.getText().toString().trim();

            // posta edo pasahitzaren EditText-a ez badago errore mezua. Bestela Saioa hasten saiatuko da.
            if (TextUtils.isEmpty(posta)) {
                String sartuEposta = getResources().getString(R.string.sartuEposta);
                etEposta.setError(sartuEposta);
            } else if (TextUtils.isEmpty(pasahitza)) {
                String sartuPasahitza = getResources().getString(R.string.sartuPasahitza);
                etPasahitza.setError(sartuPasahitza);
            } else {
                saioaHasi(posta, pasahitza);
            }
        });

        // Erregistroa activity-ra ereamango digu.
        tvErregistratuEmen.setOnClickListener(v -> {
            // Erregistro textView-ean click egitean
            Intent erregistroIntent = new Intent(LoginActivity.this, Erregistroa.class);
            startActivity(erregistroIntent);
        });

        // ImageButton-ari click egitean pasahitza erakutsiko da, berriro egiten badugu click pasahitza eskutatuko da.
        ibPasahitza.setOnClickListener(v -> {
            int currentInputType = etPasahitza.getInputType();

            // Pasahitza ez badago ikusgarri
            if (currentInputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etPasahitza.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etPasahitza.setTypeface(Typeface.DEFAULT);
                ibPasahitza.setImageResource(R.drawable.begia_off_24);
            }
            // Pasahitza ikusgarri badago
            else {
                etPasahitza.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPasahitza.setTypeface(Typeface.DEFAULT);
                ibPasahitza.setImageResource(R.drawable.begia_24);
            }

            // Kurtsorea testuaren amaieran mugitzen da.
            etPasahitza.setSelection(etPasahitza.getText().length());
        });
    }

    /**
     * Saioa hasten sailatzen da. Saioa ondo hasten bada, lehentasunakGorde behar diren edo ez konprobatzen du. Azkenik MainActivity-ra eramaten dio erabiltzaileari.
     * Ezin izan bada saioa hasi errore mezu pertsonalizatua bat ageriko da.
     * @param eposta Erabiltzailearen eposta
     * @param pasahitza Erabiltzailearen pasahitza
     */
   private void saioaHasi(String eposta, String pasahitza) {
        desaktibatuUI();
        mAuth.signInWithEmailAndPassword(eposta, pasahitza)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Saioa ondo hasi da
                        cbPasahitzaGorde = findViewById(R.id.cbPasahitzaGorde);
                        if(cbPasahitzaGorde.isChecked()) {
                            lehentasunakGorde();
                        } else if (!lehentasunakInfo.get(0).equals(eposta) && !lehentasunakInfo.get(1).equals(pasahitza)){
                            etEposta.setText("");
                            etPasahitza.setText("");
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        aktibatuUI();
                        // Toast.makeText(MainActivity.this, getResources().getString(R.string.ongiEtorri), Toast.LENGTH_SHORT).show();
                    } else {
                        Exception exception = task.getException();
                        aktibatuUI();
                        if (exception instanceof FirebaseNetworkException) {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erKonexioaLogin), Toast.LENGTH_SHORT).show();
                        } else if (exception instanceof FirebaseAuthInvalidUserException) {
                            // Eposta ez da aurkitu
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erEpostaLogin), Toast.LENGTH_SHORT).show();
                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            // Saio-hasierako kredentzial baliogabeak (eposta edo pasahitza baliogabeak)
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erPasahitzaLogin), Toast.LENGTH_SHORT).show();
                        } else {
                            // Otro error no manejado específicamente
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erLoginLogin), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Lehentasunak kargatzeko metodoa. Kasu onetan, erabiltzailearen e-posta eta pasahitza kargatzeko.
     * @return Gordetako kredentzialak bueltatuko ditu.
     */
   private List<String> lehentasunakKargatu() {
        List<String> kredentzialak = new ArrayList<>();
        // Kredentzialak.xml kargatzen du (lehenengo aldia bada 'kredentzialak.xml' sortzen du)
        SharedPreferences preferences = getSharedPreferences("kredentzialak", Context.MODE_PRIVATE);

        // xml barruan 'user'-ean eta 'pass'-ean gordetako informazioa gordetzen du.
        String eposta = preferences.getString("user", "");
        String pasahitza = preferences.getString("pass", "");

        // EditText-etan ipintzen du kargatutako informazioa
        etEposta.setText(eposta);
        etPasahitza.setText(pasahitza);

       // Informazioa listan gordetzen da
       kredentzialak.add(eposta);
       kredentzialak.add(pasahitza);

       return kredentzialak;
   }


    /**
     * Lehentasunak gordetzeko metodoa. Kasu onetan, erabiltzailearen e-posta eta pasahitza gordetzeko.
     */
   private void lehentasunakGorde() {
        // Kredentzialak.xml kargatzen da informazioa gordetzeko (lehenengo aldia bada 'kredentzialak.xml' sortzen du)
        SharedPreferences preferences = getSharedPreferences("kredentzialak", Context.MODE_PRIVATE);
        // Sartutako erabiltzaile eta pasahitzak eskuratzen dira
        String eposta = etEposta.getText().toString();
        String pasahitza = etPasahitza.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", eposta);
        editor.putString("pass", pasahitza);

        // Dena gordeko da xml-an.
        editor.apply();
   }


    /**
     * Erabiltzailearen interfazea desaktibatuko du eta Karga (ProgressBar) erakutsiko du.
     */
   private void desaktibatuUI() {
        etEposta.setEnabled(false);
        etPasahitza.setEnabled(false);
        btnSaioahasi.setEnabled(false);
        tvErregistratuEmen.setEnabled(false);
        ibPasahitza.setEnabled(false);
        cbPasahitzaGorde.setEnabled(false);
        pbKarga.setVisibility(View.VISIBLE);
   }

    /**
     * Erabiltzailearen interfazea aktibatuko du eta Karga (ProgressBar) ezkutatu du.
     */
   private void aktibatuUI() {
        etEposta.setEnabled(true);
        etPasahitza.setEnabled(true);
        btnSaioahasi.setEnabled(true);
        tvErregistratuEmen.setEnabled(true);
        ibPasahitza.setEnabled(true);
        cbPasahitzaGorde.setEnabled(true);
        pbKarga.setVisibility(View.INVISIBLE);
   }

    /**
     * Room-taula guztiak ezabtzen ditu eta auto incrementak berriz hasieratzen dira eta Firestoreko informazioa room-en kargatzen da.
     */
   private void dbKarga(){
        // Room-taula guztiak ezabtzen ditu eta auto incrementak berriz hasieratzen dira
        db.clearAllTables();
        db.irakasleaDao().resetPrimaryKeyAutoIncrementValueIrakaslea();
        db.ikasleaDao().resetPrimaryKeyAutoIncrementValueIkaslea();
        db.guneaDao().resetPrimaryKeyAutoIncrementValueGunea();
        db.puntuazioaDao().resetPrimaryKeyAutoIncrementValuePuntuazioa();

        // Firestoreko informazioa room-en kargatzen da
        loadIkasleakData();
   }

    /**
     * Ikasleen informaszioa kargatzen du eta behin bukatu ondoren Guneak kargatzen dira.
     */
   private void loadIkasleakData() {
        firestore.collection("Ikasleak")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.ikasleaDao().insert(document.toObject(Ikaslea.class));
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }

                        // Ikasleak kargatu ondoren, Guneak kargatzen dira
                        loadGuneakData();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
   }

    /**
     * Guneen informazioa kargatzen du eta behin bukatu ondoren Puntuazioak kargatzen dira.
     */
   private void loadGuneakData() {
        firestore.collection("Guneak")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.guneaDao().insert(document.toObject(Gunea.class));
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }

                        // Guneak kargatu ondoren, Puntuazioak kargatzen ditu
                        loadPuntuazioakData();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
   }

    /**
     * Puntuazioen informazioa kargatzen du eta behin bukatu ondoren Irakasleak kargatzen dira.
     */
   private void loadPuntuazioakData() {
        firestore.collection("Puntuazioak")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.puntuazioaDao().insert(document.toObject(Puntuazioa.class));
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }

                        // Puntuazioak kargatu ondoren, Irakasleak kargatzen ditu
                        loadIrakasleData();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
   }

    /**
     * Irakasleen informazioa kargatzen du.
     */
   private void loadIrakasleData(){
       firestore.collection("Irakasleak")
               .get()
               .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           db.irakasleaDao().insert(document.toObject(Irakaslea.class));
                           Log.d(TAG, document.getId() + " => " + document.getData());
                       }
                   } else {
                       Log.d(TAG, "Error getting documents: ", task.getException());
                   }
               });
   }

    /**
     * Aplikazioa lehen aldiz kargatzen den konprobatzen du. "KEY_FIRST_RUN"-en balioa true bada, aplikazioaren lehen exekuzioa dela esan nahi du.
     * @return True, lehen aldiz exekutatzen bada; False, beste batzuetan exekutatzen bada.
     */
   private boolean isFirstRun() {
        SharedPreferences preferences = getSharedPreferences("Datuak_kargatu", Context.MODE_PRIVATE);
        return preferences.getBoolean("KEY_FIRST_RUN", true);
   }

    /**
     * Aplikazioa lehenago ere exekutatu dela adierazteko erabiltzen da.
     * Lehenago exekutatu bada, "KEY_FIRST_RUN"-en balioa false bezala ezartzen da.
     */
    private void markFirstRun() {
        SharedPreferences preferences = getSharedPreferences("Datuak_kargatu", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("KEY_FIRST_RUN", false);
        editor.apply();
    }
}