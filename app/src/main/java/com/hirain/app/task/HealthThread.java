package com.hirain.app.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.util.DialogUtil;
import com.hirain.app.util.FileUtil;
import com.hirain.app.util.ImageBase64Utils;
import com.hirain.app.zmq.ZmqEcuSubClient;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.Queue;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.util.TimeUtil.getTime;

@RequiresApi(api = Build.VERSION_CODES.Q)

public class HealthThread extends Thread {
    private String name;
    private Context context;
    private Queue<String> queue = new LinkedList<>();
    private boolean isRun = true;

    public HealthThread(String name, Context context) {
        this.name = name;
        this.context = context;
        isRun = true;
        parseMessage();

    }

    private ZmqEcuSubClient subClient = ZmqEcuSubClient.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void parseMessage() {
        new Thread(() -> {
            while (isRun) {
                String poll = queue.poll();
                if (StringUtils.isNoneBlank(poll)) {
                    Log.d(APP_LOG, "queue running....");
                    String time = getTime();
                    JSONObject parse = JSONObject.parseObject(poll);

                    String command1 = parse.getString("command");

                    if (StringUtils.equalsIgnoreCase(command1, "picture")) {

                        parseImage(parse, name);

                    } else if (StringUtils.equalsIgnoreCase(command1, "healthinfo_ECM")) {
                        parseHealthInfo(parse, name, time, "ECM");

                    } else if (StringUtils.equalsIgnoreCase(command1, "healthinfo_PWM")) {
                        parseHealthInfo(parse, name, time, "PWM");
                    }
                }
            }
        }).start();

    }

    public void setRun(boolean run) {
        isRun = run;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void run() {
        subClient.initRecv();
        subClient.recMessage(message -> {
            if (StringUtils.isNoneBlank(message)) {
//                boolean contains = queue.contains(message);
//                if (!contains) {
                    queue.add(message);

//                }
            }
        });
    }

    public void closeConnect() {
        setRun(false);
        subClient.stopRecv();
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void parseImage(JSONObject parse, String name) {

        String image = parse.getString("image");
        Bitmap bitmap = ImageBase64Utils.stringToBitmap(image);
        DialogUtil.imageDialogTimer(context, bitmap);
        FileUtil.saveImage(context, name, bitmap);
        Log.i(APP_LOG, "save image");


    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void parseHealthInfo(JSONObject parse, String name, String time, String type) {
        parse.remove("command");
        parse.remove("ID");
        StringBuilder sb = new StringBuilder();

        String sna = parse.getString("SNA");
        sb.append("焦虑指数  " + sna);
        sb.append("\n");
        String hr = parse.getString("HR");
        sb.append("心率(bpm)  " + hr);
        sb.append("\n");
        String hrv = parse.getString("HRV");
        sb.append("心率变异性(ms)  " + hrv);
        sb.append("\n");

        String arr = parse.getString("ARR");
        sb.append("心率不齐  " + arr);
        sb.append("\n");

        if (StringUtils.equalsIgnoreCase(type, "PWM")) {
            String rr = parse.getString("RR");
            sb.append("呼吸率(%)  " + rr);
            sb.append("\n");
            String si = parse.getString("SI");
            sb.append("动脉硬化指数  " + si);
            sb.append("\n");
            String spo2 = parse.getString("SPO2");
            sb.append("血氧饱和度  " + spo2);
            sb.append("\n");
            String pt = parse.getString("PT");
            sb.append("脉象  " + pt);
            sb.append("\n");
        } else {
            String pbf = parse.getString("PBF");
            sb.append("体脂率(%)  " + pbf);
            sb.append("\n");
            String pbw = parse.getString("PBW");
            sb.append("体水分率(%)  " + pbw);
            sb.append("\n");

            String fag = parse.getString("FAG");
            sb.append("疲劳等级  " + fag);
            sb.append("\n");
        }
        Log.d(APP_LOG, sb.toString());
        FileUtil.saveFile(context, name, type, time, sb.toString());

    }
}
