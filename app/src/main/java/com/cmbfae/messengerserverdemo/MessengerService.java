package com.cmbfae.messengerserverdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service {

    private static final int COMMUNICATION_MSG = 1000;
    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mMessenger.getBinder();
    }

    private Messenger mMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Message msgToClient = Message.obtain(msg);//返回给客户端的消息
            switch (msg.what)
            {
                //msg 客户端传来的消息
                case COMMUNICATION_MSG:
                    msgToClient.what = COMMUNICATION_MSG;
                    try
                    {
                        //模拟耗时
                        Thread.sleep(2000);
                        msgToClient.arg1 = 5;
                        msg.replyTo.send(msgToClient);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    } catch (RemoteException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    });

}
