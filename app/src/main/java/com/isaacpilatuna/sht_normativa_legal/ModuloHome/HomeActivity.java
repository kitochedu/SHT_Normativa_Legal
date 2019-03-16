package com.isaacpilatuna.sht_normativa_legal.ModuloHome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.isaacpilatuna.sht_normativa_legal.Controlador.ControlAutenticacion;
import com.isaacpilatuna.sht_normativa_legal.Controlador.MenuControl;
import com.isaacpilatuna.sht_normativa_legal.ModuloHome.ImageAdapter;
import com.isaacpilatuna.sht_normativa_legal.ModuloNormativas.NormativasActivity;
import com.isaacpilatuna.sht_normativa_legal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private Timer timer;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private ProgressBar progressBarSponsors;
    private LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dotsImagesViews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewPagerAuspiciantes);
        progressBarSponsors=findViewById(R.id.progressBarSponsors);
        sliderDotsPanel=findViewById(R.id.linearLayoutDots);
        timer = new Timer();
        cargarAuspiciantes();
    }

    private void cargarAuspiciantes() {
        progressBarSponsors.setVisibility(View.VISIBLE);
        final ArrayList<String> imagesURLS = new ArrayList<>();
        final ArrayList<String> redirectURLS = new ArrayList<>();
        databaseReference=firebaseDatabase.getReference("sponsors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int total = (int) dataSnapshot.getChildrenCount();
                int contador=0;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String url=ds.child("url").getValue().toString();
                    String redirect=ds.child("redirect").getValue().toString();
                    imagesURLS.add(url);
                    redirectURLS.add(redirect);
                    contador++;
                    if(contador==total){
                        progressBarSponsors.setVisibility(View.GONE);
                        Log.i("URL ","URLS: "+imagesURLS.size());
                        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(),imagesURLS,redirectURLS);

                        viewPager.setAdapter(imageAdapter);
                        dibujarPuntos(imageAdapter,viewPager);
                        timer.scheduleAtFixedRate(new SliderTimerTask(HomeActivity.this,viewPager),2000,4000);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    public void dibujarPuntos(ImageAdapter imageAdapter,ViewPager viewPager){
        dotsCount=imageAdapter.getCount();
        dotsImagesViews=new ImageView[dotsCount];
        for (int i=0;i<dotsCount;i++){
            dotsImagesViews[i] = new ImageView(this);
            dotsImagesViews[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDotsPanel.addView(dotsImagesViews[i],params);
        }
        dotsImagesViews[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotsCount;i++){
                    dotsImagesViews[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }
                dotsImagesViews[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
        getMenuInflater().inflate(R.menu.home, menu);
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
        return new MenuControl().controlItemMenu(this,item,"Home");
    }


}
