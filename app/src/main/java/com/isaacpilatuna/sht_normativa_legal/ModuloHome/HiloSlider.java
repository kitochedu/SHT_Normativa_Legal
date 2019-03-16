package com.isaacpilatuna.sht_normativa_legal.ModuloHome;

import android.support.v4.view.ViewPager;
import android.view.View;

public class HiloSlider  extends Thread{
    private long milliSecs=3000;
    private ViewPager viewPager;

    public HiloSlider(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void run() {
        int contador=0;
        int numPages=viewPager.getAdapter().getCount();
        try {
            while (true){
                int actualPage=contador%numPages;
                viewPager.setCurrentItem(actualPage);
                sleep(milliSecs);
                contador++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
