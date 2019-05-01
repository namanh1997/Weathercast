package com.example.weathercast;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    ImageView imgback;
    TextView txtname;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> mangthoitiet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ket qua", "Du lieu truyen qua" + city);
        Get7DaysData(city);
        Anhxa();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Anhxa() {
        imgback = (ImageView) findViewById(R.id.imgback);
        txtname = (TextView) findViewById(R.id.txttenthanhpho);
        lv = (ListView) findViewById(R.id.listview);
        mangthoitiet = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(Main2Activity.this, mangthoitiet);
        lv.setAdapter(customAdapter);


    }

    private void Get7DaysData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=7&appid=27a77fcad68bf8df89e0124c586f0d30";
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtname.setText(name);


                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");

                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");

                                long l = Long.valueOf(ngay); // doc du lieu ngay thang
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd  HH-mm-ss");// dinh dang kieu ngay thang
                                String Day = simpleDateFormat.format(date); //doc lai du lieu sau khi dinh dang

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_max");
                                String min = jsonObjectTemp.getString("temp_min");

                                Double aDouble = Double.valueOf(max);
                                Double bDouble = Double.valueOf(min);
                                String Nhietdomax = String.valueOf(aDouble.intValue());
                                String Nhietdomin = String.valueOf(bDouble.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                mangthoitiet.add(new Thoitiet(Day, status, icon, Nhietdomax, Nhietdomin));


                            }

                            customAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);


    }

}
