package com.example.neelb.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
// -113 79 -159

public class MainScreenGood extends AppCompatActivity {
    public Context myContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("msg","loading oncreate()");
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        setContentView(R.layout.activity_main_screen_good);
        final Intent noticeIntent = new Intent(this,Notice.class);
        final Intent storiesIntent = new Intent(this,Stories.class);
        final Intent feedbackIntent = new Intent(this,GPSActivity.class); // This is the feedback thing.
        final Intent tasklistIntent = new Intent(this,tasklistv2.class);
        final Intent contactIntent = new Intent(this,Contact.class);
        final Intent musicIntent = new Intent(this,Music_Activity.class);
        final Intent animalVideos = new Intent(this,AnimalVideos.class);
        final Intent homeScreen = new Intent(this,Mainscreenactivity.class);
        final Intent deepbreathing = new Intent(this,DeepBreathing.class);
        Button deepbreathingbutton = (Button) findViewById(R.id.good_deepbreathing);
        deepbreathingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(deepbreathing);
            }
        });
        Button animalsButton = (Button) findViewById(R.id.animalVideosButton);
        animalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(animalVideos);
            }
            });
        Button musicbutton = (Button) findViewById(R.id.musicButton);
        musicbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(musicIntent);
            }
        });
        TextView aboutTheApp = (TextView) findViewById(R.id.appinfobutton);
        aboutTheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(noticeIntent);
            }
        });
        //Button back = (Button) findViewById(R.id.backButton);
        /*
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button notice = (Button) findViewById(R.id.noticeButton);
        notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(noticeIntent);
            }
        });
        */

        final Button stories = (Button) findViewById(R.id.storyButton);
        stories.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(storiesIntent);
            }
        });
        Button tasklistButton = (Button) findViewById(R.id.TaskListButton);
        tasklistButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(tasklistIntent);
            }
        });
        TextView feedbackbutton = (TextView) findViewById(R.id.feedbacktextbutton);
        feedbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(feedbackIntent);
            }
        });
        TextView changeMood = (TextView) findViewById(R.id.changeMood);
        changeMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });
        /*
        contactbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(contactIntent);
            }
        });
        */
        // Set all Info Buttons <ImageButton>



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
}
