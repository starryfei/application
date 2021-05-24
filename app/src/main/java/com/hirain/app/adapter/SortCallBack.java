package com.hirain.app.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.hirain.app.entity.User;

@Deprecated
public class SortCallBack extends SortedList.Callback<User>  {
    private RecyclerView.Adapter adapter;
    public SortCallBack(RecyclerView.Adapter adapter){
        this.adapter = adapter;
    }
    @Override
    public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
    }

    @Override
    public void onChanged(int position, int count) {

    }

    @Override
    public boolean areContentsTheSame(User oldItem, User newItem) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(User item1, User item2) {
        return false;
    }

    @Override
    public void onInserted(int position, int count) {
        adapter.notifyItemInserted(position);

    }

    @Override
    public void onRemoved(int position, int count) {
        adapter.notifyItemRemoved(position);

    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {

    }

}

