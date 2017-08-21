package com.example.neelb.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainScreenMedium extends AppCompatActivity {
    public final Context mycontext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_medium);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        /*TODO: Write Up All Intents for Medium Screen. Use "mycontext" in case we need to change.*/
        final Intent noticeIntent = new Intent(mycontext,Notice.class);
        final Intent storiesIntent = new Intent(mycontext,Stories.class);
        final Intent feedbackIntent = new Intent(mycontext,GPSActivity.class);
        final Intent tasklistIntent = new Intent(mycontext,tasklistv2.class);
        final Intent contactIntent = new Intent(mycontext,Contact.class);
        final Intent musicIntent = new Intent(mycontext,Music_Activity.class);
        final Intent animalVideos = new Intent(mycontext,AnimalVideos.class);
        final Intent homeScreen = new Intent(mycontext,Mainscreenactivity.class);
        final Intent deepbreathingIntent = new Intent(mycontext,DeepBreathing.class);
        /*TODO: Initialize all TextViews in this segment */
        TextView notice = (TextView) findViewById(R.id.medium_about);
        TextView feedback = (TextView) findViewById(R.id.medium_feedback);
        TextView moodChanger = (TextView) findViewById(R.id.medium_back);

        /*TODO: Initialize all buttons in this segment */
        Button mediumTaskList = (Button) findViewById(R.id.medium_tasklist);
        final Button deepbreathing = (Button) findViewById(R.id.medium_deepbreathing);
        Button stories = (Button) findViewById(R.id.medium_stories);
        Button peertexting = (Button) findViewById(R.id.medium_peerTexting);
        Button videos = (Button) findViewById(R.id.medium_videos);

        /*TODO: Initialize all button-handlers in this segment */
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(noticeIntent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(feedbackIntent);
            }
        });
        moodChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mediumTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(tasklistIntent);
            }
        });
        deepbreathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(deepbreathingIntent);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(storiesIntent);
            }
        });
        peertexting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(contactIntent);
            }
        });
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(animalVideos);
            }
        });
    }
}
