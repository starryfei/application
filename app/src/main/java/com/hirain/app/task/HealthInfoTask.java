package com.hirain.app.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.util.FileUtil;
import com.hirain.app.util.ImageBase64Utils;
import com.hirain.app.zmq.ZmqEcuSubClient;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.util.TimeUtil.getTime;

@Deprecated
public class HealthInfoTask extends AsyncTask<String, Void, Object> {
//    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private boolean start = true;
    public HealthInfoTask() {

    }

    @Override
    protected Object doInBackground(String... strings) {
        String name= strings[0];
        String time = getTime();
        AtomicBoolean res = new AtomicBoolean(false);
        ZmqEcuSubClient.getInstance().recMessage(message -> {
            if (StringUtils.isNoneBlank(message)) {
                Log.i(APP_LOG, message);
                JSONObject parse = JSONObject.parseObject(message);
                String command = parse.getString("command");
                if (StringUtils.equalsIgnoreCase(command, "picture")) {
                    parseImage(parse, name);
                } else if (StringUtils.equalsIgnoreCase(command, "healthinfo")) {
//                    parseHealthInfo(parse, name, time);
                }
            }
        });
        return null;
    }

    private void parseImage(JSONObject parse, String name) {
        String image = parse.getString("image");
        Bitmap bitmap = ImageBase64Utils.stringToBitmap(image);
        FileUtil.saveImageToStream(bitmap,name);

    }

//    private void parseHealthInfo(JSONObject parse, String time, String name) {
//        parse.remove("command");
//        parse.remove("ID");
//        String sna = parse.getString("SNA");
//        String hr = parse.getString("HR");
//        String hrv = parse.getString("HRV");
//        String arr = parse.getString("ARR");
//        String pbf = parse.getString("PBF");
//        String pbw = parse.getString("PBW");
//        String fag = parse.getString("FAG");
//        String rr = parse.getString("RR");
//        String si = parse.getString("SI");
//        String spo2 = parse.getString("SPO2");
//        String pt = parse.getString("PT");
//        FileUtil.createFile(name,time,parse.toJSONString());
//
//    }


    public void cancelTask() {
//        scheduledExecutorService.shutdown();
        ZmqEcuSubClient.getInstance().stop();
        Log.d(APP_LOG, "HealthInfoTask onCancelled");
    }
}