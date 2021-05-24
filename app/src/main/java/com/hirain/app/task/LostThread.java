package com.hirain.app.task;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.util.DialogUtil;
import com.hirain.app.zmq.ZmqEcuSubClient;

import org.apache.commons.lang3.StringUtils;

import static com.hirain.app.common.Constants.APP_LOG;

/**
 * 遗失物品线程检测
 */
public class LostThread extends Thread{
    private Context context;
    public LostThread(Context context){
        this.context = context;

    }
    private ZmqEcuSubClient subClient = ZmqEcuSubClient.getInstance();


    @Override
    public void run() {
        subClient.initRecv();
        subClient.recMessage(message -> {
            if (StringUtils.isNoneBlank(message)) {
                Log.i(APP_LOG, message);
                JSONObject parse = JSONObject.parseObject(message);
                String command = parse.getString("command");
                if(StringUtils.equalsIgnoreCase(command,"remind")) {
                    String content = parse.getString("content");
                    String value = "";
                    if(StringUtils.equalsIgnoreCase(content,"livingleft")){
                        value = "车内有活物";
                    } else {
                        value = "车内遗落手机";
                    }
                    DialogUtil.uiDialog(context, value);
                    return;
                }
            }

        });
    }
    public void closeConnect(){
        subClient.stop();
        subClient.stopRecv();
    }
}
