package esgi.androsecurity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import esgi.androsecurity.util.SendSMS;

public class MainActivity extends AppCompatActivity{

    EditText number, message;
    Button send;
    final static int permission_SMS = 0;
    final static int permission_GPS = 0;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText) findViewById(R.id.number);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, permission_SMS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, permission_GPS);
        }

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();

        if (sp.getBoolean("firstLaunch", true)) {
            editor.putString("number","0623717130");
            editor.putBoolean("data", true);
            editor.putBoolean("firstLaunch",false);
            editor.commit();
        }

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                SendSMS.send(number.getText().toString(),message.getText().toString(), getApplicationContext());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permission_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.parametres:
                Intent intent = new Intent(this,Settings.class);
                startActivity(intent);
                return true;
        }
        return true;
    }
}