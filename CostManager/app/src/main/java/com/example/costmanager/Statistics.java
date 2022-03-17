package com.example.costmanager;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {

    PieChart chart;
    BarChart barChart;

    int i;

    ArrayList<String> barName = new ArrayList<>();
    Float BarData[] =new Float[31];

    String name[] =new String[12];
    Float data[] =new Float[12];

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        databaseHelper = new DatabaseHelper(this);
        chart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.myBarChart);

        loadMonth();
        loadYear();
        motnhChart();
        yearChart();

    }

    public void loadMonth(){

        Cursor cursor = databaseHelper.showAllMonth();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            i=0;
            while(cursor.moveToNext()){
                barName.add(cursor.getString(1));
                BarData[i] =Float.parseFloat(cursor.getString(2));
                i++;
            }
        }

    }

    public void loadYear(){

        Cursor cursor = databaseHelper.showAllYear();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No data is found",Toast.LENGTH_SHORT).show();
        }
        else {
            i=0;
            while(cursor.moveToNext()){

                name[i] = cursor.getString(1);
                data[i] =Float.parseFloat(cursor.getString(2));
                i++;
            }
            }
        }

    private void motnhChart() {

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(i=0;i<barName.size();i++){
            barEntries.add(new BarEntry(i+1,BarData[i]));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"barMonth");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData mydata = new BarData(barDataSet);
        mydata.setValueTextSize(8f);
        mydata.setBarWidth(1f);

        barChart.setData(mydata);
        barChart.setTouchEnabled(true);
        barChart.setDrawGridBackground(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getDescription().setEnabled(false);
        Description description = new Description();
        description.setText("Full Month Chart");
        description.setTextSize(20);
        description.setTextColor(Color.MAGENTA);
        barChart.invalidate();


    }

    private void yearChart() {

           List<PieEntry> pieEntries = new ArrayList<>();
            for(i =0;i<name.length;i++){

                if(data[i]==0){
                    continue;
                }
               pieEntries.add(new PieEntry(data[i],name[i]));
            }

            PieDataSet dataSet = new PieDataSet(pieEntries,"yearChart");
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            dataSet.setSliceSpace(5f);
            dataSet.setSelectionShift(2f);

            PieData data = new PieData(dataSet);
            data.setValueTextSize(12f);

            chart.setData(data);
            chart.setUsePercentValues(false);
            chart.getDescription().setEnabled(false);


            chart.setCenterText("Year Chart");
            chart.setCenterTextSize(20);
            chart.setCenterTextColor(Color.MAGENTA);
            chart.setExtraOffsets(5,10,5,5);
            chart.animateY(1000);
            chart.invalidate();



    }
}
