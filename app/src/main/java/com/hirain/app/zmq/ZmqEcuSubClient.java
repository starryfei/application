package com.hirain.app.zmq;

import android.util.Log;

import com.hirain.app.common.Constants;
import com.hirain.app.listener.IMessageListener;

import org.apache.commons.lang3.StringUtils;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import static com.hirain.app.common.Constants.APP_LOG;

public class ZmqEcuSubClient {

    private ZMQ.Socket socket;
    private ZMQ.Context context;
    private static ZmqEcuSubClient zmqClient;
    private int count = 0;

    private boolean run =true;


    private ZmqEcuSubClient(){
    }

    public static ZmqEcuSubClient getInstance() {
        if(null == zmqClient) {
            zmqClient = new ZmqEcuSubClient();
            zmqClient.start();

        }
        return zmqClient;
    }

    public void recMessage(IMessageListener listener) {
        if (socket == null || context == null) {
            start();
        }
        while (true) {
            if(run) {
                String s = socket.recvStr();
                System.out.println(s);
                if (StringUtils.isNoneBlank(s)) {
                    Log.d(APP_LOG, s);
                    listener.messageListener(s);
                }
            }else {
                break;
            }

        }
    }
    public void initRecv(){
        run = true;
    }
    public void stopRecv(){
        run = false;
    }
    public void start() {
        if (null != socket) {
            return;
        }
        context = ZMQ.context(1);
        socket = context.socket(SocketType.SUB);
        socket.connect(Constants.ECU_SERVER_SUB);
        socket.subscribe("".getBytes());
        socket.setReceiveTimeOut(3*1000);
        Log.d(APP_LOG,"connect pub server success");
    }



    public void stop(){
        if(null == socket) {
            socket.close();
        }
        if(null == context) {
            context.term();
        }
        Log.d(APP_LOG,"stop pub server ");

    }

}
