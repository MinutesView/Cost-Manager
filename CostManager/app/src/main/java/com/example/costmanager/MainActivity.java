package com.example.costmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  DatabaseHelper databaseHelper;

    TextView textView1,textView2,income;
    GridLayout gridLayout;


    ArrayList<String> dataSum = new ArrayList<>();
    ArrayList<String> dataSumd = new ArrayList<>();
    int e,sumM =0,sumd,i,idy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


        textView1 = findViewById(R.id.daily);
        textView2 = findViewById(R.id.monthly);
        gridLayout = findViewById(R.id.gridId);
        income = findViewById(R.id.incomeshow);

        loadData();
        loadMonth();
        if(SharePmonth()!=0){

            income.setText(String.valueOf(SharePmonth()));
        }

        setSingleEvent(gridLayout);


        setSupportActionBar(toolbar);
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==fab){
                    Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
                    startActivity(intent);
                }

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void loadData(){

        Cursor cursor = databaseHelper.showAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                dataSumd.add(cursor.getString(2));
            }
            try {
                for(idy=0; idy<= dataSumd.size(); idy++){
                    int s = Integer.parseInt(String.valueOf(dataSumd.get(idy)));
                    sumd = sumd+s;
                }
            }
            catch (Exception e){
            }

        }

        textView1.setText(String.valueOf(sumd));

    }

    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){

                dataSum.add(cursor.getString(2));
            }

            try {
                for(i=0; i<= dataSum.size(); i++){
                    int s = Integer.parseInt(String.valueOf(dataSum.get(i)));
                    sumM = sumM+s;
                }
            }
            catch (Exception e){
            }

        }

        textView2.setText(String.valueOf(sumM));


    }
    public int SharePmonth(){
        SharedPreferences sharedPreferences = getSharedPreferences("incomeTotal", Context.MODE_PRIVATE);
        int Sum = sharedPreferences.getInt("income",0);
        return Sum;
    }


    private void setSingleEvent(GridLayout gridLayout) {
        for(e=0; e<gridLayout.getChildCount();e++){

            CardView cardView = (CardView) gridLayout.getChildAt(e);

            final int finalI = e;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI == 0){
                        Intent intent = new Intent(MainActivity.this, dailyone.class);
                        startActivity(intent);
                    }

                    else if(finalI == 1){
                        Intent intent = new Intent(MainActivity.this, Monthly.class);
                        startActivity(intent);
                    }
                    else if(finalI == 2){
                        Intent intent = new Intent(MainActivity.this, Yearly.class);
                        startActivity(intent);
                    }

                    else if(finalI == 3){
                        Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI == 4){
                        Intent intent = new Intent(MainActivity.this, Statistics.class);
                        startActivity(intent);
                    }
                    else if(finalI == 5){
                        Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI == 6){
                        Intent intent = new Intent(MainActivity.this, SaveTipsActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI == 7){
                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(MainActivity.this, "Please set mainActivity click",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

    boolean twice = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(twice == true){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
            twice = true;
            Toast.makeText(getApplicationContext(),"Please press BACK again to exit",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    twice = false;
                }
            },3000);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, NeedHelp.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_expense) {
            Intent intent = new Intent(MainActivity.this, Monthly.class);
            startActivity(intent);
        } else if (id == R.id.nav_income) {
            Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Statistics) {
            Intent intent = new Intent(MainActivity.this, Statistics.class);
            startActivity(intent);
        } else if (id == R.id.nav_tips) {
            Intent intent = new Intent(MainActivity.this, SaveTipsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_calculate) {
            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_note) {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_help) {
            Intent intent = new Intent(MainActivity.this, NeedHelp.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
