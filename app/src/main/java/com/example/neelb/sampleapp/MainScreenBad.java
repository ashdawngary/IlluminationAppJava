package com.example.neelb.sampleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenBad extends AppCompatActivity {
    public final Context mycontext = this;
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
    public AlertDialog GenerateAlertDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Would You like to talk to a Helpline?");

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
}
