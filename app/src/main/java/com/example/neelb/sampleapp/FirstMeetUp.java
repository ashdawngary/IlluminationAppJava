package com.example.neelb.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FirstMeetUp extends AppCompatActivity {
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_meet_up);
        name = (EditText) findViewById(R.id.EnterText);
        writeToFile("0","activity.txt",this);
        writeToFile("0","music.txt",this);
        writeToFile("0","dbreath.txt",this);
        writeToFile("0","stories.txt",this);
        writeToFile("0","videos.txt",this);
        writeToFile("0","helpline.txt",this);
        writeToFile("0","contactafriend.txt",this);
        writeToFile("0","helpline.txt",this);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        Intent mainGood = new Intent(this,MainScreenGood.class);
        Button goodDay = (Button) findViewById(R.id.continueButton);
        final Intent myIntent = new Intent(this,MainScreenGood.class);
        final Context mycontext = this;
        goodDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (EditText) findViewById(R.id.EnterText);
                String personName = name.getText().toString();
                Log.i("FirstMeetUp","name is <"+personName+">"+personName.length());
                if (personName.length() > 0 && !personName.equals("Enter A Name!") && satisfactoryName(personName)) {
                    Log.i("FirstMeetUp","Launching the Main-Screen!");
                    writeToFile(personName, "user_name.txt", mycontext);
                    startActivity(myIntent);
                }
                else{
                    name.setText("Enter A satifactory Name!");
                }
            }
        });

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
    public boolean satisfactoryName(String name){
        boolean flag = false;
        String[] badNames = {"Name","no","bad","."};
        for (int i = 0; i < badNames.length;i++){
            if (name.contains(badNames[i])){
                flag = true;
            }
        }
        return !flag && name.length() > 1;
    }
    private String readFromFile(Context context,String textfile) {

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
}
