package com.isaacpilatuna.sht_normativa_legal.ModuloHome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> imagesURLS;




    public ImageAdapter(Context mContext, ArrayList<String> imagesURLS) {
        this.imagesURLS=imagesURLS;
        this.mContext = mContext;
    }




    @Override
    public int getCount() {
        return imagesURLS.size();
    }

    @Override
    public boolean isViewFromObject( View view,  Object o) {
        return view==o;
    }



    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get()
                .load(imagesURLS.get(position))
                .into(imageView);
        imageView.setAdjustViewBounds(true);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
       container.removeView((View) object);
    }
}
