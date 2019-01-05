package com.practice.sample.musicplay;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MusicPlayService extends Service {
    private static final String NOTIFICATION_CHANNEL_ID = "channel1_ID";
    private static final String NOTIFICATION_CHANNEL_NAME = "channel1";

    private final MusicPlayServiceBinder musicPlayServiceBinder = new MusicPlayServiceBinder();
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return musicPlayServiceBinder;
    }

    public boolean isPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return true;
        }

        return false;
    }

    public void playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopMusic();
                }
            });

            makeForegroundService();
        }

        mediaPlayer.start();
    }

    public void pauseMusic() {
        if (isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            stopForeground(true);
        }
    }

    @Override
    public void onDestroy() {
        stopMusic();

        super.onDestroy();
    }

    private void makeForegroundService() {
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    importance);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        int notificationId = 1010;

        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(getApplicationContext(),
                NOTIFICATION_CHANNEL_ID);

        builder.setSmallIcon(R.drawable.icon_play);
        builder.setContentTitle("음악 플레이 서비스");
        builder.setContentText("음악 플레이 중입니다.");

        Intent intent = new Intent(getApplicationContext(), MusicPlayActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(getApplicationContext(), 1010, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        startForeground(notificationId, builder.build());
    }

    public class MusicPlayServiceBinder extends Binder {
        MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }
}
