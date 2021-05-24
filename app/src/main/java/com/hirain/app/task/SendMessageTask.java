package com.hirain.app.task;

import android.os.AsyncTask;

import com.hirain.app.listener.IMessageListener;
import com.hirain.app.zmq.ZmqEcuClient;

/**
 * 异步任务执行zmq 的通信
 */
public class SendMessageTask extends AsyncTask<String, Void, Object> {
    private ZmqEcuClient client = ZmqEcuClient.getInstance();

    private IMessageListener messageListener;
    public SendMessageTask(IMessageListener messageListener){
        this.messageListener = messageListener;
    }

    @Override
    protected Object doInBackground(String... strings) {
        String sendMessage = strings[0];
        client.sendMessage(sendMessage,messageListener);
        return null;
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
