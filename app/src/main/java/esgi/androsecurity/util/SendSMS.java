package esgi.androsecurity.util;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Tensa on 03/02/2017.
 */

public class SendSMS {

    public static void send(String number, String message,Context context){
        if (number.equals("") || message.equals("")) {
            Toast.makeText(context,"Erreur",Toast.LENGTH_LONG).show();
        }
        else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,message,null,null);
            Toast.makeText(context, "SMS sent !", Toast.LENGTH_LONG).show();
        }
    }

}
