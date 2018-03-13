package com.IlluminationJava.neelb.Illumination;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TaskList extends AppCompatActivity {
    TextView LattitudeLabel;
    TextView LongitudeLabel;
    TextView MotivationLabel;
    EditText DistanceField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        TextView LattitudeLabel = (TextView) findViewById(R.id.LatitudeLabel);
        TextView LongitudeLabel = (TextView) findViewById(R.id.LongitudeLabel);
        TextView MotivationLabel = (TextView) findViewById(R.id.MotivationLabel);
        EditText DistanceField = (EditText) findViewById(R.id.DistanceField);
        //EditText field = (EditText) findViewById(R.id.NameField);
        String name = readFromFile(this,"user_name.txt").substring(0,10);
        //String name = field.getText().toString().substring(0,10);
        MotivationLabel.setText("for "+name);
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
}
