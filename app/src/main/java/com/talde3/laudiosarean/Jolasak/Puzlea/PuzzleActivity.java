package com.talde3.laudiosarean.Jolasak.Puzlea;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.talde3.laudiosarean.Jolasak.Laberintoa.Laberintoa;
import com.talde3.laudiosarean.MainActivity;
import com.talde3.laudiosarean.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {
    ArrayList<PuzlearenPieza> piezak;
    private int unekoPuntuazioa;
    private long hasierakoDenbora = 0L;
    private TextView txtPuntuazioa;
    private Handler koronoHandler = new Handler();

    //Metodo honek segunduro egiten da, denbora kalkulatzeko eta puntuazizoa unean ikusteko balio du
    private Runnable kronoRunnable = new Runnable() {
        @Override
        public void run() {
            long milisegundoak = System.currentTimeMillis() - hasierakoDenbora;
            int segundoak = (int) (milisegundoak / 1000);
            int minutuak = segundoak / 60;
            segundoak = segundoak % 60;

            TextView txtKronometroa = findViewById(R.id.txtKronometroa);
             txtPuntuazioa = findViewById(R.id.txtPuntuazioa);

            String time = String.format("%02d:%02d", minutuak, segundoak);
            txtKronometroa.setText(time);

            // Actualizar puntuación según el tiempo transcurrido
            unekoPuntuazioa = puntazioaKalkulatu(milisegundoak);
            txtPuntuazioa.setText(String.valueOf((int) unekoPuntuazioa));

            koronoHandler.postDelayed(this, 10);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        final RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        final ImageView imgPuzlea = findViewById(R.id.imgPuzlea);

        imgPuzlea.setImageResource(R.drawable.yermoko_andre_mariaren_santutegia);

        hasierakoDenbora = System.currentTimeMillis();
        koronoHandler.postDelayed(kronoRunnable, 0);

        imgPuzlea.post(new Runnable() {
            @Override
            public void run() {
                ezarriIrudia("yermoko_andre_mari.png", imgPuzlea); // Beti argazki bera izango delako
                piezak = zatituArgazkia();
                TouchListener touchListener = new TouchListener(PuzzleActivity.this);
                // piezen ordena nahastu
                Collections.shuffle(piezak);
                for (PuzlearenPieza pieza : piezak) {
                    pieza.setOnTouchListener(touchListener);
                    relativeLayout.addView(pieza);
                    // pantailaren beheko aldean sortzen dira posizio random batean
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) pieza.getLayoutParams();
                    lParams.leftMargin = new Random().nextInt(relativeLayout.getWidth() - pieza.piezarenZabalera);
                    lParams.topMargin = relativeLayout.getHeight() - pieza.piezarenAltuera;
                    pieza.setLayoutParams(lParams);
                }
            }
        });
    }

    // ezarriIrudia fitxategi bateko irudi bat Android aplikazio baten aktiboetan kargatzea da eta emandako ImageView baten tamainara egokitzeko.
    private void ezarriIrudia(String assetName, ImageView imageView) {
        // Lortu Ikuspegiaren neurriak
        int zabalera = imageView.getWidth();
        int altuera = imageView.getHeight();

        AssetManager am = getAssets();
        try {
            InputStream is = am.open("img/" + assetName);
            // Lortu bitmaparen dimentsioak
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            int argazkiarenZabalera = bmOptions.outWidth;
            int argazkiarenAltuera = bmOptions.outHeight;

            // Irudia zenbat eskalatu behar den zehaztu
            int eskala = Math.min(argazkiarenZabalera/zabalera, argazkiarenAltuera/altuera);

            is.reset();

            // Irudi fitxategia deskodetu Ikuspegia betetzeko tamainako Bitmap batean
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = eskala;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // zatituArgazkia()  irudi bat hartzen du, zatitan banatzen du errenkada eta zutabe kopuru jakin batean oinarrituta,
    // eta puzzle bisual bat aplikatzen dio pieza bakoitzari array batean itzuli aurretik.
    private ArrayList<PuzlearenPieza> zatituArgazkia() {
        int errenkadak = 5;
        int zutabeak = 4;

        int piezaTotalak  = errenkadak*zutabeak;


        ImageView imgPuzlea  = findViewById(R.id.imgPuzlea);
        ArrayList<PuzlearenPieza> pieces = new ArrayList<>(piezaTotalak );

        // Jatorri-irudiaren bitmap eskalatua lortu
        BitmapDrawable drawable = (BitmapDrawable) imgPuzlea.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int[] dimentsioak = posizioaLortuBitmapImageViewBarruan(imgPuzlea);
        int eskalatuBitmapEzkerra  = dimentsioak[0];
        int eskalatuBitmapGoian  = dimentsioak[1];
        int eskalatuBitmapZabalera  = dimentsioak[2];
        int eskalatuBitmapAltuera  = dimentsioak[3];

        int moztutakoArgazkiZabalera = eskalatuBitmapZabalera - 2 * abs(eskalatuBitmapEzkerra);
        int moztutakoArgazkiAltuera = eskalatuBitmapAltuera - 2 * abs(eskalatuBitmapGoian);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, eskalatuBitmapZabalera , eskalatuBitmapAltuera , true);
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, abs(eskalatuBitmapEzkerra ), abs(eskalatuBitmapGoian ), moztutakoArgazkiZabalera, moztutakoArgazkiAltuera);

        // Kalkulatu piezen zabalera eta altuera
        int piezaZabalera = moztutakoArgazkiZabalera/zutabeak;
        int piezaAltuera = moztutakoArgazkiAltuera/errenkadak;

        // Sortu bitmap zati bakoitza eta gehitu ondoriozko arrayra
        int yCoord = 0;
        for (int errenkada = 0; errenkada < errenkadak; errenkada++) {
            int xCoord = 0;
            for (int errenka = 0; errenka < zutabeak; errenka++) {
                //kalkulatu pieza bakoitzaren desplazamenduae
                int desplazamenduaX = 0;
                int desplazamenduaY = 0;
                if (errenka > 0) {
                    desplazamenduaX  = piezaZabalera / 3;
                }
                if (errenkada > 0) {
                    desplazamenduaY  = piezaAltuera / 3;
                }

                // desplazamendua aplikatu pieza bakoitzari
                Bitmap piezaBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - desplazamenduaX , yCoord - desplazamenduaY , piezaZabalera + desplazamenduaX , piezaAltuera + desplazamenduaY );
                PuzlearenPieza piece = new PuzlearenPieza(getApplicationContext());
                piece.setImageBitmap(piezaBitmap);
                piece.xKordenatua = xCoord - desplazamenduaX  + imgPuzlea.getLeft();
                piece.yKordenatua = yCoord - desplazamenduaY  + imgPuzlea.getTop();
                piece.piezarenZabalera = piezaZabalera + desplazamenduaX ;
                piece.piezarenAltuera = piezaAltuera + desplazamenduaY ;

                // bitmap honek gure azken puzzlearen irudia edukiko du
                Bitmap puzzlePieza = Bitmap.createBitmap(piezaZabalera + desplazamenduaX , piezaAltuera + desplazamenduaY , Bitmap.Config.ARGB_8888);

                int bumpSize = piezaAltuera / 4;
                Canvas canvas = new Canvas(puzzlePieza);
                Path path = new Path();
                path.moveTo(desplazamenduaX , desplazamenduaY );
                if (errenkada == 0) {
                    path.lineTo(piezaBitmap.getWidth(), desplazamenduaY );
                } else {
                    path.lineTo(desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 3, desplazamenduaY );
                    path.cubicTo(desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 6, desplazamenduaY  - bumpSize, desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 6 * 5, desplazamenduaY  - bumpSize, desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 3 * 2, desplazamenduaY );
                    path.lineTo(piezaBitmap.getWidth(), desplazamenduaY );
                }

                if (errenka == zutabeak - 1) {
                    path.lineTo(piezaBitmap.getWidth(), piezaBitmap.getHeight());
                } else {
                    path.lineTo(piezaBitmap.getWidth(), desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 3);
                    path.cubicTo(piezaBitmap.getWidth() - bumpSize,desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 6, piezaBitmap.getWidth() - bumpSize, desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 6 * 5, piezaBitmap.getWidth(), desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 3 * 2);
                    path.lineTo(piezaBitmap.getWidth(), piezaBitmap.getHeight());
                }

                if (errenkada == errenkadak - 1) {
                    path.lineTo(desplazamenduaX , piezaBitmap.getHeight());
                } else {
                    path.lineTo(desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 3 * 2, piezaBitmap.getHeight());
                    path.cubicTo(desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 6 * 5,piezaBitmap.getHeight() - bumpSize, desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 6, piezaBitmap.getHeight() - bumpSize, desplazamenduaX  + (piezaBitmap.getWidth() - desplazamenduaX ) / 3, piezaBitmap.getHeight());
                    path.lineTo(desplazamenduaX , piezaBitmap.getHeight());
                }

                if (errenka == 0) {
                    path.close();
                } else {
                    path.lineTo(desplazamenduaX , desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 3 * 2);
                    path.cubicTo(desplazamenduaX  - bumpSize, desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 6 * 5, desplazamenduaX  - bumpSize, desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 6, desplazamenduaX , desplazamenduaY  + (piezaBitmap.getHeight() - desplazamenduaY ) / 3);
                    path.close();
                }

                Paint paint = new Paint();
                paint.setColor(0XFF000000);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(piezaBitmap, 0, 0, paint);

                // ertz zuri bat marraztu
                Paint border = new Paint();
                border.setColor(0X80FFFFFF);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(8.0f);
                canvas.drawPath(path, border);

                // ertz beltz bat marraztu
                border = new Paint();
                border.setColor(0X80000000);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // sortzen den bitmap-a ezarri piezari
                piece.setImageBitmap(puzzlePieza);

                pieces.add(piece);
                xCoord += piezaZabalera;
            }
            yCoord += piezaAltuera;
        }

        return pieces;
    }


    private int[] posizioaLortuBitmapImageViewBarruan(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Lortu irudiaren neurriak
        // Lortu balioak irudi-matrizetik eta jarri array batean
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Atera eskala-balioak konstanteak erabiliz (aspektu-erlazioa mantentzen bada, scaleX == scaleY)
        final float Xeskala = f[Matrix.MSCALE_X];
        final float Yeskala = f[Matrix.MSCALE_Y];

        // Lortu drawable (marrazgarriaren atzean bitmapa ere lor dezakezu eta bere zabalera/altuera lor dezakezu)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Benetako neurriak kalkulatu
        final int actW = Math.round(origW * Xeskala);
        final int actH = Math.round(origH * Yeskala);

        ret[2] = actW;
        ret[3] = actH;

        // Irudiaren posizioa lortu
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int goian = (int) (imgViewH - actH) / 2;
        int ezkerrean = (int) (imgViewW - actW) / 2;

        ret[0] = ezkerrean;
        ret[1] = goian;

        return ret;
    }


    public void egiaztatuJokuAmaiera() {
        if (amaitutaDago()) {
            koronoHandler.removeCallbacks(kronoRunnable);
            erakutsiMezua(txtPuntuazioa);
        }
    }

    private int puntazioaKalkulatu (long totalTimeInMillis) {
        int maxPuntuazioa = 10000;
        int milisegundoak = (int) totalTimeInMillis;
        int puntuazioa;

        if (milisegundoak <= 10000){
            puntuazioa=10000;
        } else if(milisegundoak<=20000){
            puntuazioa = maxPuntuazioa - ((milisegundoak-10000)*128)/1000;
        } else if(milisegundoak<=30000){
            puntuazioa = maxPuntuazioa - 1280 - ((milisegundoak-20000)*64)/1000;
        } else{
            puntuazioa = maxPuntuazioa -  1920 - ((milisegundoak-30000)*32)/1000;
        }
        if (puntuazioa < 0) {
            puntuazioa = 0;
        }
        return puntuazioa;
    }

    private boolean amaitutaDago() {
        for (PuzlearenPieza pieza : piezak) {
            if (pieza.mugitu) {
                return false;
            }
        }
        return true;
    }

    private void erakutsiMezua(TextView puntuaizoa) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(PuzzleActivity.this).inflate(R.layout.zorionak_dialog, null);
        Button successDone = view.findViewById(R.id.successDone);
        Button berriroJolastu = view.findViewById(R.id.berriroJolastu);

        // Obtener la referencia correcta de successDesc desde la vista inflada 'view'
        TextView successDesc = view.findViewById(R.id.successDesc);
        TextView successTitle= view.findViewById(R.id.successTitle);

        if (successDesc != null) {
            String puntuaizoText = puntuaizoa.getText().toString();
            successDesc.setText("Hau izan da zure puntuazioa " + puntuaizoText + "!!");
            int puntuaizoInt = Integer.parseInt(puntuaizoText);
            if(puntuaizoInt>8000) {
                successTitle.setText("Hobeezina!!!");
            } else if (puntuaizoInt>6000) {
                successTitle.setText("Oso ondo!!");
            } else if (puntuaizoInt>3500) {
                successTitle.setText("Ondo!");
            } else {
                successTitle.setText("Hurrengoan hobeto egingo duzu!");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


        successDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });

        berriroJolastu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                hasierakoDenbora = System.currentTimeMillis();
                koronoHandler.postDelayed(kronoRunnable, 0);
                puzzleBerrezarri();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }

    private void puzzleBerrezarri() {
        piezak = zatituArgazkia();
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.removeAllViews(); // Elimina las piezas anteriores del RelativeLayout
        TouchListener touchListener = new TouchListener(PuzzleActivity.this);
        // piezen ordena nahastu
        Collections.shuffle(piezak);
        for (PuzlearenPieza pieza : piezak) {
            pieza.setOnTouchListener(touchListener);
            relativeLayout.addView(pieza);
            // pantailaren beheko aldean sortzen dira posizio random batean
            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) pieza.getLayoutParams();
            lParams.leftMargin = new Random().nextInt(relativeLayout.getWidth() - pieza.piezarenZabalera);
            lParams.topMargin = relativeLayout.getHeight() - pieza.piezarenAltuera;
            pieza.setLayoutParams(lParams);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        koronoHandler.removeCallbacks(kronoRunnable);
    }
}
