package com.hirain.app.task;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.util.NetwrokUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.hirain.app.common.Constants.APP_LOG;

@Deprecated
public class NetWorkService extends IntentService {
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private Context context;
    public NetWorkService(Context context) {
        super("network");
        this.context = context;
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        scheduledExecutorService.scheduleAtFixedRate(
                () -> {
                    boolean networkAvailable = NetwrokUtil.isNetworkAvailable(context);
                    if(!networkAvailable) {
                        Toast.makeText(context, "网络已断开，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                }, 0, 1, TimeUnit.MINUTES);

        scheduledExecutorService.shutdown();
    }
}