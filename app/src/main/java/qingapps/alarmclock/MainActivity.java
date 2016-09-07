/********************************************************
 * AlarmClock Android Project                           *
 *                                                      *
 * Author:  Qingbo Jiang                                *
 *                                                      *
 * Start Date: July 24th 2016                           *
 *                                                      *
 * Purpose: A simple alarm clock.                       *
 *                                                      *
 * CopyRight: Please do not reuse unless you have       *
 *            obtained permission from the owner.       *
 *            Thank you.                                *
 ********************************************************/
package qingapps.alarmclock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
//import android.icu.util.Calendar;
import java.util.Calendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //--Alarm Manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int ringtone_selector;

    //---Alarm Manager

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;

        //init alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //init timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //init update
        update_text = (TextView) findViewById(R.id.update_text);

        //create instance of a calender
        final Calendar calendar = Calendar.getInstance();

        //create intent
        final Intent my_intent= new Intent(this.context, Alarm_Receiver.class);



        //create spinner for song selection
        Spinner spinner = (Spinner) findViewById(R.id.music_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.music_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //set onclick listener for spinner
        spinner.setOnItemSelectedListener(this);




        //init start button
        Button alarm_on = (Button) findViewById(R.id.alarm_on);



        //init onclick listener to start alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getCurrentMinute());

                int hour = alarm_timepicker.getCurrentHour();
                int minute = alarm_timepicker.getCurrentMinute();

                String hour_string;
                String minute_string;

                if(minute<10){
                    minute_string=("0" + String.valueOf(minute));
                }
                else
                {
                    minute_string = String.valueOf(minute);
                }
                if(hour==0)
                {
                    hour_string = String.valueOf(hour+12);
                    minute_string= minute_string + "am";
                }
                else if(hour==12)
                {
                    hour_string = String.valueOf(hour);
                    minute_string= minute_string + "pm";
                }
                else if(hour>12){
                    hour_string=String.valueOf(hour-12);
                    minute_string= minute_string + "pm";
                }
                else{
                    hour_string = String.valueOf(hour);
                    minute_string= minute_string + "am";
                }


                //method that changes the update text box
                set_alarm_text("Alarm set to:" + hour_string +":"+ minute_string);

                //extras
                my_intent.putExtra("extra","on");

                //extra value from dropdown menu/spinners

                my_intent.putExtra("ringtone_choice",ringtone_selector);

                //pending intent
                pending_intent= PendingIntent.getBroadcast(MainActivity.this,0,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);

                //set alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);

            }


        });

        //init stop button
        final Button alarm_off = (Button) findViewById(R.id.alarm_off);



        //init onclick listener to stop the alarm/undo alarm settings
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_alarm_text("Alarm Off!");

                //cancel alarm
                alarm_manager.cancel(pending_intent);

                //extras
                my_intent.putExtra("extra","off");
                //stop ringtone

                //extra value from dropdown menu/spinners (to prevent null/ crashing?)
                my_intent.putExtra("ringtone_choice",ringtone_selector);

                sendBroadcast(my_intent);
            }
        });

        }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        ringtone_selector = (int) l;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
