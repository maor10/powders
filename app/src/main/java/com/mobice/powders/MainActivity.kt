package com.mobice.powders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(getApplicationContext(),
            "pk.eyJ1IjoibWFvcjEwIiwiYSI6ImNqdWUwdnJmZjBjaWUzeW15NXFmN25wdmkifQ.DnKzmVZ9RZ2X78CjC-DYGQ")

        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapView);
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync {
            mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }


    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    };

    override fun onLowMemory() {
        super.onLowMemory();
        mapView?.onLowMemory();
    };

    override fun onDestroy() {
        super.onDestroy();
        mapView?.onDestroy();
    };
}
