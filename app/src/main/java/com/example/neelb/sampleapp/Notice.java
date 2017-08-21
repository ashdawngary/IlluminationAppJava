package com.example.neelb.sampleapp;

import android.content.Context;
import android.graphics.Color;
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
import java.util.HashMap;

public class Notice extends AppCompatActivity {
    public final Context mycontext = this; // we will be reading alot so we need this for sure.
    public TextView answerBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        // Need to Map Something?  Lets HashMap!  We hashmap String -> int since int is the id for the R.Raw file containing <String>
        final HashMap<String,Integer> qtoa = new HashMap<>(); //News flash aparently Hashmap doesnt like "primitives" so rip int
        qtoa.put("Disclaimer",R.raw.disclaimer); // Check: fill in R.id for Disclaimer. (put in a text file)
        qtoa.put("About Trisha",R.raw.abouttrisha); // Check: fill in R.id for author use storymode layout. (put in a text file)
        qtoa.put("About Srija",R.raw.aboutsrija); // Check: fill in R.id for author use storymode layout. (put in a text file)
        qtoa.put("About Neel",R.raw.aboutneel); // Check: fill in R.id for author use storymode layout. (put in a text file)
        qtoa.put("Future Works",R.raw.futureworks);
        answerBox = (TextView) findViewById(R.id.qanswer_text);
        Spinner questionSelector = (Spinner) findViewById(R.id.faqSpinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.faqlist,R.layout.quote_spinner_custom ); //android.R.layout.simple_spinner_item
        questionSelector.setAdapter(staticAdapter);
        questionSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position); // This is standard
                answerBox.setText(readRawTextFile(mycontext,qtoa.get(selected)));
                Log.i("FAQ","New Question Selected.");
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
