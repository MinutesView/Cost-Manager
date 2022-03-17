package com.example.costmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TipsActivity2 extends AppCompatActivity {

    String info;
    int info2;

    TextView textShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips2);

        textShow = findViewById(R.id.tipsShow);

        if(getIntent()!=null){
            info = getIntent().getStringExtra("info");
            info2= Integer.parseInt(info);
            if(info2 == 1){
                String string = getString(R.string.seven_simple_way);
                textShow.setText(string);
            }
            else if(info2 == 2){
                String string = getString(R.string.save_often);
                textShow.setText(string);
            }

            else if(info2 == 3){
                String string = getString(R.string.get_support);
                textShow.setText(string);
            }
            else if(info2 == 4){
                String string = getString(R.string.easy_steps);
                textShow.setText(string);
            }
            else if(info2 == 5){
                String string = getString(R.string.tricks_saving);
                textShow.setText(string);
            }
            else {
                Toast.makeText(getApplicationContext(),"Suggestion not found",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SaveTipsActivity.class);
        startActivity(intent);
    }
}
