package com.example.smarthomesolutions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent=getIntent();
        String text=intent.getStringExtra(MainActivity.Extra_Text);
        tv=findViewById(R.id.textViewww);
        tv.setText(text);
    }
}
