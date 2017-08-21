package com.example.neelb.sampleapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class QuoteChooser extends AppCompatActivity {
    public ConstraintLayout c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //c = (ConstraintLayout) findViewById(R.id.constraintLayoutDummy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_chooser);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        Spinner quoteSelector = (Spinner) findViewById(R.id.quoteSpinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.inspirationalquotes,R.layout.quote_spinner_custom ); //android.R.layout.simple_spinner_item
        quoteSelector.setAdapter(staticAdapter);

        final Context mycontext = this;
        quoteSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.i("item", (String) parent.getItemAtPosition(position));
                PostMessge(view,"Quote Selected.",Snackbar.LENGTH_SHORT);
               writeToFile((String) parent.getItemAtPosition(position),"selected_quote.txt",mycontext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Do Noting at the moment.
            }
        });

        Button finish = (Button) findViewById(R.id.closeQuoteScreen);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String k = "";
                try{
                    k  = readFromFile(mycontext,"selected_quote.txt");
                }
                catch (IOException e){
                    Log.i("Quoteselector","unable to Open Selected Quotes!");

                }
                if(!k.equals("")){
                    finish();
                }
                else{
                    PostMessge(v,"Choose a Quote!",Snackbar.LENGTH_SHORT);
                }
            }
        });
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
    private String readFromFile(Context context, String textfile) throws IOException {

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
