package com.example.costmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    String data;
    EditText input;
    Button save,clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        loadMemo();

        input = findViewById(R.id.inputMemo);
        save = findViewById(R.id.saveMemo);
        clear = findViewById(R.id.clearMemo);

        input.setText(String.valueOf(loadMemo()));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data =input.getText().toString();
                saveMemo(data);
                Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT).show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
                saveMemo("");
            }
        });

    }

    public void saveMemo(String save){
        SharedPreferences sharedPreferences = getSharedPreferences("saveMemo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("memo",save);
        editor.commit();
    }
    public String loadMemo(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveMemo", Context.MODE_PRIVATE);
        String save = sharedPreferences.getString("memo","");
        return save;
    }

    @Override
    public void onBackPressed() {

        data =input.getText().toString();
        saveMemo(data);
        Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
