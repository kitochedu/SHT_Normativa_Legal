package com.isaacpilatuna.sht_normativa_legal.ModuloHome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> imagesURLS;
    private ArrayList<String> redirectURLS;




    public ImageAdapter(Context mContext, ArrayList<String> imagesURLS, ArrayList<String> redirectURLS) {
        this.imagesURLS=imagesURLS;
        this.mContext = mContext;
        this.redirectURLS=redirectURLS;
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
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get()
                .load(imagesURLS.get(position))
                .into(imageView);
        imageView.setAdjustViewBounds(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url= redirectURLS.get(position);
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                urlIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(urlIntent);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
       container.removeView((View) object);
    }
}
