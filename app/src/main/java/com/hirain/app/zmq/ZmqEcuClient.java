package com.hirain.app.zmq;


import android.util.Log;

import com.hirain.app.common.Constants;
import com.hirain.app.listener.IMessageListener;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import static com.hirain.app.common.Constants.APP_LOG;


public class ZmqEcuClient {
    private ZMQ.Socket socket;
    private ZMQ.Context context;
    private static ZmqEcuClient zmqEcuClient;
    private int count = 0;
    private ZmqEcuClient(){
    }

    public static ZmqEcuClient getInstance() {
        if(null == zmqEcuClient) {
            zmqEcuClient = new ZmqEcuClient();
            zmqEcuClient.start();
        }
        return zmqEcuClient;
    }

    public void sendMessage(final String message, IMessageListener listener) {

        if (socket != null) {
            socket.send(message,0);
            String str = socket.recvStr(0);
            listener.messageListener(str);
            socket.close();
            socket = null;
        } else {
            // 失败重试
                start();
                sendMessage(message, listener);
//                Log.e(APP_LOG,"reconnect："+count);

//            }
        }
    }
    public void start() {
        if (null != socket) {
            return;
        }
        context = ZMQ.context(1);
        socket = context.socket(SocketType.REQ);
        socket.setSendTimeOut(3*1000);
        socket.setReceiveTimeOut(3*1000);
        socket.setLinger(0);

        socket.connect(Constants.ECU_SERVER);

        Log.d(APP_LOG,"connect server success");
    }



    public void stop(){
        if(null == socket) {
          socket.close();
         }
        if(null == context) {
            context.term();
        }
    }


}
