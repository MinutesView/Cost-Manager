package com.example.costmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NeedHelp extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);

        textView = findViewById(R.id.hlpShow);

        String string = getString(R.string.need_help);
        textView.setText(string);

    }
}
