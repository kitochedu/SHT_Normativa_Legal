package com.isaacpilatuna.sht_normativa_legal.ModuloHome;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.TimerTask;

public class SliderTimerTask extends TimerTask {
    AppCompatActivity appContext;
    ViewPager viewPager;

    public SliderTimerTask(AppCompatActivity appContext, ViewPager viewPager) {
        this.appContext = appContext;
        this.viewPager = viewPager;
    }

    @Override
    public void run() {
        appContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int actualPage = viewPager.getCurrentItem();
                int totalPages=viewPager.getAdapter().getCount();
                if (actualPage==(totalPages-1)){
                    viewPager.setCurrentItem(0);
                }else{
                    viewPager.setCurrentItem(actualPage+1);
                }
            }
        });
    }
}
