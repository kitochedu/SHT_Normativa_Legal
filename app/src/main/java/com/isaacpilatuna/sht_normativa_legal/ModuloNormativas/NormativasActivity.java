package com.isaacpilatuna.sht_normativa_legal.ModuloNormativas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.isaacpilatuna.sht_normativa_legal.Controlador.MenuControl;
import com.isaacpilatuna.sht_normativa_legal.Modelo.DocumentoPDF;
import com.isaacpilatuna.sht_normativa_legal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;

public class NormativasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String categoriaKey="categoria";
    private String categoriaDB="importantes";
    private String tituloCategoria="Importantes";


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage=FirebaseStorage.getInstance();
    DatabaseReference ref;
    Button btnNuevoDocumento;
    EditText txtBusquedaDocumento;
    Button btnBuscarDocumento;
    TextView txtResultadosDocumentos;
    TextView txtNombreDocumento;
    TextView txtTituloCategoriaPDFS;
    RecyclerView recyclerViewDocumentos;
    Uri pdfUri;
    ProgressBar progressBarUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_normativas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.btnNuevoDocumento=findViewById(R.id.btnNuevoDocumento);
        this.btnBuscarDocumento=findViewById(R.id.btnBuscarDocumentos);
        this.txtBusquedaDocumento=findViewById(R.id.txtBusquedaDocumentos);
        this.recyclerViewDocumentos=findViewById(R.id.recyclerViewDocumentos);
        this.txtResultadosDocumentos=findViewById(R.id.txtResultadosDocumentos);
        this.txtTituloCategoriaPDFS=findViewById(R.id.txtTituloCategoriaPDF);
        Intent thisIntent = getIntent();
        categoriaDB=thisIntent.getStringExtra(categoriaKey);
        ref = database.getReference("documentos").child(categoriaDB);
        tituloCategoria = obtenerTituloCategoria(categoriaDB);
        txtTituloCategoriaPDFS.setText(tituloCategoria);


        recyclerViewDocumentos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDocumentos.setAdapter(new RecyclerViewAdapterDocumentos(ref,this,txtResultadosDocumentos));
        btnNuevoDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNuevoDocumento();
            }
        });



    }

    private String obtenerTituloCategoria(String categoriaDB) {
        switch (categoriaDB){
            case "importantes":
                return "Importantes";

            case "biologicos":
                return "Biológicos";

            case "electricos":
                return "Eléctricos";

            case "ergonomicos":
                return "Ergonómicos";

            case "fisicoquimicos":
                return "Físico-Químicos";

            case "fisicos":
                return "Físicos";

            case "locativos":
                return "Locativos";

            case "naturales":
                return "Naturales";

            case "psicosociales":
                return "Psicosociales";

            case "quimicos":
                return "Químicos";

            case "seguridad_fisica":
                return "Seguridad Física";

        }

        return "Importantes";
    }

    public void selectPDF(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==86 && resultCode==RESULT_OK&&data!=null){
            pdfUri=data.getData();
            txtNombreDocumento.setText("Archivo seleccionado: "+data.getData().getLastPathSegment());
        }else{
            Toast.makeText(getBaseContext(),"No ha seleccionado ningún archivo",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }else{
            Toast.makeText(getBaseContext(),"Se necesitan permisos para acceder al almacenamiento",Toast.LENGTH_LONG).show();

        }
    }

    public void openDialogNuevoDocumento(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.nuevo_documento_dialog,null);
        final EditText txtAutor = dialogView.findViewById(R.id.txtAutorDocumentoInsert);
        final EditText txtTitulo = dialogView.findViewById(R.id.txtTituloDocumentoInsert);
        progressBarUpload=dialogView.findViewById(R.id.progressBarUpload);
        progressBarUpload.setProgress(0);
        progressBarUpload.setVisibility(View.GONE);
        Button btnSelectFile = dialogView.findViewById(R.id.btnInsertarDocumento);
        txtNombreDocumento=dialogView.findViewById(R.id.txtNombreArchivo);
        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(NormativasActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectPDF();
                }else{
                    ActivityCompat.requestPermissions(NormativasActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });


        dialog.setView(dialogView);
        dialog.setCancelable(false);

        DialogInterface.OnClickListener clickListenerNegative = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),"Registro cancelado",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        };
        dialog.setPositiveButton("Guardar",null);

        dialog.setNegativeButton("Cancelar",clickListenerNegative);
        dialog.setTitle("Subir nuevo documento");

        final AlertDialog customDialog = dialog.create();
        customDialog.show();
        View.OnClickListener clickListenerPositive = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String autor = txtAutor.getText().toString();
                final String titulo = txtTitulo.getText().toString();
                if(TextUtils.isEmpty(autor)){
                    txtAutor.requestFocus();
                    Toast.makeText(getBaseContext(),"El autor del documento no puede estar vacío",Toast.LENGTH_LONG).show();

                }else{
                    if(TextUtils.isEmpty(titulo)){
                        txtTitulo.requestFocus();
                        Toast.makeText(getBaseContext(),"El título del documento no puede estar vacío",Toast.LENGTH_LONG).show();

                    }else{

                        if(pdfUri==null){
                            Toast.makeText(getBaseContext(),"No ha seleccionado ningún archivo para subir",Toast.LENGTH_LONG).show();
                        }else{

                            progressBarUpload.setProgress(0);
                            progressBarUpload.setVisibility(View.VISIBLE);
                            final StorageReference storageReference = storage.getReference();

                            final StorageReference pathReference = storageReference.child("documentos");
                            final StorageReference finalReference = pathReference.child(categoriaDB).child(titulo);
                            storageReference.child("documentos").child(categoriaDB).child(titulo).putFile(pdfUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            finalReference.
                                                    getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String fileURL = uri.toString();
                                                    String documentoID = UUID.randomUUID().toString();
                                                    DocumentoPDF documentoPDF = new DocumentoPDF(titulo,autor,new Date(),fileURL);
                                                    ref.child(documentoID).setValue(documentoPDF).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                customDialog.dismiss();
                                                                Toast.makeText(getBaseContext(),"Documento guardado exitosamente",Toast.LENGTH_LONG).show();

                                                            }else{
                                                                Toast.makeText(getBaseContext(),"No se pudo guardar el documento",Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getBaseContext(),"NO SE PUDO OBTENER URL",Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getBaseContext(),"No se pudo guardar el documento",Toast.LENGTH_LONG).show();

                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    int currentProgress= (int) (100*(taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()));
                                    progressBarUpload.setProgress(currentProgress);
                                }
                            });


                        }

                    }
                }
            }

        };

        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(clickListenerPositive);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.normativas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        return new MenuControl().controlItemMenu(this,item,"Normativas");
    }


}
