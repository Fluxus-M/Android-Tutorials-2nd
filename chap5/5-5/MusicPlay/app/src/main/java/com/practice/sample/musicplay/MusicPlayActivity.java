package com.practice.sample.musicplay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicPlayActivity extends AppCompatActivity {
    private boolean bound = false;
    private MusicPlayService musicPlayService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bound = true;
            MusicPlayService.MusicPlayServiceBinder binder = (MusicPlayService.MusicPlayServiceBinder) service;
            musicPlayService = binder.getService();

            showPlayInformation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    private Button play;
    private Button pause;
    private Button stop;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        info = (TextView) findViewById(R.id.info);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMusic();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        Intent intent = new Intent(this, MusicPlayService.class);
        startService(intent);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (bound) {
            bound = false;
            unbindService(serviceConnection);
        }

        super.onDestroy();
    }

    private void showPlayInformation() {
        boolean playing = musicPlayService.isPlaying();

        if (playing) {
            info.setText("음악을 재생중 입니다.");
        } else {
            info.setText("재생 대기 상태 입니다.");
        }
    }

    private void playMusic() {
        musicPlayService.playMusic();
        showPlayInformation();
    }

    private void pauseMusic() {
        musicPlayService.pauseMusic();
        showPlayInformation();
    }

    private void stopMusic() {
        musicPlayService.stopMusic();
        showPlayInformation();
    }
}
