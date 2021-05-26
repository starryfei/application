package com.hirain.app.view;

import android.content.Context;

public class DynamicViewFactory {
    public static DynamicViewFactory INSTANCE = new DynamicViewFactory();

    public DynamicViewFactory(){
    }
    public DynamicView createDynamicView(int currentItem, Context context){
        DynamicView dynamicView;
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
        return dynamicView;
    }

    public void clearView(){
    }
}
