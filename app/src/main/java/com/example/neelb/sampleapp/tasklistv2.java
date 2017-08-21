package com.example.neelb.sampleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class tasklistv2 extends AppCompatActivity {
    public CheckBox checkbox1;
    public CheckBox checkbox2;
    public CheckBox checkbox3;
    public CheckBox checkbox4;
    public CheckBox checkbox5;
    public Intent chooseIntent;
    public String name;
    final String[][] tasks =  {{"Task1","Task2","Task3","Task4","Task5"},{"ATask1","ATask2","ATask3","ATask4","ATask5"},{"Wellness1","Wellness2","Wellness3","Wellness4","Wellness5"}};
    public TextView yourlist;
    public Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklistv2);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        final Intent quoteSelector = new Intent(this,QuoteChooser.class);
        Button qOpen = (Button) findViewById(R.id.openQuoteSelector); // Temp trigger Button
        qOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(quoteSelector);
            }
        });
        chooseIntent = new Intent(this,tasklist_selector.class);
        checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkbox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkbox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkbox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveProgress();}});
        checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveProgress();}});
        checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveProgress();}});
        checkbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveProgress();}});
        checkbox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveProgress();}});
        Button changefocus = (Button) findViewById(R.id.cfB);
        changefocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userchoose();
            }
        });
        name = readFromFile(this,"user_name.txt");
        yourlist = (TextView) findViewById(R.id.MotivationalTasklistview);
        yourlist.setText(name+"'s Activity List for "+readFromFile(this,"tasklist_choice.txt"));

        if(readFromFile(this,"tasklist_choice.txt").equals("")){
            writeToFile("","tasklist_choice.txt",this);
            Log.i("taskListv2","calling choose function.");
            userchoose();
        }
        loadchoice();
    }
    public void loadchoice(){
        String choice = readFromFile(this,"tasklist_choice.txt");
        yourlist.setText(name+"'s Tasklist for "+choice);
        switch(choice){
            case "Volunteer":
                String progress = readFromFile(this,"volunteer_progress.txt");
                if (progress.equals("")){
                    writeToFile("00000","volunteer_progress.txt",this);
                }
                Log.i("LoadProgres","Last Saved state: "+progress);
                updateScreen(readFromFile(this,"volunteer_progress.txt"),tasks[0]);
                break;
            case "StayActive":
                String progress2 = readFromFile(this,"active_progress.txt");
                if (progress2.equals("")){
                    writeToFile("00000","active_progress.txt",this);
                }
                Log.i("LoadProgres","Last Saved state: "+progress2);
                updateScreen(readFromFile(this,"active_progress.txt"),tasks[1]);
                break;
            case "Wellness":
                String progress3 = readFromFile(this,"wellness_progress.txt");
                if (progress3.equals("")){
                    writeToFile("00000","wellness_progress.txt",this);
                }
                Log.i("LoadProgres","Last Saved state: "+progress3);
                updateScreen(readFromFile(this,"wellness_progress.txt"),tasks[2]);
                break;
            default:
                Log.i("LoadProgres","Weird choice unable to load file.");
        }
    }
    public String getProgress(){
        boolean[] c = {checkbox1.isChecked(),checkbox2.isChecked(),checkbox3.isChecked(),checkbox4.isChecked(),checkbox5.isChecked()};
        String st ="";
        for (int i = 0; i < 5; i++){
            if (c[i]){
                st  += "1";
            }
            else{
                st += "0";
            }
        }
        Log.e("GetProgress","Survey says "+st);
        return st;
    }
    public void saveProgress(){
        String saveFile = getProgress();
        switch(readFromFile(this,"tasklist_choice.txt")){
            case "Volunteer":
                writeToFile(saveFile,"volunteer_progress.txt",this);
                break;
            case "StayActive":
                writeToFile(saveFile,"active_progress.txt",this);
                break;
            case "Wellness":
                writeToFile(saveFile,"wellness_progress.txt",this);
        }
        if(count(saveFile, '0') == 0) {
            Log.i("taskListv2","User has filled up all achievements!  Asking if they want to open quote chooser.");
            AlertDialog k = GenerateAlertDialogBox();
            k.show();
            Log.i("taskListv2","Opening AlertBox");

        }
    }

    public AlertDialog GenerateAlertDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("You completed all the tasks!  Would you like to choose a new Inspirational Quote?");

        builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Close dialog and then open Quoteselector.
                dialog.dismiss();
                final Intent quoteSelector = new Intent(myContext,QuoteChooser.class);
                startActivity(quoteSelector);
            }
        });

        builder.setNegativeButton("Pass", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }

    public int count(String largeText,char needle){
        int count = 0;
        for(int index = 0; index < largeText.length();index ++){
            if (largeText.charAt(index) == needle){
                count += 1;
            }
        }
        return count;
    }
    public void updateScreen(String progress,String[] headers){
        // stub out for now ...
        checkbox1.setText(headers[0]);
        checkbox2.setText(headers[1]);
        checkbox3.setText(headers[2]);
        checkbox4.setText(headers[3]);
        checkbox5.setText(headers[4]);
        checkbox1.setChecked(progress.charAt(0) == '1');
        checkbox2.setChecked(progress.charAt(1) == '1');
        checkbox3.setChecked(progress.charAt(2) == '1');
        checkbox4.setChecked(progress.charAt(3) == '1');
        checkbox5.setChecked(progress.charAt(4) == '1');
        yourlist.setText(name+"'s Tasklist for "+readFromFile(this,"tasklist_choice.txt"));
    }
    public void userchoose(){
        startActivity(chooseIntent);
        //updateScreen();
        Log.i("UserChoose","Startactivity has ended.");
        yourlist.setText(name+"'s Activity List for "+readFromFile(this,"tasklist_choice.txt"));
        loadchoice();
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
