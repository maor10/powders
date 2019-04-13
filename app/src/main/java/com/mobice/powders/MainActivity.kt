package com.mobice.powders

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style

class MainActivity : AppCompatActivity(), PermissionsListener, OnMapReadyCallback {

    private var mapView: MapView? = null
    private var permissionsManager: PermissionsManager = PermissionsManager(this)
    private lateinit var mapboxMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(
            getApplicationContext(),
            "pk.eyJ1IjoibWFvcjEwIiwiYSI6ImNqdWUwdnJmZjBjaWUzeW15NXFmN25wdmkifQ.DnKzmVZ9RZ2X78CjC-DYGQ"
        )

        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap

        mapboxMap.setStyle(Style.MAPBOX_STREETS)
        {
            enableLocationComponent(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(loadedMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // There are permisttions :)

            val options = LocationComponentOptions.builder(this)
                .trackingGesturesManagement(true)
                .accuracyColor(ContextCompat.getColor(this, R.color.colorLocationArea))
                .build()

            val locationComponent = mapboxMap.locationComponent

            locationComponent.activateLocationComponent(this, loadedMapStyle)
            locationComponent.applyStyle(options)

            // Enable to make component visible
            locationComponent.isLocationComponentEnabled = true

            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS


        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
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
    }

    override fun onLowMemory() {
        super.onLowMemory();
        mapView?.onLowMemory();
    }

    override fun onDestroy() {
        super.onDestroy();
        mapView?.onDestroy();
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        // TODO
        //wat is dis
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationComponent(mapboxMap.style!!)
        } else {
            // TODO CHANGE TO STRING CONST
            Toast.makeText(this, R.string.user_location_not_granted, Toast.LENGTH_LONG).show()
            finish()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}