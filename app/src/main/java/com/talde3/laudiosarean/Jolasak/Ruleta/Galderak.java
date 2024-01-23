package com.talde3.laudiosarean.Jolasak.Ruleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.talde3.laudiosarean.R;

import java.util.ArrayList;
import java.util.List;

public class Galderak extends AppCompatActivity {

    TextView tvGunea;
    TextView tvGaldera;
    RadioGroup rgErantzunak;
    RadioButton rbErantzuna1;
    RadioButton rbErantzuna2;
    RadioButton rbErantzuna3;
    RadioButton rbErantzuna4;

    int yermoZbk = 0;
    private List<Question> yermoAndreMari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galderak2);

        Intent intent = getIntent();
        String testua = intent.getStringExtra("Gunea");


        tvGunea = findViewById(R.id.tvGunea);
        tvGaldera = findViewById(R.id.tvGaldera);
        rgErantzunak = findViewById(R.id.rgErantzunak);
        rbErantzuna1 = findViewById(R.id.rbErantzuna1);
        rbErantzuna2 = findViewById(R.id.rbErantzuna2);
        rbErantzuna3 = findViewById(R.id.rbErantzuna3);
        rbErantzuna4 = findViewById(R.id.rbErantzuna4);

        tvGunea.setText(testua);

        yermoAndreMari = new ArrayList<>();
        yermoAndreMari.add(new Question("Zenbat urte inguru ditu Yermoko Andre Mariaren Santutegia?", "5 urte inguru", "50 urte inguru", "5000 urte inguru", "500 urte inguru", "500 urte inguru"));
        yermoAndreMari.add(new Question("Zer mendiren hegalean kokatzen da Yermoko Andre Mariaren Santutegia?", "Gorbean kokatzen da", "Anboton kokatzen da", "Kamaraka mendiaren hegalean", "Ez da kokatzen mendi batean", "Kamaraka mendiaren hegalean"));

        if (testua.equals("Yermoko Andre Mariren Santutegia") && yermoZbk == 0) {

            tvGaldera.setText(yermoAndreMari.get(yermoZbk).getQuestion());
            rbErantzuna1.setText(yermoAndreMari.get(yermoZbk).getOptionA());
            rbErantzuna2.setText(yermoAndreMari.get(yermoZbk).getOptionB());
            rbErantzuna3.setText(yermoAndreMari.get(yermoZbk).getOptionC());
            rbErantzuna4.setText(yermoAndreMari.get(yermoZbk).getOptionD());
        }

        if (testua.equals("Yermoko Andre Mariren Santutegia") && yermoZbk == 1) {
            tvGaldera.setText(yermoAndreMari.get(yermoZbk).getQuestion());
            rbErantzuna1.setText(yermoAndreMari.get(yermoZbk).getOptionA());
            rbErantzuna2.setText(yermoAndreMari.get(yermoZbk).getOptionB());
            rbErantzuna3.setText(yermoAndreMari.get(yermoZbk).getOptionC());
            rbErantzuna4.setText(yermoAndreMari.get(yermoZbk).getOptionD());
        }

        if (testua.equals("Yermoko Andre Mariren Santutegia") && yermoZbk >= 2) {
            tvGaldera.setText("Yermoko Andre Mariren Santutegiko guneko enatzun guztiak egin dituzu");
        }
    }
}