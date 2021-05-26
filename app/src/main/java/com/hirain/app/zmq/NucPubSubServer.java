package com.hirain.app.zmq;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.common.Constants;
import com.hirain.app.listener.IMessageListener;

import org.apache.commons.lang3.StringUtils;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.hirain.app.common.Constants.APP_LOG;

public class NucPubSubServer {

    private ZMQ.Socket pubSocket;
    private ZMQ.Socket subSocket;
    private ZMQ.Context pubContext;
    private ZMQ.Context subContext;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


    private static NucPubSubServer zmqClient;
    private int count = 0;


    private NucPubSubServer() {
    }

    public void initServer() {
        pubServerStart();
        subClientStart();
    }

    public static NucPubSubServer getInstance() {
        if (null == zmqClient) {
            zmqClient = new NucPubSubServer();
        }
        return zmqClient;
    }

    public void sendMessage(String message) {

        boolean send = pubSocket.send(message, 0);
        Log.d(APP_LOG, "send status: " + send);
        // 发送失败需要重试
        if (!send) {
            wait10();
            count++;
            Log.d(APP_LOG, "resend " + count);
            if (count < 5) {
                sendMessage(message);
            } else {
                return;
            }
        }

    }

    public void recvMessage(IMessageListener messageListener, String command) {
        while (true) {
            String s = subSocket.recvStr();
            if (StringUtils.isNoneBlank(s)) {
                Log.d(APP_LOG, s);
                JSONObject jsonObject = JSONObject.parseObject(s);
                String res = jsonObject.getString("command");
                if (StringUtils.equalsIgnoreCase(res, command)) {
                    messageListener.messageListener(s);
                    return;

                }
            }
        }
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        scheduledExecutorService.scheduleAtFixedRate(
//                () -> {
//
//                }, 0, 50, TimeUnit.MILLISECONDS);
//
//        try {
//            countDownLatch.await();
//            scheduledExecutorService.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    private void wait10() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pubServerStart() {
        if (null != pubSocket) {
            return;
        }
        pubContext = ZMQ.context(1);
        pubSocket = pubContext.socket(SocketType.PUB);
        pubSocket.connect(Constants.NUC_SERVER_PUB);
        Log.d(APP_LOG, "pub connect nuc server success");
    }

    private void subClientStart() {
        if (null != subSocket) {
            return;
        }
        subContext = ZMQ.context(1);
        subSocket = subContext.socket(SocketType.SUB);
        subSocket.subscribe("".getBytes());
        subSocket.connect(Constants.NUC_SERVER_SUB);

        Log.d(APP_LOG, "sub connect nuc server success");
    }


    public void stop() {
        if (null == pubSocket) {
            pubSocket.close();
        }

        if (null == pubContext) {
            pubContext.term();
        }
        if (null == subSocket) {
            subSocket.close();
        }

        if (null == subContext) {
            subContext.term();
        }
    }

}
