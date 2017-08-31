package com.example.neelb.sampleapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AnimalVideos extends AppCompatActivity {
    public VideoView mVideoView;
    public Context myContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_videos);
        if (readFromFile(this,"videos.txt").equals("0")){
            PostMessge(findViewById(android.R.id.content),"Meet each one of these adorable animals and laugh along with their never-ending mischief!", Snackbar.LENGTH_LONG);
            writeToFile("1","activity.txt",myContext);
        }
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        Spinner videoSelector = (Spinner) findViewById(R.id.videoSelect);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.videoNames,R.layout.quote_spinner_custom ); //android.R.layout.simple_spinner_item
        videoSelector.setAdapter(staticAdapter);
        videoSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                int id_to_use = -1; // Failsafe
                switch (selected){
                    case "Dog Video #2":
                        id_to_use = R.raw.dogs2;
                        break;
                    case "Dog Video #1":
                        id_to_use = R.raw.dogs1;
                        break;
                }
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                        + id_to_use);
                mVideoView.setVideoURI(uri);
                mVideoView.requestFocus();
                mVideoView.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mVideoView  = (VideoView)findViewById(R.id.ViewVideo);
        mVideoView.setMediaController(new MediaController(this));

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
