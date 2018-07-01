package com.example.drakinosh.drpanda;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.example.drakinosh.drpanda.R;

public class TabFragment3 extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap mGoogleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View rootView =  inflater.inflate(R.layout.tab_fragment3, container,false);

        //create mapview from xml layout
        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(saveInstanceState);
        mapView.getMapAsync(this);


        //initialize googleMap from mapView


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap =  googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(27.695203, 85.311265)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(27.695203, 85.311265), 10));

        // add marker

    }

    @Override
    public void onResume() {

        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
        mapView.onPause();

    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}

