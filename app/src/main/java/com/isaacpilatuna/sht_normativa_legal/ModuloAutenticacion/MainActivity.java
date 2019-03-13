package com.isaacpilatuna.sht_normativa_legal.ModuloAutenticacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.isaacpilatuna.sht_normativa_legal.Controlador.ControlAutenticacion;
import com.isaacpilatuna.sht_normativa_legal.ModuloHome.HomeActivity;
import com.isaacpilatuna.sht_normativa_legal.R;


public class MainActivity extends AppCompatActivity {
    private ImageView logoPrincipal;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoPrincipal= (ImageView) findViewById(R.id.imageViewLogoPrincipal);
        logoPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarAplicacion();
            }
        });

    }

    private void iniciarAplicacion(){
        sharedPreferences=getApplicationContext().getSharedPreferences(ControlAutenticacion.PREFS_FILENAME,0);
        String accountAuth=sharedPreferences.getString(ControlAutenticacion.ACCOUNT_AUTH_KEY,ControlAutenticacion.DEFAULT_NON_AUTH_KEY);
        if(accountAuth.equals(ControlAutenticacion.DEFAULT_NON_AUTH_KEY)){
            iniciarLogin();
        }else{
            iniciarHome();
        }


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
