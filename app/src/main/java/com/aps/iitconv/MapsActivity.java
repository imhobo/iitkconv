package com.aps.iitconv;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends MainActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_maps, frameLayout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        IconGenerator iconFactory = new IconGenerator(this);


        Map<LatLng,String> places = new HashMap<>();

        places.put(new LatLng(26.507527, 80.234566),"Visitor's Hostel");
        places.put(new LatLng(26.512941, 80.235817),"Auditorium");
        places.put(new LatLng(26.510820, 80.234165),"LH 18,19,20");
        places.put(new LatLng(26.505257, 80.233660),"Health Centre");
        places.put(new LatLng(26.510784, 80.246734),"IIT Gate");
        places.put(new LatLng(26.505290, 80.227989),"Hall 8");
        places.put(new LatLng(26.505717, 80.226873),"Hall 10,11");


        for (Map.Entry<LatLng, String> pair : places.entrySet())
            addIcon(iconFactory,pair.getValue(),pair.getKey());


        LatLng iitk = new LatLng(26.512439, 80.232882);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(iitk).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

    }

    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        iconFactory.setColor(Color.YELLOW);
        iconFactory.setTextAppearance(R.style.iconGenText);


        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        mMap.addMarker(markerOptions);
    }
}