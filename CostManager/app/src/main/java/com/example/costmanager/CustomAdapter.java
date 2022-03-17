package com.example.costmanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

     String[] nAll;
     String[] txt;
    Context context;
    public LayoutInflater inflater;

    TextView textView1,textView2;

    CustomAdapter(Context context, String[] txt, String[] nAll ){
        this.context=  context;
        this.txt = txt;
        this.nAll = nAll;
    }


    @Override
    public int getCount() {
        return txt.length;
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
           view = inflater.inflate(R.layout.samplelist,viewGroup,false);
        }

       textView1 = view.findViewById(R.id.textshow);
       textView2 = view.findViewById(R.id.numbershow);

        textView1.setText(txt[i]);
       textView2.setText(nAll[i]);
        return view;
    }
}
