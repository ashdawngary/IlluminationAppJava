package com.example.neelb.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

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
        EditText field = (EditText) findViewById(R.id.NameField);
        String name = field.getText().toString().substring(0,10);
        MotivationLabel.setText("for "+name);
    }
}
