package esgi.androsecurity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Tensa on 03/02/2017.
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++){
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        for (SmsMessage message : messages){

            String strMessageFrom = message.getDisplayOriginatingAddress();
            String strMessageBody = message.getDisplayMessageBody();
            String body = strMessageBody + " - " + strMessageFrom;

            Toast.makeText(context, "From : " +strMessageFrom+"\nBody : "+strMessageBody, Toast.LENGTH_LONG).show();

            //SendSMS.send("0623717130",body,context);
        }
    }

}
