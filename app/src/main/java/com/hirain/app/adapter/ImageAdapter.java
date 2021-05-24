package com.hirain.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.hirain.app.R;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

public class ImageAdapter extends PagerAdapter {

    public ImageAdapter() {

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.mode_item, null);
        View linearLayout = LayoutInflater.from(container.getContext()).inflate(R.layout.image_layout, null);
        //new LinearLayout(container.getContext());
        RadiusImageView imageView = (RadiusImageView) linearLayout.findViewById(R.id.image);
 //        linearLayout.setId(R.id.item_id);

//        switch (position) {
//            case 0:
//                imageView.setImageResource(R.mipmap.a);
//
//                break;
//            case 1:
//                imageView.setImageResource(R.mipmap.b);
//                break;
//            case 2:
//                imageView.setImageResource(R.mipmap.c);
//                break;


//        }
        container.addView(linearLayout);

//        linearLayout.setOnClickListener(v -> {
//            System.out.println("click:  "+position);
//
//        });
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
