package com.example.costmanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMonth extends AppCompatActivity {
    String daa,cdate,sumMonth;
    TextView textView,textView1;
    EditText editText2;
    ImageButton imageButton;
    String result,result2;
    int l;

    String indexId,indexnumb;

    ArrayList<String> indexNum2 = new ArrayList<>();
    ArrayList<String> listData2 = new ArrayList<>();
    ArrayList<String> dataSum2 = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_month);

        databaseHelper = new DatabaseHelper(this);

        loadMonth();

        textView = findViewById(R.id.index);
        textView1 = findViewById(R.id.textEdit);
        editText2 = findViewById(R.id.numberEdit);
        imageButton = findViewById(R.id.doneEdit);

        Bundle bundle= getIntent().getExtras();

        if(bundle!=null){
            indexId = bundle.getString("mid");
            indexnumb = bundle.getString("mindexId");
            l = Integer.parseInt(indexnumb);

            textView.setText(indexId);
        }
        result = listData2.get(l);
        result2 = dataSum2.get(l);
        textView1.setText(result);
        editText2.setText(result2);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText2.length()==0){
                        editText2.setError("Enter amount");
                }
                else {
                    daa = textView.getText().toString();
                    cdate = textView1.getText().toString();
                    sumMonth = editText2.getText().toString();


                    Boolean mUpdated = databaseHelper.updateMonth(daa,cdate,sumMonth);
                    if(mUpdated == true){
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error found",Toast.LENGTH_SHORT).show();

                    }

                    Intent intent = new Intent(EditMonth.this,Monthly.class);
                    startActivity(intent);
                }


            }
        });
    }

    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                indexNum2.add(cursor.getString(0));
                listData2.add(cursor.getString(1));
                dataSum2.add(cursor.getString(2));
            }
        }
    }


}
