package com.example.ibeacon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class extend_PopularActivity extends BaseAdapter {
   static LayoutInflater inflater=null;
   int icons[];

    Context context;

    public extend_PopularActivity(Context context, int icons[]){
        this.context=context;
        this.icons=icons;

    }

    @Override
    public int getCount() {
        return 0;

    }

    @Override
    public Object getItem(int i) {
        return getItemId(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row= view;
        if(row==null){
            inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.activity_populor_listview,null);

        }
        ImageView imageView = (ImageView)

        row.findViewById(R.id.imageView);
        imageView.setImageResource(icons[i]);
        //ImageView img=(ImageView) row.findViewById(R.id.imageView);
        //img.setImageResource(icons[i]);
        Log.d("'msg","i="+i);
        return row;
    }
}
