package com.talde3.laudiosarean.Jolasak.Ruleta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.talde3.laudiosarean.Jolasak.Question;
import com.talde3.laudiosarean.R;

import java.util.ArrayList;
import java.util.List;

public class Galderak extends AppCompatActivity {

    private TextView tvGunea;
    private TextView tvGaldera;
    private RadioGroup rgErantzunak;
    private RadioButton rbErantzuna1;
    private RadioButton rbErantzuna2;
    private RadioButton rbErantzuna3;
    private RadioButton rbErantzuna4;
    private Button btnErantzun;

    private List<Question> yermoAndreMari;
    private List<Question> burdinHesia;
    private List<Question> santaAguedaErmita;
    private List<Question> katuxakoJauregia;
    private List<Question> lamuzaSanPedroEliza;
    private List<Question> lamuzaJauregia;
    private List<Question> lezeagakaoSorgina;
    private int yermoZbk = 0;
    private int burdinZbk = 0;
    private int santaZbk = 0;
    private int katuxaZbk = 0;
    private int lamuzaSanPedroZbk = 0;
    private int lamuzaJauregiaZbk = 0;
    private int lezeagaZbk = 0;

    private List<Question> momentukoGunea;
    private int guneaZbk;

    private boolean erantzuna1;
    private boolean erantzuna2;

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
        btnErantzun = findViewById(R.id.btnErantzun);

        tvGunea.setText(testua);
        String gunea = (String) tvGunea.getText();
        Log.d("TAG", "Gunea: " + gunea);

        yermoAndreMari = new ArrayList<>();
        yermoAndreMari.add(new Question("Zenbat urte inguru ditu Yermoko Andre Mariaren Santutegia?", "5 urte inguru", "50 urte inguru", "5000 urte inguru", "500 urte inguru", "500 urte inguru"));
        yermoAndreMari.add(new Question("Zer mendiren hegalean kokatzen da Yermoko Andre Mariaren Santutegia?", "Gorbean kokatzen da", "Anboton kokatzen da", "Kamaraka mendiaren hegalean", "Ez da kokatzen mendi batean", "Kamaraka mendiaren hegalean"));

        burdinHesia = new ArrayList<>();
        burdinHesia.add(new Question("Zer da Burdin Hesia?", "Hesi defendatzailea", "Hesi erasotzailea", "Hesi apaingarria", "Ez da hesi bat", "Hesi defendatzailea"));
        burdinHesia.add(new Question("Zenbat kilometro luze ditu Burdin Hesiak? ", "10 km", "80 km", "40 km", "60 km", "80 km"));

        santaAguedaErmita = new ArrayList<>();
        santaAguedaErmita.add(new Question("Nori eskeinita dago Santa Aguedako ermita?", "San Fausto", "San Victor", "Santa Teresa", "Santa Agueda", "Santa Agueda"));
        santaAguedaErmita.add(new Question("Non dago Santa Aguedako ermita?", "Araban", "Bizkaian", "Gipuzkoan", "Nafarroan", "Araban"));

        katuxakoJauregia = new ArrayList<>();
        katuxakoJauregia.add(new Question("Katuxako jauregiaren aurpegi nagusia nora begira dago?", "Nerbioi ibaiari begira", "Errepidera begira", "Pareta zuri bati begira", "Zerura begira", "Nerbioi ibaiari begira"));
        katuxakoJauregia.add(new Question("Katuxako jauregia zerez eginda dago?", "Harriz", "Lokatzez", "Egurrez", "Burdinez", "Harriz"));

        lamuzaSanPedroEliza = new ArrayList<>();
        lamuzaSanPedroEliza.add(new Question("Zein da Lamuzako San Pedro elizaren berezitasun nagusia?", "Bere kanpandorrea", "Bere sabaia", "Bere kanapaia", "Bere atea", "Bere kanpandorrea"));
        lamuzaSanPedroEliza.add(new Question("Zer zen Lamuzako San Pedro eliza, eliza bihurtu baino lehen?", "Etxe bat", "Jauregi bat", "Tenplu bat", "Eliza bat izan da beti", "Tenplu bat"));

        lamuzaJauregia = new ArrayList<>();
        lamuzaJauregia.add(new Question("Lamuzako jauregian zenbat zuhaitz-espezie daude?", "5", "79", "35", "17", "79"));
        lamuzaJauregia.add(new Question("Nor edo nortzuk bizi ziren Lamuzako jauregian?", "Errege katolikoak", "Urkixoko markesak", "Laudioko alkateak", "Inor ere ez","Aratuzteetan"));

        lezeagakaoSorgina = new ArrayList<>();
        lezeagakaoSorgina.add(new Question("Zein ospakizunetan parte hartzen du Lezeagako sorgina? ", "Halloweenen", "Aratuzteetan", "Gabonetan", "Aratuzteetan"));
        lezeagakaoSorgina.add(new Question("Zein izan zen Lezagako sorginak desfilatu zuen lehen urtea?", "1982", "1965", "Betidanik", "1982"));

        if (gunea.equals("Yermoko Andre Mariren Santutegia")) {
            momentukoGunea = yermoAndreMari;
            guneaZbk = yermoZbk;
            setGalderak(testua, yermoZbk, yermoAndreMari);
        }
        if (gunea.equals("Burdin Hesia")) {
            momentukoGunea = burdinHesia;
            guneaZbk = burdinZbk;
            setGalderak(testua, burdinZbk, burdinHesia);
        }
        if (gunea.equals("Santa Aguedako ermita")) {
            momentukoGunea = santaAguedaErmita;
            guneaZbk = santaZbk;
            setGalderak(testua, santaZbk, santaAguedaErmita);
        }
        if (gunea.equals("Katuxako jauregia")) {
            momentukoGunea = katuxakoJauregia;
            guneaZbk = katuxaZbk;
            setGalderak(testua, katuxaZbk, katuxakoJauregia);
        }
        if (gunea.equals("Lamuzako San Pedro eliza")) {
            guneaZbk = lamuzaSanPedroZbk;
            momentukoGunea = lamuzaSanPedroEliza;
            setGalderak(testua, lamuzaSanPedroZbk, lamuzaSanPedroEliza);
        }
        if (gunea.equals("Lamuza jauregia")) {
            momentukoGunea = lamuzaJauregia;
            guneaZbk = lamuzaJauregiaZbk;
            setGalderak(testua, lamuzaJauregiaZbk, lamuzaJauregia);
        }
        if (gunea.equals("Lezeagako sorgina")) {
            momentukoGunea = lezeagakaoSorgina;
            guneaZbk = lezeagaZbk;
            setGalderak(testua, lezeagaZbk, lezeagakaoSorgina);
        }

        btnErantzun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int aukeratutakoRadioButtonId = rgErantzunak.getCheckedRadioButtonId();

                if (aukeratutakoRadioButtonId != -1 && guneaZbk <= 1) {
                    RadioButton aukeratutakoRadioButton = findViewById(aukeratutakoRadioButtonId);
                    String aukeratutakoErantzuna = aukeratutakoRadioButton.getText().toString();
                    String erantzunZuzena = momentukoGunea.get(guneaZbk).getCorrectAnswer();
                    aukeratutakoRadioButton.setChecked(false);
                    if (aukeratutakoErantzuna.equals(erantzunZuzena)) {
                        if (guneaZbk == 0) erantzuna1 = true;
                        if (guneaZbk == 1) erantzuna2 = true;
                    } else {
                        if (guneaZbk == 0) erantzuna1 = false;
                        if (guneaZbk == 1) erantzuna2 = false;
                    }
                    // indizea gehitzen du eta galdera/erantzunak eguneratzen dira
                    if (guneaZbk < momentukoGunea.size() - 1) {
                        guneaZbk++;
                        zbkGehitu(momentukoGunea);
                        setGalderak(tvGunea.getText().toString(), guneaZbk, momentukoGunea);
                    } else {
                        // Alert dailog zabaltzen da galderak bukatzean
                        alertDialog(erantzuna1, erantzuna2);
                    }
                } else {
                    // Erantzuna aukeratu gabe
                    Log.d("TAG", "Aukeratu erantzuna");
                }
            }
        });
    }

    public void setGalderak(String guneIzena, int guneaZbk, List<Question> galderakList) {
        Log.d("TAG", "Galdera " + galderakList.get(guneaZbk).getQuestion());
        if (guneIzena.equals("Lezeagako sorgina")) {
            rbErantzuna4.setEnabled(false);
            rbErantzuna4.setVisibility(View.GONE);
        }
        if (guneaZbk == 0) {
            Log.d("TAG", "Galdera2 " + galderakList.get(guneaZbk).getQuestion());
            tvGaldera.setText(galderakList.get(guneaZbk).getQuestion());
            rbErantzuna1.setText(galderakList.get(guneaZbk).getOptionA());
            rbErantzuna2.setText(galderakList.get(guneaZbk).getOptionB());
            rbErantzuna3.setText(galderakList.get(guneaZbk).getOptionC());
            rbErantzuna4.setText(galderakList.get(guneaZbk).getOptionD());
        }
        if (guneaZbk == 1) {
            tvGaldera.setText(galderakList.get(guneaZbk).getQuestion());
            rbErantzuna1.setText(galderakList.get(guneaZbk).getOptionA());
            rbErantzuna2.setText(galderakList.get(guneaZbk).getOptionB());
            rbErantzuna3.setText(galderakList.get(guneaZbk).getOptionC());
            rbErantzuna4.setText(galderakList.get(guneaZbk).getOptionD());
        }
        if ( guneaZbk >= 2) {
            tvGaldera.setText(guneIzena + " " + getResources().getString(R.string.gunekoErantzunGuztiak));
        }
    }

    public int zbkGehitu (List<Question> momentukoGunea) {
        if (momentukoGunea == yermoAndreMari) {
            yermoZbk++;
        }
        if (momentukoGunea == burdinHesia) {
            burdinZbk++;
        }
        if (momentukoGunea == santaAguedaErmita) {
            santaZbk++;
        }
        if (momentukoGunea == katuxakoJauregia) {
            katuxaZbk++;
        }
        if (momentukoGunea == lamuzaSanPedroEliza) {
            lamuzaSanPedroZbk++;
        }
        if (momentukoGunea == lamuzaJauregia) {
            lamuzaJauregiaZbk++;
        }
        if (momentukoGunea == lezeagakaoSorgina) {
            lezeagaZbk++;
        }

        return 1;
    }

    private void alertDialog(boolean erantzuna1, boolean erantzuna2) {
        View view = LayoutInflater.from(Galderak.this).inflate(R.layout.ruleta_dialog, null);
        Button bukatu = view.findViewById(R.id.bukatu);
        TextView galdera1 = view.findViewById(R.id.galdera1);
        TextView galdera2 = view.findViewById(R.id.galdera2);
        ImageView icon = view.findViewById(R.id.succesImage);

        if (erantzuna1) galdera1.setText(getResources().getString(R.string.galderaZuzena1)); else galdera1.setText(getResources().getString(R.string.galderaOkerra1));
        if (erantzuna2) galdera2.setText(getResources().getString(R.string.galderaZuzena2)); else galdera2.setText(getResources().getString(R.string.galderaOkerra2));

        if (erantzuna1 && erantzuna2) {
            icon.setImageResource(R.drawable.check_circle);
        } else if ((erantzuna1 && !erantzuna2) || (!erantzuna1 && erantzuna2)) {
            icon.setImageResource(R.drawable.help_circle);
        } else {
            icon.setImageResource(R.drawable.cancel_circle);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Galderak.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        bukatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        alertDialog.show();
    }
}