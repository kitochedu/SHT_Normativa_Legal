package com.isaacpilatuna.sht_normativa_legal.ModuloNormativas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.isaacpilatuna.sht_normativa_legal.R;

public class RecyclerViewHolderDocumentos extends RecyclerView.ViewHolder{
    public TextView txtAutorDocumento;
    public TextView txtTituloDocumento;
    public TextView txtFechaSubidaDocumento;
    public Button btnVerPDF;
    public String urlPDF;

    public RecyclerViewHolderDocumentos(final View itemView) {
        super(itemView);
        txtAutorDocumento=itemView.findViewById(R.id.txtAutorDocumento);
        txtTituloDocumento=itemView.findViewById(R.id.txtTituloDocumento);
        txtFechaSubidaDocumento=itemView.findViewById(R.id.txtFechaDeSubidaDocumento);
        btnVerPDF=itemView.findViewById(R.id.btnVerDocumento);

    }

    public TextView getTxtAutorDocumento() {
        return txtAutorDocumento;
    }

    public void setTxtAutorDocumento(TextView txtAutorDocumento) {
        this.txtAutorDocumento = txtAutorDocumento;
    }

    public TextView getTxtTituloDocumento() {
        return txtTituloDocumento;
    }

    public void setTxtTituloDocumento(TextView txtTituloDocumento) {
        this.txtTituloDocumento = txtTituloDocumento;
    }

    public TextView getTxtFechaSubidaDocumento() {
        return txtFechaSubidaDocumento;
    }

    public void setTxtFechaSubidaDocumento(TextView txtFechaSubidaDocumento) {
        this.txtFechaSubidaDocumento = txtFechaSubidaDocumento;
    }
}
