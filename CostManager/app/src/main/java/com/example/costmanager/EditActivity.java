package com.example.costmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    String textName,textValue,textIndex;
    TextView textView;
    EditText editText1, editText2;
    ImageButton imageButton;
    String result,result2,sumMonth,daa,dDate,cdate,c,yaa,y,yy;
    String[] d;
    int l,i,sum=0,dSub,mSub,shareD,mtotal,sub,mLast,ytotal,yrup;

    String indexId,indexnumb;

    ArrayList<String> listData2 = new ArrayList<>();
    ArrayList<String> dataSum2 = new ArrayList<>();

    ArrayList<String> listData3 = new ArrayList<>();
    ArrayList<String> dataSum3 = new ArrayList<>();
    ArrayList<String> indexNum3 = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        databaseHelper = new DatabaseHelper(this);


        Date dateC = Calendar.getInstance().getTime();
        DateFormat se = new SimpleDateFormat("MM/dd/yy");
        dDate = se.format(dateC);


        c = dDate;
        d = c.split("/");
        yaa = String.valueOf(ShareA2Mon());
        y = String.valueOf(d[2]);
        daa =String.valueOf(ShareA2Date());

        cdate= yaa+"/"+daa+"/"+y;

        loadData();
        loadMonth();


        textView = findViewById(R.id.index);
        editText1 = findViewById(R.id.textEdit);
        editText2 = findViewById(R.id.numberEdit);
        imageButton = findViewById(R.id.doneEdit);


        Bundle bundle= getIntent().getExtras();

        if(bundle!=null){
           indexId = bundle.getString("id");
           indexnumb = bundle.getString("indexId");
            l = Integer.parseInt(indexnumb);

            textView.setText(indexId);
        }
        result = listData2.get(l);
        result2 = dataSum2.get(l);
        dSub = Integer.parseInt(result2);
        shareD = Integer.parseInt(String.valueOf(ShareA2Date()));
        mSub = Integer.parseInt(String.valueOf(dataSum3.get(shareD)));
        mtotal = mSub - dSub;
        yrup = sum - dSub;


        editText1.setText(result);
        editText2.setText(result2);



        imageButton.setOnClickListener(new View.OnClickListener() {
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
                else {
                    textIndex = textView.getText().toString();
                    textName = editText1.getText().toString();
                    textValue = editText2.getText().toString();
                    sub = Integer.parseInt(String.valueOf(textValue));
                    Boolean isUpdated = databaseHelper.updateData(textIndex,textName,textValue);

                    updateMonth();

                    upDateYear();

                    if(isUpdated == true){
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error found",Toast.LENGTH_SHORT).show();

                    }



                    Intent intent = new Intent(EditActivity.this,dailyone.class);
                    startActivity(intent);
                }


                }
        });


    }

    public int ShareA2Date(){
        SharedPreferences sharedPreferences = getSharedPreferences("getDate", Context.MODE_PRIVATE);
        int sdate = sharedPreferences.getInt("lastDate",0);
        return sdate;
    }
    public int ShareA2Mon(){
        SharedPreferences sharedPreferences = getSharedPreferences("getMon", Context.MODE_PRIVATE);
        int sMon = sharedPreferences.getInt("lastMon",0);
        return sMon;
    }

    public void loadData(){


        Cursor cursor = databaseHelper.showAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                listData2.add(cursor.getString(1));
                dataSum2.add(cursor.getString(2));
            }
        }

    }
    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                indexNum3.add(cursor.getString(0));
                listData3.add(cursor.getString(1));
                dataSum3.add(cursor.getString(2));
            }

            try {
                for(i=0; i<= dataSum3.size(); i++){
                    int s = Integer.parseInt(String.valueOf(dataSum3.get(i)));
                    sum = sum+s;
                }
            }
            catch (Exception e){
            }
        }
        //textView.setText(String.valueOf(sum));

    }
    public void updateMonth(){

        try {
            mLast = mtotal + sub;
            sumMonth = String.valueOf(mLast);
        }
        catch (Exception e){
        }
        Boolean mUpdated = databaseHelper.updateMonth(daa,cdate,sumMonth);
        if(mUpdated != true){
            Toast.makeText(getApplicationContext(),"Month NOT save",Toast.LENGTH_SHORT).show();
        }
    }
    public void upDateYear(){
        try {
            ytotal = yrup + sub;
            yy = String.valueOf(ytotal);
            ytotal=0;
        }
        catch (Exception e){
        }
        Boolean yUpdated = databaseHelper.updateYear(yaa,yy);
        if(yUpdated !=true){
            Toast.makeText(getApplicationContext(),"YEAR Update Error",Toast.LENGTH_SHORT).show();
        }
    }

}
