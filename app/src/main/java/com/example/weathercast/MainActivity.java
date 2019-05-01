package com.example.weathercast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnxemthem, btntimkiemtrenbando;
    TextView txtThanhpho, txtQuocgia, txtTemp, txtTrangthai, txtdoam, txtcloud, txtwind, txtNgay;
    ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        String city = getIntent().getStringExtra("name");
        edtSearch.setText(city);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();// lay chuoi tu man hinh vao
                GetCurrentWeatherData(city);
            }
        });
        btnxemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
        btntimkiemtrenbando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this); //khai bao doi tuong resquest truyen vao man hinh chinh
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=27a77fcad68bf8df89e0124c586f0d30";// khai bao url cua API
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, // lay du lieu tu api ve
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Log.d("Ketqua",response);
                        //    JSONObject jsonObject = new JSONObject(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response); // khai bao doi tuong json de doc chuoi Json tu API
                            String day = jsonObject.getString("dt"); // doc du lieu ngay thang tu chuoi json
                            String name = jsonObject.getString("name");// doc du lieu ten thanh pho tu chuoi json
                            txtThanhpho.setText("Thành phố: " + name); // set du lieu ra ngoai man hinh
                            long l = Long.valueOf(day); // doc du lieu ngay thang
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");// dinh dang kieu ngay thang
                            String Day = simpleDateFormat.format(date); //doc lai du lieu sau khi dinh dang
                            txtNgay.setText(Day); // set du lieu ra ngoai man hinh
                            JSONArray jsonArrayweather = jsonObject.getJSONArray("weather"); // doc dieu lieu thoi tiet ve tu chuoi json
                            JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                            String status = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");// lay du lieu cua icon tu json
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon); // chuyen thanh dinh dang png
                            txtTrangthai.setText(status); // chuyen icon ra ngoai man hinh
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp"); // doc du lieu nhiet do tu json
                            String doam = jsonObjectMain.getString("humidity");// doc du lieu do am tu json

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue()); // chuyen dinh dang du lieu
                            txtTemp.setText(Nhietdo + "°C");
                            txtdoam.setText(doam + "%");

                            JSONObject jsonObjectwind = jsonObject.getJSONObject("wind"); // doc toc do gio
                            String gio = jsonObjectwind.getString("speed");
                            txtwind.setText(gio + "m/s");

                            JSONObject jsonObjectclouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectclouds.getString("all");
                            txtcloud.setText(may + "%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String quocgia = jsonObjectSys.getString("country");
                            txtQuocgia.setText("Quốc gia: " + quocgia);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Không lấy được dữ liệu thời tiết",
                                    Toast.LENGTH_SHORT).show();
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

    private void Anhxa() {
        edtSearch = (EditText) findViewById(R.id.edtSearch1);
        btnSearch = (Button) findViewById(R.id.btnSearch1);
        btnxemthem = (Button) findViewById(R.id.btnxemthem1);
        btntimkiemtrenbando = (Button) findViewById(R.id.btntimkiemtrenbando);
        txtThanhpho = (TextView) findViewById(R.id.txtcity1);
        txtQuocgia = (TextView) findViewById(R.id.txtstate1);
        txtTemp = (TextView) findViewById(R.id.txtTemp1);
        txtTrangthai = (TextView) findViewById(R.id.txtTrangthai1);
        txtdoam = (TextView) findViewById(R.id.txtdoam);
        txtcloud = (TextView) findViewById(R.id.txtclound);
        txtwind = (TextView) findViewById(R.id.txtwind);
        txtNgay = (TextView) findViewById(R.id.txtNgay1);
        imgIcon = (ImageView) findViewById(R.id.imageIcon);
    }
}
