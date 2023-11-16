package com.talde3.laudiosarean;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class Erregistroa extends AppCompatActivity {


    private Spinner spinnerKlasea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erregistroa);

        spinnerKlasea = findViewById(R.id.spinnerKlasea);

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
    }
}