package com.talde3.laudiosarean;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde3.laudiosarean.Room.Datubase;
import com.talde3.laudiosarean.Room.Entities.Errekor;
import com.talde3.laudiosarean.Room.Entities.Gunea;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static Datubase db;
    public static FirebaseFirestore firestore;
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

        if(intent != null) {
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                Ikaslea ikaslea = (Ikaslea) bundle.getSerializable("ikaslea");
                db.ikasleaDao().insert(ikaslea);
                firestore.collection("Ikasleak").document(ikaslea.getEmail()).set(ikaslea);
            }
        }

        /*try {
            File currentDB = getDatabasePath("LaudioDB");
            File externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File backupDB = new File(externalStorageDir, "LaudioDB.db");

            Datubase.exportDatabase(currentDB, backupDB);
        } catch (IOException e2) {
            e2.printStackTrace();
        }*/

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

        tvErregistratuEmen.setOnClickListener(v -> {
            // Acción a realizar cuando se hace clic en el TextView
            Intent erregistroIntent = new Intent(LoginActivity.this, Erregistroa.class);
            startActivity(erregistroIntent);
        });

        ibPasahitza.setOnClickListener(v -> {
            int currentInputType = etPasahitza.getInputType();

            if (currentInputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etPasahitza.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etPasahitza.setTypeface(Typeface.DEFAULT);
                ibPasahitza.setImageResource(R.drawable.begia_off_24);
            } else {
                etPasahitza.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPasahitza.setTypeface(Typeface.DEFAULT);
                ibPasahitza.setImageResource(R.drawable.begia_24);
            }

            // Mover el cursor al final del texto para mantener la visibilidad
            etPasahitza.setSelection(etPasahitza.getText().length());
        });
    }

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
                            // Saio-hasierako kredentzial baliogabeak (eposta edp pasahitz baliogabeak)
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erPasahitzaLogin), Toast.LENGTH_SHORT).show();
                        } else {
                            // Otro error no manejado específicamente
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erLoginLogin), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

   private List<String> lehentasunakKargatu() {
        List<String> kredentzialak = new ArrayList<>();
        // kredentzialak.xml kargatzen du (lehenengo aldia bada 'kredentzialak.xml' sortzen du)
        SharedPreferences preferences = getSharedPreferences("kredentzialak", Context.MODE_PRIVATE);

        // xml barruan 'user'-ean eta 'pass'-ean gordetako informazioa gordetzen du.
        String eposta = preferences.getString("user", "");
        String pasahitza = preferences.getString("pass", "");

        // editText-etan ipintzen du kargatutako informazioa
        etEposta.setText(eposta);
        etPasahitza.setText(pasahitza);

       // Informazioa listan gordetzen da
       kredentzialak.add(eposta);
       kredentzialak.add(pasahitza);

       return kredentzialak;
   }

   private void lehentasunakGorde() {
        // kredentzialak.xml kargatzen da informazioa gordetzeko (lehenengo aldia bada 'kredentzialak.xml' sortzen du)
        SharedPreferences preferences = getSharedPreferences("kredentzialak", Context.MODE_PRIVATE);
        // sartutako erabiltzaile eta pasahitzak eskuratzen dira
        String eposta = etEposta.getText().toString();
        String pasahitza = etPasahitza.getText().toString();

        // xml-an gordetzeko editorea.
        // <string name="user">'erabiltzailea'</string>
        // <string name="pass>'pasahitza'</string>
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", eposta);
        editor.putString("pass", pasahitza);

        // dena gordeko da xml-an.
        editor.apply(); // antes -> editor.commit();
   }

   private void desaktibatuUI() {
        etEposta.setEnabled(false);
        etPasahitza.setEnabled(false);
        btnSaioahasi.setEnabled(false);
        tvErregistratuEmen.setEnabled(false);
        ibPasahitza.setEnabled(false);
        cbPasahitzaGorde.setEnabled(false);
        pbKarga.setVisibility(View.VISIBLE);
   }

   private void aktibatuUI() {
        etEposta.setEnabled(true);
        etPasahitza.setEnabled(true);
        btnSaioahasi.setEnabled(true);
        tvErregistratuEmen.setEnabled(true);
        ibPasahitza.setEnabled(true);
        cbPasahitzaGorde.setEnabled(true);
        pbKarga.setVisibility(View.INVISIBLE);
   }

   private void dbKarga(){
        db.clearAllTables();
        db.ikasleaDao().resetPrimaryKeyAutoIncrementValueIkaslea();
        db.guneaDao().resetPrimaryKeyAutoIncrementValueGunea();
        db.puntuazioaDao().resetPrimaryKeyAutoIncrementValuePuntuazioa();
        db.errekorDao().resetPrimaryKeyAutoIncrementValueErrekor();

        loadIkasleakData();
   }

   private void loadIkasleakData() {
        firestore.collection("Ikasleak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.ikasleaDao().insert(document.toObject(Ikaslea.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            // Después de cargar Ikasleak, carga Guneak
                            loadGuneakData();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
   }

   private void loadGuneakData() {
        firestore.collection("Guneak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.guneaDao().insert(document.toObject(Gunea.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            // Después de cargar Guneak, carga Puntuazioak
                            loadPuntuazioakData();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
   }
   private void loadPuntuazioakData() {
        firestore.collection("Puntuazioak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.puntuazioaDao().insert(document.toObject(Puntuazioa.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            // Después de cargar Puntuazioak, carga Errekorrak
                            loadErrekorData();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
   }

   private void loadErrekorData(){
        firestore.collection("Errekorrak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.errekorDao().insert(document.toObject(Errekor.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
   }

   private boolean isFirstRun() {
        SharedPreferences preferences = getSharedPreferences("Datuak_kargatu", Context.MODE_PRIVATE);
        return preferences.getBoolean("KEY_FIRST_RUN", true);
   }

    private void markFirstRun() {
        SharedPreferences preferences = getSharedPreferences("Datuak_kargatu", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("KEY_FIRST_RUN", false);
        editor.apply();
    }
}