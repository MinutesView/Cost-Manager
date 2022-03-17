package com.example.costmanager;

        import android.content.Intent;
        import android.database.Cursor;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class Yearly extends AppCompatActivity {


    int sum =0;
    ArrayList<String> listData = new ArrayList<>();
    ArrayList<String> dataSum = new ArrayList<>();
    ArrayList<String> indexNum = new ArrayList<>();


    ListView listView;
    TextView textView,textView2;
    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly);



        listView = findViewById(R.id.dailyList);
        textView = findViewById(R.id.totalSum);
        databaseHelper = new DatabaseHelper(this);

        loadYear();

    }

    public void loadYear(){

        Cursor cursor = databaseHelper.showAllYear();
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
                for(int i=0; i<= dataSum.size(); i++){
                    int s = Integer.parseInt(String.valueOf(dataSum.get(i)));
                    sum = sum+s;
                }
            }
            catch (Exception e){
            }

        }

        textView.setText(String.valueOf(sum));


        DailyAdapter adapter = new DailyAdapter(Yearly.this, listData, dataSum);
        listView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
