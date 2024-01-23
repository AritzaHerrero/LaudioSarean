package com.talde3.laudiosarean.Jolasak.Ruleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        burdinHesia = new ArrayList<>();
        burdinHesia.add(new Question("Zer da Burdin Hesia?", "Hesi defendatzailea", "Hesi erasotzailea", "Hesi apaingarria", "Ez da hesi bat", "Hesi defendatzailea"));
        burdinHesia.add(new Question("Zenbat kilometro luze ditu Burdin Hesiak? ", "10 km", "80 km", "40 km", "60 km", "80 km"));

        santaAguedaErmita = new ArrayList<>();
        santaAguedaErmita.add(new Question("Nori eskeinita dago Santa Aguedako ermita?", "San Fausto", "San Victor", "Santa Teresa", "Santa Agueda", "Santa Agueda"));
        santaAguedaErmita.add(new Question("Non dago da Santa Aguedako ermita?", "Araban", "Bizkaian", "Gipuzkoan", "Nafarroan", "Araban"));

        katuxakoJauregia = new ArrayList<>();
        katuxakoJauregia.add(new Question("Katuxako jauregiaren aurpegi nagusia nora begira dago?", "Bere kanpandorrea", "Bere sabaia", "Bere kanapaia", "Bere atea", "Bere kanpandorrea"));
        katuxakoJauregia.add(new Question("Zer zen Lamuzako San Pedro eliza, eliza bihurtu baino lehen? ", "Etxe bat", "Jauregi bat", "Tenplu bat", "Eliza bat izan da beti", "Tenplu bat"));

        lamuzaSanPedroEliza = new ArrayList<>();
        lamuzaSanPedroEliza.add(new Question("Zein da Lamuzako San Pedro elizaren berezitasun nagusia?", "Bere kanpandorrea", "Bere sabaia", "Bere kanapaia", "Bere atea", "Bere kanpandorrea"));
        lamuzaSanPedroEliza.add(new Question("Zer zen Lamuzako San Pedro eliza, eliza bihurtu baino lehen?", "Etxe bat", "Jauregi bat", "Laudioko alkateak", "Inor ere ez", "Urkixoko markesek"));

        lamuzaJauregia = new ArrayList<>();
        lamuzaJauregia.add(new Question("Lamuzako jauregian zenbat zuhaitz-espezie daude?", "5", "79", "35", "17", "79"));
        lamuzaJauregia.add(new Question("Nor edo nortzuk bizi ziren Lamuzako jauregian?", "Errege katolikoak", "Urkixoko markesek", "Laudioko alkateak", "Inor ere ez", "Urkixoko markesek"));

        lezeagakaoSorgina = new ArrayList<>();
        lezeagakaoSorgina.add(new Question("Zenbat urte inguru ditu Yermoko Andre Mariaren Santutegia?", "5 urte inguru", "50 urte inguru", "5000 urte inguru", "500 urte inguru", "500 urte inguru"));
        lezeagakaoSorgina.add(new Question("Zer mendiren hegalean kokatzen da Yermoko Andre Mariaren Santutegia?", "Gorbean kokatzen da", "Anboton kokatzen da", "Kamaraka mendiaren hegalean", "Ez da kokatzen mendi batean", "Kamaraka mendiaren hegalean"));


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