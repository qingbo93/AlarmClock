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


        //create an intent to the ringtone service
        Intent service_intent=new Intent(context, RingtonePlayingService.class);

        //start ringtone service
        context.startService(service_intent);


    }






}
