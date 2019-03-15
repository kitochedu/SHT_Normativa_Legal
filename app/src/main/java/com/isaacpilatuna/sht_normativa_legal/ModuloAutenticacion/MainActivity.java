package com.isaacpilatuna.sht_normativa_legal.ModuloAutenticacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.isaacpilatuna.sht_normativa_legal.Controlador.ControlAutenticacion;
import com.isaacpilatuna.sht_normativa_legal.ModuloHome.HomeActivity;
import com.isaacpilatuna.sht_normativa_legal.R;


public class MainActivity extends AppCompatActivity {
    private ImageView logoPrincipal;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBarInicio;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoPrincipal= (ImageView) findViewById(R.id.imageViewLogoPrincipal);

        progressBarInicio = findViewById(R.id.progressBarInicio);
        countDownTimer=new CountDownTimer(timeLeftInMilliSeconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliSeconds=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                iniciarAplicacion();

            }
        }.start();

    }

    private void iniciarAplicacion(){
        progressBarInicio.setVisibility(View.VISIBLE);
        sharedPreferences=getApplicationContext().getSharedPreferences(ControlAutenticacion.PREFS_FILENAME,0);
        String accountAuth=sharedPreferences.getString(ControlAutenticacion.ACCOUNT_AUTH_KEY,ControlAutenticacion.DEFAULT_NON_AUTH_KEY);
        if(accountAuth.equals(ControlAutenticacion.DEFAULT_NON_AUTH_KEY)){
            iniciarLogin();
        }else{
            iniciarHome();
        }
        progressBarInicio.setVisibility(View.GONE);


    }

    private void iniciarLogin(){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void iniciarHome(){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }










}
