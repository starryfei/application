package com.hirain.app.task;

import com.hirain.app.listener.IMessageListener;
import com.hirain.app.zmq.NucPubSubServer;

/**
 * 异步任务执行zmq 的通信
 */
public class NucMessageThread {
    private NucPubSubServer nucServer = NucPubSubServer.getInstance();

    public void sendMessage(String command){
        new Thread(() -> nucServer.sendMessage(command)).start();
    }
    public void recvMessage(IMessageListener messageListener,String command) {
        nucServer.recvMessage(messageListener,command);
    }


}
