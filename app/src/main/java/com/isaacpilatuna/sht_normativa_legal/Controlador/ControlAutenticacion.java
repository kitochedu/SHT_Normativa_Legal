package com.isaacpilatuna.sht_normativa_legal.Controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.isaacpilatuna.sht_normativa_legal.ModuloAutenticacion.LoginActivity;

public class ControlAutenticacion {

    public static final String PREFS_FILENAME = "com.isaacpilatuna.manualdesupervivenciauniversitaria.prefs";
    public static final String ACCOUNT_AUTH_KEY ="accountAuth";
    public static final String DEFAULT_NON_AUTH_KEY ="NonAuth";

    private SharedPreferences sharedPreferences;

    public void logOut(final AppCompatActivity activity){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Cerrar sesión");
        builder.setMessage("¿Desea cerrar la sesión?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                sharedPreferences=activity.getSharedPreferences(PREFS_FILENAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putString(ACCOUNT_AUTH_KEY,DEFAULT_NON_AUTH_KEY);
                editor.commit();
                Intent intent = new Intent(activity,LoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        DialogInterface.OnClickListener clickListenerNegative = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        builder.setNegativeButton(android.R.string.no, clickListenerNegative);
        builder.show();
    }
}
