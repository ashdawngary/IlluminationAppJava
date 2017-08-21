package com.example.neelb.sampleapp;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class AnimalVideos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_videos);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.kittens);
        VideoView mVideoView  = (VideoView)findViewById(R.id.ViewVideo);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
    }
}
