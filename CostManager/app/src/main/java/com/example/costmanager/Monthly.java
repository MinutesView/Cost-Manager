package com.example.costmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Monthly extends AppCompatActivity {


    int sum =0;
    int i,ytotal=0,sub,sumM;
    int rs,r,res,idd,iid,editId,index;
    String sumMonth,dDate,cdate,c,y,m,s;
    String[] d;


    String daa,yaa,yy;
    ArrayList<String> listData = new ArrayList<>();
    ArrayList<String> dataSum = new ArrayList<>();
    ArrayList<String> indexNum = new ArrayList<>();


    ListView listView;
    TextView textView,textView2;
    private DatabaseHelper databaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);

        listView = findViewById(R.id.dailyList);
        textView = findViewById(R.id.totalSum);
        textView2= findViewById(R.id.totalIncome);
        databaseHelper = new DatabaseHelper(this);

        Date dateC = Calendar.getInstance().getTime();
        DateFormat se = new SimpleDateFormat("MM/dd/yy");
        dDate = se.format(dateC);


        c = dDate;
        d = c.split("/");
        r = Integer.parseInt(d[1]);
        m = String.valueOf(d[0]);
        y = String.valueOf(d[2]);

        loadMonth();

        if(SharePmonth()!=0){
            textView2.setText(String.valueOf(SharePmonth()));
        }
        SharePmonth();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(),"long press to modify or delete "+i,Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {

                rs= i;
                s=String.valueOf(i);
                cdate= m+s+y;

                editId=Integer.parseInt(indexNum.get(i));
                index = i;

                final AlertDialog.Builder builder = new AlertDialog.Builder(Monthly.this);
                view = getLayoutInflater().inflate(R.layout.month_alert, null);

                Button button1 = view.findViewById(R.id.textEdit);
                Button button2 = view.findViewById(R.id.numberEdit);


                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                builder.setView(view);
                builder.show();

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Monthly.this,EditMonth.class);
                        intent.putExtra("mid",String.valueOf(editId));
                        intent.putExtra("mindexId",String.valueOf(index));
                        startActivity(intent);

                    }
                });
                alertDialog.dismiss();


                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(Monthly.this);

                        builder1.setMessage("Are you sure to delete this ?");

                        builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                daa =String.valueOf(i);
                                sub= Integer.parseInt(dataSum.get(rs));
                                yaa = String.valueOf(ShareA2Mon());

                                updateMonth();
                                upDateYear();
                                Intent intent = new Intent(Monthly.this,Monthly.class);
                                startActivity(intent);
                            }
                        });

                        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Monthly.this,Monthly.class);
                                startActivity(intent);
                            }
                        });
                        builder1.show();

                    }
                });

                return true;
            }
        });

    }
    public int ShareA2Mon(){
        SharedPreferences sharedPreferences = getSharedPreferences("getMon", Context.MODE_PRIVATE);
        int sMon = sharedPreferences.getInt("lastMon",0);
        return sMon;
    }
    public void updateMonth(){

            sumMonth = "";
        Boolean mUpdated = databaseHelper.updateMonth(daa,cdate,sumMonth);
        if(mUpdated != true){
            Toast.makeText(getApplicationContext(),"Month NOT save",Toast.LENGTH_SHORT).show();
        }
    }
    public void upDateYear(){
        try {
            ytotal = sum - sub;
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

    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                indexNum.add(cursor.getString(0));
                listData.add(cursor.getString(1));
                dataSum.add(cursor.getString(2));
            }

            try {
                for(i=0; i<= dataSum.size(); i++){
                    int s = Integer.parseInt(String.valueOf(dataSum.get(i)));
                    sum = sum+s;
                }
            }
            catch (Exception e){
            }
        }
        textView.setText(String.valueOf(sum));
        DailyAdapter adapter = new DailyAdapter(Monthly.this, listData, dataSum);
        listView.setAdapter(adapter);
    }

    public int SharePmonth(){
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
