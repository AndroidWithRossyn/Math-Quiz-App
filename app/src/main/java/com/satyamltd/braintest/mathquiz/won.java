package com.satyamltd.braintest.mathquiz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class won extends AppCompatActivity {
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.won);
        tv = (TextView) findViewById(R.id.congo);
        Bundle b = getIntent().getExtras();
        int y = b.getInt("score");
        tv.setText("FINAL SCORE:" + y);
    }
}
