package com.example.mysmsapp;

import android.app.Application;
import android.content.IntentFilter;
import android.provider.Telephony;

public class SmsApp extends Application {

    private SmsBroadcastReceiver receiver;


    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new SmsBroadcastReceiver();
        registerReceiver(receiver,new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

    }

    @Override
    public void onTerminate() {

        unregisterReceiver(receiver);
        super.onTerminate();

    }
}
