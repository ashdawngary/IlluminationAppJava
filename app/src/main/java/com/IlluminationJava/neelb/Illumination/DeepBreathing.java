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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DeepBreathing extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    public final Context mycontext = this;
    public final Context myContext = mycontext;
    public int maxvolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_breathing);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        maxvolume = 50;
        if (readFromFile(this,"dbreath.txt").equals("0")){
            PostMessge(findViewById(android.R.id.content),"Follow along with this guided breathing exercise to help you slow down and increase your focus.", Snackbar.LENGTH_LONG);
            writeToFile("1","dbreath.txt",myContext);
        }
        /* Intialize Buttons */
        final Button playpause = (Button) findViewById(R.id.deepbreathing_play);
        Button restart = (Button) findViewById(R.id.deepbreathing_restart);
        Button pause = (Button) findViewById(R.id.deepbreathing_pause);
        /* Set up Handlers */
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(mycontext, R.raw.guidedimagerylong);
                    setVolume(25);
                    mediaPlayer.start();
                }
                else{
                    mediaPlayer.start();
                }

            }

        });
        pause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!(mediaPlayer == null)){
                    mediaPlayer.pause();
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(mycontext,R.raw.guidedimagerylong);
                    setVolume(25);
                    mediaPlayer.start();
                }
                else{
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
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
    public void PostMessge(View v, String t, int mode){
        final Snackbar sn = Snackbar.make(v,t,mode);
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
    @Override
    protected void onDestroy(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        super.onDestroy();
    }

}
