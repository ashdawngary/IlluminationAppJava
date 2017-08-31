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
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainScreenBad extends AppCompatActivity {
    public final Context mycontext = this;
    public final String crisistextline = "";
    public final String suicidePrevention = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_bad);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        /*TODO: Intialize ALL Intents */
        final Intent peercontact = new Intent(this,Contact.class);

        /*TODO: Intialize All Buttons*/
        Button peercontactbutton = (Button)findViewById(R.id.bad_peertexting);
        Button texthelpline = (Button) findViewById(R.id.bad_TextHelpLine);
        if(readFromFile(this,"contactafriend.txt").equals("1")){
            writeToFile("1","contactafriend.txt",this);
        }
        if(readFromFile(this,"helpline.txt").equals("1")){
            writeToFile("1","helpline.txt",this);
        }
        /*TODO: Implement ALL Listeners */
        peercontactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(peercontact);
            }
        });
        texthelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateAlertDialogBox().show();
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
    public AlertDialog GenerateAlertDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Everyone needs to lean on someone at some point. Enter in three trusted contacts and have them at the ready whenever you want to text them how you feel. Would You like to talk to a Helpline?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Close dialog and then open Quoteselector.
                dialog.dismiss();
                // Start Text Intent.
                launchCall("No Helpline number saved.");
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }
    public void launchCall(String telephoneNumber){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"+telephoneNumber));
        startActivity(sendIntent);
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
