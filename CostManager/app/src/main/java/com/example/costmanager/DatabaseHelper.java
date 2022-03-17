package com.example.costmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Daily table
    private static  final String DATABASE_NAME = "dailyData.db";
    private static  final String TABLE_NAME = "my_table";
    private static  final String ENTER_TEXT_NAME = "Input_name";
    private static  final String AMOUNT = "amount";
    private static  final String ID_AS_DAY = "id";

    //Month table
    private static  final String TABLE_MONTH = "month_table";
    private static  final String MONTH_ID= "mid";
    private static  final String MONTH_DATE = "date_input";
    private static  final String MONTH_AMOUNT = "m_amount";

    //Year table
    private static  final String TABLE_YEAR = "year_table";
    private static  final String YEAR_ID= "yid";
    private static  final String YEAR_NAME = "year_name_input";
    private static  final String YEAR_AMOUNT = "year_amount";


    private static  final int VERSOIN_NUMBER = 1;




    private static  final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+ID_AS_DAY+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ENTER_TEXT_NAME+" VARCHAR(30),"+AMOUNT+" INTEGER);";
    private static  final String CREATE_MONTH = "CREATE TABLE "+TABLE_MONTH+" ("+MONTH_ID+" INTEGER PRIMARY KEY,"+MONTH_DATE+" VARCHAR(30),"+MONTH_AMOUNT+" INTEGER);";
    private static  final String CREATE_YEAR = "CREATE TABLE "+TABLE_YEAR+" ("+YEAR_ID+" INTEGER PRIMARY KEY,"+YEAR_NAME+" VARCHAR(30),"+YEAR_AMOUNT+" INTEGER);";

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSOIN_NUMBER);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            sqLiteDatabase.execSQL(CREATE_MONTH);
            sqLiteDatabase.execSQL(CREATE_YEAR);
        }
        catch (Exception e){
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{
            Toast.makeText(context,"Upgrade: ",Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MONTH);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_YEAR);

            onCreate(sqLiteDatabase);

        }
        catch (Exception e){
        }

    }

    public  long saveData(String textName, String textValue){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENTER_TEXT_NAME,textName);
        contentValues.put(AMOUNT,textValue);
        long rowNumber = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowNumber;
    }

    public Cursor showAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
    public Boolean updateData(String textIndex, String textName, String textValue) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_AS_DAY,textIndex);
        contentValues.put(ENTER_TEXT_NAME,textName);
        contentValues.put(AMOUNT,textValue);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID_AS_DAY+" = ?",new String[]{textIndex});
        return true;
    }


    public int deleteData(int res) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_NAME,ID_AS_DAY+" = ?", new String[]{String.valueOf(res)});
        return result;
    }


    public Cursor showAllMonth() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_MONTH,null);
        return cursor;
    }

    public long saveMonth(String saveA, String td, String mSum) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONTH_ID,saveA);
        contentValues.put(MONTH_DATE,td);
        contentValues.put(MONTH_AMOUNT,mSum);
        long rowNumb = sqLiteDatabase.insert(TABLE_MONTH,null,contentValues);
        return rowNumb;

    }

    public Boolean updateMonth(String daa, String cdate, String sumMonth) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONTH_ID,daa);
        contentValues.put(MONTH_DATE,cdate);
        contentValues.put(MONTH_AMOUNT,sumMonth);
        sqLiteDatabase.update(TABLE_MONTH,contentValues,MONTH_ID+" = ?",new String[]{daa});
        return true;
    }

    public int deleteMonth(int dlt) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_MONTH,MONTH_ID+" = ?", new String[]{String.valueOf(dlt)});
        return result;

    }

    public long saveYear(String yIndex, String yName, String yAmount) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(YEAR_ID,yIndex);
            contentValues.put(YEAR_NAME,yName);
            contentValues.put(YEAR_AMOUNT,yAmount);
            long rowNumY = sqLiteDatabase.insert(TABLE_YEAR,null,contentValues);
            return rowNumY;


    }

    public Cursor showAllYear() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_YEAR,null);
        return cursor;
    }

    public Boolean updateYear(String yaa, String yy) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(YEAR_ID,yaa);
        contentValues.put(YEAR_AMOUNT,yy);
        sqLiteDatabase.update(TABLE_YEAR,contentValues,YEAR_ID+" = ?",new String[]{yaa});
        return true;
    }

    public int deleteYear(int delt) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int rslt = sqLiteDatabase.delete(TABLE_YEAR,YEAR_ID+" = ?", new String[]{String.valueOf(delt)});
        return rslt;


    }
}
