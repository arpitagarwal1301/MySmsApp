package com.example.mysmsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SmsBroadcastReceiver extends BroadcastReceiver {


    private String smsSender = "";
    private String smsBody = "";
    private static SmsListener smsListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_DELIVER_ACTION)){


            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

                smsSender = smsMessage.getDisplayOriginatingAddress();
                smsBody += smsMessage.getDisplayMessageBody();
            }


            smsListener.onTextReceived(smsSender,smsBody);

        }
    }

    public static void setListener(SmsListener listener){
        smsListener = listener;
    }

    public interface SmsListener {
        void onTextReceived(String address,String msg);
    }

}
