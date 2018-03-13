package com.IlluminationJava.neelb.Illumination;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

public class TransitionActivity extends AppCompatActivity {
    public static String textToDisplay;
    public static TextView transition_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        transition_text = (TextView) findViewById(R.id.transition_title);
        textToDisplay = readFromFile(this,"transitiondisplay.txt");
        transition_text.setText(textToDisplay);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        /*
        Button advance = (Button) findViewById(R.id.transition_advance);
        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advance();
            }
        });
        */
        Timer timer = new Timer();
        AsyncTask1 task = new AsyncTask1(this);
        timer.schedule(task,2000);
    }
    public void advance(){
        switch(textToDisplay){
            case "Activities":
                Intent dispAct = new Intent(this,tasklistv2.class);
                startActivity(dispAct);
                finish(); // Once they exit this shouldnt be seen.
                break;
            case "Music":
                Intent dispMusic = new Intent(this,Music_Activity.class);
                startActivity(dispMusic);
                finish();
                break;
            case "Stories":
                Intent dispstories = new Intent(this,Stories.class);
                startActivity(dispstories);
                finish();
                break;
            case "Animal Videos":
                Intent dispVideos  = new Intent(this,AnimalVideos.class);
                startActivity(dispVideos);
                finish();
                break;
            case "Deep Breathing":
                Intent dispDB = new Intent(this,DeepBreathing.class);
                startActivity(dispDB);
                finish();
                break;
            case "About Illumination":
                Log.i("Transition","Loaded Info Class");
                Intent notice = new Intent(this,Notice.class);
                startActivity(notice);
                finish();
                break;
            default:
                Log.i("Transition","Cant Find <"+textToDisplay+">");
                transition_text.setText("Unable to load class.");

        }
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
class AsyncTask1 extends TimerTask {
    public TransitionActivity k;
    public AsyncTask1(TransitionActivity p){
        k = p;
    }
    @Override
    public void run(){
        Log.i("ExampleTask","Advancing the Transition Activity defined in k!");
        if(k == null){
         Log.i("ExampleTask","Nothing to Advance!");
        }
        else {
            k.advance();
        }
    }

}
