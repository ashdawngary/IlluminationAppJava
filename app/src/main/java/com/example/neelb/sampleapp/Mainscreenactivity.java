package com.example.neelb.sampleapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.preference.EditTextPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Mainscreenactivity extends AppCompatActivity {
    //EditText name;
    TextView WelcomeName;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.notificationicon);//Get an image in R.drawable and insert it here pls.  Must be black and white to adhere to Android Requirements.
        mBuilder.setContentTitle("Wanna Listen To Some Music?");
        mBuilder.setContentText("Illumanition wants to remind you to check back in!");
        if(!readFromFile(this,"selected_quote.txt").equals("")){
            mBuilder.setContentTitle("Your Inspirational Quote");
            mBuilder.setContentText(readFromFile(this,"selected_quote.txt"));
        }
        Intent ifYouClick = new Intent(this,Music_Activity.class);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Music_Activity.class);
        stackBuilder.addNextIntent(ifYouClick);
        final String myname = readFromFile(this,"user_name.txt");

        final Intent myIntentX = new Intent(this,FirstMeetUp.class);
        final Intent loadMedium = new Intent(this,MainScreenMedium.class);
        final Intent loadBad = new Intent(this,MainScreenBad.class);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreenactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WelcomeName = (TextView) findViewById(R.id.WelcomeMessage);
        //name = (EditText) findViewById(R.id.NameField);
        if(!myname.equals("")){
            //name.setText(myname);
            WelcomeName.setText("Welcome, "+myname);
        }
        else {
            startActivity(myIntentX); // New Nameset Page
        }
        final Intent myIntent = new Intent(this,MainScreenGood.class);
        final Intent pickaColor = new Intent(this,favorite_color_picker.class);
        final Context mycontext = this;
        //Button goodDay = (Button) findViewById(R.id.goodDay); RIP OLD BUTTON 8/4/17
        ImageButton goodDay2 = (ImageButton) findViewById(R.id.goodDayImage);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("","user_name.txt",mycontext);
                startActivity(myIntentX);
            }
        });
        ImageButton baddday = (ImageButton) findViewById(R.id.badday);
        baddday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loadBad);
            }
        });
        ImageButton mediumday = (ImageButton) findViewById(R.id.mehday);
        mediumday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loadMedium);
            }
        });
        goodDay2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    // Write code to edit stuff
                    //System.out.println("Button was hit.");
                    String personName = myname;
                    WelcomeName.setText("Welcome, "+personName);
                    writeToFile(personName,"user_name.txt",mycontext);
                    Log.i("MainScreen","name is <"+personName+">"+personName.length());
                    if(personName.length() > 0 && !personName.equals("Enter A Name!") && satisfactoryName(personName)){
                        //name.setText(personName);
                        WelcomeName.setText("Welcome, "+personName);
                        startActivity(myIntent);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(2, mBuilder.build());
                    }
                    else {
                        writeToFile("","user_name.txt",mycontext);
                        Log.i("MainScreen","Booting up first-meet-screen");
                        startActivity(myIntentX);
                    }

                    //if(readFromFile(mycontext,"color.txt") == ""){
                    //    startActivity(pickaColor);
                    //}
                    //else{
                    //    Log.i("MainScreen",readFromFile(mycontext,"color.txt"));
                   // }

                }
        });

    }
    public boolean satisfactoryName(String name){
        boolean flag = false;
        String[] badNames = {"Name","name","no","bad","."};
        for (int i = 0; i < badNames.length;i++){
            if (name.contains(badNames[i])){
                flag = true;
            }
        }
        return !flag && name.length() > 1;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainscreenactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
