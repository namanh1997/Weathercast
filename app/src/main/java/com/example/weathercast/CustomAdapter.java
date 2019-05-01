package com.example.weathercast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_listview,null);

        Thoitiet thoitiet  = arrayList.get(i);

        TextView txtDay =(TextView) convertView.findViewById(R.id.txtngaythang);
        TextView txtStatus =(TextView) convertView.findViewById(R.id.txtstatuss);
        TextView txtMaxtemp =(TextView) convertView.findViewById(R.id.txtmax);
        TextView txtMintemp =(TextView) convertView.findViewById(R.id.txtmin);
        ImageView imgStatus = (ImageView) convertView.findViewById(R.id.imgtrangthai);
        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMaxtemp.setText(thoitiet.Maxtemp+"°C");
        txtMintemp.setText(thoitiet.Mintemp+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet.Image+".png").into(imgStatus);
        return convertView;
    }
}
