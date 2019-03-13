package com.isaacpilatuna.sht_normativa_legal.ModuloNormativas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.isaacpilatuna.sht_normativa_legal.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewPDFActivity extends AppCompatActivity {

    PDFView pdfview;
    ProgressBar progressBar;
    final String URL_KEY="URL_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_pdf);


        pdfview=  findViewById(R.id.pdf_viewer);
        progressBar=findViewById(R.id.progressBarPDF);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        String pdfURL= (String) extras.get(URL_KEY);

        RetrievePDFStream retrievePDF = new RetrievePDFStream();
        retrievePDF.execute(pdfURL);

    }

    class RetrievePDFStream extends AsyncTask<String,Void,InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (MalformedURLException e) {

                Toast.makeText(getBaseContext(),"URL incorrecta",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(),"No tiene los permisos",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfview.fromStream(inputStream).load();

            progressBar.setVisibility(View.GONE);
        }
    }
}
