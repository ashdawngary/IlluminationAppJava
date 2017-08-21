package com.example.neelb.sampleapp;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeepBreathing extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    public final Context mycontext = this;
    public int maxvolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_breathing);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        maxvolume = 50;
        /* Intialize Buttons */
        final Button playpause = (Button) findViewById(R.id.deepbreathing_play);
        Button restart = (Button) findViewById(R.id.deepbreathing_restart);
        /* Set up Handlers */
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(mycontext,R.raw.meditation);
                    setVolume(25);
                    mediaPlayer.start();
                    playpause.setText("Pause");
                }
                else if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playpause.setText("Play");
                }
                else{
                    mediaPlayer.start();
                    playpause.setText("Pause");
                }

            }

        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(mycontext,R.raw.meditation);
                    setVolume(25);
                    mediaPlayer.start();
                    playpause.setText("Pause");
                }
                else{
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    playpause.setText("Pause");
                }
            }
        });
    }
    public void setVolume(int currVolume){
        /*
        Utilizing the log functions to calculate volume for player.
         */
        float log1=(float)(Math.log(maxvolume-currVolume)/Math.log(maxvolume));
        mediaPlayer.setVolume(1-log1,1-log1);

    }
}
