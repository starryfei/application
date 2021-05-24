package com.hirain.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hirain.app.R;
import com.hirain.app.entity.Mode;

import java.util.List;

public class ModeAdapter extends BaseAdapter {
    private List<Mode> channelList;
    private LayoutInflater layoutInflater;

    public ModeAdapter(List<Mode> list, Context context){
        channelList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return channelList.size();
    }

    @Override
    public Object getItem(int position) {
        return channelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            //加载布局
            convertView =layoutInflater.inflate(R.layout.mode_item,null);

            holder = new ViewHolder();
            holder.imgChannel = (ImageView)convertView.findViewById(R.id.mode_image);
            holder.decChannel = (TextView)convertView.findViewById(R.id.pager_text_view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        //设置图标和文字
        Mode mode = channelList.get(position);
        if(mode != null){
            holder.decChannel.setText(mode.getDec());
            switch (position){
                case 0:
                    holder.imgChannel.setImageResource(R.mipmap.commute);
                    break;
                case 1:
                    holder.imgChannel.setImageResource(R.mipmap.meeting);
                    break;
                case 2:
                    holder.imgChannel.setImageResource(R.mipmap.travel);
                    break;
                case 3:
                    holder.imgChannel.setImageResource(R.mipmap.mode3);
                    break;
                case 4:
                    holder.imgChannel.setImageResource(R.mipmap.mode4);
                    break;
                case 5:
                    holder.imgChannel.setImageResource(R.mipmap.mode5);
                    break;
                case 6:
                    holder.imgChannel.setImageResource(R.mipmap.mode6);
                    break;
                case 7:
                    holder.imgChannel.setImageResource(R.mipmap.other);
                    break;

            }
        }

        return convertView;
    }

    class ViewHolder{
        ImageView imgChannel;
        TextView decChannel;
    }
}
