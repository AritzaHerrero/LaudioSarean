package com.talde3.laudiosarean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.talde3.laudiosarean.Room.Dao.IkasleaDao;
import com.talde3.laudiosarean.Room.Datubase;
import com.talde3.laudiosarean.Room.Entities.Erabiltzailea;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEposta;
    private EditText etPasahitza;
    private Button btnSaioahasi;
    private CheckBox cbPasahitzaGorde;
    private TextView tvErregistratuEmen;
    private ImageButton ibPasahitza;
    private ProgressBar pbKarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Datubase db = Datubase.getInstance(getApplicationContext());

        Ikaslea e = new Ikaslea("12345678A", "Aingeru", "Siranaula", "aingeru@gmail.com", "12345678", "1");
        IkasleaDao eDAO = db.ikasleaDao();
        eDAO.insertAll(e);

        etEposta = findViewById(R.id.etEposta);
        etPasahitza = findViewById(R.id.etPasahitza);
        btnSaioahasi = findViewById(R.id.btnSaioahasi);
        tvErregistratuEmen = findViewById(R.id.tvErregistratuEmen);
        ibPasahitza = findViewById(R.id.ibPasahitza);
        ibPasahitza = findViewById(R.id.ibPasahitza);
        cbPasahitzaGorde = findViewById(R.id.cbPasahitzaGorde);
        pbKarga = findViewById(R.id.pbKarga);

        mAuth = FirebaseAuth.getInstance();
        lehentasunakKargatu();

        aktibatuUI();
        btnSaioahasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        tvErregistratuEmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se hace clic en el TextView
                Intent erregistroIntent = new Intent(LoginActivity.this, Erregistroa.class);
                startActivity(erregistroIntent);
            }
        });

        ibPasahitza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    private void saioaHasi(String eposta, String pasahitza) {
        desaktibatuUI();
        mAuth.signInWithEmailAndPassword(eposta, pasahitza)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Saioa ondo hasi da
                            cbPasahitzaGorde = findViewById(R.id.cbPasahitzaGorde);
                            if(cbPasahitzaGorde.isChecked()) {
                                lehentasunakGorde();
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
                    }
                });
    }

    private void lehentasunakKargatu() {
        // kredentzialak.xml kargatzen du (lehenengo aldia bada 'kredentzialak.xml' sortzen du)
        SharedPreferences preferences = getSharedPreferences("kredentzialak", Context.MODE_PRIVATE);

        // xml barruan 'user'-ean eta 'pass'-ean gordetako informazioa gordetzen du.
        String eposta = preferences.getString("user", "");
        String pasahitza = preferences.getString("pass", "");

        // editText-etan ipintzen du kargatutako informazioa
        etEposta.setText(eposta);
        etPasahitza.setText(pasahitza);
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
        editor.commit();
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
}