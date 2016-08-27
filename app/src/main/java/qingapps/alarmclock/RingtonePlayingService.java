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

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.security.Provider;
/**
 * Created by king on 8/26/2016.
 */
public class RingtonePlayingService extends Service {


    MediaPlayer media_song;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
       Log.i("LocalService", "Recieved start id " + startId +": " + intent);

        media_song = MediaPlayer.create(this,R.raw.test);
        media_song.start();

       return START_NOT_STICKY;
   }
    @Override
    public void onDestroy()
    {
        Toast.makeText(this,"On Destroy Call", Toast.LENGTH_SHORT).show();
    }
}
