package com.example.neelb.sampleapp;

import android.content.Intent;
import android.graphics.Color;
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
        //final Intent tasklistIntent = new Intent(this,GPSActivity.class); replacing with tsklistintentv2 this is a feedbck thing
        final Intent tasklistIntent = new Intent(this,tasklistv2.class);
        final Intent contactIntent = new Intent(this,Contact.class);
        final Intent musicIntent = new Intent(this,Music_Activity.class);
        final Intent animalVideos = new Intent(this,AnimalVideos.class);
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
        /*
        Button contactbutton = (Button) findViewById(R.id.contactButton);
        contactbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(contactIntent);
            }
        });
        */
    }
}
