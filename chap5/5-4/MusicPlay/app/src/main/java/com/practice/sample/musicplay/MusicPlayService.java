package com.practice.sample.musicplay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayService extends Service {
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
        }
    }

    @Override
    public void onDestroy() {
        stopMusic();

        super.onDestroy();
    }

    public class MusicPlayServiceBinder extends Binder {
        MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }
}
