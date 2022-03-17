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


public class dailyone extends AppCompatActivity {

    int editId,index;
    String sumMonth,dDate,cdate,c,daa,yaa,yy,y,m,s;
    String[] d;
    int res,sumaAllM,r,ytotal=0;
    int rs,idd,iid,sub;
    int sum =0,sumM;
    ArrayList<String> listData = new ArrayList<>();
    ArrayList<String> dataSum = new ArrayList<>();
    ArrayList<String> indexNum = new ArrayList<>();
    ArrayList<String> dataSum3 = new ArrayList<>();


    ListView listView;
    TextView textView;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailyone);

        listView = findViewById(R.id.dailyList);
        textView = findViewById(R.id.totalSum);
        databaseHelper = new DatabaseHelper(this);

        Date dateC = Calendar.getInstance().getTime();
        DateFormat se = new SimpleDateFormat("MM/dd/yy");
        dDate = se.format(dateC);

        c = dDate;
        d = c.split("/");
        r = Integer.parseInt(d[1]);
        m = String.valueOf(d[0]);
        y = String.valueOf(d[2]);
        s= String.valueOf(ShareA2Date());

        cdate= m+s+y;


        loadData();
        loadMonth();
        ShareA2Date();

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

                editId=Integer.parseInt(indexNum.get(i));
                index = i;

                final AlertDialog.Builder builder = new AlertDialog.Builder(dailyone.this);
                view = getLayoutInflater().inflate(R.layout.daily_edit, null);


                Button button1 = view.findViewById(R.id.textEdit);
                Button button2 = view.findViewById(R.id.numberEdit);


                 final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                builder.setView(view);
                builder.show();

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(dailyone.this,EditActivity.class);
                        intent.putExtra("id",String.valueOf(editId));
                        intent.putExtra("indexId",String.valueOf(index));
                        startActivity(intent);

                    }
                });
                alertDialog.dismiss();


                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(dailyone.this);

                        builder1.setMessage("Are you sure to delete this ?");

                        builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                daa =String.valueOf(ShareA2Date());
                                yaa = String.valueOf(ShareA2Mon());
                                res=Integer.parseInt(indexNum.get(rs));
                                sub= Integer.parseInt(dataSum.get(rs));
                                int result = databaseHelper.deleteData(res);
                            listView.deferNotifyDataSetChanged();
                            if(result> 0){
                                Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Wrong: cannot delete"+res,Toast.LENGTH_SHORT).show();
                            }

                            updateMonth();
                            upDateYear();

                                Intent intent = new Intent(dailyone.this,dailyone.class);
                                startActivity(intent);

                            }

                        });

                        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(dailyone.this,dailyone.class);
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

                indexNum.add(cursor.getString(0));
                listData.add(cursor.getString(1));
              dataSum.add(cursor.getString(2));
            }

            try {
                for(idd=0; idd<= dataSum.size(); idd++){
                    int s = Integer.parseInt(String.valueOf(dataSum.get(idd)));
                    sum = sum+s;
                }
            }
            catch (Exception e){
            }
        }

        textView.setText(String.valueOf(sum));

        DailyAdapter adapter = new DailyAdapter(dailyone.this, listData, dataSum);
        listView.setAdapter(adapter);



    }

    public void updateMonth(){

        try {
            sumaAllM = sum - sub;
            sumMonth = String.valueOf(sumaAllM);
            sumaAllM=0;
        }
        catch (Exception e){
        }
        Boolean mUpdated = databaseHelper.updateMonth(daa,cdate,sumMonth);
        if(mUpdated != true){
            Toast.makeText(getApplicationContext(),"Month NOT save",Toast.LENGTH_SHORT).show();
        }
    }

    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                dataSum3.add(cursor.getString(2));
            }
            try {
                for(iid=0; iid<= dataSum3.size(); iid++){
                    int s = Integer.parseInt(String.valueOf(dataSum3.get(iid)));
                    sumM = sumM+s;
                }
            }
            catch (Exception e){
            }
        }
    }
    public void upDateYear(){
        try {
            ytotal = sumM - sub;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
