package com.IlluminationJava.neelb.Illumination;

import android.content.Context;
import android.database.*;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.provider.ContactsContract.Contacts;
import android.content.Intent;
import android.net.Uri;
import java.io.*;
import android.provider.ContactsContract;
import android.app.Activity;
import android.widget.TextView;

public class Contact extends AppCompatActivity {
    String firstcontact;
    String secondcontact;
    String thirdcontact;
    int editcontactNumber;
    @Override
    protected void onCreate(final Bundle savedInstanceState){
        if(readFromFile(this,"helpline.txt").equals("0")){
            PostMessge(findViewById(android.R.id.content),"If you feel helpless and think you are in an emergency, you MUST use either of these helplines. The trained professionals on the other end want to help you and will not judge you and your feelings.",Snackbar.LENGTH_LONG);
            writeToFile("1","helpline.txt",this);
        }
        else{
            Log.i("Sysout","Already seen Contact.java notificaiton: "+readFromFile(this,"helpline.txt"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(220,220,220));
        try {
            loadContacts();
        }
        catch(IOException e){
            // Alert them that there arent any set pref.
        }
        Button editfirstContact = (Button) findViewById(R.id.selectFirstContact);
        editfirstContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Write code to edit stuff
                editcontactNumber = 1;
                doLaunchContactPicker();
                savecontacts();
            }
        });
        Button editsecondContact = (Button) findViewById(R.id.selectSecondContact);
        editsecondContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Write code to edit stuff
                editcontactNumber = 2;
                doLaunchContactPicker();
                savecontacts();
            }
        });
        Button editThirdContact = (Button) findViewById(R.id.selectThirdContact);
        editsecondContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Write code to edit stuff
                editcontactNumber = 3;
                doLaunchContactPicker();
                savecontacts();
            }
        });

        Button callFirstContact = (Button) findViewById(R.id.ContactFirst);
        callFirstContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Write code to edit stuff
                launchCall(firstcontact);
            }
        });
        Button callSecondContact = (Button) findViewById(R.id.contactSecond);
        callSecondContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Write code to edit stuff
                launchCall(secondcontact);
            }
        });
        Button callThirdContact = (Button) findViewById(R.id.contactThird);
        callThirdContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Write code to edit stuff
                launchCall(thirdcontact);
            }
        });



    }
    private static final int PICK_CONTACT = 1001;

    public void doLaunchContactPicker() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, PICK_CONTACT);
    }
    public void launchCall(String telephoneNumber){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"+telephoneNumber));
        startActivity(sendIntent);
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:"+cNumber);
                            if (editcontactNumber == 1){
                                firstcontact = cNumber;
                            }
                            else if(editcontactNumber == 2){
                                secondcontact = cNumber;
                            }
                            else{
                                thirdcontact = cNumber;
                            }
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }
                }
                break;
        }
    }


    public void loadContacts() throws IOException{
        String fileName = "contacts.txt";
        String line = null;
        String[] contacts = new String[3];
        int ct = 0;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null && (ct < 3)) {
                System.out.println(line);
                contacts[ct] = line;
                ct += 1;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
            contacts[0] = "uninit";
            contacts[1] = "uninit";
            contacts[2] = "uninit";
        }
        firstcontact = contacts[0];
        secondcontact = contacts[1];
        thirdcontact = contacts[2];
    }
    public void savecontacts(){
        try {
            PrintWriter pw = new PrintWriter("contacts.txt");
            pw.close();
        }
        catch(FileNotFoundException ex){
            File f = new File("contacts.txt");
        }
        try {
            PrintWriter pw = new PrintWriter("contacts.txt");
            pw.println(firstcontact);
            pw.println(secondcontact);
            pw.println(thirdcontact);
            pw.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Couldnt Save Data :/ : "+ex.getMessage());
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
