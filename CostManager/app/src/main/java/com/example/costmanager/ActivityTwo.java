package com.example.costmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityTwo extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    String textName, textValue;

    int a=0;
    int dl,end,save,sumaAllM=0,sumY,ymonth;
    int incre = 0;
    String cdate;
    String c,yIndex,yName,yAmount="0";
    String daa,yaa,yy,sumMonth,saveA;
    String Tm,td,day,dm,dy;
    String mSum ="0";

    String d[];
    int r,t,m,y,mo,mTotal,ytotal=0,add;
    int iid,ii,sumM,sumd;

     String[] textAll = new String[50];
    String[] numbAll = new String[50];

    ArrayList<String> dataSum3 = new ArrayList<>();
    ArrayList<String> dataSum = new ArrayList<>();
    ArrayList<String> dataS = new ArrayList<>();
    ArrayList<String> indexNum2 = new ArrayList<>();
    ArrayList<String> indexMonth = new ArrayList<>();

    ArrayList<String> indexYear = new ArrayList<>();
    ArrayList<String> yearSum = new ArrayList<>();
    String[] ar  = {"","January","February","March","April","May","June","July","August","September","October","November","December"};
    int[] arr  = {0,31,29,31,30,31,30,31,31,30,31,30,31};



    TextView dateShow,month,totalShow,date,month_date,shareM,mont_sum;
    ImageButton imgbtn;
    EditText editText1, editText2;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase2 = databaseHelper.getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        loadData();
        loadMonth();
        loadYear();

        dateShow =  findViewById(R.id.date_show);
        date =  findViewById(R.id.date);
        month_date =  findViewById(R.id.date_month);
        //sP save & show txt
        month =  findViewById(R.id.month);
        shareM =  findViewById(R.id.shareP_month);
        mont_sum =  findViewById(R.id.month_sum);


        imgbtn = (ImageButton) findViewById(R.id.done);
        editText1= (EditText) findViewById(R.id.textInput);
        editText2= (EditText) findViewById(R.id.numberInput);
        listView = findViewById(R.id.listview);
        totalShow = findViewById(R.id.totalForList);


        Date dateC = Calendar.getInstance().getTime();
        DateFormat se = new SimpleDateFormat("MM/dd/yy");
        cdate = se.format(dateC);

        dateShow.setText(cdate);
         c = dateShow.getText().toString();
         d = c.split("/");
         r = Integer.parseInt(d[1]);
         m = Integer.parseInt(d[0]);
         y = Integer.parseInt(d[2]);
        date.setText(String.valueOf(r));
        month_date.setText(String.valueOf(m));
        mont_sum.setText(String.valueOf(sumd));


        if(loadDate()!=0){
            month.setText(String.valueOf(loadDate()));
        }
        else {
            month.setText(String.valueOf(loadDate()));
        }
        if(loadDate()!=0){
            shareM.setText(String.valueOf(loadMon()));
        }
        else{
            shareM.setText(String.valueOf(loadMon()));
        }



        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.length()==0 && editText2.length()==0){
                    editText1.setError("Enter text");
                    editText2.setError("Enter amount");
                }
                else{
                     textName = editText1.getText().toString();
                     textValue = editText2.getText().toString();
                     t= Integer.parseInt(textValue);


                    textAll[incre] = textName;
                    numbAll[incre] = textValue;
                        try {
                            a=a+t;
                            totalShow.setText(String.valueOf(a));
                        }
                        catch (Exception e){
                        }

                    incre = incre + 1;

                            CustomAdapter adapter = new CustomAdapter(ActivityTwo.this, textAll,numbAll);
                            listView.setAdapter(adapter);


                            //All steps

                            mo = Integer.parseInt(month_date.getText().toString());
                            daa = String.valueOf(r);
                            yaa = String.valueOf(m);
                            end=arr[m];


                            //Step 1 --------------------------------------------------------------
                            if(r == loadDate() && m == loadMon()){

                                //daily save
                                saveDay();

                                //month update
                                updateMonth();

                                //year update
                                upDateYear();

                                //sharedP won't call
                            }


                            //Step 2:--------------------------------------------------
                            else if(r > loadDate() && m == loadMon()){
                                //daily full delete
                                deleteDay();

                                //daily save
                                saveDay();

                                //month save...............
                                //I used update method

                                //month update with value a
                                updateMont();
                                //year update
                                upDateYear();

                                // shareP call
                                shareDate(r);
                            }


                            //Step 3 -------------------------------------------------------------
                            else if(r < loadDate() && m > loadMon()){

                                //daily full delete
                                deleteDay();

                                //daily save
                                saveDay();

                                //month full delete
                                deleteMonth();

                                //month save..........
                                //month create & save
                                saveMonth();

                                //month update with value a
                                updateMont();

                               ////////////////////   New Year   /////////////////
                                if (mo == 1) {
                                    //year delete
                                    deleteYear();

                                    //Year create
                                    saveYear();
                                }
                                /////////////////////   old year  ////////////////
                                //year update
                                upDateYr();


                                //call shareP
                                shareDate(r);
                                shareMon(m);

                            }

                            //Step 4: ------------------------------------------------------

                            else if(r == loadDate() && m > loadMon()){
                                //daily full delete
                                deleteDay();

                                //daily save
                                saveDay();

                                //month full delete
                                deleteMonth();

                                //month save...............
                                //month create & save
                                saveMonth();

                                //month update
                                updateMont();

                                ////////////////////   New Year   /////////////////
                                if (m == 1) {
                                    //year delete
                                    deleteYear();

                                    //Year create
                                    saveYear();
                                }
                                /////////////////////   old year  ////////////////
                                //year update
                                upDateYr();

                                //shareP

                                shareMon(m);
                            }

                            //Step 5 -------------------------------------------------------------
                            else if(r > loadDate() && m > loadMon()){
                                //daily save
                                saveDay();

                                //month create & save
                                saveMonth();

                                //month update......with value a
                                updateMont();

                                //Year create
                                saveYear();

                                //year update
                                upDateYear();

                                //call shareP
                                shareDate(r);
                                shareMon(m);

                                //no need next two line
                                month.setText(String.valueOf(r));
                                shareM.setText(String.valueOf(m));
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                            }

                    editText1.setText("");
                    editText2.setText("");





                }
            }
        });

    }

    public void shareDate(int sdate){
        SharedPreferences sharedPreferences = getSharedPreferences("getDate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastDate",sdate);
        editor.commit();
    }

    public int loadDate(){
        SharedPreferences sharedPreferences = getSharedPreferences("getDate", Context.MODE_PRIVATE);
        int sdate = sharedPreferences.getInt("lastDate",0);
        return sdate;
    }
    public void shareMon(int sMon){
        SharedPreferences sharedPreferences = getSharedPreferences("getMon", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastMon",sMon);
        editor.commit();
    }

    public int loadMon(){
        SharedPreferences sharedPreferences = getSharedPreferences("getMon", Context.MODE_PRIVATE);
        int sMon = sharedPreferences.getInt("lastMon",0);
        return sMon;
    }


    public void loadData(){

        Cursor cursor = databaseHelper.showAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                indexNum2.add(cursor.getString(0));
                dataSum.add(cursor.getString(2));
            }

            try {
                for(iid=0; iid<= dataSum.size(); iid++){
                    int s = Integer.parseInt(String.valueOf(dataSum.get(iid)));
                    sumd = sumd+s;
                }
            }
            catch (Exception e){
            }
        }

    }

    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                indexMonth.add(cursor.getString(0));
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
    public void loadYear(){

        Cursor cursor = databaseHelper.showAllYear();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        }
        else {
            while(cursor.moveToNext()){
                indexYear.add(cursor.getString(0));
                yearSum.add(cursor.getString(2));
            }
        }
    }

//..................Daily......................
    public void saveDay(){
        long rowNumber = databaseHelper.saveData(textName,textValue);
        if(rowNumber>-1){
            Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_LONG).show();
        }

    }

    public void deleteDay(){
        loadData();
        for(dl=0; dl<=indexNum2.size();dl++){
            try {
                int res=Integer.parseInt(String.valueOf(indexNum2.get(dl)));
                int result = databaseHelper.deleteData(res);
                if(result < 0){
                    Toast.makeText(getApplicationContext(),"Something went wrong ",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){

            }

        }

    }
    //...................Month............

    public void saveMonth(){
        for(save=01; save <=end;save++){

            day = String.valueOf(save);
            dm = String.valueOf(m);
            dy = String.valueOf(y);
            saveA= String.valueOf(save);
            td= dm+"/"+day+"/"+dy;
            long rowNumb = databaseHelper.saveMonth(saveA,td,mSum);
            if(rowNumb == -1) {
                Toast.makeText(getApplicationContext(),"Month NOT save",Toast.LENGTH_LONG).show();
            }
        }

    }
    public void updateMonth(){

            try {
                sumaAllM = sumd+a;
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
    public void updateMont(){

        try {
            sumaAllM = a;
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
    public void deleteMonth(){
        loadMonth();
        for(dl=0; dl<=indexMonth.size();dl++){
            try {
                int dlt=Integer.parseInt(indexMonth.get(dl));
                int result = databaseHelper.deleteMonth(dlt);
                listView.deferNotifyDataSetChanged();
                if(result < 0){
                    Toast.makeText(getApplicationContext(),"Something went wrong ",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){

            }

        }

    }

    //...............Year........



    //Year create and save are same code
    public void saveYear(){
        for(ymonth=1;ymonth<=12;ymonth++){
            yIndex= String.valueOf(ymonth);
            yName=ar[ymonth];
            long rowNumY = databaseHelper.saveYear(yIndex,yName,yAmount);
            if(rowNumY == -1) {
                Toast.makeText(getApplicationContext(),"Month NOT save ",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void upDateYear(){
        loadYear();
        try {
            ytotal = sumM+a;
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
    public void upDateYr(){
        loadYear();
        try {
            ytotal = a;
            yy = String.valueOf(ytotal);
            ytotal=0;
        }
        catch (Exception e){
        }
        Boolean yUpdated = databaseHelper.updateYear(yaa,yy);
        if(yUpdated != true){
            Toast.makeText(getApplicationContext(),"YEAR Update Error",Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteYear(){
        loadYear();
        for(dl=0; dl<=indexYear.size();dl++){
            try {
                int dlt=Integer.parseInt(indexYear.get(dl));
                int rslt = databaseHelper.deleteYear(dlt);
                if(rslt < 0){
                    Toast.makeText(getApplicationContext(),"Something went wrong ",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){

            }

        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
