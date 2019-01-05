package com.practice.sample.musicplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicPlayActivity extends AppCompatActivity {
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
    }

    private void playMusic() {
        Intent intent = new Intent(this, MusicPlayService.class);
        intent.putExtra("command", 0);
        startService(intent);
    }

    private void pauseMusic() {
        Intent intent = new Intent(this, MusicPlayService.class);
        intent.putExtra("command", 1);
        startService(intent);
    }

    private void stopMusic() {
        Intent intent = new Intent(this, MusicPlayService.class);
        intent.putExtra("command", 2);
        startService(intent);
    }
}
