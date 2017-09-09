package com.example.neelb.sampleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
/*
Hey there whats your name  - bolded
Illumination Logo instead of Iluimination
Continue needs to be centered

---------------------------------------


 */
public class tasklistv2 extends AppCompatActivity {
    public CheckBox checkbox1;
    public CheckBox checkbox2;
    public CheckBox checkbox3;
    public CheckBox checkbox4;
    public CheckBox checkbox5;
    public Intent chooseIntent;
    public String name;
    // Ignore the tasks array it is obselete now.
    final String[][] tasks =  {{"Task1","Task2","Task3","Task4","Task5"},{"ATask1","ATask2","ATask3","ATask4","ATask5"},{"Wellness1","Wellness2","Wellness3","Wellness4","Wellness5"}};
    public TextView yourlist;
    public Context myContext = this;
    public ProgressBar mainprogressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (readFromFile(this,"activity.txt").equals("0")){
            PostMessge(findViewById(android.R.id.content),"Explore tons of activities that are enjoyable and good for you! Choose from the healthy activity list or the volunteer opportunities list and track your progress towards a more resilient you. ",Snackbar.LENGTH_LONG);
            writeToFile("1","activity.txt",myContext);
        }else{
            
        }
        myContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklistv2);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
        final Intent quoteSelector = new Intent(this,QuoteChooser.class);
        /*Button qOpen = (Button) findViewById(R.id.openQuoteSelector); // Temp trigger Button
        qOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(quoteSelector);
            }
        });
        */
        mainprogressbar = (ProgressBar) findViewById(R.id.activity_completion_pb);
        mainprogressbar.setMax(100);
        mainprogressbar.setProgress(0);
        mainprogressbar.setIndeterminate(false);
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
        Button refresh = (Button) findViewById(R.id.tasklist_genNew);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = readFromFile(myContext ,"tasklist_choice.txt");
                String caller = content.toLowerCase();
                caller.replace(' ','_');
                switch(content){
                    case "Volunteering":
                        writeToFile("00000","volunteer_progress.txt",myContext);
                        break;
                    case "Activities For You":
                        writeToFile("00000","activities_progress.txt",myContext);
                        break;
                    default:
                        Log.i("refresh","Unable to Refresh due to unknown task: "+content);
                }
                writeToFile("",caller+"_0.txt",myContext);
                loadchoice();
            }
        });
        Spinner changefocus = (Spinner) findViewById(R.id.activites_catagories);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.activities_catagories,R.layout.quote_spinner_custom ); //android.R.layout.simple_spinner_item
        changefocus.setAdapter(staticAdapter);
        changefocus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = (String) parent.getItemAtPosition(position);
                //String[] checkboxes_content = getTasks(selected);
                Log.i("item", "writing: "+selected);
                writeToFile(selected,"tasklist_choice.txt",myContext);
                loadchoice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Button changefocus = (Button) findViewById(R.id.cfB);
        /*changefocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userchoose();
            }
        });*/
        name = readFromFile(this,"user_name.txt");
        yourlist = (TextView) findViewById(R.id.MotivationalTasklistview);
        setListName();
        if(readFromFile(this,"tasklist_choice.txt").equals("")){
            writeToFile("","tasklist_choice.txt",this);
            Log.i("taskListv2","calling choose function.");
            userchoose();
        }
        loadchoice();
    }
    public void setListName() {

        yourlist.setText(name + "'s Activity List");
    }
    public String[] randomfiveSubset(String[] tasks){
        int maximum = tasks.length -1;
        int minimum = 0;
        ArrayList<Integer> c = new ArrayList();
        Random rn = new Random();
        int range = maximum - minimum + 1;


        int size_of_c = 0;
        while (size_of_c != 5){
            int randomNum =  rn.nextInt(range) + minimum;
            if (!c.contains(randomNum)){
                c.add(randomNum);
                size_of_c += 1;
            }
        }
        String[] tsks = new String[5];
        for(int i = 0;i<5;i++){
            tsks[i] = tasks[c.get(i)];
        }
        return tsks;
    }
    public void reloadtasks(String content){
        String caller = content.toLowerCase();
        caller.replace(' ','_');

        switch(content){
            case "Activities For You":
                String[] tasks = randomfiveSubset(getResources().getStringArray(R.array.activities_list));
                writeToFile(tasks[0],caller+"_0.txt",myContext);
                writeToFile(tasks[1],caller+"_1.txt",myContext);
                writeToFile(tasks[2],caller+"_2.txt",myContext);
                writeToFile(tasks[3],caller+"_3.txt",myContext);
                writeToFile(tasks[4],caller+"_4.txt",myContext);
                break;
            case "Volunteering":
                writeToFile("Volunteer Task #1",caller+"_0.txt",myContext);
                writeToFile("Volunteer Task #2",caller+"_1.txt",myContext);
                writeToFile("Volunteer Task #3",caller+"_2.txt",myContext);
                writeToFile("Volunteer Task #4",caller+"_3.txt",myContext);
                writeToFile("Volunteer Task #5",caller+"_4.txt",myContext);
                break;
            default:
                Log.i("reloadtasks"," reloadtasks encountered bad argument: "+content);
        }
    }

    public String[] getTasks(String content){
        String [] c = new String[5];
        String caller = content.toLowerCase();
        caller.replace(' ','_');
        c[0] = readFromFile(myContext,caller+"_0.txt");
        c[1] = readFromFile(myContext,caller+"_1.txt");
        c[2] = readFromFile(myContext,caller+"_2.txt");
        c[3] = readFromFile(myContext,caller+"_3.txt");
        c[4] = readFromFile(myContext,caller+"_4.txt");
        Log.i("getTasks()","Loaded Elements for "+content+" c0 has length "+c[0].length());
        if(c[0].length() < 2){
            reloadtasks(content);
            return getTasks(content);
        }
        return c;
    }

    public void loadchoice(){
        String choice = readFromFile(this,"tasklist_choice.txt");
        switch(choice){
            case "Volunteering":
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.volunteermatch.org/"));
                startActivity(browserIntent);

                // Cut out all of this stuff
                String progress = readFromFile(this,"volunteer_progress.txt");
                if (progress.equals("")){
                    writeToFile("00000","volunteer_progress.txt",this);
                }
                Log.i("LoadProgres","Last Saved state: "+progress);
                updateScreen(readFromFile(this,"volunteer_progress.txt"),getTasks("Volunteer"));
                break;
            case "Activities For You":
                String prgs = readFromFile(this,"activities_progress.txt");
                if (prgs.equals("")){
                    writeToFile("00000","activities_progress.txt",this);
                }
                Log.i("LoadProgres","Last Saved state: "+prgs);
                updateScreen(readFromFile(this,"activities_progress.txt"),getTasks("Activities For You"));
                break;

            default:
                Log.i("LoadProgres","Weird choice unable to load file. ( recieved: <"+choice+">");
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
        Log.i("GetProgress","Survey says "+st);
        return st;
    }
    public void saveProgress(){
        String saveFile = getProgress();
        switch(readFromFile(this,"tasklist_choice.txt")){
            case "Volunteering":
                writeToFile(saveFile,"volunteer_progress.txt",this);
                break;
            case "Activities For You":
                writeToFile(saveFile,"activities_progress.txt",this);
                break;
            default:
                Log.e("SaveProgress","Bad Argument Detected: ");
        }
        int pgs = count(saveFile,'1')*20;
        Log.i("SaveProgress","Saving Progress Bar to "+pgs);
        mainprogressbar.setProgress(pgs);


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
    }
    public void userchoose(){
        writeToFile("Volunteering","tasklist_choice.txt",myContext);
        //startActivity(chooseIntent);
        //updateScreen();
        Log.i("UserChoose","Startactivity has ended.");
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
        catch(NullPointerException e){
            Log.e("Exception","Write Failed due to a Null Pointer Exception.");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " +e.toString());
        }
    }
    public void PostMessge(View v, String t, int mode){
        final Snackbar sn = Snackbar.make(v,t,mode);
        View snackbarView = sn.getView();
        TextView snackTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setMaxLines(6);
        sn.setAction("Dismiss",new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sn.dismiss();
            }
        });
        sn.show();
    }
}
