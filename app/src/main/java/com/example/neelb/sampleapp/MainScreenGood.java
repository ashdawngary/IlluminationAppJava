package com.example.neelb.sampleapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
// -113 79 -159

public class MainScreenGood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(240,240,240));
        setContentView(R.layout.activity_main_screen_good);
        final Intent noticeIntent = new Intent(this,Notice.class);
        final Intent storiesIntent = new Intent(this,Stories.class);
        final Intent feedbackIntent = new Intent(this,GPSActivity.class); // This is the feedback thing.
        final Intent tasklistIntent = new Intent(this,tasklistv2.class);
        final Intent contactIntent = new Intent(this,Contact.class);
        final Intent musicIntent = new Intent(this,Music_Activity.class);
        final Intent animalVideos = new Intent(this,AnimalVideos.class);
        final Intent homeScreen = new Intent(this,Mainscreenactivity.class);

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

        Button back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*
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
        ImageButton taskListInfo = (ImageButton) findViewById(R.id.taskListInfo);
        ImageButton musicInfo = (ImageButton) findViewById(R.id.musicInfo);
        ImageButton storiesInfo = (ImageButton) findViewById(R.id.storiesInfo);
        ImageButton breathingInfo = (ImageButton) findViewById(R.id.breathingInfo);
        ImageButton animalVideosInfo = (ImageButton) findViewById(R.id.animalvideosInfo);
        taskListInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {PostMessge(v,"Set goals for yourself and achieve them!",Snackbar.LENGTH_LONG);}
        });
        musicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {PostMessge(v,"Listen To some music!",Snackbar.LENGTH_LONG);}
        });
        storiesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {PostMessge(v,"Read some real life accounts",Snackbar.LENGTH_LONG);}
        });
        breathingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {PostMessge(v,"Practice Deep Breathing",Snackbar.LENGTH_LONG);}
        });
        animalVideosInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {PostMessge(v,"Watch some cute animal videos!",Snackbar.LENGTH_LONG);}
        });
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
}
