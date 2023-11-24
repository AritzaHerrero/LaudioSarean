package com.talde3.laudiosarean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;

public class Erregistroa extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEposta;
    private EditText etIzena;
    private EditText etAbizena;
    private EditText etPasahitza1;
    private EditText etPasahitza2;
    private Spinner spinnerKlasea;
    private Button btnErregistratu;
    private String eposta;
    private String izena;
    private String abizenak;
    private String pasahitza1;
    private String pasahitza2;
    private String klasea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erregistroa);

        mAuth = FirebaseAuth.getInstance();

        etEposta = findViewById(R.id.etEposta);
        etIzena = findViewById(R.id.etIzena);
        etAbizena = findViewById(R.id.etAbizena);
        etPasahitza1 = findViewById(R.id.etPasahitza1);
        etPasahitza2 = findViewById(R.id.etPasahitza2);
        spinnerKlasea = findViewById(R.id.spinnerKlasea);
        btnErregistratu = findViewById(R.id.btnErregistratu);

        ArrayList<String> klaseak = new ArrayList<>();
        klaseak.add("DAM 1");
        klaseak.add("DAW 1");
        klaseak.add("DAM 2");
        klaseak.add("DAW 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, klaseak);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKlasea.setAdapter(adapter);

        spinnerKlasea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 // Aukeratutakoa
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // Aukera gabe
             }
         });

        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eposta = etEposta.getText().toString();
                izena = etIzena.getText().toString();
                abizenak = etAbizena.getText().toString();
                pasahitza1 = etPasahitza1.getText().toString();
                pasahitza2 = etPasahitza2.getText().toString();

                String trimIzena = izena.trim();
                //abizeneri trim eta array batean gordetzen da. Lehen abizena lehen arrayean bigarrena bigarrenean.
                String[] trimAbizenak = abizenak.trim().split("\\s+");

                // Emaila konprobatzen du android-en metodoekin.
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(eposta).matches()) {
                    String erEmail = getResources().getString(R.string.erEposta);
                    etEposta.setError(erEmail);
                }
                // izena beteta dagoen ikusten du,
                else if (trimIzena.isEmpty()) {
                    String erIzena = getResources().getString(R.string.erIzena);
                    etIzena.setError(erIzena);
                }
                // Zenbat array dauden konprobatzen du abizen kopuruak jakiteko.
                else if (trimAbizenak.length < 2) {
                    String erAbizenak = getResources().getString(R.string.erAbizenak);
                    etAbizena.setError(erAbizenak);
                }
                // pasahitzak ez daudela hutsik konprobatzen du.
                else if (pasahitza1.isEmpty()) {
                    String erPasahitza1 = getResources().getString(R.string.erPasahitza1);
                    etPasahitza1.setError(erPasahitza1);
                }
                else if (pasahitza2.isEmpty()){
                    String erPasahitza1 = getResources().getString(R.string.erPasahitza1);
                    etPasahitza2.setError(erPasahitza1);
                }
                // Bi pasahitzak berdinak diren konprobatzen du.
                else if (!pasahitza1.equals(pasahitza2)) {
                    Toast.makeText(Erregistroa.this, getResources().getString(R.string.erPasahitza2), Toast.LENGTH_SHORT).show();
                }
                // Dena zuzen badago erregistroa egiten da.
                else {
                    klasea = spinnerKlasea.getSelectedItem().toString();

                    mAuth.createUserWithEmailAndPassword(eposta, pasahitza1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Erregistroa.this, "Zure kontua sortu da", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Erregistroa.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                // Erregistro okerra. Errore konkretuentzako mezu desberdinekin.
                                String erroreMezua = getResources().getString(R.string.erOkerra);

                                // Zein izan den errorea.
                                Exception exception = task.getException();
                                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Pasaitzak ez du formatu ona.
                                    String erPasahitzFormatua = getResources().getString(R.string.erPasahitzFormatua);
                                    erroreMezua += erPasahitzFormatua;
                                } else if (exception instanceof FirebaseAuthUserCollisionException) {
                                    // E-posta erregistratuta dago
                                    String erEpostaErregistratuta = getResources().getString(R.string.erEpostaErregistratuta);
                                    erroreMezua += erEpostaErregistratuta;
                                } else if (exception instanceof FirebaseNetworkException) {
                                    // Internet konexio gabe
                                    String erInternet = getResources().getString(R.string.erInternet);
                                    erroreMezua += erInternet;
                                } else {
                                    // Beste errore batzuk.
                                    String erEzezaguna = getResources().getString(R.string.erEzezaguna);
                                    erroreMezua += erEzezaguna + exception.getMessage();
                                }
                                Toast.makeText(Erregistroa.this, erroreMezua, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}