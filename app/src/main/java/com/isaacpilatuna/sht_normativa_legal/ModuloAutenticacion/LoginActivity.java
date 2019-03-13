package com.isaacpilatuna.sht_normativa_legal.ModuloAutenticacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.isaacpilatuna.sht_normativa_legal.Controlador.ControlAutenticacion;
import com.isaacpilatuna.sht_normativa_legal.ModuloHome.HomeActivity;
import com.isaacpilatuna.sht_normativa_legal.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnCrearCuenta;
    ProgressBar progressBarLogin;
    EditText txtCorreo;
    EditText txtPassword;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.btnIniciarSesionLogin);
        btnCrearCuenta=findViewById(R.id.btnCrearCuentaLogin);
        txtCorreo=findViewById(R.id.txtCorreoLogin);
        txtPassword=findViewById(R.id.txtPasswordLogin);
        progressBarLogin=findViewById(R.id.progressBarLogin);


        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion(v);
            }
        });
    }

    public void iniciarSesion(View v){
        final String correo = txtCorreo.getText().toString().trim();
        String password=txtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this,"El correo no puede estar vacío",Toast.LENGTH_LONG).show();
            txtCorreo.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"La contraseña no puede estar vacía",Toast.LENGTH_LONG).show();
            txtPassword.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        btnCrearCuenta.setEnabled(false);
        btnLogin.setEnabled(false);
        txtCorreo.setEnabled(false);
        txtPassword.setEnabled(false);

        firebaseAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    sharedPreferences=getApplicationContext().getSharedPreferences(ControlAutenticacion.PREFS_FILENAME,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putString(ControlAutenticacion.ACCOUNT_AUTH_KEY,correo);
                    editor.commit();
                    progressBarLogin.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Inicio de sesión exitoso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressBarLogin.setVisibility(View.GONE);
                    btnCrearCuenta.setEnabled(true);
                    btnLogin.setEnabled(true);
                    txtCorreo.setEnabled(true);
                    txtPassword.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
