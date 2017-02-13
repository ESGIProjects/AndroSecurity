package esgi.androsecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Tensa on 13/02/2017.
 */

public class Settings extends AppCompatActivity {

    SharedPreferences sp;
    EditText number;
    Switch data;
    TextView tv;
    Button button;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        number = (EditText) findViewById(R.id.number);
        data = (Switch) findViewById(R.id.data);
        tv = (TextView) findViewById(R.id.dataLabel);
        button = (Button) findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();

                editor.putString("number",number.getText().toString());
                editor.putBoolean("data", data.isChecked());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
