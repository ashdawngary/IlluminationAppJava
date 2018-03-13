package com.IlluminationJava.neelb.Illumination;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
    public  Intent noticeIntent;
    public  Intent storiesIntent;
    public  Intent feedbackIntent; // This is the feedback thing.
    public  Intent tasklistIntent;
    public  Intent contactIntent;
    public  Intent musicIntent;
    public  Intent animalVideos;
    public  Intent homeScreen;
    public  Intent deepbreathing;
    public  Intent transition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeIntent = new Intent(this,Notice.class);
        storiesIntent = new Intent(this,Stories.class);
        feedbackIntent = new Intent(this,GPSActivity.class); // This is the feedback thing.
        tasklistIntent = new Intent(this,tasklistv2.class);
        contactIntent = new Intent(this,Contact.class);
        musicIntent = new Intent(this,Music_Activity.class);
        animalVideos = new Intent(this,AnimalVideos.class);
        homeScreen = new Intent(this,Mainscreenactivity.class);
        deepbreathing = new Intent(this,DeepBreathing.class);
        transition = new Intent(this,TransitionActivity.class);
        Log.i("msg","loading oncreate()");
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        setContentView(R.layout.activity_main_screen_good);

        Button deepbreathingbutton = (Button) findViewById(R.id.good_deepbreathing);
        deepbreathingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("Deep Breathing","transitiondisplay.txt",myContext);
                startActivity(transition);
                //startActivity(deepbreathing);
            }
        });
        Button animalsButton = (Button) findViewById(R.id.animalVideosButton);
        animalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("Animal Videos","transitiondisplay.txt",myContext);
                startActivity(transition);
                //startActivity(animalVideos);
            }
            });
        Button musicbutton = (Button) findViewById(R.id.musicButton);
        musicbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                writeToFile("Music","transitiondisplay.txt",myContext);
                startActivity(transition);
                //startActivity(musicIntent);
            }
        });
        TextView aboutTheApp = (TextView) findViewById(R.id.appinfobutton);
        aboutTheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.illuminationapp.com"));
                startActivity(browserIntent);
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
                writeToFile("Stories","transitiondisplay.txt",myContext);
                startActivity(transition);
                //startActivity(storiesIntent);
            }
        });
        Button tasklistButton = (Button) findViewById(R.id.TaskListButton);
        tasklistButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                writeToFile("Activities","transitiondisplay.txt",myContext);
                startActivity(transition);
                //startActivity(tasklistIntent);
            }
        });
        TextView feedbackbutton = (TextView) findViewById(R.id.feedbacktextbutton);
        feedbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenFeedBackBox().show();
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
    public AlertDialog GenFeedBackBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Would you like to send feedback to the app developers?");

        builder.setPositiveButton("Illumination", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Close dialog and then open Quoteselector.
                dialog.dismiss();
                // Start Text Intent.
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.illuminationapp.com/feedback"));
                startActivity(browserIntent);
            }
        });

        builder.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
                //startActivity(feedbackIntent);
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }
}
