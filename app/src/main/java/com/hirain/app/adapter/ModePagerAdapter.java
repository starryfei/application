package com.hirain.app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.hirain.app.R;

import java.util.List;

public class ModePagerAdapter extends PagerAdapter {
    private List<String> modeList;

    public ModePagerAdapter(List<String> modeList) {
        this.modeList = modeList;
    }

    @Override
    public int getCount() {
        return modeList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.mode_item, null);
        View linearLayout = LayoutInflater.from(container.getContext()).inflate(R.layout.mode_item, null);
        //new LinearLayout(container.getContext());
        TextView textView = (TextView) linearLayout.findViewById(R.id.pager_text_view);
 //        linearLayout.setId(R.id.item_id);
        textView.setText(modeList.get(position));
        switch (position) {
            case 0:
                linearLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                break;
            case 1:
                linearLayout.setBackgroundColor(Color.parseColor("#673AB7"));
                break;
            case 2:
                linearLayout.setBackgroundColor(Color.parseColor("#009688"));
                break;
            case 3:
                linearLayout.setBackgroundColor(Color.parseColor("#607D8B"));
                break;
            case 4:
                linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
                break;
            case 5:
                linearLayout.setBackgroundColor(Color.parseColor("#673AC6"));
                break;
            case 6:
                linearLayout.setBackgroundColor(Color.parseColor("#009682"));
                break;

        }
        container.addView(linearLayout);

        linearLayout.setOnClickListener(v -> {
            System.out.println("click:  "+position);

        });
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
