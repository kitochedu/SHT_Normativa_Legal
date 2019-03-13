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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isaacpilatuna.sht_normativa_legal.Controlador.ControlAutenticacion;
import com.isaacpilatuna.sht_normativa_legal.ModuloHome.HomeActivity;
import com.isaacpilatuna.sht_normativa_legal.Modelo.Usuario;
import com.isaacpilatuna.sht_normativa_legal.R;

public class RegistroActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnCrearCuenta;
    ProgressBar progressBarRegistro;
    EditText txtCorreo;
    EditText txtNombre;
    EditText txtPassword;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference("usuarios");
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnLogin=findViewById(R.id.btnLoginSignUp);
        btnCrearCuenta=findViewById(R.id.btnCrearCuentaSignUp);
        progressBarRegistro=findViewById(R.id.progressBarSignUp);
        txtNombre=findViewById(R.id.txtNombreCompletoSignUp);
        txtPassword=findViewById(R.id.txtPasswordSignUp);
        txtCorreo=findViewById(R.id.txtCorreoSignUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta(v);
            }
        });
    }

    private void crearCuenta(View v) {
        final String nombre=txtNombre.getText().toString().trim();
        final String correo=txtCorreo.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"El nombre  no puede estar vacío",Toast.LENGTH_LONG).show();
            txtNombre.requestFocus();
            return;
        }
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
        progressBarRegistro.setVisibility(View.VISIBLE);
        btnCrearCuenta.setEnabled(false);
        btnLogin.setEnabled(false);
        txtCorreo.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPassword.setEnabled(false);
        firebaseAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Usuario usuario = new Usuario(nombre,correo,"Básico");
                    String currentUID= firebaseAuth.getCurrentUser().getUid();
                    reference.child(currentUID).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                sharedPreferences=getApplicationContext().getSharedPreferences(ControlAutenticacion.PREFS_FILENAME,0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.putString(ControlAutenticacion.ACCOUNT_AUTH_KEY,correo);
                                editor.commit();

                                progressBarRegistro.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Registro Exitoso",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                                finish();


                            }else{
                                progressBarRegistro.setVisibility(View.GONE);
                                btnCrearCuenta.setEnabled(true);
                                btnLogin.setEnabled(true);
                                txtCorreo.setEnabled(true);
                                txtNombre.setEnabled(true);
                                txtPassword.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"No se pudo registrar el usuario en la base de datos",Toast.LENGTH_LONG).show();
                            }
                        }
                    });



                }else{
                    progressBarRegistro.setVisibility(View.GONE);
                    btnCrearCuenta.setEnabled(true);
                    btnLogin.setEnabled(true);
                    txtCorreo.setEnabled(true);
                    txtNombre.setEnabled(true);
                    txtPassword.setEnabled(true);
                    if(task.getException().getClass().getName() == FirebaseAuthUserCollisionException.class.getName()){
                        Toast.makeText(getApplicationContext(),"El correo ya se encuentra registrado",Toast.LENGTH_LONG).show();

                    }else{
                       Toast.makeText(getApplicationContext(),"No se pudo realizar el registro",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

    }
}
