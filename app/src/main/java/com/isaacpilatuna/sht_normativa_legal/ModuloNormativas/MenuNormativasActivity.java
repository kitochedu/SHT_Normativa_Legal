package com.isaacpilatuna.sht_normativa_legal.ModuloNormativas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.isaacpilatuna.sht_normativa_legal.Controlador.MenuControl;
import com.isaacpilatuna.sht_normativa_legal.R;

public class MenuNormativasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String categoriaKey="categoria";
    private ImageView imageViewImportantes;
    private ImageView imageViewBiologicos;
    private ImageView imageViewElectricos;
    private ImageView imageViewErgonomicos;
    private ImageView imageViewFisicoQuimicos;
    private ImageView imageViewFisicos;
    private ImageView imageViewLocativos;
    private ImageView imageViewNaturales;
    private ImageView imageViewPsicosociales;
    private ImageView imageViewQuimicos;
    private ImageView imageViewSeguridadFisica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_normativas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        obtenerImagesViews();
        agregarClickListenerToImages();
    }

    private void agregarClickListenerToImages() {
        imageViewImportantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("importantes");
            }
        });
        imageViewBiologicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("biologicos");
            }
        });
        imageViewElectricos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("electricos");
            }
        });
        imageViewErgonomicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("ergonomicos");
            }
        });
        imageViewFisicoQuimicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("fisicoquimicos");
            }
        });
        imageViewFisicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("fisicos");
            }
        });
        imageViewLocativos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("locativos");
            }
        });
        imageViewNaturales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("naturales");
            }
        });
        imageViewPsicosociales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("psicosociales");
            }
        });
        imageViewQuimicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("quimicos");
            }
        });
        imageViewSeguridadFisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscador("seguridad_fisica");
            }
        });

    }

    private void obtenerImagesViews() {
        imageViewBiologicos=findViewById(R.id.imageViewBiologicos);
        imageViewImportantes=findViewById(R.id.imageViewImportantes);
        imageViewElectricos=findViewById(R.id.imageViewElectricos);
        imageViewErgonomicos=findViewById(R.id.imageViewErgonomicos);
        imageViewFisicoQuimicos=findViewById(R.id.imageViewFisicoQuimico);
        imageViewFisicos=findViewById(R.id.imageViewFisicos);
        imageViewLocativos=findViewById(R.id.imageViewLocativos);
        imageViewNaturales=findViewById(R.id.imageViewNaturales);
        imageViewPsicosociales=findViewById(R.id.imageViewPsicosociales);
        imageViewQuimicos=findViewById(R.id.imageViewQuimicos);
        imageViewSeguridadFisica=findViewById(R.id.imageViewSeguridadFisica);
    }

    private void abrirBuscador(String categoria){
        Intent intent = new Intent(this,NormativasActivity.class);
        intent.putExtra(categoriaKey,categoria);
        startActivity(intent);

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
        getMenuInflater().inflate(R.menu.menu_normativas, menu);
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
