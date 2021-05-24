package com.hirain.app.task;

import android.os.AsyncTask;

import com.hirain.app.listener.IMessageListener;
import com.hirain.app.zmq.NucPubSubServer;

/**
 * 异步任务执行zmq 的通信
 */
@Deprecated
public class NucMessageTask extends AsyncTask<String, Void, Object> {
    private NucPubSubServer nucServer = NucPubSubServer.getInstance();

    private IMessageListener messageListener;
    public NucMessageTask(IMessageListener messageListener){
        this.messageListener = messageListener;
    }

    @Override
    protected Object doInBackground(String... strings) {
        String sendMessage = strings[0];
        String command = strings[1];
        nucServer.sendMessage(sendMessage);

        //nucServer.recvMessage(messageListener,command);
        return command;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
