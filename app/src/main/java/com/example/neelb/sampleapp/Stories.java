package com.example.neelb.sampleapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Stories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        final Context mycontext = this;
        Spinner storyChooser = (Spinner) findViewById(R.id.storySpinner);
        final TextView storyTextView = (TextView) findViewById(R.id.storyProjector);
        final TextView authorCredit = (TextView) findViewById(R.id.authorCredit);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.StoryTitles, android.R.layout.simple_spinner_item);
        storyChooser.setAdapter(staticAdapter);
        storyChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("item",(String) parent.getItemAtPosition(position) );
                int id_for_story = -1;
                String storyAuthor = "";
                switch((String) parent.getItemAtPosition(position)){
                    case "SampleStory1":
                        id_for_story = R.raw.samplestory1;
                        storyAuthor = "Neel";
                        break;
                    case "SampleStory2":
                        id_for_story = R.raw.samplestory2;
                        storyAuthor = "Unknown";
                        break;
                    default:
                        storyAuthor = "Error Spinner Item Not found";
                        id_for_story = R.raw.unabletofindstory;
                }
                String textToDisplay = readRawTextFile(mycontext,id_for_story);
                Log.i("textDisplay","Length of Story is: "+textToDisplay.length());
                storyTextView.setText(textToDisplay);
                authorCredit.setText("Author: "+storyAuthor);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static String readRawTextFile(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
