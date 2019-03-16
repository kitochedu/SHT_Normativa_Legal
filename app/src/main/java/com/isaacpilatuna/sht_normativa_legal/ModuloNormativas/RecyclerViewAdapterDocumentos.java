package com.isaacpilatuna.sht_normativa_legal.ModuloNormativas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.isaacpilatuna.sht_normativa_legal.Modelo.DocumentoPDF;
import com.isaacpilatuna.sht_normativa_legal.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapterDocumentos  extends RecyclerView.Adapter<RecyclerViewHolderDocumentos> {

    AppCompatActivity normasActivity;
    TextView txtResultados;
    ArrayList<DocumentoPDF> listaDocumentos=new ArrayList<>();
    final String URL_KEY="URL_KEY";



    public RecyclerViewAdapterDocumentos(String categoriaDB, AppCompatActivity normasActivity, final TextView txtResultados) {
        super();
        this.normasActivity=normasActivity;
        this.txtResultados=txtResultados;
        ValueEventListener valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDocumentos.clear();
                for(DataSnapshot snapshotChild:dataSnapshot.getChildren()){
                    String autor = snapshotChild.child("autor").getValue().toString();
                    String titulo = snapshotChild.child("titulo").getValue().toString();
                    String url = snapshotChild.child("url").getValue().toString();
                    Long tiempoPublicacion = (Long) snapshotChild.child("fecha").child("time").getValue();
                    Date fechaPublicacion = new Date(tiempoPublicacion);
                    listaDocumentos.add(new DocumentoPDF(titulo,autor,fechaPublicacion,url));
                }
                txtResultados.setText(listaDocumentos.size()+" Resultados");
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("documentos").child(categoriaDB)
                .orderByChild("titulo");
        query.addValueEventListener(valueEventListener);


    }

    @NonNull
    @Override
    public RecyclerViewHolderDocumentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_documento,viewGroup,false);



        return new RecyclerViewHolderDocumentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderDocumentos holder, int posicion) {
        final DocumentoPDF documento = listaDocumentos.get(posicion);
        holder.txtAutorDocumento.setText("Autor: "+documento.getAutor());
        holder.txtTituloDocumento.setText("Titulo: "+documento.getTitulo());
        holder.urlPDF=documento.getUrl();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
        holder.txtFechaSubidaDocumento.setText("Subido: "+format.format(documento.getFecha()));
        holder.btnVerPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(normasActivity,ViewPDFActivity.class);
                intent.putExtra(URL_KEY,documento.getUrl());
                normasActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDocumentos.size();
    }
}
