package com.IlluminationJava.neelb.Illumination;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Music_Activity extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    public String chosenSong;
    public int maxvolume;
    public final Context myContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        maxvolume = 50;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        SeekBar volumebar = (SeekBar) findViewById(R.id.volumeSeekBar);
        if (readFromFile(this,"music.txt").equals("0")){
            PostMessge(findViewById(android.R.id.content),"Whether you want to feel calmer or more full of energy, listen to these tracks to help you feel better!", Snackbar.LENGTH_LONG);
            writeToFile("1","music.txt",myContext);
        }
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
        ImageButton restartViolin = (ImageButton) findViewById(R.id.relaxViolinRestart);
        restartViolin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    stopSong();
                }
                chosenSong = "relaxViolin";
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
                mediaPlayer = MediaPlayer.create(this,R.raw.musictrack1);
                mediaPlayer.start();
                break;
            case "jubilife":
                mediaPlayer = MediaPlayer.create(this,R.raw.musictrack2);
                mediaPlayer.start();
                break;
            case "relaxViolin":
                mediaPlayer = MediaPlayer.create(this,R.raw.nmedit);
                mediaPlayer.start();
                break;
            default:
                break;
        }
    }
    public void stopSong(){
        mediaPlayer.stop();
    }
    public void PostMessge(View v, String t, int mode){
        final Snackbar sn = Snackbar.make(v,t,mode);
        View snackbarView = sn.getView();
        TextView snackTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setMaxLines(6);
        sn.setAction("Dismiss",new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sn.dismiss();
            }
        });
        sn.show();
    }
    private String readFromFile(Context context, String textfile) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(textfile);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    private void writeToFile(String data,String textfile,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(textfile, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
