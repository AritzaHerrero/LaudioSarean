package com.talde3.laudiosarean.Jolasak.TestGune4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.talde3.laudiosarean.Jolasak.GalderaDatuakFragment;
import com.talde3.laudiosarean.Jolasak.Question;
import com.talde3.laudiosarean.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Galderak extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answerRadioGroup;
    private Button submitButton;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 30 * 1000; // 30 segundos en milisegundos
    private TextView txtPuntuazioa;
    private TextView txtKronometroa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galderak);

        txtPuntuazioa = findViewById(R.id.txtPuntuazioa);
        txtKronometroa = findViewById(R.id.txtKronometroa);

        countDownTimer.start();

        questionTextView = findViewById(R.id.questionTextView);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        initializeQuestions();

        // Lehen galdera erakusti
        showQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                detenerCronometro();
            }
        });
    }

    private void initializeQuestions() {
        // Galdera eta erantzunak
        questions = new ArrayList<>();
        questions.add(new Question("Ze mendetan eraiki zen?", "XVII", "XV", "XVIV", "XVII"));
        questions.add(new Question("Ze materialez eraikita nago?", "Burdinez", "Egurrez", "Harriz", "Harriz"));
        questions.add(new Question("Ze motatako eraikina da?", "Erromanikoa", "Barrokoa", "Grekoa", "Barrokoa"));

        // Galderak nahastatu
        Collections.shuffle(questions);

        // Galdera bakoitzaren erantzuna nahastatu
        for (Question question : questions) {
            List<String> options = new ArrayList<>();
            options.add(question.getOptionA());
            options.add(question.getOptionB());
            options.add(question.getOptionC());

            Collections.shuffle(options);

            question.setOptionA(options.get(0));
            question.setOptionB(options.get(1));
            question.setOptionC(options.get(2));
            // Erantzun zuzena ezarri
            options.add(question.getCorrectAnswer());
            question.setCorrectAnswer(options.get(3));
        }
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);

            // Galdera eta erantzunak erakutsi
            questionTextView.setText(currentQuestion.getQuestion());
            ((RadioButton) answerRadioGroup.getChildAt(0)).setText(currentQuestion.getOptionA());
            ((RadioButton) answerRadioGroup.getChildAt(1)).setText(currentQuestion.getOptionB());
            ((RadioButton) answerRadioGroup.getChildAt(2)).setText(currentQuestion.getOptionC());

            // RadioButton guztiak desmarkatu
            answerRadioGroup.clearCheck();
        }
    }

    private void checkAnswer() {
        int selectedRadioButtonId = answerRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            Question currentQuestion = questions.get(currentQuestionIndex);
            String correctAnswer = currentQuestion.getCorrectAnswer();

            // Erantzuna zuzena den begiratu eta zuzena bada dialog berria zabaldu (erantzun_zuzena_dialog)
            if (selectedAnswer.equals(correctAnswer)) {
                View view = LayoutInflater.from(Galderak.this).inflate(R.layout.erantzun_zuzena_dialog, null);
                Button hurrengoGaldera = view.findViewById(R.id.hurrengoGaldera);

                AlertDialog.Builder builder = new AlertDialog.Builder(Galderak.this);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);

                hurrengoGaldera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        currentQuestionIndex++;
                        showQuestion();
                        if (currentQuestionIndex == 3) {
                            finish();
                        }
                    }
                });

                alertDialog.show();
            } else {
                // Erantzuna okerra,dialog berria zabaldu (erantzun_okerra_dialog)
                View view = LayoutInflater.from(Galderak.this).inflate(R.layout.erantzun_okerra_dialog, null);
                Button hurrengoGaldera = view.findViewById(R.id.hurrengoGaldera);

                AlertDialog.Builder builder = new AlertDialog.Builder(Galderak.this);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);

                hurrengoGaldera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reiniciarJokoDatuak();
                        alertDialog.dismiss();
                        currentQuestionIndex++;
                        showQuestion();
                        if (currentQuestionIndex == 3) {
                            finish();
                        }
                    }
                });

                alertDialog.show();
            }
        } else {
            // Ez badu erantzunik aukeratu
            Toast.makeText(this, getString(R.string.erantzunaAukeratu), Toast.LENGTH_SHORT).show();
        }
    }
    public int puntazioaKalkulatu(long totalTimeInMillis) {
        int segundoak = (int) totalTimeInMillis/1000;
        int puntuazioa;
        puntuazioa = segundoak*100 ;

        if (puntuazioa < 0) {
            puntuazioa = 0;
        }

        return puntuazioa;
    }


    public void detenerCronometro() {
        // Detener el temporizador
        countDownTimer.cancel();
    }

    public void reiniciarJokoDatuak() {
        countDownTimer.start();
    }


    private CountDownTimer countDownTimer = new CountDownTimer(hasierakoDenbora, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            hasierakoDenbora = millisUntilFinished;

            int segundoak = (int) (millisUntilFinished / 1000) % 60;

            String time = String.format("%02d", segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo restante
            unekoPuntuazioa = puntazioaKalkulatu(millisUntilFinished);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));
        }
        @Override
        public void onFinish() {
            Toast.makeText(Galderak.this, getString(R.string.denboraGabe), Toast.LENGTH_SHORT).show();
        }
    };

}
