package com.hirain.app.view;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class DynamicViewFactory {
    public static DynamicViewFactory INSTANCE = new DynamicViewFactory();
    private Map<Integer, DynamicView> cacheView;

    public DynamicViewFactory(){
        cacheView = new HashMap<>();
    }
    public DynamicView createDynamicView(int currentItem, Context context){
        DynamicView dynamicView = cacheView.get(currentItem);
        if(dynamicView!=null) {
            return dynamicView;
        }
        if(currentItem ==1) {
            dynamicView = new MeetView(context);
        } else if(currentItem == 0){
            dynamicView = new CommuteView(context);
        } else if(currentItem == 2){
            dynamicView = new TravelView(context);
        } else if(currentItem == 6) { //新手司机模式
            dynamicView = new DriverView(context);
        }
        else {
            dynamicView = new CommonView(context, currentItem);
        }
        cacheView.put(currentItem,dynamicView);
        return dynamicView;
    }

    public void clearView(){
        cacheView.clear();
    }
}
