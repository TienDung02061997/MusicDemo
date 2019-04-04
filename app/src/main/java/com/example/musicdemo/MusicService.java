package com.example.musicdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MusicService extends Service {


    public static final String ACTION_START_SERVICE = "ACTION_START_SERVICE";

    public static final String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";

    public static final String ACTION_PAUSE = "ACTION_PAUSE";

    public static final String ACTION_PLAY = "ACTION_PLAY";



    private MediaPlayer mMediaPlayer;

    public MusicService() {

    }


    @Override
    public void onCreate() {
        // Create music
        mMediaPlayer =MediaPlayer.create(getBaseContext(),R.raw.banduyen);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();//lay ra action cua intent(lay ra hoat dong chung duoc thuc hien)

            switch (action) {
                case ACTION_START_SERVICE:
                    startForegroundService();

                    break;
                case ACTION_STOP_SERVICE:
                    stopForegroundService();
                    Toast.makeText(this, " service is stopped.", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_PLAY:
                    Toast.makeText(getApplicationContext(), "You  Play", Toast.LENGTH_LONG).show();
                    break;

                case ACTION_PAUSE:
                    Toast.makeText(getApplicationContext(), "Pause  Music", Toast.LENGTH_LONG).show();
                    break;



            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void stopForegroundService() {
        Toast.makeText(this,ACTION_STOP_SERVICE , Toast.LENGTH_SHORT).show();

        // Stop foreground service and remove the notification.
        stopForeground(true);
        mMediaPlayer.stop();
        // Stop the foreground service.
        stopSelf();
    }




    private void startForegroundService() {
        Toast.makeText(getApplicationContext(), " service is started.", Toast.LENGTH_SHORT).show();





        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        builder.setSmallIcon(R.drawable.ic_android_black_24dp);
        // Make the notification max priority.
        builder.setPriority(1);
        // Make head-up notification.


        // Add Play button intent in notification.
        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction(ACTION_PLAY);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);
        NotificationCompat.Action playAction = new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent);
        builder.addAction(playAction);

        // Add Pause button intent in notification.
        Intent pauseIntent = new Intent(this, MusicService.class);
        pauseIntent.setAction(ACTION_PAUSE);
        PendingIntent pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
        NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", pendingPrevIntent);
        builder.addAction(prevAction);

        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);




    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "bound Service destroy", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
