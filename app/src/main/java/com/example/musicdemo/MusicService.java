package com.example.musicdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
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
        mMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.banduyen);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_START_SERVICE:
                    startForegroundService();
                    break;
                case ACTION_PLAY:
                    if (mMediaPlayer.isPlaying()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.MusicPlay), Toast.LENGTH_LONG).show();
                    } else {
                        mMediaPlayer.start();
                    }
                    break;
                case ACTION_PAUSE:
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        Toast.makeText(getApplicationContext(), getString(R.string.PauseMusic), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.Errorpause), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundService() {
        Toast.makeText(getApplicationContext(), getString(R.string.servicestarted), Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_android_black_24dp);
        builder.setPriority(1);
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
        Notification notification = builder.build();
        //startForeground
        startForeground(1, notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, getString(R.string.boundServicedestroy), Toast.LENGTH_SHORT).show();
        stopSelf();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, ACTION_STOP_SERVICE, Toast.LENGTH_SHORT).show();

        stopForeground(true);
        mMediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyConnections extends Binder {
        MusicService getConnection() {
            return MusicService.this;
        }
    }
}
