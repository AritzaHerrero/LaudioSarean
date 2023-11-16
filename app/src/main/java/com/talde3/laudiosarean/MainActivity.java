package com.talde3.laudiosarean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEposta;
    private EditText etPasahitza;
    private Button btnSaioahasi;
    private CheckBox cbPasahitzaGorde;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEposta = findViewById(R.id.etEposta);
        etPasahitza = findViewById(R.id.etPasahitza);
        btnSaioahasi = findViewById(R.id.btnSaioahasi);

        mAuth = FirebaseAuth.getInstance();
        lehentasunakKargatu();
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
    }

    private void saioaHasi(String eposta, String pasahitza) {
        mAuth.signInWithEmailAndPassword(eposta, pasahitza)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Saioa ondo hasi da
                            cbPasahitzaGorde = findViewById(R.id.cbPasahitzaGorde);
                            if(cbPasahitzaGorde.isChecked()) {
                                lehentasunakGorde();
                            } else {
                                // Toast.makeText(MainActivity.this, "Ez gorde", Toast.LENGTH_SHORT).show();
                            }
                            // Toast.makeText(MainActivity.this, getResources().getString(R.string.ongiEtorri), Toast.LENGTH_SHORT).show();
                        } else {
                            Exception exception = task.getException();

                            if (exception instanceof FirebaseNetworkException) {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.erKonexioaLogin), Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                // Eposta ez da aurkitu
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.erEpostaLogin), Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                // Saio-hasierako kredentzial baliogabeak (eposta edp pasahitz baliogabeak)
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.erPasahitzaLogin), Toast.LENGTH_SHORT).show();
                            } else {
                                // Otro error no manejado específicamente
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.erLoginLogin), Toast.LENGTH_SHORT).show();
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
}