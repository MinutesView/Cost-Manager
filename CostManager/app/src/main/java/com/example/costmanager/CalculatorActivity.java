package com.example.costmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {


    double inputa,inputb,result,sum;

    TextView textView;
    EditText editText1,editText2;
    Button add,sub,multi,div,clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        editText1 = findViewById(R.id.input1);
        editText2 = findViewById(R.id.input2);

        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        multi = findViewById(R.id.multiply);
        div = findViewById(R.id.divide);

        clear = findViewById(R.id.clear);

        textView = findViewById(R.id.totalText);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.length()==0 || editText2.length()==0){
                    if(editText1.length()==0){
                        editText1.setError("Enter text");
                    }
                    else{
                        editText2.setError("Enter amount");
                    }
                }
                else{

                    inputa = Double.parseDouble(editText1.getText().toString());
                    inputb = Double.parseDouble(editText2.getText().toString());

                    result = inputa+inputb;
                    editText1.setText(String.valueOf(result));
                    textView.setText("Addition");
                    editText2.setText("");


                }


            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.length()==0 || editText2.length()==0){
                    if(editText1.length()==0){
                        editText1.setError("Enter text");
                    }
                    else{
                        editText2.setError("Enter amount");
                    }
                }
                else{

                    inputa = Double.parseDouble(editText1.getText().toString());
                    inputb = Double.parseDouble(editText2.getText().toString());

                    result = inputa-inputb;
                    editText1.setText(String.valueOf(result));
                    textView.setText("Subtraction");
                    editText2.setText("");


                }


            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.length()==0 || editText2.length()==0){
                    if(editText1.length()==0){
                        editText1.setError("Enter text");
                    }
                    else{
                        editText2.setError("Enter amount");
                    }
                }
                else{

                    inputa = Double.parseDouble(editText1.getText().toString());
                    inputb = Double.parseDouble(editText2.getText().toString());

                    result = inputa*inputb;
                    editText1.setText(String.valueOf(result));
                    textView.setText("Multiplication");
                    editText2.setText("");


                }

            }
        });

        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.length()==0 || editText2.length()==0){
                    if(editText1.length()==0){
                        editText1.setError("Enter text");
                    }
                    else{
                        editText2.setError("Enter amount");
                    }
                }
                else{

                    inputa = Double.parseDouble(editText1.getText().toString());
                    inputb = Double.parseDouble(editText2.getText().toString());

                    result = inputa/inputb;
                    editText1.setText(String.valueOf(result));
                    textView.setText("Division");
                    editText2.setText("");


                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CalculatorActivity.this,CalculatorActivity.class);
                startActivity(intent);


                }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
