package com.example.neelb.sampleapp;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stories extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    public int CurrentPage;
    public int maxPage;
    public String currentStory;
    public TextView PageDown;
    public TextView PageUp;
    public final Context myContext = this;
    public final  String DEBUG_TAG = "GestureDebug";
    public GestureDetectorCompat mDetector;
    public final Context mycontext = this;
    public void UpdateStory(){
        // Step 1: Initialize
        int story_id_for_display = 0;
        String storyAuthor = "uninitialized";
        if (readFromFile(this,"stories.txt").equals("0")){
            PostMessge(findViewById(android.R.id.content),"Read some real life accounts", Snackbar.LENGTH_LONG);
            writeToFile("1","stories.txt",myContext);
        }
        //int[] sampleStory1Pages = {R.raw.samplestory1_1,R.raw.samplestory1_2,R.raw.samplestory1_3};
        //int[] sampleStory2Pages = {R.raw.samplestory2_1,R.raw.samplestory2_2};
        int[] alirothrockpages = {R.raw.ali_1,R.raw.ali_2};
        int[] andrewPages = {R.raw.andrew_1,R.raw.andrew_2};
        int[] melisaPages = {R.raw.melissa_1,R.raw.melissa_2,R.raw.melissa_3};
        //CurrentPage = 1;
        final TextView PageLabelView = (TextView) findViewById(R.id.pageLabel);
        final TextView storyTextView = (TextView) findViewById(R.id.storyProjector);
        final TextView authorCredit = (TextView) findViewById(R.id.authorCredit);
        switch(currentStory){
            case "Meet Ali":
                story_id_for_display = alirothrockpages[CurrentPage-1];
                storyAuthor = "Ali Rothrock";
                maxPage = 2;
                break;
            case "Meet Andrew":
                story_id_for_display = andrewPages[CurrentPage-1];
                storyAuthor = "Andrew Onimus";
                maxPage = 2;
                break;
            case "Meet Melissa":
                story_id_for_display = melisaPages[CurrentPage-1];
                storyAuthor = "Melissa Rice";
                maxPage = 3;
                break;
        }
        // Step 2: Paste all the data that your get
        PageLabelView.setText("Page: "+CurrentPage+"/"+maxPage);
        authorCredit.setText("Author: "+storyAuthor);
        String textToDisplay = readRawTextFile(mycontext,story_id_for_display);
        Log.i("textDisplay","Length of Story is: "+textToDisplay.length());
        storyTextView.setText(textToDisplay);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CurrentPage = 1;
        maxPage = 1000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        PageDown = (TextView) findViewById(R.id.pagepositive);
        PageUp = (TextView) findViewById(R.id.pagenegative); // Re-Inflation since it isnt working atm ;-;

        PageUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = Math.max(CurrentPage-1,1);
                UpdateStory();
            }
        });
        PageDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = Math.min(CurrentPage+1,maxPage);
                UpdateStory();
            }
        });



        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        final Context mycontext = this;
        Spinner storyChooser = (Spinner) findViewById(R.id.storySpinner);
        final TextView PageLabelView = (TextView) findViewById(R.id.pageLabel);
        final TextView storyTextView = (TextView) findViewById(R.id.storyProjector);
        final TextView authorCredit = (TextView) findViewById(R.id.authorCredit);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.StoryTitles,R.layout.spinner_row);//original : android.R.layout.simple_spinner_item
        storyChooser.setAdapter(staticAdapter);
        storyChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("item",(String) parent.getItemAtPosition(position) );
                int id_for_story = -1;
                String storyAuthor = "";
                currentStory = (String) parent.getItemAtPosition(position);
                /*)
                switch((String) parent.getItemAtPosition(position)){
                    case "SampleStory1":
                        //CurrentPage = 1;
                        //maxPage = 3;
                        //storyAuthor = "Neel";
                        UpdateStory();
                        break;
                    case "SampleStory2":
                        CurrentPage = 1;
                        maxPage = 2;
                        storyAuthor = "Unknown";
                        UpdateStory();
                        break;
                    default:
                        // just stall we arent sure.
                }
                */
                CurrentPage = 1;
                UpdateStory();

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
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
    public void PostMessge(View v, String t, int mode){
        final Snackbar sn = Snackbar.make(v,t,mode);
        View snackbarView = sn.getView();
        TextView snackTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setMaxLines(4);
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
