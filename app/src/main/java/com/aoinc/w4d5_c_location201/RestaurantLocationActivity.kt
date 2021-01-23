package com.aoinc.w4d5_c_location201

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RestaurantLocationActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener
{
    private lateinit var mMap: GoogleMap

    private lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_location)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // HACK: we're skipping the permissions request work and enabling permissions in the emulator
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 6f, this)
    }

    override fun onStop() {
        super.onStop()

        // disconnect update process, avoid memory leaks
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        Log.d("TAG_X", "${location.latitude}, ${location.longitude}")

        // check if lateinit var is initialized before setting location
        if (this::mMap.isInitialized) {
            val curLocation = LatLng(location.latitude, location.longitude)
            mMap.addMarker(MarkerOptions().position(curLocation).title("My Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 15f))
        }
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}