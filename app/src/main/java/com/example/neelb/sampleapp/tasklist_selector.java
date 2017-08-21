package com.example.neelb.sampleapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class tasklist_selector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist_selector);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        ImageButton stayactive = (ImageButton) findViewById(R.id.stayactiveselect);
        ImageButton volunteerSelect = (ImageButton) findViewById(R.id.volunteerselect);
        ImageButton wellness = (ImageButton) findViewById(R.id.wellnessselect);
        try {
            String choice = readFromFile(this, "tasklist_choice.txt");
        }
        catch(IOException e){
            Log.e("Tasklist Selector","Compensating by creating file to appease people.");
            writeToFile("","tasklist_choice.txt",this);
        }
            final Context mycontext = this;
        stayactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("StayActive","tasklist_choice.txt",mycontext);
                finish();
            }
        });
        volunteerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("Volunteer","tasklist_choice.txt",mycontext);
                finish();
            }
        });
        wellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("Wellness","tasklist_choice.txt",mycontext);
                finish();
            }
        });
    }
    private String readFromFile(Context context,String textfile) throws IOException{

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
