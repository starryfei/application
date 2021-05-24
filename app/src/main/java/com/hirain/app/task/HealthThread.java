package com.hirain.app.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.util.DialogUtil;
import com.hirain.app.util.FileUtil;
import com.hirain.app.util.ImageBase64Utils;
import com.hirain.app.zmq.ZmqEcuSubClient;

import org.apache.commons.lang3.StringUtils;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.util.TimeUtil.getTime;

public class HealthThread extends Thread{
    private String name;
    private Context context;
    public HealthThread(String name, Context context){
        this.name = name;
        this.context = context;
    }
    private ZmqEcuSubClient subClient = ZmqEcuSubClient.getInstance();


    @Override
    public void run() {
        subClient.initRecv();
        subClient.recMessage(message -> {
            String time = getTime();
            if (StringUtils.isNoneBlank(message)) {
                Log.i(APP_LOG, message);
                JSONObject parse = JSONObject.parseObject(message);
                String command1 = parse.getString("command");
                if (StringUtils.equalsIgnoreCase(command1, "picture")) {
                    parseImage(parse, name);
                } else if (StringUtils.equalsIgnoreCase(command1, "healthinfo_ECM")) {
                    parseHealthInfo(parse, name, time,"ECM");
                } else if(StringUtils.equalsIgnoreCase(command1,"healthinfo_PWM")) {
                    parseHealthInfo(parse,name,time,"PWM");
                }
            }

        });
    }
    public void closeConnect(){
        subClient.stop();
        subClient.stopRecv();
    }

//    public void recvMessage() {
//        new Thread(() -> {
//
//        }).start();
//
//
//    }
    private void parseImage(JSONObject parse, String name) {
        String image = parse.getString("image");
        Bitmap bitmap = ImageBase64Utils.stringToBitmap(image);
        DialogUtil.imageDialogTimer(context,bitmap);
        FileUtil.saveImageToStream(bitmap,name);

    }

    private void parseHealthInfo(JSONObject parse, String name, String time,String type) {
        parse.remove("command");
        parse.remove("ID");
        StringBuilder sb = new StringBuilder();

        String sna = parse.getString("SNA");
        sb.append("焦虑指数  "+sna);
        sb.append("\n");
        String hr = parse.getString("HR");
        sb.append("心率(bpm)  "+hr);
        sb.append("\n");
        String hrv = parse.getString("HRV");
        sb.append("心率变异性(ms)  "+hrv);
        sb.append("\n");

        String arr = parse.getString("ARR");
        sb.append("心率不齐  "+arr);
        sb.append("\n");

        if(StringUtils.equalsIgnoreCase(type,"PWM")) {
            String rr = parse.getString("RR");
            sb.append("呼吸率(%)  "+rr);
            sb.append("\n");
            String si = parse.getString("SI");
            sb.append("动脉硬化指数  "+si);
            sb.append("\n");
            String spo2 = parse.getString("SPO2");
            sb.append("血氧饱和度  "+spo2);
            sb.append("\n");
            String pt = parse.getString("PT");
            sb.append("脉象  "+pt);
            sb.append("\n");
        } else {
            String pbf = parse.getString("PBF");
            sb.append("体脂率(%)  "+pbf);
            sb.append("\n");
            String pbw = parse.getString("PBW");
            sb.append("体水分率(%)  "+pbw);
            sb.append("\n");

            String fag = parse.getString("FAG");
            sb.append("疲劳等级  "+fag);
            sb.append("\n");
        }

        FileUtil.createFile(name,type,time,sb.toString());

    }
}
