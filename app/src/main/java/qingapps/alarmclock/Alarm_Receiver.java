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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by king on 8/18/2016.
 */
public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Sucess!", "We did it fam");

        //fetch extras from intent, on or off
        String get_extras= intent.getExtras().getString("extra");
        Log.e("Log of my Key:", get_extras);

        //fetch extra from intent, song id
        int selection_id = intent.getExtras().getInt("ringtone_choice");
        Log.e("Choice from key:", String.valueOf(selection_id));




        //create an intent to the ringtone service
        Intent service_intent=new Intent(context, RingtonePlayingService.class);

        //pass extra from reciever to ringtone playing services
        service_intent.putExtra("extra",get_extras);

        //pass extra int
        service_intent.putExtra("ringtone_choice", selection_id);

        //start ringtone service
        context.startService(service_intent);


    }






}
