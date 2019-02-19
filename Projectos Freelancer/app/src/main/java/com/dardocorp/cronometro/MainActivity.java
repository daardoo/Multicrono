package com.dardocorp.cronometro;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat(".##");
    ProgressBar pb;
    ProgressBar pb2;

    TextView Tiempo1;
    TextView Tiempo2;
    TextView viTextView,vfTextView,TtTextView,ATextView,EdtTextView,starTextView;
    TextView respuestaFinal;

    ImageButton btn_start1,btn_stop1,btn_restart1;
    ImageButton btn_start2,btn_stop2,btn_restart2;
    ImageButton btn_startall,btn_stopall;
    Button btn_calcular;

    //booleano que permite indicar si los cronometros estan actualmente trabajando
    Boolean correTiempo1=false;
    Boolean correTiempo2=false;

    Double ftiempo1;
    Double ftiempo2;

    Chronometer cronoObj1;
    Chronometer cronoObj2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pb=findViewById(R.id.pbobj1);
        pb2=findViewById(R.id.pbobj2);

        Tiempo1=findViewById(R.id.tiempofinal1);
        Tiempo2=findViewById(R.id.tiempofinal2);
        respuestaFinal= findViewById(R.id.resultadotv);
        viTextView=findViewById(R.id.vitextview);
        vfTextView=findViewById(R.id.vftextview);
        TtTextView=findViewById(R.id.Tttextview);
        ATextView=findViewById(R.id.Atextview) ;
        EdtTextView=findViewById(R.id.Edttextview);
        starTextView=findViewById(R.id.Startextview);

        btn_startall=findViewById(R.id.buttonStartobjall);
        btn_stopall=findViewById(R.id.buttonStoptobjall);


        btn_start1=findViewById(R.id.buttonStartobj1);
        btn_start2=findViewById(R.id.buttonStartobj2);

        btn_stop1=findViewById(R.id.buttonStopobj1);
        btn_stop2=findViewById(R.id.buttonStopobj2);

        btn_calcular=findViewById(R.id.CalcularFormula);

        cronoObj1=findViewById(R.id.viewcontador1);
        cronoObj2=findViewById(R.id.viewcontador2);

        //############################CRONOMETRO 1###############################################

        //Start para el cronometro 1
        btn_start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCronoObj1();
            }
        });

        btn_stop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCronoObj1();
            }
        });



        //############################CRONOMETRO 2###############################################
        //Start para el cronometro 2
        btn_start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCronoObj2();



            }

        });

        btn_stop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCronoObj2();
            }
        });
        //############################CALCULAR RESULTADOS###############################################
        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Double vi=6.28/ftiempo1;
                viTextView.setText("VI ="+vi+"");
                Double vf = 6.28/ftiempo2;
                vfTextView.setText("VF ="+vf+"");
                Double Tt= ftiempo2-ftiempo1;
                TtTextView.setText("TT ="+Tt+"");
                Double A=(vi-vf)/Tt;
                ATextView.setText("A ="+A+"");
                Double Edt=(0-vf)/A;
                EdtTextView.setText("EDT ="+Edt+"");
                Double Estrella=(-1*vi)/Edt;
                starTextView.setText("STR ="+Estrella+"");
                Double res=0+(vi*Edt)+(0.5*(-1*Estrella)*(Edt*Edt));
                respuestaFinal.setText(""+res);
            }
        });

        //############################BOTONES PARA AMBOS CRONOMETROS###############################################

        btn_startall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCronoObj1();
                startCronoObj2();
            }
        });

        btn_stopall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCronoObj1();
                stopCronoObj2();
            }
        });




    }
    //############################Control de boton de volumen###############################################


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action , keycode;

        action= event.getAction();
        keycode = event.getKeyCode();

        switch (keycode){
            case KeyEvent.KEYCODE_VOLUME_UP:
            {
                if(KeyEvent.ACTION_UP==action){

                    ControlParavolumeButtonsCronoObj1();

                }
                break;
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            {
                if(KeyEvent.ACTION_DOWN==action){

                    ControlParavolumeButtonsCronoObj2();
                }
                break;
            }


        }
        return super.dispatchKeyEvent(event);
    }





    //############################CRONOMETRO 1###############################################
    private void startCronoObj1() {
        if (!correTiempo1){
            cronoObj1.setBase(SystemClock.elapsedRealtime());
            cronoObj1.start();
            Drawable drawable = getResources().getDrawable(R.drawable.circle_ob1) ;
            pb.setBackground(drawable);
            correTiempo1=true;

        }
    }
    private void stopCronoObj1() {
        if (correTiempo1){
            Drawable drawable = getResources().getDrawable(R.drawable.circle_obj2) ;
            pb.setBackground(drawable);

            cronoObj1.stop();
            correTiempo1=false;

            long elapsedMillis=showElapsedTime(cronoObj1);
            double temp1=((double)elapsedMillis)/1000;
            String stringelapsedMillis = df2.format(temp1);
            temp1=Double.parseDouble(stringelapsedMillis);
            ftiempo1=temp1;
            Tiempo1.setText("T1 = "+ftiempo1);

        }
    }
    private void ControlParavolumeButtonsCronoObj1() {
        if (!correTiempo1){
            cronoObj1.setBase(SystemClock.elapsedRealtime());
            Drawable drawable = getResources().getDrawable(R.drawable.circle_ob1) ;
            pb.setBackground(drawable);
            cronoObj1.start();
            correTiempo1=true;

        }
        else{

            cronoObj1.stop();
            correTiempo1=false;

            long elapsedMillis=showElapsedTime(cronoObj1);
            double temp1=((double)elapsedMillis)/1000;
            String stringelapsedMillis = df2.format(temp1);
            temp1=Double.parseDouble(stringelapsedMillis);
            ftiempo1=temp1;
            Tiempo1.setText("T1 = "+ftiempo1);
            Drawable drawable = getResources().getDrawable(R.drawable.circle_obj2) ;
            pb.setBackground(drawable);


        }
    }

    //############################CRONOMETRO 2###############################################
    private void startCronoObj2() {
        if (!correTiempo2){
            cronoObj2.setBase(SystemClock.elapsedRealtime());
            cronoObj2.start();
            Drawable drawable = getResources().getDrawable(R.drawable.circle_ob1) ;
            pb2.setBackground(drawable);
            correTiempo2=true;

        }
    }
    private void stopCronoObj2() {
        if (correTiempo2){

            cronoObj2.stop();
            correTiempo2=false;

            long elapsedMillis=showElapsedTime(cronoObj2);
            double temp1=((double)elapsedMillis)/1000;
            String stringelapsedMillis = df2.format(temp1);
            temp1=Double.parseDouble(stringelapsedMillis);
            ftiempo2=temp1;
            Tiempo2.setText("T2 = "+ftiempo2);
            Drawable drawable = getResources().getDrawable(R.drawable.circle_obj2) ;
            pb2.setBackground(drawable);




        }
    }
    private void ControlParavolumeButtonsCronoObj2() {
        if (!correTiempo2){
            cronoObj2.setBase(SystemClock.elapsedRealtime());
            Drawable drawable = getResources().getDrawable(R.drawable.circle_ob1) ;
            pb2.setBackground(drawable);
            cronoObj2.start();
            correTiempo2=true;


        }
        else{
            cronoObj2.stop();
            correTiempo2=false;
            long elapsedMillis=showElapsedTime(cronoObj2);
            double temp1=((double)elapsedMillis)/1000;
            String stringelapsedMillis = df2.format(temp1);
            temp1=Double.parseDouble(stringelapsedMillis);
            ftiempo2=temp1;
            Tiempo2.setText("T2 = "+ftiempo2);
            Drawable drawable = getResources().getDrawable(R.drawable.circle_obj2) ;
            pb2.setBackground(drawable);


        }
    }

    //############################MOSTRAR TIEMPO###############################################
    //showElapsedTime(cronometro)
    private long showElapsedTime(Chronometer crono) {
        long elapsedMillis = SystemClock.elapsedRealtime() - crono.getBase();
        return elapsedMillis;
    }

}
