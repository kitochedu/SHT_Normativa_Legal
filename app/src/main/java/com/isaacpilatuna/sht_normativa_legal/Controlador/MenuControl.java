package com.isaacpilatuna.sht_normativa_legal.Controlador;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.isaacpilatuna.sht_normativa_legal.ModuloContactos.ContactosActivity;
import com.isaacpilatuna.sht_normativa_legal.ModuloMision.MisionActivity;
import com.isaacpilatuna.sht_normativa_legal.ModuloNormativas.NormativasActivity;
import com.isaacpilatuna.sht_normativa_legal.R;

public class MenuControl {

    public boolean controlItemMenu(AppCompatActivity activity,MenuItem item,String modulo){
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if(!modulo.equals("Home")){
                activity.finish();
            }

        } else if (id == R.id.nav_mision) {
            if(!modulo.equals("Mision")){
                Intent intentMision = new Intent(activity,MisionActivity.class);
                activity.startActivity(intentMision);
                if(!modulo.equals("Home")){
                    activity.finish();
                }
            }

        } else if (id == R.id.nav_contactos) {
            if(!modulo.equals("Contactos")){
                Intent intentContactos = new Intent(activity,ContactosActivity.class);
                activity.startActivity(intentContactos);
                if(!modulo.equals("Home")){
                    activity.finish();
                }
            }

        } else if (id == R.id.nav_normativas) {
            if(!modulo.equals("Normativas")){
                Intent intentNormativas = new Intent(activity,NormativasActivity.class);
                activity.startActivity(intentNormativas);
                if(!modulo.equals("Home")){
                    activity.finish();
                }
            }

        }else if(id == R.id.nav_log_out){
            new ControlAutenticacion().logOut(activity);


        }


        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
