package com.IlluminationJava.neelb.Illumination;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Notice extends AppCompatActivity {
    public final Context mycontext = this; // we will be reading alot so we need this for sure.
    public TextView answerBox;
    public final Context myContext = mycontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        // Need to Map Something?  Lets HashMap!  We hashmap String -> int since int is the id for the R.Raw file containing <String>
        final HashMap<String,Integer> qtoa = new HashMap<>(); //News flash aparently Hashmap doesnt like "primitives" so rip int

        qtoa.put("About Illumination",R.raw.disclaimer); // Check: fill in R.id for Disclaimer. (put in a text file)
        /*
        qtoa.put("About Trisha",R.raw.abouttrisha); // Check: fill in R.id for author use storymode layout. (put in a text file)
        qtoa.put("About Srija",R.raw.aboutsrija); // Check: fill in R.id for author use storymode layout. (put in a text file)
        qtoa.put("About Neel",R.raw.aboutneel); // Check: fill in R.id for author use storymode layout. (put in a text file)
        */
        qtoa.put("Future Updates",R.raw.futureworks);
        answerBox = (TextView) findViewById(R.id.qanswer_text);
        Spinner questionSelector = (Spinner) findViewById(R.id.faqSpinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.faqlist,R.layout.quote_spinner_custom ); //android.R.layout.simple_spinner_item
        questionSelector.setAdapter(staticAdapter);
        questionSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(answerBox == null){
                    Log.e("AnswerBoxNull","Answer Box is Null! Forcing a deifntion!");
                    loadAnswerBox();
                }
                String selected = (String) parent.getItemAtPosition(position); // This is standard
                Log.i("Notice","Selected: "+selected);
                int file = qtoa.get(selected);
                Log.i("Notice","file is at "+file);
                String c = readRawTextFile(mycontext,file);
                answerBox.setText(c);
                Log.i("FAQ","New Question Selected.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void loadAnswerBox(){
        answerBox = (TextView) findViewById(R.id.qanswer_text);
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
