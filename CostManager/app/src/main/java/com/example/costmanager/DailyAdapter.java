package com.example.costmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DailyAdapter extends BaseAdapter {


    ArrayList<String> txtList= new ArrayList<String>();
    ArrayList<String> numbList= new ArrayList<String>();

    Context context;
    public LayoutInflater inflater;

    TextView textView1,textView2;

    DailyAdapter(Context context, ArrayList txtList, ArrayList numbList ){
        this.context=  context;
        this.txtList = txtList;
        this.numbList = numbList;
    }

    @Override
    public int getCount() {
        return txtList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.daily_list,viewGroup,false);
        }

        textView1 = view.findViewById(R.id.dailyViewText);
        textView2 = view.findViewById(R.id.dailyViewAmount);

        textView1.setText(txtList.get(i));
        textView2.setText(numbList.get(i));
        return view;
    }
}
