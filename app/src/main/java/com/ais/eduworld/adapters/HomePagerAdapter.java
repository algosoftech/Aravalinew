package com.ais.eduworld.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.ais.eduworld.R;

public class HomePagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> sliderList = new ArrayList<>();
    public HomePagerAdapter(Context context,ArrayList<String> sliderList){
        this.context = context;
        this.sliderList = sliderList;
        if(context!=null)
            inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return sliderList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View sliderView = inflater.inflate(R.layout.slideritem,container,false);
        ImageView imageView = (ImageView) sliderView.findViewById(R.id.ivslider);
        String image = sliderList.get(position);
//        Glide.with(context)
//                .load("http://ais.aravalieduworld.com/slider/43s1.jpg")
//                .centerCrop()
//                .placeholder(R.drawable.bannerplace)
//                .crossFade()
//                .into(imageView);
        Picasso.with(context)
                .load(image)
                .placeholder(R.drawable.bannerplace)
                .into(imageView);
        ((ViewPager) container).addView(sliderView, 0);
        return sliderView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
