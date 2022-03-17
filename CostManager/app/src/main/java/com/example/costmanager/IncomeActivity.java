package com.example.costmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IncomeActivity extends AppCompatActivity {


    int day,month,daySum=0,monthSum=0;

    String input;

    TextView textView,incomeShow;
    EditText editText;
    Button clear,daily,monthly,show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);


        loadDay();
        loadMonth();

        textView = findViewById(R.id.Text);
        incomeShow = findViewById(R.id.incomeM);
        editText = findViewById(R.id.amount);
        clear = findViewById(R.id.clear);
        daily = findViewById(R.id.daily);
        monthly = findViewById(R.id.monthly);
        show = findViewById(R.id.showTotal);



        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.length()==0){
                    editText.setError("Enter amount");
                }
                else{

                    input = editText.getText().toString();
                    day= Integer.parseInt(input);
                    daySum = loadDay()+day;
                    textView.setText("Daily Total");
                    incomeShow.setText(String.valueOf(daySum));
                    saveDay(daySum);
                    saveMonth(daySum);
                    editText.setText("");

                }
            }
        });


        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.length()==0){
                    editText.setError("Enter amount");
                }
                else{

                    input = editText.getText().toString();
                    month= Integer.parseInt(input);
                    textView.setText("Monthly Total");
                    incomeShow.setText(String.valueOf(month));
                    saveMonth(month);
                    editText.setText("");

                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Income Total");
                incomeShow.setText("0");
                saveDay(0);
                saveMonth(0);

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeShow.setText(String.valueOf(loadMonth()));
            }
        });


    }

    public void saveDay(int daySum){
        SharedPreferences sharedPreferences = getSharedPreferences("incomeSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("income",daySum);
        editor.commit();
    }
    public int loadDay(){
        SharedPreferences sharedPreferences = getSharedPreferences("incomeSave", Context.MODE_PRIVATE);
        int daySum = sharedPreferences.getInt("income",0);
        return daySum;
    }
    public void saveMonth(int Sum){
        SharedPreferences sharedPreferences = getSharedPreferences("incomeTotal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("income",Sum);
        editor.commit();
    }
    public int loadMonth(){
        SharedPreferences sharedPreferences = getSharedPreferences("incomeTotal", Context.MODE_PRIVATE);
        int Sum = sharedPreferences.getInt("income",0);
        return Sum;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
