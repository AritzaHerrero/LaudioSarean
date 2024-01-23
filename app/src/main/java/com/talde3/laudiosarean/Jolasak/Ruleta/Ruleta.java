package com.talde3.laudiosarean.Jolasak.Ruleta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.talde3.laudiosarean.Jolasak.Kruzigrama.Kruzigrama;
import com.talde3.laudiosarean.LoginActivity;
import com.talde3.laudiosarean.MainActivity;
import com.talde3.laudiosarean.R;

import java.util.Random;

public class Ruleta extends AppCompatActivity {

    Button button;
    TextView textView;
    ImageView iv_wheel;

    Random r;

    int degree = 0, degree_old = 0;

    private static final float FACTOR = 4.86f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        iv_wheel = findViewById(R.id.iv_wheel);

        r = new Random();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(degree_old, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        textView.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        currentNumber(360 - (degree % 360));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                iv_wheel.startAnimation(rotate);
            }
        });
    }
    private void currentNumber(int degrees) {
        String text = "";
        Log.d("TAG", "DEGREES " + degrees);
        if ((degrees >= (FACTOR * 1) && degrees < (FACTOR * 5.3)) || degrees == 360) {
            text = "Lamuza jauregia";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 5.3) && degrees < (FACTOR * 16)) {
            text = "Lezeagako sorgina";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 16) && degrees < (FACTOR * 26.5)) {
            text = "Yermoko Andre Mariren Santutegia";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 26.5) && degrees < (FACTOR * 37)) {
            text = "Burdin Hesia";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 37) && degrees < (FACTOR * 47.7)) {
            text = "Santa Aguedako ermita";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 47.7) && degrees < (FACTOR * 58.1)) {
            text = "Katuxako jauregia";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 58.1) && degrees < (FACTOR * 69)) {
            text = "Lamuzako San Pedro eliza";
            alertDialog(text);
        }

        if (degrees >= (FACTOR * 69) && degrees < (FACTOR * 73)) {
            text = "Lamuza jauregia";
            alertDialog(text);
        }

        if ((degrees >= (FACTOR * 73) && degrees < 360) || (degrees >= 0 && degrees < (FACTOR * 1))) {
            text = "Lamuza jauregia";
            alertDialog(text);
        }
    }

    private void alertDialog(String text) {
        View view = LayoutInflater.from(Ruleta.this).inflate(R.layout.ruleta_emaitza_dialog, null);
        Button jolastu = view.findViewById(R.id.jolastu);
        TextView testua = view.findViewById(R.id.successTitle);
        ImageView icon = view.findViewById(R.id.succesImage);

        testua.setText(text);
        String testuaString = (String) testua.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(Ruleta.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        jolastu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ruleta.this, Galderak.class);
                intent.putExtra("Gunea",  testuaString);
                startActivity(intent);
                alertDialog.dismiss();
                button.setEnabled(true);
            }
        });

        alertDialog.show();
    }
}