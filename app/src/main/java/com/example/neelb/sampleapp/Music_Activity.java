package com.example.neelb.sampleapp;

import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Music_Activity extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    public String chosenSong;
    public int maxvolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        maxvolume = 50;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        SeekBar volumebar = (SeekBar) findViewById(R.id.volumeSeekBar);
        volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

                System.out.println("New Volume: "+progress);
                if (mediaPlayer != null) {
                    setVolume(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        ImageButton playjubilife_ = (ImageButton) findViewById(R.id.jubilifeRestart);
        //Button playjubilife = (Button) findViewById(R.id.jubilifeMusic); Rip Playjubilife Button 8/7/17
        playjubilife_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    stopSong();
                }
                chosenSong = "jubilife";
                startSong();

            }
        });
        //Button playtetristhemea = (Button) findViewById(R.id.playTetris); RIP button 8/7/17
        ImageButton playtetristhemea_ = (ImageButton) findViewById(R.id.tetrisRestart);
        //mediaPlayer = MediaPlayer.create(this, R.raw.tetristhemea);
        playtetristhemea_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mediaPlayer != null){
                    stopSong();
                }
                chosenSong = "tetristhemea";
                startSong();
            }
        });
        final ImageButton pauseplay = (ImageButton) findViewById(R.id.playpause);
        //final Button pausebutton = (Button) findViewById(R.id.musicpause); RIp Pause button 8/7/17
        pauseplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        pauseplay.setImageResource(R.drawable.playbutton);
                    } else {
                        mediaPlayer.start();
                        pauseplay.setImageResource(R.drawable.pause);
                    }
                }

            }
        });
        Button exit = (Button) findViewById(R.id.exitMusicButton);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    setVolume(0);
                    stopSong();

                }
                finish();
            }
        });
    }
    @Override
    protected void onDestroy(){
        if(mediaPlayer != null){
            //  Shut down MediaPlayer
            mediaPlayer.stop();
            setVolume(0);
            stopSong();
            Log.i("MusicPlayer","onDestroy() cleanup");
        }
        super.onDestroy();
    }
    public void setVolume(int currVolume){
        /*
        Utilizing the log functions to calculate volume for player.
         */
        float log1=(float)(Math.log(maxvolume-currVolume)/Math.log(maxvolume));
        mediaPlayer.setVolume(1-log1,1-log1);

    }
    public void startSong(){
        switch(chosenSong){
            case "tetristhemea":
                mediaPlayer = MediaPlayer.create(this,R.raw.tetristhemea);
                mediaPlayer.start();
                break;
            case "jubilife":
                mediaPlayer = MediaPlayer.create(this,R.raw.pearljubilife);
                mediaPlayer.start();
                break;
            default:
                break;
        }
    }
    public void stopSong(){
        mediaPlayer.stop();
    }

}
