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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.security.Provider;
import java.util.Random;

/**
 * Created by king on 8/26/2016.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
       Log.i("LocalService", "Recieved start id " + startId +": " + intent);

        //fetch extra values
        String state = intent.getExtras().getString("extra");

        int choice = intent.getExtras().getInt("ringtone_choice");

        //notification services
        NotificationManager notification_manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //setup intent that goes to main activity

        Intent intent_main_activity = new Intent(this.getApplicationContext(),MainActivity.class);

        //set pending intent
        PendingIntent pending_main_activity = PendingIntent.getActivity(this,0,intent_main_activity,0);


        //make notification params

            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("Alarm")
                    .setContentText("Dismiss")
                    .setContentIntent(pending_main_activity)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setAutoCancel(true)
                    .build();


        //converts states to start ids
        assert state!=null;
        switch (state) {
            case "on":
                startId = 1;
                break;
            case "off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        //no music playing and user press on, should start playing

        if(!this.isRunning && startId ==1){
            if(choice==0)
            {
                Random r = new Random();
                int max=2;
                int min=1;
                choice = r.nextInt(max - min+1) + min;
                Log.i("Random Number: ", String.valueOf(choice));

            }

            if(choice==1)
            {
                media_song = MediaPlayer.create(this,R.raw.harumodoki);
                media_song.start();
            }

            else if(choice==2)
            {
                media_song = MediaPlayer.create(this,R.raw.vidro_moyou);
                media_song.start();
            }

            Log.i("no music", "play music");


            this.isRunning=true;
            this.startId=0;

            //setup notification call command
            notification_manager.notify(0,notification_popup);
        }

        //if music playing and alarm off, music should stop
        else if(this.isRunning && startId ==0) {
            Log.i("yes music", "end music");

            media_song.stop();
            media_song.reset();
            this.isRunning=false;
        }

        //do nothing when alarm off pressed and no music playing, do nothing
        else if(this.isRunning && startId ==1){
            Log.i("no music", "end music");

        }

        //music playing and user presses on, do nothing
        else if(!this.isRunning && startId ==0){
            Log.i("yes music", "play music");

        }
        //other cases, do nothing
        else{

        }


       return START_NOT_STICKY;
   }
    @Override
    public void onDestroy()
    {
        Toast.makeText(this,"On Destroy Call", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        this.isRunning=false;

    }
}
