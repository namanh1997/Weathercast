
package com.example.weathercast;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity {

    TextView txtLocationAddress;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    Button btnChon;
    String location;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        btnChon = findViewById(R.id.btnChon);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {
                        if (marker != null) {
                            map.clear();
                        }
                        location = getAddressFromLocation(point.latitude, point.longitude);
                        marker = map.addMarker(new MarkerOptions()
                                .position(new LatLng(point.latitude, point.longitude))
                                .title(location));
                        marker.showInfoWindow();
                    }
                });
                map.getUiSettings().setZoomControlsEnabled(true);
                LatLng latLng = new LatLng(21, 105.75);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
//                initCameraIdle();
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                location = getCity(location);
                intent.putExtra("name", location);
                startActivity(intent);
            }
        });
    }

    private String getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        String city = null;
        String address = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                address = fetchedAddress.getAddressLine(0);
                city = fetchedAddress.getLocality();
                System.out.println(address);

            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private String getCity(String address) {
        String[] list = address.split(",");
        String city = list[list.length-2].trim();
        System.out.println(city);
        return city;
    }

}
