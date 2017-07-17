package com.example.neelb.sampleapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button playjubilife = (Button) findViewById(R.id.jubilifeMusic);
        playjubilife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    stopSong();
                }
                chosenSong = "jubilife";
                startSong();

            }
        });
        Button playtetristhemea = (Button) findViewById(R.id.playTetris);
        //mediaPlayer = MediaPlayer.create(this, R.raw.tetristhemea);
        playtetristhemea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mediaPlayer != null){
                    stopSong();
                }
                chosenSong = "tetristhemea";
                startSong();
            }
        });
        Button pausebutton = (Button) findViewById(R.id.musicpause);
        pausebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else{
                    mediaPlayer.start();
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
